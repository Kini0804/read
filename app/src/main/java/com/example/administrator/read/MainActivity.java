package com.example.administrator.read;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.read.login.page_login;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;
    fragment1 fragment1;
    fragment2 fragment2;
    fragment3 fragment3;
    fragment4 fragment4;
    Fragment[] fragments;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        fragment1 = new fragment1();
        fragment2=new fragment2();
        fragment3=new fragment3();
        fragment4=new fragment4();
        fragments = new Fragment[]{fragment1,fragment2,fragment3,fragment4};
        number=0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview,fragment1).show(fragment1).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setdata();
    }

    private void setdata() {
        SharedPreferences user_data=getSharedPreferences("user_data",MODE_PRIVATE);
        int user_id=user_data.getInt("user_id",0);
        if(user_id==0)
        {
            Intent intent=new Intent(this,page_login.class);
            startActivity(intent);
        }

    }

    private void switchFragment(int lastfragment,int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    if(number!=0)
                    {
                        switchFragment(number,0);
                        number=0;
                    }
                    return true;
                case R.id.type:
                    if(number!=1)
                    {
                        switchFragment(number,1);
                        number=1;
                    }
                    return true;
                case R.id.book:
                    if(number!=2)
                    {
                        switchFragment(number,2);
                        number=2;
                    }
                    return true;
                case R.id.myinfo:
                    if(number!=3)
                    {
                        switchFragment(number,3);
                        number=3;
                    }
                    return true;
            }
            return false;
        }
    };

}
