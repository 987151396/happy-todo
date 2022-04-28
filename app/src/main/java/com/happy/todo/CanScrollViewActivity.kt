package com.happy.todo

import com.happy.todo.lib_common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_canscrollview.*

/**
 * Author by Ouyangle, Date on 2022/1/19.
 * PS: Not easy to write code, please indicate.
 */
class CanScrollViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_canscrollview
    }

    override fun initView() {
        tv1_false.setOnClickListener {
            tv1_false.isSelected = true
            tv1_true.isSelected = false
            canScrollView.isAutoPlay(false)
        }

        tv1_true.setOnClickListener {
            tv1_false.isSelected = false
            tv1_true.isSelected = true
            canScrollView.isAutoPlay(true)
        }


        tv2_false.setOnClickListener {
            tv2_false.isSelected = true
            tv2_true.isSelected = false
            canScrollView.isLoop(false)
        }

        tv2_true.setOnClickListener {
            tv2_false.isSelected = false
            tv2_true.isSelected = true
            canScrollView.isLoop(true)
        }

        tv3_mode1.setOnClickListener {
            tv3_mode1.isSelected = true
            tv3_mode2.isSelected = false
            tv3_mode3.isSelected = false

        }

        tv3_mode2.setOnClickListener {
            tv3_mode1.isSelected = false
            tv3_mode2.isSelected = true
            tv3_mode3.isSelected = false

        }
    }

    override fun initToolbar() {

    }
}