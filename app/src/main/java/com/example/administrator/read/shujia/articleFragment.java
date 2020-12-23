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
import com.example.administrator.read.internet.internet;
import com.example.administrator.read.pdfread;
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
public class articleFragment extends Fragment {
    private ViewGroup mMoreLayout;
    int user_id;
    Handler handler;
    ArrayList<String> book_name=new ArrayList<String>();
    ArrayList<String> book_img=new ArrayList<String>();
    ArrayList<String> context=new ArrayList<String>();
    ArrayList<String> author=new ArrayList<String>();
    ArrayList<String> article_url=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article, container, false);
        mMoreLayout = (ViewGroup) rootView.findViewById(R.id.article_layout);
        handler=new Handler();
        setdata();
        return rootView;
    }

    private void setdata() {
        final String url="http://122.114.237.201/userinfo/articlecase";
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
                        JSONArray bookcase=result_json.getJSONArray("articlecase");
                        for (int i = 0; i < bookcase.length(); i++) {
                            JSONObject object=bookcase.getJSONObject(i);
                            book_id.add(object.getInt("articleId"));
                            book_name.add(object.getString("articleName"));
                            book_img.add(object.getString("articlePicture"));
                            String article=internet.getarticleinfo("http://122.114.237.201/read/Articleinfo",object.getInt("articleId"));
                            JSONObject article_json = new JSONObject(article);
                            JSONArray article_info = article_json.getJSONArray("articleinfo");
                            for (int j = 0; j < article_info.length(); j++) {
                                JSONObject object1=article_info.getJSONObject(j);
                                context.add(object1.getString("articleIntroduction"));
                                author.add(object1.getString("articleAuthor"));
                                article_url.add(object1.getString("articleContext"));
                            }

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
        //找到该容器（这里的控件为LinearLayout，转换为ViewGroup是因为ViewGroup是容器的基类）

        //由于文字也是动态生成，使用android中array文件定义资源文件，并取出
        final String[] categories = getResources().getStringArray(R.array.articles);
        final int size = book_id.size();
        final int rowCount = size;

        fillViews(mMoreLayout,0, rowCount);
    }
    private void fillViews(ViewGroup layout, int start, int end) {
        // 表格第一条线
        for (int i = start; i < end; i++) {

            //找到索引，便于根据索引添加图片文件和文字
            final int firstIndex = i;

            // 父布局
            final LinearLayout linearLayout = new LinearLayout(getActivity());

            // 第一个子布局
            View.inflate(getActivity(), R.layout.article_department, linearLayout);
            layout.addView(linearLayout);
            //根据索引getChildAt到指定的位置
            final View firstView = linearLayout.getChildAt(0);
            firstView.setTag(i);        //设置tag，便于在后面判断点击的哪一个
            firstView.setOnClickListener(new click());     //设置点击
            final TextView firstTextView = (TextView) firstView.findViewById(R.id.BookName);
            firstTextView.setText(book_name.get(i));   //设置文字
            final TextView secondTextView = (TextView) firstView.findViewById(R.id.authorName);
            secondTextView.setText(author.get(i));   //设置文字
            final TextView thirdTextView = (TextView) firstView.findViewById(R.id.innertext);
            thirdTextView.setText(context.get(i));   //设置文字
            final url_imgview firstImageView = (url_imgview) firstView.findViewById(R.id.imageView);
            firstImageView.geturlimg(book_img.get(i));

        }
    }
    class click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int count= (int) v.getTag();
            Intent intent = new Intent(getActivity(), pdfread.class);
            intent.putExtra("url",article_url.get(count));
            intent.putExtra("book_name",book_name.get(count));
            startActivity(intent);
        }
    }
}
