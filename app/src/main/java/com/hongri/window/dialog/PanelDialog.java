package com.hongri.window.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import com.hongri.window.R;
import com.hongri.window.utils.Util;

/**
 *
 * @author zhongyao
 * @date 2017/12/22
 */

public class PanelDialog extends Dialog {
    public PanelDialog(Context context, int themeResId) {
        super(context, R.style.RightDialog);

        initView(context);
    }

    private void initView(Context context) {
        //去除标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏（全屏）
        //this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.window_dialog);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setGravity(Gravity.RIGHT);
        //this.getWindow().setLayout((int)context.getResources().getDimension(R.dimen.dialog_width), LayoutParams.MATCH_PARENT);
        this.getWindow().setLayout((int)context.getResources().getDimension(R.dimen.dialog_width), Util.getScreenHeight(context));



        //getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
    }
}
