package com.example.administrator.read.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.read.R;

public class page_rsetpsw2 extends AppCompatActivity {

    private ImageView iv_rspsw2_bk;
    private Button bt_rspsw2_sure;
    private TextView psw;
    private TextView psw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_rsetpsw2);
        Intent intent=getIntent();
        int user_id=intent.getIntExtra("user_id",0);
        iv_rspsw2_bk = (ImageView) findViewById(R.id.rspsw2_iv_bk);
        bt_rspsw2_sure = (Button) findViewById(R.id.rspsw2_bt_sure);
        psw=(TextView) findViewById(R.id.rspsw2_et_psw);
        bt_rspsw2_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itnt_rspsw2_sure = new Intent(page_rsetpsw2.this,page_login.class);
                startActivity(itnt_rspsw2_sure);
            }
        });
    }
}
