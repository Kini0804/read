package com.example.administrator.read.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.read.R;
import com.example.administrator.read.internet.internet;

import org.json.JSONException;
import org.json.JSONObject;

public class page_rgst2 extends AppCompatActivity {

    private ImageView iv_rgst2_bk;
    private Button bt_rgst2_sure;
    private TextView psw;
    private TextView psw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_rgst2);
        Intent intent=getIntent();
        final int user_id=intent.getIntExtra("user_id",0);
        iv_rgst2_bk = (ImageView) findViewById(R.id.rgst2_iv_bk);
        bt_rgst2_sure = (Button) findViewById(R.id.rgst2_bt_start);
        psw=(TextView) findViewById(R.id.rgst2_et_psw);
        psw2=(TextView) findViewById(R.id.rgst2_et_rpsw);
        bt_rgst2_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlStr="http://122.114.237.201/usercontrol/setpassword";
                final String pass=psw.getText().toString().trim();
                String pass2=psw2.getText().toString().trim();
                if(pass.equals("")||pass2.equals(""))
                {
                    Toast.makeText(page_rgst2.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }
                else if(!pass.equals(pass2))
                {
                    Toast.makeText(page_rgst2.this,"两次密码应该相同！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new Thread() {
                        @Override
                        public void run() {
                            String result = internet.rsetpassword(urlStr,pass,user_id);
                            System.out.println(result);
                            try {
                                JSONObject result_json = new JSONObject(result);
                                String message = result_json.getString("message:");
                                if ("success".equals(message)) {
                                    Intent itnt_rspsw2_sure = new Intent(page_rgst2.this,page_login.class);
                                    startActivity(itnt_rspsw2_sure);
                                    finish();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(page_rgst2.this, "验证码错误！", Toast.LENGTH_SHORT).show();
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
        iv_rgst2_bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itnt_rgst2_bk = new Intent(page_rgst2.this,page_rgst.class);
                startActivity(itnt_rgst2_bk);
                finish();
            }
        });
    }


}
