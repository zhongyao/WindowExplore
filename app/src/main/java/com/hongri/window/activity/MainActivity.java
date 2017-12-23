package com.hongri.window.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hongri.window.R;
import com.hongri.window.dialog.PanelDialog;

/**
 * @author hongri
 *         Android所有的视图都是通过Window来实现的，不管是Activity，Dialog，还是Toast
 *         所以Window是View的直接管理者。
 */
public class MainActivity extends Activity implements OnClickListener {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btnDialog)
    Button btnDialog;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialog:
                PanelDialog dialog = new PanelDialog(this, 1);
                dialog.show();
                break;
            default:
                break;
        }
    }
}
