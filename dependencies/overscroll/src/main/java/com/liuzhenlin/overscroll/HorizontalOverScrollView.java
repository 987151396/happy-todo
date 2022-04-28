/*
 * Created on 2017/12/23.
 * Copyright © 2017 刘振林. All rights reserved.
 */

package com.liuzhenlin.overscroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.ArraySet;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;

import com.liuzhenlin.overscroll.listener.OverFlyingDetector;

import java.lang.reflect.Field;
import java.util.Set;

import static android.os.Build.VERSION_CODES.CUPCAKE;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static androidx.customview.widget.ViewDragHelper.INVALID_POINTER;

/**
 * @author <a href="mailto:2233788867@qq.com">刘振林</a>
 */
@SuppressLint("LongLogTag")
@RequiresApi(api = CUPCAKE)
public class HorizontalOverScrollView extends HorizontalScrollView implements OverScrollingView,
        OverFlyingDetector.OnOverFlyingListener,
        ViewPropertyAnimatorListener, ViewPropertyAnimatorUpdateListener {
    private static final String TAG = "HorizontalOverScrollView";
    private static final boolean DEBUG = false;

    private View mInnerView;

    /** Distance to travel before drag may begin */
    protected final int mTouchSlop;

    /** Last known pointer id for touch events */
    private int mActivePointerId = INVALID_POINTER;

    private final float[] mTouchX = new float[2];
    private final float[] mTouchY = new float[2];

    /**
     * Whether the overscroll of this view is enabled
     *
     * @see #setOverScrollEnabled(boolean),#isOverScrollEnabled()
     */
    private boolean mIsOverScrollEnabled;

    /** @see #getOverScrollEdge() */
    @OverScrollEdge
    private int mOverScrollEdge = OVERSCROLL_EDGE_UNSPECIFIED;

    /** @see #getOverScrollState() */
    @OverScrollState
    private int mOverScrollState = OVERSCROLL_STATE_IDLE;

    /** @see #getOverScrollOffset() */
    private float mOverScrollOffset;

    /** The time in milliseconds that the springback of overscroll will last for */
    private static final int DURATION_SPRING_BACK = 250;

    /** @see #setOnScrollCallback(OnScrollCallback) */
    private OnScrollCallback mScrollCallback;

    /** Detector to detect the flying of this view and to judge whether it is overflying */
    private final OverFlyingDetector mOverFlyingDetector;

    /** Interpolator for the overscroll animator{@link #animate()} */
    private final Interpolator mInterpolator = new DecelerateInterpolator();

    /** Whether the animator{@link #animate()} animating overscroll is running or not */
    private boolean mIsAnimatorRunning;

    @Override
    public boolean isOverScrollEnabled() {
        return mIsOverScrollEnabled;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setOverScrollEnabled(boolean enabled) {
        mIsOverScrollEnabled = enabled;
        if (enabled)
            // Disable the fluorescence effect of this view being pulled to some end
            ViewCompat.setOverScrollMode(this, ViewCompat.OVER_SCROLL_NEVER);
        else
            ViewCompat.setOverScrollMode(this, ViewCompat.OVER_SCROLL_ALWAYS);
    }

    @Override
    public void setOnScrollCallback(@Nullable OnScrollCallback callback) {
        mScrollCallback = callback;
    }

    /**
     * @return the edge of overscroll if it is going on, maybe one of {@link #OVERSCROLL_EDGE_START},
     * {@link #OVERSCROLL_EDGE_END}, {@link #OVERSCROLL_EDGE_START_OR_END},
     * or else it will be {@link #OVERSCROLL_EDGE_UNSPECIFIED}
     */
    @OverScrollEdge
    public int getOverScrollEdge() {
        return mOverScrollEdge;
    }

    /**
     * @return the current state of overscroll, may be one of {@link #OVERSCROLL_STATE_IDLE},
     * {@link #OVERSCROLL_STATE_TOUCH_SCROLL}, {@link #OVERSCROLL_STATE_AUTO_SCROLL}
     */
    @OverScrollState
    public int getOverScrollState() {
        return mOverScrollState;
    }

    /** @return the current horizontal offset of the overscroll in this view */
    public float getOverScrollOffset() {
        return mOverScrollOffset;
    }

    public HorizontalOverScrollView(Context context) {
        this(context, null);
    }

    public HorizontalOverScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalOverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalOverScrollView, defStyleAttr, 0);
        setOverScrollEnabled(a.getBoolean(R.styleable
                .HorizontalOverScrollView_overscrollEnabled, true));
        a.recycle();

        mOverFlyingDetector = new OverFlyingDetector();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (DEBUG)
            addOnOverScrollListener(new OnOverScrollListener() {
                @Override
                public void onOverScrollStart(@NonNull OverScrollingView parent, @NonNull View view, int edge) {
                    Log.d(TAG, "onOverScrollStart edge=" + edge);
                }

                @Override
                public void onOverScrollEnd(@NonNull OverScrollingView parent, @NonNull View view, int edge) {
                    Log.d(TAG, "onOverScrollEnd edge=" + edge);
                }

                @Override
                public void onOverScrollOffsetChange(@NonNull OverScrollingView parent, @NonNull View view, float offset) {
                    Log.d(TAG, "onOverScrollOffsetChange   offset=" + offset);
                }

                @Override
                public void onOverScrollStateChange(@NonNull OverScrollingView parent, @NonNull View view, int state) {
                    Log.d(TAG, "onOverScrollStateChange   state=" + state);
                }
            });
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 1)
            mInnerView = getChildAt(0);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        mInnerView = child;
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        mInnerView = null;
    }

    public final boolean isLayoutRtl() {
        return Build.VERSION.SDK_INT >= JELLY_BEAN_MR1 && getLayoutDirection() == LAYOUT_DIRECTION_RTL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
            case MotionEventCompat.ACTION_POINTER_DOWN:
                final int actionIndex = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, actionIndex);
                markCurrTouchPoint(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                final int index = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id "
                            + mActivePointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                markCurrTouchPoint(ev);
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressWarnings("deprecation")
    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up.
            // Choose a new active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            markCurrTouchPoint(ev);
        }
    }

    @SuppressWarnings("deprecation")
    private void markCurrTouchPoint(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
        System.arraycopy(mTouchX, 1, mTouchX, 0, mTouchX.length - 1);
        mTouchX[mTouchX.length - 1] = MotionEventCompat.getX(ev, pointerIndex);
        System.arraycopy(mTouchY, 1, mTouchY, 0, mTouchY.length - 1);
        mTouchY[mTouchY.length - 1] = MotionEventCompat.getY(ev, pointerIndex);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Only when we have intercepted touch event and over-scroll is enabled
        // can we handle overflying.
        if (ev.getAction() != MotionEvent.ACTION_UP && mIsOverScrollEnabled && mInnerView != null)
            mOverFlyingDetector.handleOverFling(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return handleOverScroll(ev) || super.onTouchEvent(ev);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SwitchIntDef")
    @Override
    public boolean handleOverScroll(@NonNull MotionEvent ev) {
        if (!(mIsOverScrollEnabled && mInnerView != null))
            return false;

        final boolean handledAsOverFlying = mOverFlyingDetector.handleOverFling(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                switch (mOverScrollState) {
                    case OVERSCROLL_STATE_IDLE: {
                        // It will be positive when the user's finger moves from start to end in horizontal
                        final float dx = isLayoutRtl() ? mTouchX[mTouchX.length - 2] - mTouchX[mTouchX.length - 1]
                                : mTouchX[mTouchX.length - 1] - mTouchX[mTouchX.length - 2];
                        final boolean scrolledToStart = hasScrolledToStart();
                        final boolean scrolledToEnd = hasScrolledToEnd();
                        // If the width of the child is less than the current view's,
                        // there'll be no limit for over-scrolling this view from horizontal directions.
                        if (scrolledToStart && scrolledToEnd) {
                            mOverScrollEdge = OVERSCROLL_EDGE_START_OR_END;
                            // pull it from the horizontal start
                        } else if (scrolledToStart && dx > 0f) {
                            mOverScrollEdge = OVERSCROLL_EDGE_START;
                            // pull it from the end of horizontal
                        } else if (scrolledToEnd && dx < 0f) {
                            mOverScrollEdge = OVERSCROLL_EDGE_END;
                        } else break;
                        deliverOverScrollStartIfNeeded();
                        deliverOverScrollStateChangeIfNeeded(OVERSCROLL_STATE_TOUCH_SCROLL);
                        return true;
                    }
                    case OVERSCROLL_STATE_TOUCH_SCROLL: {
                        final float dx = computeOverScrollDeltaOffsetX();
                        if (dx == 0f) return true;

                        final float offset = ViewCompat.getTranslationX(mInnerView);
                        switch (mOverScrollEdge) {
                            case OVERSCROLL_EDGE_START: {
                                final boolean rtl = isLayoutRtl();
                                float newOffset = offset + dx;
                                if (!rtl && newOffset < 0f)
                                    newOffset = 0f;
                                else if (rtl && newOffset > 0f)
                                    newOffset = 0f;
                                // Translate the current shown child/page to make it scroll just
                                // as if this view was overscrolling.
                                ViewCompat.setTranslationX(mInnerView, newOffset);
                                deliverOverScrollOffsetChangeIfNeeded();

                                // Refresh the touch x cached in our parent class for fear that
                                // this view will scroll its contents beyond the distance we need
                                // it to scroll by on the user scrolling the horizontal start
                                // of the contents back to its initial position.
                                invalidateParentClassCachedTouchX();
                                // We should end the overscroll on the user dragging the start of the
                                // contents back to its initial position to enable general scrolling.
                                if (newOffset == 0f)
                                    endOverScroll();
                                return true;
                            }
                            case OVERSCROLL_EDGE_END: {
                                final boolean rtl = isLayoutRtl();
                                float newOffset = offset + dx;
                                if (!rtl && newOffset > 0f)
                                    newOffset = 0f;
                                else if (rtl && newOffset < 0f)
                                    newOffset = 0f;
                                ViewCompat.setTranslationX(mInnerView, newOffset);
                                deliverOverScrollOffsetChangeIfNeeded();

                                invalidateParentClassCachedTouchX();
                                if (newOffset == 0f)
                                    endOverScroll();
                                return true;
                            }
                            case OVERSCROLL_EDGE_START_OR_END:
                                ViewCompat.setTranslationX(mInnerView, offset + dx);
                                deliverOverScrollOffsetChangeIfNeeded();
                                return true;
                        }
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                // If handling this gesture as overflying, we do not need to spring this view back
                // through the animator since it will be auto-over-scrolled and auto-bounced back
                // after a few milliseconds.
                if (handledAsOverFlying) break;
            case MotionEvent.ACTION_CANCEL:
                if (mOverScrollState == OVERSCROLL_STATE_TOUCH_SCROLL)
                    animateHorizontalOverScroll(ViewCompat
                            .getTranslationX(mInnerView), 0f, DURATION_SPRING_BACK);
                break;
        }
        return false;
    }

    /**
     * End the overscroll started by user's dragging or animator {@link #animate()}
     */
    private void endOverScroll() {
        // Only when the animator is not animating the overscroll can we really end it
        // for fear that the overscroll has been set to end but the animator is also running.
        if (!mIsAnimatorRunning) {
            deliverOverScrollStateChangeIfNeeded(OVERSCROLL_STATE_IDLE);
            deliverOverScrollEnd();
            mOverScrollEdge = OVERSCROLL_EDGE_UNSPECIFIED;
        }
    }

    @Override
    public float computeOverScrollDeltaOffsetY() {
        return 0f;
    }

    @Override
    public float computeOverScrollDeltaOffsetX() {
        if (mOverScrollState != OVERSCROLL_STATE_TOUCH_SCROLL)
            return 0f;

        final float dx = mTouchX[mTouchX.length - 1] - mTouchX[mTouchX.length - 2];
        // noinspection deprecation
        final float offset = ViewCompat.getTranslationX(mInnerView);
        // Return the displacement each time the user's finger moves from right to left
        // after he/she over-scrolled it from left and vice versa.
        if (offset > 0f && dx < 0f || offset < 0f && dx > 0f)
            return dx;
        else {
            MarginLayoutParams mlp = (MarginLayoutParams) mInnerView.getLayoutParams();
            final float ratio = Math.abs(offset) /
                    ((getWidth() - getPaddingLeft() - getPaddingRight() - mlp.leftMargin - mlp.rightMargin) * 0.95f);
            return (float) (1d / (2d + Math.tan(Math.PI / 2d * ratio)) * dx);
        }
    }

    @Override
    public void animateVerticalOverScroll(float from, float to, int duration) {

    }

    @Override
    public void animateHorizontalOverScroll(float from, float to, int duration) {
        final boolean rtl = isLayoutRtl();
        final float dx = to - from;
        if (dx == 0) return;

        MarginLayoutParams mlp = (MarginLayoutParams) mInnerView.getLayoutParams();
        if (mInnerView.getWidth() <= getWidth()
                - getPaddingLeft() - getPaddingRight() - mlp.leftMargin - mlp.rightMargin)
            mOverScrollEdge = OVERSCROLL_EDGE_START_OR_END;
        else if (rtl)
            mOverScrollEdge = dx < 0 ? OVERSCROLL_EDGE_START : OVERSCROLL_EDGE_END;
        else
            mOverScrollEdge = dx > 0 ? OVERSCROLL_EDGE_START : OVERSCROLL_EDGE_END;

        ViewCompat.animate(mInnerView).setListener(this).setUpdateListener(this)
                .translationX(to).setDuration(duration).setInterpolator(mInterpolator).start();
    }

    @Override
    public void onAnimationStart(View view) {
        mIsAnimatorRunning = true;
        deliverOverScrollStartIfNeeded();
        deliverOverScrollStateChangeIfNeeded(OVERSCROLL_STATE_AUTO_SCROLL);
    }

    @Override
    public void onAnimationUpdate(View view) {
        deliverOverScrollOffsetChangeIfNeeded();
    }

    @Override
    public void onAnimationEnd(View view) {
        mIsAnimatorRunning = false;
        // noinspection deprecation
        final float offset = ViewCompat.getTranslationX(mInnerView);
        // Bounce the contents in this view to its initial position
        // (the position before it is over-scrolled) if needed
        if (offset != 0f) {
            animateHorizontalOverScroll(offset, 0f, DURATION_SPRING_BACK);
        } else {
            ViewCompat.animate(mInnerView).setListener(null).setUpdateListener(null);
            endOverScroll();
        }
    }

    @Override
    public void onAnimationCancel(View view) {
    }

    @Override
    public void onStartEdgeOverFling(float overWidth, int duration) {
        // noinspection deprecation
        animateHorizontalOverScroll(ViewCompat
                .getTranslationX(mInnerView), isLayoutRtl() ? -overWidth : overWidth, duration);
    }

    @Override
    public void onEndEdgeOverFling(float overWidth, int duration) {
        // noinspection deprecation
        animateHorizontalOverScroll(ViewCompat
                .getTranslationX(mInnerView), isLayoutRtl() ? overWidth : -overWidth, duration);
    }

    @Override
    public void onTopEdgeOverFling(float overHeight, int duration) {
    }

    @Override
    public void onBottomEdgeOverFling(float overHeight, int duration) {
    }

    protected class OverFlyingDetector extends com.liuzhenlin.overscroll.listener.OverFlyingDetector {
        @SuppressWarnings("WeakerAccess")
        public OverFlyingDetector() {
            super(HorizontalOverScrollView.this, HorizontalOverScrollView.this);
        }

        @Override
        protected boolean hasViewScrolledToTop() {
            return false;
        }

        @Override
        protected boolean hasViewScrolledToBottom() {
            return false;
        }

        @Override
        protected boolean hasViewScrolledToStart() {
            return hasScrolledToStart();
        }

        @Override
        protected boolean hasViewScrolledToEnd() {
            return hasScrolledToEnd();
        }
    }

    @Override
    public boolean hasScrolledToStart() {
        if (mScrollCallback != null) {
            return mScrollCallback.hasScrolledToStart(this);

        } else if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWICH) {
            return !canScrollHorizontally(isLayoutRtl() ? 1 : -1);
        }
        return false;
    }

    @Override
    public boolean hasScrolledToEnd() {
        if (mScrollCallback != null) {
            return mScrollCallback.hasScrolledToEnd(this);

        } else if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWICH) {
            return !canScrollHorizontally(isLayoutRtl() ? -1 : 1);
        }
        return false;
    }

    @Override
    public boolean hasScrolledToTop() {
        return false;
    }

    @Override
    public boolean hasScrolledToBottom() {
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // OverScroll Listener
    ///////////////////////////////////////////////////////////////////////////

    private void deliverOverScrollStartIfNeeded() {
        if (mOverScrollState == OVERSCROLL_STATE_IDLE && mOnOverScrollListeners != null)
            for (OnOverScrollListener listener : mOnOverScrollListeners)
                listener.onOverScrollStart(this, mInnerView, mOverScrollEdge);
    }

    private void deliverOverScrollStateChangeIfNeeded(int state) {
        if (mOverScrollState != state) {
            mOverScrollState = state;
            if (mOnOverScrollListeners != null)
                for (OnOverScrollListener listener : mOnOverScrollListeners)
                    listener.onOverScrollStateChange(this, mInnerView, mOverScrollState);
        }
    }

    private void deliverOverScrollOffsetChangeIfNeeded() {
        // noinspection deprecation
        final float offset = ViewCompat.getTranslationX(mInnerView);
        if (mOverScrollOffset != offset) {
            mOverScrollOffset = offset;
            if (mOnOverScrollListeners != null)
                for (OnOverScrollListener listener : mOnOverScrollListeners)
                    listener.onOverScrollOffsetChange(this, mInnerView, mOverScrollOffset);
        }
    }

    private void deliverOverScrollEnd() {
        if (mOnOverScrollListeners != null)
            for (OnOverScrollListener listener : mOnOverScrollListeners)
                listener.onOverScrollEnd(this, mInnerView, mOverScrollEdge);
    }

    private Set<OnOverScrollListener> mOnOverScrollListeners;

    @Override
    public void addOnOverScrollListener(@NonNull OnOverScrollListener listener) {
        if (mOnOverScrollListeners == null)
            mOnOverScrollListeners = new ArraySet<>();
        mOnOverScrollListeners.add(listener);
    }

    @Override
    public void removeOnOverScrollListener(@Nullable OnOverScrollListener listener) {
        if (mOnOverScrollListeners != null)
            mOnOverScrollListeners.add(listener);
    }

    @Override
    public void clearOnOverScrollListeners() {
        if (mOnOverScrollListeners != null)
            mOnOverScrollListeners.clear();
    }

    ///////////////////////////////////////////////////////////////////////////
    // reflection method
    ///////////////////////////////////////////////////////////////////////////

    private Field mLastMotionXField;

    /**
     * Refresh the cached touch X {@link HorizontalScrollView#mLastMotionX}
     * of {@link HorizontalScrollView} to ensure it will scroll left or right
     * within {@code Math.abs(mTouchX[mTouchX.length-1] - mTouchX[mTouchX.length-2])} pixels
     * when it receives touch event again.
     */
    private void invalidateParentClassCachedTouchX() {
        try {
            if (mLastMotionXField == null) {
                // noinspection JavaReflectionMemberAccess
                mLastMotionXField = HorizontalScrollView.class.getDeclaredField("mLastMotionX");
                mLastMotionXField.setAccessible(true);
            }
            mLastMotionXField.set(this, (int) (mTouchX[mTouchX.length - 2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}