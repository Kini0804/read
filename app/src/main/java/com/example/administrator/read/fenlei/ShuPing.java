package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.read.R;

/**
 * @author Administrator
 * @version $Rev $
 * @des ${TODO}
 * @updateAuthor $Author $
 * @updateDes ${TODO}
 */
public class ShuPing extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shuping);
        Button moretu= (Button) findViewById(R.id.moretu);
        moretu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShuPing.this, MoreBook.class);
                startActivity(intent);
            }
        });

    }
}