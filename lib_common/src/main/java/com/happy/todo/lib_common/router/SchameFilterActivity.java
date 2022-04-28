package com.happy.todo.lib_common.router;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @作者: TwoSX
 * @时间: 2017/11/30 下午6:18
 * @描述: 用于监听Schame事件, 之后直接把url传递给ARouter
 */
public class SchameFilterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
