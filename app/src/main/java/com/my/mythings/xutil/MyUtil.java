package com.my.mythings.xutil;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.my.mythings.model.Thing;
import com.my.mythings.model.Things;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author 文琳
 * @time 2020/6/17 9:51
 * @desc 封装一些常用的方法
 */
public class MyUtil {
    private static final String DATA = "DATA";

    public static void setHelper(RecyclerView rv, Items items, MultiTypeAdapter adapter) {
        //得到拖动ViewHolder的position
        //得到目标ViewHolder的position
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(items, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(items, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder != null)
                        viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && items != null && items.size() > 0) {
                    List<Thing> list = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        Object o = items.get(i);
                        if (o instanceof Thing) {
                            Thing t = (Thing) o;
                            t.setPosition(i);
                            list.add(t);
                        }
                    }
                    save(list);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        helper.attachToRecyclerView(rv);
    }

    /**
     * 保存数据
     *
     * @param list 物品们
     */
    public static void save(List<Thing> list) {
        SPUtils.getInstance().put(DATA, new Gson().toJson(new Things(list), Things.class));
    }

    /**
     * 获取物品列表
     *
     * @return 所求
     */
    public static List<Thing> getThingList() {
        List<Thing> list = new ArrayList<>();
        String thingsStr = SPUtils.getInstance().getString(DATA);
        if (thingsStr == null || thingsStr.length() <= 0) return list;
        Things things = new Gson().fromJson(thingsStr, Things.class);
        if (things == null) return list;
        return things.getList();
    }

    /**
     * 获取解析后的物品和价格所组成的数组
     *
     * @param str 输入的字符串
     * @return 所求
     */
    public static String[] getNameAndPriceByRegex(String str) {
        String[] arr = new String[2];
        Pattern pattern = Pattern.compile("-?\\d*\\.?\\d*$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {//matches()方法是全局匹配，find()是部分匹配，这里用任意一种都行
            arr[0] = str.replace(matcher.group(), "");
            arr[1] = matcher.group();
        }
        if (arr[1].contains(".")){//当有小数点时，将末尾的0全部清除，然后再判断尾部是否是小数点，如果是就清除
            arr[1] = arr[1].replaceAll("0+?$", "");//去掉多余的0
            arr[1] = arr[1].replaceAll("[.]$", "");
        }
        return arr;
    }

    /**
     * 获取解析后的物品和价格所组成的数组
     *
     * @param str 输入的字符串
     * @return 所求
     */
    public static String[] getNameAndPrice(String str) {
        String[] arr = new String[2];
        String priceStr = "";
        int dotNum = 0;//防止出现“1.8.1”这类出现，所以小数点上限为1
        for (int i = str.length() - 1; i >= 0; i--) {
            String s = str.substring(i, i + 1);
            if (s.matches("\\.|\\d")) {
                if (s.equals("."))
                    dotNum++;
                if (dotNum > 1)
                    break;
                priceStr = s + priceStr;
            } else {
                break;
            }
        }
        int index = -1;
        if (priceStr.length() > 0) {
            index = str.indexOf(priceStr);
        }
        if (index != -1) {
            str = str.substring(0, index);//直接裁剪掉价格只剩名字，如果没有价格，则取原字符串
        }
        arr[0] = str;
        arr[1] = priceStr;
        return arr;
    }

    /**
     * 设置总价值文本
     *
     * @param tvTotal   文本控件
     * @param items     列表
     * @param adapter   适配器
     * @param list      物品集合
     * @param preString 前缀字符串
     */
    public static void setTotalText(TextView tvTotal, Items items, MultiTypeAdapter adapter, List<Thing> list, String preString) {
        if (tvTotal == null) return;
        float total = 0;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPosition(list.size() - 1 - i);
            if (list.get(i).getPrice().length() > 0) {
                try {
                    total += Float.parseFloat(list.get(i).getPrice()) * 100;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (items.size() > 0) {
            items.clear();
            adapter.notifyDataSetChanged();
        }
        items.addAll(list);
        adapter.notifyDataSetChanged();
        String str = preString + "：" + total / 100;
        if (str.endsWith(".00")) {
            str = str.replaceAll(".00", "");
        } else if (str.endsWith(".0")) {
            str = str.replaceAll(".0", "");
        }
        tvTotal.setText(str);
    }
}
