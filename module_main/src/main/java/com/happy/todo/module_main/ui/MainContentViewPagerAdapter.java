package com.happy.todo.module_main.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @作者: TwoSX
 * @时间: 2018/1/12 上午11:05
 * @描述: MainContentViewPagerAdapter
 */
public class MainContentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MainContentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList != null ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }
}
