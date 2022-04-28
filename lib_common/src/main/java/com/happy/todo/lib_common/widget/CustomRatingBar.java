package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.happy.todo.lib_common.R;

import java.math.BigDecimal;

/**
 * 自定义的Ratingar
 */
public class CustomRatingBar extends LinearLayout {
    private boolean mClickable;
    private boolean halfstart;
    private int starCount;
    private float startLightCount;
    private OnRatingChangeListener onRatingChangeListener;
    private float starImageWidth;
    private float starImageHeight;
    private float starImagePadding;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;
    private int y = 1;
    private boolean isEmpty = true;

    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }


    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setmClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public void halfStar(boolean halfstart) {
        this.halfstart = halfstart;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarImageWidth(float starImageWidth) {
        this.starImageWidth = starImageWidth;
    }

    public void setStarImageHeight(float starImageHeight) {
        this.starImageHeight = starImageHeight;
    }


    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setImagePadding(float starImagePadding) {
        this.starImagePadding = starImagePadding;
    }


    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingBar);

        starHalfDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_starHalfDrawable);
        starEmptyDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_starEmptyDrawable);
        starFillDrawable = typedArray.getDrawable(R.styleable.CustomRatingBar_starFillDrawable);
        starImageWidth = typedArray.getDimension(R.styleable.CustomRatingBar_starImageWidth, 60);
        starImageHeight = typedArray.getDimension(R.styleable.CustomRatingBar_starImageHeight, 120);
        starImagePadding = typedArray.getDimension(R.styleable.CustomRatingBar_starImagePadding, 15);
        starCount = typedArray.getInteger(R.styleable.CustomRatingBar_starCount, 5);
        startLightCount = typedArray.getFloat(R.styleable.CustomRatingBar_startLightCount, 0f);
        mClickable = typedArray.getBoolean(R.styleable.CustomRatingBar_clickable, true);
        halfstart = typedArray.getBoolean(R.styleable.CustomRatingBar_halfStart, false);

        typedArray.recycle();

        for (int i = 0; i < starCount; ++i) {
            ImageView imageView = getStarImageView(context, isEmpty);
            imageView.setOnClickListener(
                    v -> {
                        if (mClickable) {
                            if (halfstart) {
                                if (y % 2 == 0) {
                                    setLightStar(indexOfChild(v) + 1f);
                                } else {
                                    setLightStar(indexOfChild(v) + 0.5f);
                                }
                                if (onRatingChangeListener != null) {
                                    if (y % 2 == 0) {
                                        onRatingChangeListener.onRatingChange(indexOfChild(v) + 1f);
                                        y++;
                                    } else {
                                        onRatingChangeListener.onRatingChange(indexOfChild(v) + 0.5f);
                                        y++;
                                    }
                                }
                            } else {
                                setLightStar(indexOfChild(v) + 1f);
                                if (onRatingChangeListener != null) {
                                    onRatingChangeListener.onRatingChange(indexOfChild(v) + 1f);
                                }
                            }

                        }

                    }
            );
            addView(imageView);
        }

        setLightStar(startLightCount);
    }


    private ImageView getStarImageView(Context context, boolean isEmpty) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(
                Math.round(starImageWidth),
                Math.round(starImageHeight)
        );
        imageView.setLayoutParams(para);
        imageView.setPadding(0, 0, Math.round(starImagePadding), 0);
        if (isEmpty) {
            imageView.setImageDrawable(starEmptyDrawable);
        } else {
            imageView.setImageDrawable(starFillDrawable);
        }
        return imageView;
    }

    /**
     * 点亮星星数
     *
     * @param starCount
     */
    public void setLightStar(float starCount) {
        startLightCount = starCount;
        int fint = (int) starCount;
        BigDecimal b1 = new BigDecimal(Float.toString(starCount));
        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
        float fPoint = b1.subtract(b2).floatValue();


        starCount = fint > this.starCount ? this.starCount : fint;
        starCount = starCount < 0 ? 0 : starCount;

        //draw full star
        for (int i = 0; i < starCount; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }

        //draw half star
        if (fPoint > 0) {
            ((ImageView) getChildAt(fint)).setImageDrawable(starHalfDrawable);

            //draw empty star
            for (int i = this.starCount - 1; i >= starCount + 1; --i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
            }

        } else {
            //draw empty star
            for (int i = this.starCount - 1; i >= starCount; --i) {
                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
            }

        }

    }

    public float getStar() {
        return startLightCount;
    }

    /**
     * change start listener
     */
    public interface OnRatingChangeListener {

        void onRatingChange(float RatingCount);

    }

}