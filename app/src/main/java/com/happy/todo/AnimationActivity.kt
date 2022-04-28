package com.happy.todo

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.bumptech.glide.Glide
import com.happy.todo.animation.Rotate3dAnimation
import com.happy.todo.lib_common.base.BaseActivity
import com.happy.todo.lib_common.utils.AppLogUtil
import kotlinx.android.synthetic.main.activity_animation_layout.*

/**
 * Author by Ouyangle, Date on 2021/12/31.
 * PS: Not easy to write code, please indicate.
 */
class AnimationActivity : BaseActivity() {
    var isDark = false
    override fun getLayoutId(): Int {
        return R.layout.activity_animation_layout
    }

    override fun initView() {
        card.setOnClickListener {
            card.post {
                //取card中心点
                AppLogUtil.d("Animation", "getMeasuredWidth :" + card.getMeasuredWidth() + "getMeasuredHeight : " + card.getMeasuredHeight())
                val centerX: Float = card.getMeasuredWidth() / 2f
                val centerY: Float = card.getMeasuredHeight() / 2f

                // 构建3D旋转动画对象，旋转角度为0到90度
                val rotation = Rotate3dAnimation(
                    this, 0f, 90f, centerX, centerY,
                    0f, false
                )
                // 动画持续时间500毫秒
                rotation.duration = 300
                // 动画完成后保持完成的状态
                rotation.fillAfter = true
                rotation.interpolator = AccelerateInterpolator()
                card.startAnimation(rotation);
                //监听器  翻转到90度的时候 卡面图片改变 然后将卡牌从270度翻转到360度刚好转回来
                //这里注意不是90-180度,因为90-180翻转过来的图片是左右相反的镜像图
                rotation.setAnimationListener(object :
                    Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        //正反面判断
                        isDark = !isDark
                        //点正面切换背面,反之亦然
                        if (isDark) {
                            Glide.with(this@AnimationActivity).load(R.mipmap.head_img2).into(card)
                        } else {
                            Glide.with(this@AnimationActivity).load(R.mipmap.head_img1).into(card)
                        }
                        //270度翻转到360度
                        val rotation = Rotate3dAnimation(
                            this@AnimationActivity, 270f, 360f, centerX, centerY,
                            0f, true
                        )
                        rotation.duration = 300
                        // 动画完成后保持完成的状态
                        rotation.fillAfter = false
                        card.startAnimation(rotation)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
        }
    }

    override fun initToolbar() {

    }
}