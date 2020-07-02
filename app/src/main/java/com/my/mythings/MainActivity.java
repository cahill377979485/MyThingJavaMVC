package com.my.mythings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * MVC的写法
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_input)
    TextView tvInput;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.cb_search)
    AppCompatCheckBox cbSearch;

    private Items items = new Items();
    private MultiTypeAdapter adapter;
    private boolean updating;
    private int updatePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new MultiTypeAdapter(items);
        adapter.register(Thing.class, new ThingBinder(thing -> new AlertDialog.Builder(this)
                .setTitle("操作")
                .setNeutralButton("删除", (dialog, which) -> {
                    deleteThing(thing.getName());
                    dialog.dismiss();
                }).setNegativeButton("更新", (dialog, which) -> {
                    String str = thing.getName() + thing.getPrice();
                    et.setText(str);
                    updating = true;
                    updatePosition = thing.getPosition();
                    tvInput.setText("更新");
                }).setPositiveButton("取消", null)
                .create().show()));
        rv.setLayoutManager(new GridLayoutManager(this, 1));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        //监听输入内容
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cbSearch.isChecked()) {
                    List<Thing> listResult = new ArrayList<>();
                    List<Thing> list = MyUtil.getThingList();
                    for (int i = 0; i < list.size(); i++) {
                        Thing t = list.get(i);
                        if (t.getName().contains(s.toString()))
                            listResult.add(t);
                    }
                    MyUtil.setTotalText(tvTotal, items, adapter, listResult, "总价值小计");
                }
            }
        });
        MyUtil.setHelper(rv, items, adapter);
        //从本地加载数据
        refreshData();
    }

    private void refreshData() {
        List<Thing> list = MyUtil.getThingList();
        MyUtil.setTotalText(tvTotal, items, adapter, list, "总价值");
        rv.smoothScrollToPosition(0);
        rv.setItemViewCacheSize(list.size());
    }

    private void addThing(String str) {
        String[] arr = MyUtil.getNameAndPrice(str);
        List<Thing> list = MyUtil.getThingList();
        list.add(0, new Thing(0, arr[0], arr[1]));
        MyUtil.save(list);
        if (cbSearch.isChecked()) {
            cbSearch.setChecked(false);
        }
        refreshData();
        et.setText("");
        ToastUtils.getInstance().showSuccess("添加成功");
    }

    private void deleteThing(String name) {
        List<Thing> list = MyUtil.getThingList();
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                Object o = items.get(i);
                if (o instanceof Thing) {
                    Thing t = (Thing) o;
                    if (t.getName().equals(name)) {
                        list.remove(i);
                        break;
                    }
                }
            }
        }
        MyUtil.save(list);
        refreshData();
        ToastUtils.getInstance().showSuccess("删除成功");
    }

    private void updateThing(String str) {
        String[] arr = MyUtil.getNameAndPrice(str);
        List<Thing> list = MyUtil.getThingList();
        list.get(list.size() - 1 - updatePosition).setName(arr[0]);//因为是倒序所以这里要list.size-1-position
        list.get(list.size() - 1 - updatePosition).setPrice(arr[1]);
        MyUtil.save(list);
        refreshData();
        tvInput.setText("录入");
        updating = false;
        et.setText("");
        ToastUtils.getInstance().showSuccess("更新成功");
    }

    @OnClick({R.id.tv_input, R.id.cb_search})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_input: {
                String str = et.getText().toString().trim();
                if (str.length() == 0) {
                    ToastUtils.getInstance().showInfo("请先输入");
                    refreshData();
                    return;
                }
                if (updating) {
                    updateThing(str);
                } else {
                    addThing(str);
                }
                break;
            }
            case R.id.cb_search: {
                if (!cbSearch.isChecked()) {
                    ToastUtils.getInstance().showInfo("结束搜索");
                    refreshData();
                } else {
                    String str = et.getText().toString().trim();
                    if (str.length() == 0) {
                        ToastUtils.getInstance().showError("请先输入");
                        refreshData();
                        return;
                    }
                    ToastUtils.getInstance().showInfo("开始搜索");
                    et.setText(str);
                    et.setSelection(str.length());
                }
                break;
            }
            default:
        }
    }
}