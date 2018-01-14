package com.hongri.window.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.hongri.window.R;
import com.hongri.window.utils.Util;

/**
 * @author zhongyao
 * @date 2017/12/22
 */

public class PanelDialog extends Dialog {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ll)
    LinearLayout ll;

    public PanelDialog(Context context, int themeResId) {
        super(context, R.style.RightDialog);

        initView(context);
    }

    private void initView(Context context) {
        //去除标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏（全屏）
        //this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,LayoutParams.FLAG_FULLSCREEN);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
        // .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.window_dialog);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setGravity(Gravity.RIGHT);
        //this.getWindow().setLayout((int)context.getResources().getDimension(R.dimen.dialog_width), LayoutParams
        // .MATCH_PARENT);
        this.getWindow().setLayout((int)context.getResources().getDimension(R.dimen.dialog_width),
            Util.getScreenHeight(context));

        initTextColor(context);
        //getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
    }

    private void initTextColor(Context context) {
        if (tv == null) {
            tv = (TextView)findViewById(R.id.tv);
        }
        int color = parseColor("#ffffff00");
        Log.d("yao", color + "");
        //tv.setTextColor(color);
        //tv.setTextColor(context.getResources().getColor(R.color.red));
        CharSequence text = "哈哈哈哈";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(color), 0, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);

        /**
         * 注意：
         * long 是64位，int 是32位。
         * 当使用强制类型把long转换为int类型有可能会出现负数问题,
         * 而Long.MAX_VALUE在Java中的值是2的63次方,即011111111111111(63个1)
         * 最前面的是符号位 0为正数,1为负数,int截取了后面的32位数 为111111111(32个1)
         * 这个值就是-1,因为负数是用补码表示的111111111(32个1) 正好就是-1的补码,所以 最终的结果就是-1.
         * 例如：
         *  subDateFmtStr("20141229 16:21:58","20150210 11:43:28")
         *
         */
        long ll = 2147483649L;
        int dd = (int)ll;
        Toast.makeText(context, "" + dd, Toast.LENGTH_LONG).show();
    }

    private int parseColor(String colorString) {
        // Use a long to avoid rollovers on #ffXXXXXX
        long color = Long.parseLong(colorString.substring(1), 16);
        if (colorString.length() == 7) {
            // Set the alpha value
            color |= 0x00000000ff000000;
        } else if (colorString.length() != 9) {
            throw new IllegalArgumentException("Unknown color");
        }
        return (int)color;
    }
}
