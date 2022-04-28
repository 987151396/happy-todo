package com.happy.todo.module_user;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.happy.todo.lib_common.base.BaseActivity;

/**
 * 用于测试单个fragment
 * Created by Jaminchanks on 2018-01-14.
 */
public abstract class SingleFragmentActivity extends BaseActivity {

    private Fragment mFragment;

    @Override
    protected void initView() {
        initFragment();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_test_activity_fragment;
    }

    final protected void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (null == mFragment) {
            mFragment = onCreateFragment();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragmentContainer, mFragment);
            transaction.commit();

        }
    }

    public Fragment getFragment() {
        return mFragment;
    }

    protected abstract Fragment onCreateFragment();

}
