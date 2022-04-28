package com.happy.todo.lib_common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.ScreenUtil;
import com.happy.todo.lib_common.utils.SizeUtil;

/**
 * @Describe：搜索进度条
 * @Date： 2019/03/15
 * @Author： dengkewu
 * @Contact：
 */
public class ProgressView extends View {

    private Paint paint;

    private int view_end = 0;
    private int view_start = 0;
    private int view_end1 = 0;
    private int view_start1 = 0;
    private int view_end2 = 0;
    private int view_start2 = 0;
    private int width = SizeUtil.dp2px(50);
    private int height = 10;

    private int duration = 1000;//动画时长
    private int end = ScreenUtil.getScreenWidth();
    private int color = ResourceUtils.getColor(R.color.theme_color);
    private ValueAnimator animator1;
    private ValueAnimator animator2;
    private ValueAnimator animator;
    private boolean isAnimation = true;
    private ValueAnimator valueAnimator;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF r = new RectF();
        r.left = view_start;
        r.right = view_end;
        r.top = 0;
        r.bottom = height;
        canvas.drawRect(r, paint);

        RectF r1 = new RectF();
        r1.left = view_start1;
        r1.right = view_end1;
        r1.top = 0;
        r1.bottom = height;
        canvas.drawRect(r1, paint);

        RectF r2 = new RectF();
        r2.left = view_start2;
        r2.right = view_end2;
        r2.top = 0;
        r2.bottom = height;
        canvas.drawRect(r2, paint);
    }

    public void startAnim() {
        isAnimation = true;
        setAlpha(1);
        view_end = 0;
        view_start = 0;
        view_end1 = 0;
        view_start1 = 0;
        view_end2 = 0;
        view_start2 = 0;

        if (valueAnimator!=null){
            valueAnimator.cancel();
        }
        if (animator != null) {
            animator.cancel();
        }
        if (animator1 != null) {
            animator1.cancel();
        }
        if (animator2 != null) {
            animator2.cancel();
        }

        if (animator == null) {
            animator = new ValueAnimator();
            animator.setIntValues(0, end + width);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(duration);
            if (!animator.isStarted())
                animator.start();
            animator.addUpdateListener(animation -> {
                view_end = width + (int) animation.getAnimatedValue();
                view_start = (int) animation.getAnimatedValue();
                invalidate();
            });
            animator.addUpdateListener(animation -> {
                if (isAnimation) {
                    if ((int) animation.getAnimatedValue() > end / 2 && !animator1.isStarted()) {
                        animator1.start();
                    }
                }
            });
        } else {
            if (!animator.isStarted())
                animator.start();
        }

        if (animator1 == null) {
            animator1 = new ValueAnimator();
            animator1.setInterpolator(new DecelerateInterpolator());
            animator1.setIntValues(0, end + 2 * width);
            animator1.setDuration(duration);
            animator1.setInterpolator(new DecelerateInterpolator());
            animator1.addUpdateListener(animation -> {
                view_end1 = width + (int) animation.getAnimatedValue();
                view_start1 = (int) animation.getAnimatedValue();
                invalidate();
            });

            animator1.addUpdateListener(animation -> {
                if (isAnimation) {
                    if ((int) animation.getAnimatedValue() > end / 2 && !animator2.isStarted()) {
                        animator2.start();
                    }
                }
            });
        }
        if (animator2==null) {
            animator2 = new ValueAnimator();
            animator2.setIntValues(0, end + 3 * width);
            animator2.setDuration(duration);
            animator2.setInterpolator(new DecelerateInterpolator());
            animator2.addUpdateListener(animation -> {
                view_end2 = width + (int) animation.getAnimatedValue();
                view_start2 = (int) animation.getAnimatedValue();
                invalidate();
            });
            animator2.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (isAnimation) {
                        if (!animator.isStarted()) {
                            animator.start();
                        }

                    }

                }
            });
            animator2.addUpdateListener(animation -> {

            });
        }
    }

    public void hide() {
        isAnimation = false;
        setAlpha(0);
        if (animator != null) {
            animator.end();
        }
        if (animator1 != null) {
            animator1.end();
        }
        if (animator2 != null) {
            animator2.end();
        }
    }

    public void cancel() {
        isAnimation = false;
        view_end = 0;
        view_start = 0;
        view_end1 = 0;
        view_start1 = 0;
        view_end2 = 0;
        view_start2 = 0;
        if (animator != null&&animator.isRunning()) {
            animator.cancel();
        }
        if (animator1 != null&&animator1.isRunning()) {
            animator1.cancel();
        }
        if (animator2 != null&&animator2.isRunning()) {
            animator2.cancel();
        }
        if (onAnimationEndListener != null) {
            onAnimationEndListener = null;
        }
    }

    public interface onAnimationEndListener {
        void onEnd(boolean isAnim);
    }

    private onAnimationEndListener onAnimationEndListener;

    public void setOnAnimationEndListener(ProgressView.onAnimationEndListener onAnimationEndListener) {
        this.onAnimationEndListener = onAnimationEndListener;
    }

    /**
     * 结束动画，延时200毫秒刷新列表避免卡顿
     * @return
     */
    public ProgressView endAnimation(){
        isAnimation =false;
        valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(200);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (onAnimationEndListener != null) {
                    onAnimationEndListener.onEnd(isAnimation);
                }
                if (animator != null) {
                    animator.cancel();
                }
                if (animator1 != null) {
                    animator1.cancel();
                }
                if (animator2 != null) {
                    animator2.cancel();
                }
            }
        });
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            setAlpha(animatedValue);
        });
        valueAnimator.start();
        return this;
    }

    //页面销毁时触发
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }
}
