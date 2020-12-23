package com.example.administrator.read.fenlei;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.read.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class mulu extends Fragment {
    private ViewGroup mMoreLayout;
    ListView listview;
    ArrayList<Integer> chapter_id=new ArrayList<Integer>();
    ArrayList<String> chapter_name=new ArrayList<String>();
    public static mulu newInstance(ArrayList<Integer> chapter_id, ArrayList<String> chapter_name) {
        mulu newFragment = new mulu();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("chapter_id", chapter_id);
        bundle.putStringArrayList("chapter_name",chapter_name);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            chapter_id= args.getIntegerArrayList("chapter_id");
            chapter_name= args.getStringArrayList("chapter_name");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mulu, container, false);
        listview=(ListView) view.findViewById(R.id.zhangjie_content);
        Button button3=(Button) view.findViewById(R.id.daoxu);
        final List<Map<String, Object>> list =DataList();
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),list,R.layout.list1,new String[]{"dizhangjie","neirong"},new int[]{R.id.dizhangjie,R.id.neirong});
        listview.setAdapter(adapter);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.reverse(list);   //这行就是将list的内容反转，下面再装进adapter里，就可以倒序显示了
                SimpleAdapter adapter=new SimpleAdapter(getActivity(),list,R.layout.list1,new String[]{"dizhangjie","neirong"},new int[]{R.id.dizhangjie,R.id.neirong});
                listview.setAdapter(adapter);
            }
        });
        return view;
    }


    private List<Map<String,Object>> DataList()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < chapter_id.size(); i++) {
            Map<String,Object> map =new HashMap<String, Object>();
            map.put("dizhangjie",chapter_name.get(i));
            map.put("neirong","");
            list.add(map);
        }
        return list;
    }
}




