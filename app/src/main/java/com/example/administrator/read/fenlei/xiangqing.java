package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.read.R;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class xiangqing extends Fragment {
    private ViewGroup mMoreLayout;
    ArrayList<String> user_name=new ArrayList<String>();
    ArrayList<String> post_context=new ArrayList<String>();
    Button more;
    TextView user_name1;
    TextView user_name2;
    TextView post1;
    TextView post2;
    public static xiangqing newInstance(ArrayList<String> user_name, ArrayList<String> post_context) {
        xiangqing newFragment = new xiangqing();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("user_name", user_name);
        bundle.putStringArrayList("post_context",post_context);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xiangqing, container, false);
        more=(Button) view.findViewById(R.id.more);
        user_name1=(TextView) view.findViewById(R.id.uesername);
        user_name2=(TextView) view.findViewById(R.id.uesername2);
        post1=(TextView) view.findViewById(R.id.textView6);
        post2=(TextView) view.findViewById(R.id.textView8);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            user_name= args.getStringArrayList("user_name");
            post_context= args.getStringArrayList("post_context");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MoreBook.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("user_name", user_name);
                bundle.putStringArrayList("post_context",post_context);
                intent.putExtra("bds",bundle);
                startActivity(intent);
            }
        });
        if(user_name.size()==0){
            user_name.add("暂无");
            user_name.add("暂无");
            post_context.add("暂无");
            post_context.add("暂无");
        }
        else if(user_name.size()==1){
            user_name.add("暂无");
            post_context.add("暂无");
        }
            user_name1.setText(user_name.get(0));
            user_name2.setText(user_name.get(1));
            post1.setText(post_context.get(0));
            post2.setText(post_context.get(1));

    }
}



