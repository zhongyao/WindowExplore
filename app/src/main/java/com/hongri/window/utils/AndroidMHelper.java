package com.hongri.window.utils;

import java.lang.reflect.Field;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

/**
 *
 * @author zhongyao
 * @date 2018/1/6
 */

public class AndroidMHelper {

    public boolean setStatusBarLightMode(Activity activity, boolean isDarkMode) {

        //如果是6.0及以上Android系统
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            //7.0及以上系统将状态栏改为透明的方式
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明

                } catch (Exception e) {}
            }
            if (isDarkMode) {


                //沉浸式
                activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                //非沉浸式

                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //非沉浸式
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }

            return true;
        }
        return false;
    }
}
