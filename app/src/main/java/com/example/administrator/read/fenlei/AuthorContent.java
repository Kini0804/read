package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.read.MainActivity;
import com.example.administrator.read.R;

/**
 * @author Administrator
 * @version $Rev $
 * @des ${TODO}
 * @updateAuthor $Author $
 * @updateDes ${TODO}
 */
public class AuthorContent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorcontent);
        Button authortu= (Button) findViewById(R.id.authortu);
        authortu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorContent.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
