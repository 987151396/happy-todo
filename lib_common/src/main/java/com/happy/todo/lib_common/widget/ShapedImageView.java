package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ImageUtil;

import java.util.Arrays;

/** Support ``circle`` & ``round rect`` shaped
 * Support ``stroke``
 * Support ``TransitionDrawable``
 * ***New*** **Support custom PathExtension***/

public class ShapedImageView extends AppCompatImageView {

    public static final int SHAPE_MODE_ROUND_RECT = 1;
    public static final int SHAPE_MODE_CIRCLE = 2;

    private int mShapeMode = 0;
    private float mRadius = 0;
    private int mStrokeColor = 0x26000000;
    private float mStrokeWidth = 0;
    private boolean mShapeChanged;

    private Path mPath;
    private Shape mShape, mStrokeShape;
    private Paint mPaint, mStrokePaint, mPathPaint, mMaskPaint;
    private Bitmap mShapeBitmap, mStrokeBitmap,mMaskBitmap;

    private PathExtension mExtension;

    private PorterDuffXfermode DST_IN = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private PorterDuffXfermode DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);

    private boolean isHavaMask = false;
    private int mRoundMode;

    public ShapedImageView(Context context) {
        super(context);
        init(null);
    }

    public ShapedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShapedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setLayerType(LAYER_TYPE_HARDWARE, null);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShapedImageView);
            mShapeMode = a.getInt(R.styleable.ShapedImageView_shape_mode, 0);
            mRadius = a.getDimension(R.styleable.ShapedImageView_round_radius, 0);

            mStrokeWidth = a.getDimension(R.styleable.ShapedImageView_stroke_width, 0);
            mStrokeColor = a.getColor(R.styleable.ShapedImageView_stroke_color, mStrokeColor);

            mRoundMode = a.getInt(R.styleable.ShapedImageView_round_mode, 0);

            a.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setXfermode(DST_IN);

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setFilterBitmap(true);
        mStrokePaint.setColor(Color.BLACK);

        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setFilterBitmap(true);
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setXfermode(DST_OUT);

        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint.setFilterBitmap(true);
        mMaskPaint.setColor(Color.WHITE);
        mMaskPaint.setXfermode(DST_OUT);
        mMaskPaint.setAlpha(255/3*2);

        mPath = new Path();

        mMaskBitmap = ImageUtil.getBitmap(R.mipmap.hotel_icon_mask);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed || mShapeChanged) {
            mShapeChanged = false;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            switch (mShapeMode) {
                case SHAPE_MODE_ROUND_RECT:
                    break;
                case SHAPE_MODE_CIRCLE:
                    int min = Math.min(width, height);
                    mRadius = (float) min / 2;
                    break;
            }

            if (mShape == null || mRadius != 0) {
                float[] radius = new float[8];
                Arrays.fill(radius, mRadius);
                if(mRoundMode == 1){//top
                    radius[4] = 0;
                    radius[5] = 0;
                    radius[6] = 0;
                    radius[7] = 0;
                }else if(mRoundMode == 2){//bottom
                    radius[0] = 0;
                    radius[1] = 0;
                    radius[2] = 0;
                    radius[3] = 0;
                }
                mShape = new RoundRectShape(radius, null, null);
                mStrokeShape = new RoundRectShape(radius, null, null);
            }
            mShape.resize(width, height);
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            makeStrokeBitmap();
            makeShapeBitmap();
            makeMaskBitmap();

            if (mExtension != null) {
                mExtension.onLayout(mPath, width, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mStrokeWidth > 0 && mStrokeShape != null) {
            if (mStrokeBitmap == null || mStrokeBitmap.isRecycled()) {
                makeStrokeBitmap();
            }
            int i = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
            mStrokePaint.setXfermode(null);
            canvas.drawBitmap(mStrokeBitmap, 0, 0, mStrokePaint);
            canvas.translate(mStrokeWidth, mStrokeWidth);
            mStrokePaint.setXfermode(DST_OUT);
            mStrokeShape.draw(canvas, mStrokePaint);
            canvas.restoreToCount(i);
        }

        if (mExtension != null) {
            canvas.drawPath(mPath, mPathPaint);
        }

        switch (mShapeMode) {
            case SHAPE_MODE_ROUND_RECT:
            case SHAPE_MODE_CIRCLE:
                if (mShapeBitmap == null || mShapeBitmap.isRecycled()) {
                    makeShapeBitmap();
                }
                canvas.drawBitmap(mShapeBitmap, 0, 0, mPaint);
                break;
        }

        if(isHavaMask) {
            makeMaskBitmap();
            canvas.drawBitmap(mMaskBitmap, 0, 0, mMaskPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseBitmap(mShapeBitmap);
        releaseBitmap(mStrokeBitmap);
    }

    private void makeStrokeBitmap() {
        if (mStrokeWidth <= 0) return;

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return;

        releaseBitmap(mStrokeBitmap);

        mStrokeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mStrokeBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(mStrokeColor);
        c.drawRect(new RectF(0, 0, w, h), p);
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    private void makeShapeBitmap() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return;

        releaseBitmap(mShapeBitmap);

        mShapeBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mShapeBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);
        mShape.draw(c, p);
    }

    private void makeMaskBitmap() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w == 0 || h == 0) return;

        releaseBitmap(mMaskBitmap);

        mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mMaskBitmap);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLACK);
        mShape.draw(c, p);
    }

    public void setExtension(PathExtension extension) {
        mExtension = extension;
        requestLayout();
    }

    public void setHavaMask(boolean havaMask) {
        isHavaMask = havaMask;
        postInvalidate();
    }

    public void setStroke(int strokeColor, float strokeWidth) {
        if (mStrokeWidth <= 0) return;

        if (mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            mStrokeShape.resize(width - mStrokeWidth * 2, height - mStrokeWidth * 2);

            postInvalidate();
        }

        if (mStrokeColor != strokeColor) {
            mStrokeColor = strokeColor;

            makeStrokeBitmap();
            postInvalidate();
        }
    }

    public void setStrokeColor(int strokeColor) {
        setStroke(strokeColor, mStrokeWidth);
    }

    public void setStrokeWidth(float strokeWidth) {
        setStroke(mStrokeColor, strokeWidth);
    }

    public void setShape(int shapeMode, float radius) {
        mShapeChanged = mShapeMode != shapeMode || mRadius != radius;

        if (mShapeChanged) {
            mShapeMode = shapeMode;
            mRadius = radius;

            mShape = null;
            mStrokeShape = null;
            requestLayout();
        }
    }

    public void setShapeMode(int shapeMode) {
        setShape(shapeMode, mRadius);
    }

    public void setShapeRadius(float radius) {
        setShape(mShapeMode, radius);
    }

    public interface PathExtension {
        void onLayout(Path path, int width, int height);
    }

}