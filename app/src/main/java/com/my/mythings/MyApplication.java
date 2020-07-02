package com.my.mythings;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * @author 文琳
 * @time 2020/6/16 17:17
 * @desc
 */
public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(this);
    }
}
