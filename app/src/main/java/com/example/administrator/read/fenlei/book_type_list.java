package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.read.R;
import com.example.administrator.read.internet.internet;
import com.example.administrator.read.tongyong.url_imgview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class book_type_list extends AppCompatActivity {
    private ViewGroup mMoreLayout;
    String type;
    Handler handler;
    ArrayList<String> author=new ArrayList<String>();
    ArrayList<String> bookname=new ArrayList<String>();
    ArrayList<String> describtion=new ArrayList<String>();
    ArrayList<String> img=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new Handler();
        setContentView(R.layout.book_type_list);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        System.out.println(type);
        mMoreLayout = (ViewGroup)findViewById(R.id.article_layout);
        initUI();
    }
    private void initUI() {
        new Thread(){
            @Override
            public void run() {
                String result=internet.gettypebook(type);
                JSONObject result_json= null;
                try {
                    result_json = new JSONObject(result);
                    JSONArray list=result_json.getJSONArray("ClassifiedBooklist");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object=list.getJSONObject(i);
                        author.add(object.getString("bookAuthor"));
                        bookname.add(object.getString("bookName"));
                        describtion.add(object.getString("bookIntroduction"));
                        img.add(object.getString("bookPicture"));
                        book_id.add(object.getInt("bookId"));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            final int size = book_id.size();
                            final int rowCount = size;
                            fillViews(mMoreLayout, 0, rowCount);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
       // final int size = book_id.size();
       // final int rowCount = size;
        //fillViews(mMoreLayout, 0, rowCount);
    }
    private void fillViews(ViewGroup layout, int start, int end) {
        // 表格第一条线

        for (int i = start; i < end; i++) {

            //找到索引，便于根据索引添加图片文件和文字

            // 父布局
            final LinearLayout linearLayout = new LinearLayout(this);

            // 第一个子布局
            View.inflate(this, R.layout.article_department, linearLayout);
            layout.addView(linearLayout);
            //根据索引getChildAt到指定的位置
            final View firstView = linearLayout.getChildAt(0);
            firstView.setTag(i);        //设置tag，便于在后面判断点击的哪一个
            firstView.setOnClickListener(new click());     //设置点击
            final TextView firstTextView = (TextView) firstView.findViewById(R.id.BookName);
            firstTextView.setText(bookname.get(i));   //设置文字
            final TextView secondTextView = (TextView) firstView.findViewById(R.id.authorName);
            secondTextView.setText(author.get(i));   //设置文字
            final TextView thirdTextView = (TextView) firstView.findViewById(R.id.innertext);
            thirdTextView.setText(describtion.get(i));   //设置文字
            final url_imgview firstImageView = (url_imgview) firstView.findViewById(R.id.imageView);
            firstImageView.geturlimg(img.get(i));

        }
    }


    class click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int count= (int) v.getTag();
            Intent intent=new Intent(book_type_list.this,book_info.class);
            intent.putExtra("book_id",book_id.get(count));
            intent.putExtra("flag",1);
            startActivity(intent);
        }
    }
}
