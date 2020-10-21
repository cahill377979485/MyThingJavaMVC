package com.my.mythings.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
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
        LinkedList<Integer> listNew1 = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            int r = random.nextInt(listOri.size());
            listNew1.add(listOri.get(r));
            listOri.remove(r);
        }
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < listNew1.size(); i++) {
            sb1.append(listNew1.get(i));
        }
        LinkedList<Integer> listNew2 = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            int r = random.nextInt(listNew1.size());
            listNew2.add(listNew1.get(r));
            listNew1.remove(r);
        }
        String str1 = sb1.toString();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < listNew2.size(); i++) {
            sb2.append(listNew2.get(i));
        }
        String str2 = sb2.toString();
        LogUtils.e("str1=" + str1);
        LogUtils.e("str2=" + str2);
        int num1 = Integer.parseInt(str1);
        int num2 = Integer.parseInt(str2);
        int delta = Math.abs(num1 - num2);
        String strDelta = String.valueOf(delta);
        LogUtils.e("strDelta=" + strDelta);
        boolean success = false;
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
    private void check() {
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
                check();
                break;
            case R.id.tv2:
                answerWord = "2";
                check();
                break;
            case R.id.tv3:
                answerWord = "3";
                check();
                break;
            case R.id.tv4:
                answerWord = "4";
                check();
                break;
            case R.id.tv5:
                answerWord = "5";
                check();
                break;
            case R.id.tv6:
                answerWord = "6";
                check();
                break;
            case R.id.tv7:
                answerWord = "7";
                check();
                break;
            case R.id.tv8:
                answerWord = "8";
                check();
                break;
            case R.id.tv9:
                answerWord = "9";
                check();
                break;
            case R.id.tv0:
                answerWord = "0";
                check();
                break;
        }
    }
}
