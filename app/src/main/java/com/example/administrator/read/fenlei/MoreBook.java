package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.read.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version $Rev $
 * @des ${TODO}
 * @updateAuthor $Author $
 * @updateDes ${TODO}
 */
public class MoreBook extends AppCompatActivity{
    ListView listview;
    ArrayList<String> user_name=new ArrayList<String>();
    ArrayList<String> post_context=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morebook);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bds");
        user_name=bundle.getStringArrayList("user_name");
        post_context=bundle.getStringArrayList("post_context");
        listview=(ListView) findViewById(R.id.shuping_content);
        SimpleAdapter adapter=new SimpleAdapter(this,DataList(),R.layout.list2,new String[]{"image","zuozhe","neirong"},new int[]{R.id.shujizaidu,R.id.yonghu,R.id.nei});
        listview.setAdapter(adapter);
        Button morere=(Button) findViewById(R.id.morere);
        morere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button morerer=(Button) findViewById(R.id.morerer);
        morerer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MoreBook.this,ShuPing.class);
                startActivity(i);
            }
        });

    }
    private List<Map<String,Object>> DataList()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String,Object> map =new HashMap<String, Object>();
        for (int i = 0; i < user_name.size(); i++) {
            map=new HashMap<String,Object> ();
            map.put("image",R.drawable.ic_launcher_background);
            map.put("zuozhe",user_name.get(i));
            map.put("neirong",post_context.get(i));
            list.add(map);
        }
        return list;

}
}