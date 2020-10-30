package com.my.mythings.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.my.mythings.R;
import com.my.mythings.xutil.ToastUtils;

import java.util.LinkedList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoorActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView tv;

    private String keyWord;//钥匙
    private String answerWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        ButterKnife.bind(this);
        setRandomPuzzle();
    }

    /**
     * 设置题目
     */
    private void setRandomPuzzle() {
        Random random = new Random();
        LinkedList<Integer> listOri = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            listOri.add(i);
        }
        //从0-9中挑出5个数字
        LinkedList<Integer> listNew1 = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            int r = random.nextInt(listOri.size());
            listNew1.add(listOri.get(r));
            listOri.remove(r);
        }
        //用这五个数字组成一个五位数，0开头的话就是四位数了
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < listNew1.size(); i++) {
            sb1.append(listNew1.get(i));
        }
        //用取出的5个数字，头尾相互调换顺序，得到另一个五位数数，0开头的话就是四位数了
        LinkedList<Integer> listNew2 = new LinkedList<>();
        for (int i = 0; i < listNew1.size(); i++) {
            listNew2.add(0,listNew1.get(i));
        }
//        for (int i = 0; i < 5; i++) {//这个代码，随机性更高但是结果可能数字较少，不利于隐藏解法
//            int r = random.nextInt(listNew1.size());
//            listNew2.add(listNew1.get(r));
//            listNew1.remove(r);
//        }
        String str1 = sb1.toString();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < listNew2.size(); i++) {
            sb2.append(listNew2.get(i));
        }
        String str2 = sb2.toString();
//        LogUtils.e("str1=" + str1);
//        LogUtils.e("str2=" + str2);
        //得出差
        int num1 = Integer.parseInt(str1);
        int num2 = Integer.parseInt(str2);
        int delta = Math.abs(num1 - num2);
        String strDelta = String.valueOf(delta);
//        LogUtils.e("strDelta=" + strDelta);
        boolean success = false;
        //从差中取第一个数字作为钥匙，如果是9则顺延，如果差中全是9，则通过递归的方式重新获取钥匙
        for (int i = 0; i < strDelta.length(); i++) {
            String k = strDelta.substring(i, i + 1);
            if (!k.equals("9")) {
                success = true;
                keyWord = k;
//                LogUtils.e("keyWord=" + keyWord);
                String left = strDelta.substring(0, i) + strDelta.substring(i + 1);
                tv.setText(left);
                break;
            }
        }
        if (!success) setRandomPuzzle();//递归方法重新获取题目
    }

    /**
     * 核验
     */
    private void tryOpenTheDoor() {
        if (keyWord.equals(answerWord)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ToastUtils.getInstance().showError("答案错误，题目重置");
            setRandomPuzzle();
        }
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9, R.id.tv0})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                answerWord = "1";
                tryOpenTheDoor();
                break;
            case R.id.tv2:
                answerWord = "2";
                tryOpenTheDoor();
                break;
            case R.id.tv3:
                answerWord = "3";
                tryOpenTheDoor();
                break;
            case R.id.tv4:
                answerWord = "4";
                tryOpenTheDoor();
                break;
            case R.id.tv5:
                answerWord = "5";
                tryOpenTheDoor();
                break;
            case R.id.tv6:
                answerWord = "6";
                tryOpenTheDoor();
                break;
            case R.id.tv7:
                answerWord = "7";
                tryOpenTheDoor();
                break;
            case R.id.tv8:
                answerWord = "8";
                tryOpenTheDoor();
                break;
            case R.id.tv9:
                answerWord = "9";
                tryOpenTheDoor();
                break;
            case R.id.tv0:
                answerWord = "0";
                tryOpenTheDoor();
                break;
        }
    }
}
