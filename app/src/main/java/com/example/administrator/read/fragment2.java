package com.example.administrator.read;

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

import com.example.administrator.read.fenlei.book_type_Fragment;
import com.example.administrator.read.shujia.articleFragment;

import java.util.ArrayList;

/**
 * @author Administrator
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class fragment2 extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment2, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.container);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionsPagerAdapter = new fragment2.SectionsPagerAdapter(getChildFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<>();
        datas.add(new book_type_Fragment());
        datas.add(new articleFragment());
        mSectionsPagerAdapter.setData(datas);
        mViewPager.setAdapter(mSectionsPagerAdapter);
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
