package com.hongri.window.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.Window;

/**
 * @author zhongyao
 * @date 2018/1/6
 * 小米MIUI系统
 */

public class MIUIHelper {
    /**
     * 设置状态栏字体图标为深色，需要MIUI6以上
     *
     * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        boolean result = false;
        try {
            Window window = activity.getWindow();
            Class clazz = window.getClass();
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (isFontColorDark) {
                //状态栏透明且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
