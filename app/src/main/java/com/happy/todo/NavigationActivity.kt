package com.happy.todo

import android.content.Intent
import com.happy.todo.lib_common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation_layout.*

/**
 * Author by Ouyangle, Date on 2021/12/31.
 * PS: Not easy to write code, please indicate.
 */
class NavigationActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_navigation_layout
    }

    override fun initView() {
        animationActivity.setOnClickListener {
            startActivity(Intent(this,AnimationActivity::class.java))
        }

        canScrollView.setOnClickListener {
            startActivity(Intent(this,CanScrollViewActivity::class.java))
        }

        mainActivity.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        progressBarActivity.setOnClickListener {
            startActivity(Intent(this,ProgressBarActivity::class.java))
        }
    }

    override fun initToolbar() {

    }
}