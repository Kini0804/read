package com.example.administrator.read;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.read.shujia.articleFragment;
import com.example.administrator.read.shujia.bookFragment;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class fragment3 extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TextView book;
    TextView article;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment3, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.container);
        book = (TextView) view.findViewById(R.id.t1);
        article = (TextView) view.findViewById(R.id.t2);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<>();
        datas.add(new bookFragment());
        datas.add(new articleFragment());
        mSectionsPagerAdapter.setData(datas);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(change);
        change.onPageSelected(mViewPager.getCurrentItem());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
    }
    private ViewPager.OnPageChangeListener change=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if(i==0)
            {
                book.setTextColor(Color.rgb(250,211,144));
                article.setTextColor(Color.rgb(0,0,0));
            }
            else
            {
                article.setTextColor(Color.rgb(250,211,144));
                book.setTextColor(Color.rgb(0,0,0));
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };
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
