package com.example.administrator.read.tongyong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.read.R;
import com.example.administrator.read.fenlei.book_info;
import com.example.administrator.read.internet.internet;

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
public class book_list extends AppCompatActivity {
    ArrayList<String> author=new ArrayList<String>();
    ArrayList<String> bookname=new ArrayList<String>();
    ArrayList<String> describtion=new ArrayList<String>();
    ArrayList<String> img=new ArrayList<String>();
    ArrayList<Integer> book_id=new ArrayList<Integer>();
    ArrayList<String> article_url=new ArrayList<String>();
    private Handler handler=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
        ImageView button=(ImageView) findViewById(R.id.return_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        handler=new Handler();
        setinfo();

    }

    private void setinfo() {
        final ListView listView=(ListView) findViewById(R.id.list_view);
        final TextView textView_type=(TextView) findViewById(R.id.type);
        final Intent intent=getIntent();
        final String url=intent.getStringExtra("url");
        final String type=intent.getStringExtra("type");
        textView_type.setText(type);
        int flag=intent.getIntExtra("flag",0);
        if(flag==1) {
            new Thread() {
                @Override
                public void run() {
                    String text = internet.gethttpresult(url);
                    JSONObject result = null;
                    try {
                        result = new JSONObject(text);
                        final JSONArray booklist = result.getJSONArray("Booklist");
                        for (int i = 0; i < booklist.length(); i++) {
                            try {
                                JSONObject object = booklist.getJSONObject(i);
                                author.add(object.getString("bookAuthor"));
                                bookname.add(object.getString("bookName"));
                                describtion.add(object.getString("bookIntroduction"));
                                img.add(object.getString("bookPicture"));
                                book_id.add(object.getInt("bookId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(new myadapter());
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent1=new Intent(book_list.this,book_info.class);
                                        intent1.putExtra("book_id",book_id.get(position));
                                        intent1.putExtra("flag",1);
                                        startActivity(intent1);
                                    }
                                });
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
        if(flag==2) {
            new Thread() {
                @Override
                public void run() {
                    String text = internet.gethttpresult(url);
                    JSONObject result = null;
                    try {
                        result = new JSONObject(text);
                        final JSONArray booklist = result.getJSONArray("articleslist");
                        for (int i = 0; i < booklist.length(); i++) {
                            try {
                                JSONObject object = booklist.getJSONObject(i);
                                author.add(object.getString("articleAuthor"));
                                bookname.add(object.getString("articleName"));
                                describtion.add(object.getString("articleIntroduction"));
                                img.add(object.getString("articlePicture"));
                                book_id.add(object.getInt("articleId"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(new myadapter());
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent1=new Intent(book_list.this,book_info.class);
                                        intent1.putExtra("book_id",book_id.get(position));
                                        intent1.putExtra("flag",2);
                                        startActivity(intent1);
                                    }
                                });
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

    private class myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return book_id.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(book_list.this,R.layout.article_department,null);
            TextView book=(TextView) view.findViewById(R.id.BookName);
            url_imgview imgview=(url_imgview) view.findViewById(R.id.imageView);
            TextView authorname=(TextView) view.findViewById(R.id.authorName);
            TextView innertext=(TextView) view.findViewById(R.id.innertext);
            book.setText(bookname.get(position));
            authorname.setText(author.get(position));
            innertext.setText(describtion.get(position));
            System.out.println(img);
            imgview.geturlimg(img.get(position));
            return view;
        }
    }
}
