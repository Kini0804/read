package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.read.R;
import com.example.administrator.read.article_read;
import com.example.administrator.read.internet.internet;
import com.example.administrator.read.login.page_login;
import com.example.administrator.read.pdfread;
import com.example.administrator.read.tongyong.url_imgview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class book_info extends AppCompatActivity {
    int flag;
    SectionsPagerAdapter mm;
    Handler handler;
    ArrayList<Fragment> datas=new ArrayList<>();;
    ViewPager mViewPager;
    int book_id;
    String book_name;
    String book_author;
    String book_introduction;
    String book_pic;
    String article_url;
    ArrayList<String> user_name=new ArrayList<String>();
    ArrayList<String> post_context=new ArrayList<String>();
    ArrayList<Integer> chapter_id=new ArrayList<Integer>();
    ArrayList<String> chapter_name=new ArrayList<String>();
    ArrayList<String> chapter_context=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookinfo);
        Intent intent=getIntent();
        handler=new Handler();
        flag=intent.getIntExtra("flag",0);
        book_id=intent.getIntExtra("book_id",0);
        final String urlStr="http://122.114.237.201/read/bookinfo";
        final String urlStr_article="http://122.114.237.201/read/Articleinfo";
        final String urlStr2="http://122.114.237.201/read/chapters";
        if(book_id==0)
        {
            Toast.makeText(this,"网络请求失败！",Toast.LENGTH_SHORT).show();
        }
        else {
            if(flag==1) {
                new Thread() {
                    @Override
                    public void run() {
                        String result = internet.getbookinfo(urlStr, book_id);
                        String result2 = internet.getbookinfo(urlStr2, book_id);
                        System.out.println(result);
                        System.out.println(result2);
                        if (result.equals("") || result2.equals("")) {
                            Looper.prepare();
                            Toast.makeText(book_info.this, "请求失败！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            try {
                                JSONObject result_json = new JSONObject(result);
                                JSONArray bookinfo = result_json.getJSONArray("bookinfo");
                                JSONObject book = bookinfo.getJSONObject(0);
                                book_name = book.getString("bookName");
                                book_author = book.getString("bookAuthor");
                                book_introduction = book.getString("bookIntroduction");
                                book_pic = book.getString("bookPicture");
                                JSONArray post = result_json.getJSONArray("post");
                                for (int i = 0; i < post.length(); i++) {
                                    JSONObject object = post.getJSONObject(i);
                                    user_name.add(object.getString("userName"));
                                    post_context.add(object.getString("postContext"));
                                }
                                JSONObject result_json2 = new JSONObject(result2);
                                JSONArray chapters = result_json2.getJSONArray("chapters");
                                for (int i = 0; i < chapters.length(); i++) {
                                    JSONObject object = chapters.getJSONObject(i);
                                    chapter_id.add(object.getInt("chapterId"));
                                    chapter_name.add(object.getString("chapterName"));
                                    chapter_context.add(object.getString("chapterContext"));
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setinfo();
                                        setpagerview();
                                        setclickitem();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.toString());
                            }
                        }

                    }
                }.start();
            }
            else if(flag==2){
                new Thread() {
                    @Override
                    public void run() {
                        String result = internet.getarticleinfo(urlStr_article, book_id);
                        System.out.println(result);
                        if (result.equals("") ) {
                            Looper.prepare();
                            Toast.makeText(book_info.this, "请求失败！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        } else {
                            try {
                                JSONObject result_json = new JSONObject(result);
                                JSONArray bookinfo = result_json.getJSONArray("articleinfo");
                                JSONObject book = bookinfo.getJSONObject(0);
                                book_name = book.getString("articleName");
                                book_author = book.getString("articleAuthor");
                                book_introduction = book.getString("articleIntroduction");
                                book_pic = book.getString("articlePicture");
                                article_url=book.getString("articleContext");
                                JSONArray post = result_json.getJSONArray("post");
                                for (int i = 0; i < post.length(); i++) {
                                    JSONObject object = post.getJSONObject(i);
                                    user_name.add(object.getString("userName"));
                                    post_context.add(object.getString("postContext"));
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setinfo();
                                        setpagerview();
                                        setclickitem();
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(e.toString());
                            }
                        }

                    }
                }.start();
            }
    }



    }

    private void setinfo() {
        url_imgview imgview=(url_imgview) findViewById(R.id.bookimage);
        imgview.geturlimg(book_pic);
        TextView book_info_name=(TextView) findViewById(R.id.book_info_name);
        TextView book_info_authorn=(TextView) findViewById(R.id.book_info_authorn);
        TextView book_info_introduction=(TextView) findViewById(R.id.book_info_introduction);
        book_info_name.setText(book_name);
        book_info_authorn.setText(book_author);
        book_info_introduction.setText(book_introduction);
    }

    private void setclickitem() {
        Button button2=(Button) findViewById(R.id.button2);
        Button button3=(Button) findViewById(R.id.button3);
        Button begin_read=(Button) findViewById(R.id.yuedu);
        Button putbook=(Button) findViewById(R.id.putbook);
        Button returnbutton=(Button) findViewById(R.id.return1);
        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        begin_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    Intent intent= new Intent(book_info.this,article_read.class);
                    intent.putExtra("book_id",book_id);
                    intent.putExtra("book_name",book_name);
                    startActivity(intent);
                }
                else if(flag==2){
                    Intent intent= new Intent(book_info.this,pdfread.class);
                    intent.putExtra("url",article_url);
                    intent.putExtra("article_name",book_name);
                    startActivity(intent);
                }

            }
        });
        TextView authorn= (TextView) findViewById(R.id.book_info_authorn);
        authorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_info.this, AuthorContent.class);
                startActivity(intent);
            }
        });
        putbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlputbook="http://122.114.237.201/read/addbookcase";
                final String urlputarticle="http://122.114.237.201/read/addarticlecase";
                SharedPreferences user_data=getSharedPreferences("user_data",MODE_PRIVATE);
                final int user_id=user_data.getInt("user_id",0);
                if(user_id==0)
                {
                    Toast.makeText(book_info.this,"你还未登录，请先登录......",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(book_info.this,page_login.class);
                    startActivity(intent);
                }
                else {
                    new Thread(){
                        @Override
                        public void run() {
                            String result=null;
                            if(flag==1){
                                result= internet.addbook(urlputbook,user_id,book_id);
                            }
                            else if(flag==2){
                                result= internet.addarticle(urlputarticle,user_id,book_id);
                            }
                            System.out.println(result);
                            if (result.equals(""))
                            {
                                Looper.prepare();
                                Toast.makeText(book_info.this,"请求失败！",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            else {
                                try {
                                    JSONObject result_json = new JSONObject(result);
                                    String message=result_json.getString("message");
                                    if("success".equals(message))
                                    {
                                        Looper.prepare();
                                        Toast.makeText(book_info.this,"好棒哟，成功加入书架了呢！",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    else
                                    {
                                        Looper.prepare();
                                        Toast.makeText(book_info.this,"已经在你的书架里了哟宝贝~",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println(e.toString());
                                }
                            }
                        }
                    }.start();
                }

            }
        });
    }

    private void setpagerview() {
        mm=new SectionsPagerAdapter(getSupportFragmentManager());
        System.out.println(user_name);
        System.out.println(post_context);
        datas.add(xiangqing.newInstance(user_name,post_context));
        datas.add(mulu.newInstance(chapter_id,chapter_name));
        mm.setData(datas);
        mViewPager = (ViewPager) findViewById(R.id.content);
        mViewPager.setAdapter(mm);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> datas;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(ArrayList<Fragment> datas) {
            this.datas = datas;
        }

        @Override
        public Fragment getItem(int position) {
            return datas == null ? null : datas.get(position);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

    }
}
