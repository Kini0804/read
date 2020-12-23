package com.example.administrator.read.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.read.R;

public class page_rsetpsw extends AppCompatActivity {

    private TimeCount tc_rspsw_get;
    private ImageView iv_rspsw_bk;
    private Button bt_rspsw_sure;
    private Button bt_rspsw_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_rsetpsw);

        tc_rspsw_get = new TimeCount(60000,1000);
        iv_rspsw_bk = (ImageView) findViewById(R.id.rspsw_iv_bk);
        bt_rspsw_sure = (Button) findViewById(R.id.rspsw_bt_sure);
        bt_rspsw_get = (Button) findViewById(R.id.rspsw_bt_get);

        bt_rspsw_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tc_rspsw_get.start();
            }
        });

        iv_rspsw_bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itnt_rspsw_bk = new Intent(page_rsetpsw.this,page_login.class);
                startActivity(itnt_rspsw_bk);
            }
        });
        bt_rspsw_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itnt_rspsw_sure = new Intent(page_rsetpsw.this,page_rsetpsw2.class);
                startActivity(itnt_rspsw_sure);
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long milliInFuture, long countDownInterval){
            super(milliInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            bt_rspsw_get.setBackgroundResource(R.drawable.lgi_bt_count);
            bt_rspsw_get.setClickable(false);
            bt_rspsw_get.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish(){
            bt_rspsw_get.setText("重新获取");
            bt_rspsw_get.setClickable(true);
            bt_rspsw_get.setBackgroundResource(R.drawable.lgi_bt_normal);
        }
    }
}
