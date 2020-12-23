package com.example.administrator.read.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.read.R;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class book_type_Fragment extends Fragment {
    private ViewGroup mMoreLayout;
    String[] categories;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.book_type, container, false);
        mMoreLayout = (ViewGroup) view.findViewById(R.id.article_layout);
        initUI();
        return view;
    }
    private void initUI() {
        //找到该容器（这里的控件为LinearLayout，转换为ViewGroup是因为ViewGroup是容器的基类）


        //由于文字也是动态生成，使用android中array文件定义资源文件，并取出
        categories = getResources().getStringArray(R.array.book_type);

        final int size = categories.length;     //String[]的长度
        final int rowCount = size / 2;          //需要布局的行数(每行三个)

        /**
         * 动态添加布局方法封装
         * 参数 1.父容器    2.资源文字数组  3.从第几个开始   4.行数
         */
        fillViews(mMoreLayout, categories, 0, rowCount);
    }
    private void fillViews(ViewGroup layout, String[] categories, int start, int end) {
        // 表格第一条线
        for (int i = start; i < end; i++) {

            //找到索引，便于根据索引添加图片文件和文字
            final int firstIndex = i * 2;
            final int secondIndex = i * 2 + 1;

            final String firstCategory = categories[firstIndex];
            final String secondCategory = categories[secondIndex];

            // 父布局
            final LinearLayout linearLayout = new LinearLayout(getActivity());

            // 第一个子布局
            View.inflate(getActivity(), R.layout.type_department, linearLayout);

            // 第二个子布局
            View.inflate(getActivity(), R.layout.type_department, linearLayout);

            // 第三个子布局

            layout.addView(linearLayout);

            // 表格最后一条线

            //根据索引getChildAt到指定的位置
            final View firstView = linearLayout.getChildAt(0);
            firstView.setTag(firstIndex);        //设置tag，便于在后面判断点击的哪一个
            firstView.setOnClickListener(new click());     //设置点击
            final TextView firstTextView = (TextView) firstView.findViewById(R.id.text_title);
            firstTextView.setText(firstCategory);   //设置文字
            final ImageView firstImageView = (ImageView) firstView.findViewById(R.id.image_icon);
            firstImageView.setImageResource(R.drawable.beijing); //将之前缓存的图片设置出来
            final View secondView = linearLayout.getChildAt(1);
            secondView.setTag(secondIndex);
            secondView.setOnClickListener(new click());
            final TextView secondTextView = (TextView) secondView.findViewById(R.id.text_title);
            secondTextView.setText(secondCategory);
            final ImageView secondImageView = (ImageView) secondView.findViewById(R.id.image_icon);
            secondImageView.setImageResource(R.drawable.beijing);
        }
    }
    class click implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int count= (int) v.getTag();
            Intent intent = new Intent(getActivity(), book_type_list.class);
            intent.putExtra("type",categories[count]);
            startActivity(intent);
        }
    }
}
