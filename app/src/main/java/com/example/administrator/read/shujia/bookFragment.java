package com.example.administrator.read.shujia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.read.R;
import com.example.administrator.read.article_read;
import com.example.administrator.read.internet.internet;
import com.example.administrator.read.login.page_login;
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
public class bookFragment extends Fragment {
    private ViewGroup mMoreLayout;
    int user_id;
    Handler handler;
    ArrayList<String> book_name=new ArrayList<String>();
    ArrayList<String> book_img=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(user_id!=0){
            View rootView = inflater.inflate(R.layout.book, container, false);
            mMoreLayout = (ViewGroup) rootView.findViewById(R.id.layout_more);
            handler=new Handler();
            setdata();
            return rootView;
        }
        else{
            View rootView = inflater.inflate(R.layout.book_notlog, container, false);
            TextView textView=(TextView) rootView.findViewById(R.id.not_log);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),page_login.class);
                    intent.putExtra("flag",2);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            return rootView;
        }
    }

    private void setdata() {
        final String url="http://122.114.237.201/userinfo/bookcase";
        new Thread(){
            @Override
            public void run() {
                String result= internet.getuserinfo(url,user_id);
                System.out.println(result);
                if (result.equals(""))
                {
                    Looper.prepare();
                    Toast.makeText(getActivity(),"请求失败！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else {
                    try {
                        JSONObject result_json = new JSONObject(result);
                        JSONArray bookcase=result_json.getJSONArray("bookcase");
                        for (int i = 0; i < bookcase.length(); i++) {
                            JSONObject object=bookcase.getJSONObject(i);
                            book_id.add(object.getInt("bookId"));
                            book_name.add(object.getString("bookName"));
                            book_img.add(object.getString("bookPicture"));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                initUI();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences user_data=getActivity().getSharedPreferences("user_data",getActivity().MODE_PRIVATE);
        user_id=user_data.getInt("user_id",0);
    }
    private void initUI() {

        final int size = book_id.size();     //String[]的长度
        int rowCount = size / 3;          //需要布局的行数(每行三个)
        int leastcount=size%3;
        if(leastcount>0)
        {
            rowCount++;
        }

        /**
         * 动态添加布局方法封装
         * 参数 1.父容器    2.资源文字数组  3.从第几个开始   4.行数
         */
        fillViews(mMoreLayout,0, rowCount);
    }
    private void fillViews(ViewGroup layout, int start, int end) {
        // 表格第一条线
        String firstCategory ;
        String secondCategory;
        String thirdCategory ;
        for (int i = start; i < end; i++) {

            //找到索引，便于根据索引添加图片文件和文字
            final int firstIndex = i * 3;
            final int secondIndex = i * 3 + 1;
            final int thirdIndex = i * 3 + 2;
            if(secondIndex>=book_id.size())
            {
                firstCategory = book_name.get(firstIndex);
                secondCategory = "";
                thirdCategory = "";
            }
            else if(thirdIndex>=book_id.size()){
                firstCategory = book_name.get(firstIndex);
                secondCategory = book_name.get(secondIndex);
                thirdCategory = "";
            }
            else{
                firstCategory = book_name.get(firstIndex);
                secondCategory = book_name.get(secondIndex);
                thirdCategory = book_name.get(thirdIndex);
            }


            // 父布局
            final LinearLayout linearLayout = new LinearLayout(getActivity());

            // 第一个子布局
            View.inflate(getActivity(), R.layout.book_department, linearLayout);

            // 第二个子布局
            View.inflate(getActivity(), R.layout.book_department, linearLayout);

            // 第三个子布局
            View.inflate(getActivity(), R.layout.book_department, linearLayout);

            layout.addView(linearLayout);

            // 表格最后一条线

            //根据索引getChildAt到指定的位置
            final View firstView = linearLayout.getChildAt(0);
            firstView.setTag(firstIndex);        //设置tag，便于在后面判断点击的哪一个
            firstView.setOnClickListener(new click());     //设置点击
            final TextView firstTextView = (TextView) firstView.findViewById(R.id.text_title);
            firstTextView.setText(firstCategory);   //设置文字
            final url_imgview firstImageView = (url_imgview) firstView.findViewById(R.id.image_icon);
            firstImageView.geturlimg(book_img.get(firstIndex));


            if(!secondCategory.equals("")){
                final View secondView = linearLayout.getChildAt(1);
                secondView.setTag(secondIndex);
                secondView.setOnClickListener(new click());
                final TextView secondTextView = (TextView) secondView.findViewById(R.id.text_title);
                secondTextView.setText(secondCategory);
                final url_imgview secondImageView = (url_imgview) firstView.findViewById(R.id.image_icon);
                secondImageView.geturlimg(book_img.get(secondIndex));
            }

            if(!thirdCategory.equals("")){
                final View thirdView = linearLayout.getChildAt(2);
                thirdView.setTag(thirdIndex);
                thirdView.setOnClickListener(new click());
                final TextView thirdTextView = (TextView) thirdView.findViewById(R.id.text_title);
                thirdTextView.setText(thirdCategory);
                final url_imgview thiredImageView = (url_imgview) firstView.findViewById(R.id.image_icon);
                thiredImageView.geturlimg(book_img.get(thirdIndex));
            }
        }
    }
    class click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int count= (int) v.getTag();
            Intent intent = new Intent(getActivity(), article_read.class);
            intent.putExtra("book_id",book_id.get(count));
            intent.putExtra("book_name",book_name.get(count));
            startActivity(intent);
        }
    }
}
