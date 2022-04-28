package com.happy.todo.module_user;

import android.content.Context;

import com.happy.todo.lib_common.base.BaseActivity;
import com.wx.wheelview.widget.NestedScrollView;

/**
 * 测试用
 * Created by Jaminchanks on 2018-01-13.
 */
public class TestActivity extends BaseActivity {
    @Override
    protected void initView() {
        //清除登录信息
//        LoginManage.logout();


    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_test_activity_test;
    }


    public class TestView extends NestedScrollView {

        public TestView(Context context) {
            super(context);
        }
    }
}
