package com.hongri.window.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hongri.window.R;
import com.hongri.window.dialog.PanelDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏（全屏）
        //this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnDialog.setOnClickListener(this);
        btnAddView.setOnClickListener(this);
        btnRemoveView.setOnClickListener(this);
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
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            //WindowManager.LayoutParams params = getWindow().getAttributes();
            //params.flags |= WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN;
            //getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}

