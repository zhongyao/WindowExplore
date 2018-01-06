package com.hongri.window.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hongri.window.R;
import com.hongri.window.dialog.PanelDialog;
import com.hongri.window.utils.Helper;

/**
 * @author hongri
 *         Android所有的视图都是通过Window来实现的，不管是Activity，Dialog，还是Toast
 *         所以Window是View的直接管理者。
 *         View是Window存在的实体
 */
public class MainActivity extends Activity implements OnClickListener {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btnDialog)
    Button btnDialog;
    @BindView(R.id.btnAddView)
    Button btnAddView;
    @BindView(R.id.btnRemoveView)
    Button btnRemoveView;

    LinearLayout ll;
    @BindView(R.id.statusBarView)
    View statusBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏（全屏）
        //this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);

        Helper.statusBarLightMode(this);

        /**
         * 6.0及以上系统
         */
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            //将7.0及以上系统默认浅灰状态栏改成透明
            if (VERSION.SDK_INT >= VERSION_CODES.N) {
                Class decorViewClazz;
                try {
                    decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //针对MIUI系统需特殊处理
            if (Build.BRAND.equalsIgnoreCase("Xiaomi")) {
                try {
                    Window window = getWindow();
                    Class clazz = window.getClass();
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //针对Flyme系统需特殊处理
            if (Build.BRAND.equalsIgnoreCase("Meizu")) {
                Window window = getWindow();
                try {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField(
                        "MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                    Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                    darkFlag.setAccessible(true);
                    meizuFlags.setAccessible(true);
                    int bit = darkFlag.getInt(null);
                    int value = darkFlag.getInt(lp);
                    value |= bit;
                    meizuFlags.setInt(lp, value);
                    window.setAttributes(lp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //沉浸式
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_main);
        //ViewGroup contentFrameLayout = (ViewGroup)this.findViewById(android.R.id.content);
        //View parentView = contentFrameLayout.getChildAt(0);
        //if (parentView != null && Build.VERSION.SDK_INT >= 14) {
        //    parentView.setFitsSystemWindows(true);
        //}
        ButterKnife.bind(this);

        initStatusBar();

        btnDialog.setOnClickListener(this);
        btnAddView.setOnClickListener(this);
        btnRemoveView.setOnClickListener(this);
    }

    private void initStatusBar() {
        statusBarView.getLayoutParams().height = getStatusbarHeight();
        statusBarView.setVisibility(View.GONE);
    }

    /**
     * 通过反射获取状态栏高度
     */
    private int getStatusbarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnDialog:
                PanelDialog dialog = new PanelDialog(this, 1);
                dialog.show();
                break;
            case R.id.btnAddView:
                ll = new LinearLayout(this);
                ll.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
                ll.setGravity(Gravity.BOTTOM);
                ll.setBackgroundColor(Color.BLUE);
                ((ViewGroup)(this.getWindow().getDecorView())).addView(ll);
                break;
            case R.id.btnRemoveView:
                ((ViewGroup)(this.getWindow().getDecorView())).removeView(ll);
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //WindowManager.LayoutParams params = getWindow().getAttributes();
            ////全屏且不展示状态栏等
            //params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            ////全屏但展示状态栏等
            ////params.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
            //getWindow().setAttributes(params);

            //全屏（不展示状态栏等）
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //加上FLAG_FORCE_NOT_FULLSCREEN，全屏但也展示状态栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            //WindowManager.LayoutParams params = getWindow().getAttributes();
            //params.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
            //getWindow().setAttributes(params);
            //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}

