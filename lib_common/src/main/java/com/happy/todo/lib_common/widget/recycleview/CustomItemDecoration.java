package com.happy.todo.lib_common.widget.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.happy.todo.lib_common.R;

/**
 * Created by cxk on 2018/1/11.
 * <p>
 * recycleview分割线
 */

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Drawable mDivider;
    private int mDividerHeight = 2;//分割线高度，默认为1px
    private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private int pindingStart = 0;
    private int pindingEnd = 0;

    /**
     * 系统默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public CustomItemDecoration(Context context, int orientation) {
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }


    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    public CustomItemDecoration(Context context, int orientation, int drawableId) {
        this(context, orientation);
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     * 判断是不是默认的分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public CustomItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (dividerHeight != 0) {
            mDividerHeight = dividerHeight;
        }

        if (dividerColor != 0) {
            mPaint.setColor(dividerColor);
        } else {
            mPaint.setColor(context.getResources().getColor(R.color.divider_color));
        }

        mPaint.setStyle(Paint.Style.FILL);
    }


    /**
     * 自定义分割线
     * 判断是不是默认的分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public CustomItemDecoration(Context context, int orientation, int dividerHeight, int dividerColor,int pinding) {
        this(context, orientation);

        this.pindingStart = pinding;
        this.pindingEnd = pinding;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (dividerHeight != 0) {
            mDividerHeight = dividerHeight;
        }

        if (dividerColor != 0) {
            mPaint.setColor(dividerColor);
        } else {
            mPaint.setColor(context.getResources().getColor(R.color.divider_color));
        }

        mPaint.setStyle(Paint.Style.FILL);
    }



    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent);
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawVertical(c, parent);
        }
//        else if (mOrientation == GridLayoutManager.VERTICAL) {
//            drawGridVerticalDivider(c, parent);
//            Log.e("dasdadas","dasdada");
//        }
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + pindingStart;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() - pindingEnd;
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //绘制纵向 item 分割线
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    public void drawGridVerticalDivider(Canvas c, RecyclerView parent) {
        // 这里传入的parent是recycleview，通过它我们可以获取列表的所有的元素，
        // 这里我们遍历列表中的每一个元素，对每一个元素绘制垂直分割线
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            //获取当前item布局参数，通过它可以知道该item的精确位置，我们通过这个位置去绘制它的分割线
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;

            int left = 0;
            int right = 0;

            //左边第一列，
            if ((i % 3) == 0) {
                //item左边分割线
                left = child.getLeft();
                right = left + mDividerHeight;
                c.drawRect(left, top, right, bottom, mPaint);
                //item右边分割线
                left = child.getRight() + params.rightMargin - mDividerHeight;
                right = left + mDividerHeight;
            } else {
                //非左边第一列
                left = child.getRight() + params.rightMargin - mDividerHeight;
                right = left + mDividerHeight;
            }
            //画分割线
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
