package com.my.mythings.xutil;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.my.mythings.MyApplication;
import com.my.mythings.R;

/**
 * 带背景颜色和图标的简单单例吐司提示，当新的提示出现时旧的提示当即被替换
 */
public class ToastUtils {

    private static Toast toast;
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static ToastUtils toastUtils;

    //单例
    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (toastUtils == null) {
            toastUtils = new ToastUtils();
        }
        return toastUtils;
    }

    public void showToast(int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showToast(null, str);
    }

    public void showInfo(int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showInfo(null, str);
    }

    public void showSuccess(int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showSuccess(null, str);
    }

    public void showError(int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showError(null, str);
    }

    public void showWarning(int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showWarning(null, str);
    }

    public void showToast(String str) {
        showToast(null, str);
    }

    public void showInfo(String str) {
        showInfo(null, str);
    }

    public void showSuccess(String str) {
        showSuccess(null, str);
    }

    public void showError(String str) {
        showError(null, str);
    }

    public void showWarning(String str) {
        showWarning(null, str);
    }

    public void showToast(ViewGroup parent, int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showToast(null, str);
    }

    public void showInfo(ViewGroup parent, int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showInfo(parent, str);
    }

    public void showSuccess(ViewGroup parent, int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showSuccess(parent, str);
    }

    public void showError(ViewGroup parent, int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showError(parent, str);
    }

    public void showWarning(ViewGroup parent, int strId) {
        String str = MyApplication.context.getResources().getString(strId);
        showWarning(parent, str);
    }

    /**
     * 显示普通的吐司提示
     *
     * @param parent 父控件
     * @param text   显示文本
     */
    public void showToast(ViewGroup parent, String text) {
        handler.post(() -> {
            if (Checker.notNull(toast))
                toast = null;
            toast = new Toast(MyApplication.context);
            View view = getView(parent);
            toast.setView(view);
            toast.getView().setBackgroundResource(R.drawable.toast_bg_round_black);
            TextView tv = toast.getView().findViewById(R.id.tv_toast);
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.myWhite));
            tv.setTextSize(16f);
            ImageView iv = toast.getView().findViewById(R.id.iv_toast);
            iv.setVisibility(View.GONE);
            toast.show();
        });
    }

    /**
     * 显示普通的吐司提示
     *
     * @param parent 父控件
     * @param text   显示文本
     */
    public void showInfo(ViewGroup parent, String text) {
        handler.post(() -> {
            if (Checker.notNull(toast))
                toast = null;
            toast = new Toast(MyApplication.context);
            View view = getView(parent);
            toast.setView(view);
            toast.getView().setBackgroundResource(R.drawable.toast_bg_round_black);
            TextView tv = toast.getView().findViewById(R.id.tv_toast);
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.myWhite));
            tv.setTextSize(16f);
            ImageView iv = toast.getView().findViewById(R.id.iv_toast);
            iv.setImageResource(R.mipmap.common_sign_toast_info);
            iv.setVisibility(View.VISIBLE);
            toast.show();
        });
    }

    /**
     * 显示成功的吐司提示
     *
     * @param parent 父控件
     * @param text   显示文本
     */
    public void showSuccess(ViewGroup parent, String text) {
        handler.post(() -> {
            if (Checker.notNull(toast))
                toast = null;
            toast = new Toast(MyApplication.context);
            View view = getView(parent);
            toast.setView(view);
            toast.getView().setBackgroundResource(R.drawable.toast_bg_round_green);
            TextView tv = toast.getView().findViewById(R.id.tv_toast);
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.myWhite));
            tv.setTextSize(16f);
            ImageView iv = toast.getView().findViewById(R.id.iv_toast);
            iv.setImageResource(R.mipmap.common_sign_toast_success);
            iv.setVisibility(View.VISIBLE);
            toast.show();
        });
    }

    /**
     * 显示错误的吐司提示
     *
     * @param parent 父控件
     * @param text   显示文本
     */
    public void showError(ViewGroup parent, String text) {
        handler.post(() -> {
            if (Checker.notNull(toast))
                toast = null;
            toast = new Toast(MyApplication.context);
            View view = getView(parent);
            toast.setView(view);
            toast.getView().setBackgroundResource(R.drawable.toast_bg_round_red);
            TextView tv = toast.getView().findViewById(R.id.tv_toast);
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.myWhite));
            tv.setTextSize(16f);
            ImageView iv = toast.getView().findViewById(R.id.iv_toast);
            iv.setImageResource(R.mipmap.common_sign_toast_error);
            iv.setVisibility(View.VISIBLE);
            toast.show();
        });
    }

    /**
     * 显示错误的吐司提示
     *
     * @param parent 父控件
     * @param text   显示文本
     */
    public void showWarning(ViewGroup parent, String text) {
        handler.post(() -> {
            if (Checker.notNull(toast))
                toast = null;
            toast = new Toast(MyApplication.context);
            View view = getView(parent);
            toast.setView(view);
            toast.getView().setBackgroundResource(R.drawable.toast_bg_round_yellow);
            TextView tv = toast.getView().findViewById(R.id.tv_toast);
            tv.setText(text);
            tv.setTextColor(ContextCompat.getColor(MyApplication.context, R.color.myWhite));
            tv.setTextSize(16f);
            ImageView iv = toast.getView().findViewById(R.id.iv_toast);
            iv.setImageResource(R.mipmap.common_sign_toast_warning);
            iv.setVisibility(View.VISIBLE);
            toast.show();
        });
    }

    private static View getView(ViewGroup parent) {
        View view;
        if (Checker.isNull(parent)) {
            view = LayoutInflater.from(MyApplication.context).inflate(R.layout.common_toast_layout, null);
        } else {
            view = LayoutInflater.from(MyApplication.context).inflate(R.layout.common_toast_layout, parent, false);
        }
        return view;
    }
}