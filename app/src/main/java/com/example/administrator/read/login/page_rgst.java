package com.example.administrator.read.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.read.R;
import com.example.administrator.read.internet.internet;

import org.json.JSONException;
import org.json.JSONObject;

public class page_rgst extends AppCompatActivity {

    private TimeCount tc_rgst_get;
    private Button bt_rgst_get;
    private Button bt_rgst_rgst;
    private TextView tv_rgst_golog;
    private TextView phone_number;
    private TextView test_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_rgst1);

        tc_rgst_get = new TimeCount(60000, 1000);
        bt_rgst_get = (Button) findViewById(R.id.rgst_bt_get);
        bt_rgst_rgst = (Button) findViewById(R.id.rgst_bt_rgst);
        tv_rgst_golog = (TextView)findViewById(R.id.rgst_tv_golog);
        phone_number=(TextView) findViewById(R.id.rgst_et_tel);
        test_code=(TextView) findViewById(R.id.rgst_et_code);
        bt_rgst_get.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tc_rgst_get.start();
            }
        });

        bt_rgst_rgst.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlStr="http://122.114.237.201/usercontrol/check";
                final String phone=phone_number.getText().toString().trim();
                final String code=test_code.getText().toString().trim();
                if("".equals(phone)||"".equals(code))
                {
                    Toast.makeText(page_rgst.this,"电话和验证码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    new Thread() {
                        @Override
                        public void run() {
                            String result = internet.checkuser(urlStr,phone, code);
                            System.out.println(result);
                            try {
                                JSONObject result_json = new JSONObject(result);
                                String message = result_json.getString("message:");
                                if ("success".equals(message)) {
                                    Intent itnt_rgst_rgst = new Intent(page_rgst.this,page_rgst2.class);
                                    itnt_rgst_rgst.putExtra("user_id",result_json.getInt("userID"));
                                    startActivity(itnt_rgst_rgst);
                                    finish();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(page_rgst.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.toString());
                            }
                        }
                    }.start();
                }
            }
        });

        tv_rgst_golog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itnt_rgst_golog = new Intent(page_rgst.this,page_login.class);
                startActivity(itnt_rgst_golog);
                finish();
            }
        });
    }

    class TimeCount extends CountDownTimer{
        public TimeCount(long milliInFuture, long countDownInterval){
            super(milliInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            bt_rgst_get.setBackgroundResource(R.drawable.lgi_bt_count);
            bt_rgst_get.setClickable(false);
            bt_rgst_get.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish(){
            bt_rgst_get.setText("重新获取");
            bt_rgst_get.setClickable(true);
            bt_rgst_get.setBackgroundResource(R.drawable.lgi_bt_normal);
        }
    }



}
