/*
 * Created on 2017/12/28.
 * Copyright © 2017 刘振林. All rights reserved.
 */

package com.liuzhenlin.overscroll;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.liuzhenlin.overscroll.listener.OverFlyingDetector;

import java.lang.reflect.Field;
import java.util.Set;

import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH;
import static androidx.customview.widget.ViewDragHelper.INVALID_POINTER;

/**
 * @author <a href="mailto:2233788867@qq.com">刘振林</a>
 */
public class OverScrollView extends NestedScrollView implements OverScrollingView,
        OverFlyingDetector.OnOverFlyingListener,
        ViewPropertyAnimatorListener, ViewPropertyAnimatorUpdateListener {
    private static final String TAG = "OverScrollView";
    private static final boolean DEBUG = false;

    private View mInnerView;

    /** Distance to travel before drag may begin */
    protected final float mTouchSlop;

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
     * @return the edge of overscroll if it is going on, maybe one of {@link #OVERSCROLL_EDGE_TOP},
     * {@link #OVERSCROLL_EDGE_BOTTOM}, {@link #OVERSCROLL_EDGE_TOP_OR_BOTTOM},
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

    /** @return the current vertical offset of the overscroll in this view */
    public float getOverScrollOffset() {
        return mOverScrollOffset;
    }

    public OverScrollView(Context context) {
        this(context, null);
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OverScrollView, defStyleAttr, 0);
        setOverScrollEnabled(a.getBoolean(R.styleable.OverScrollView_overscrollEnabled, true));
        a.recycle();

        mOverFlyingDetector = new OverFlyingDetector();
        mTouchSlop = ViewConfiguration.getTouchSlop() * context.getResources().getDisplayMetrics().density;
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
                        // It will be positive when the user's finger moves from top to bottom
                        final float dy = mTouchY[mTouchY.length - 1] - mTouchY[mTouchY.length - 2];
                        final boolean scrolledToTop = hasScrolledToTop();
                        final boolean scrolledToBottom = hasScrolledToBottom();
                        // If the height of the child is less than the current view's,
                        // there'll be no limit for over-scrolling this view from vertical directions.
                        if (scrolledToTop && scrolledToBottom) {
                            mOverScrollEdge = OVERSCROLL_EDGE_TOP_OR_BOTTOM;
                            // pull it from the top
                        } else if (scrolledToTop && dy > 0) {
                            mOverScrollEdge = OVERSCROLL_EDGE_TOP;
                            // pull it from the bottom
                        } else if (scrolledToBottom && dy < 0) {
                            mOverScrollEdge = OVERSCROLL_EDGE_BOTTOM;
                        } else break;
                        deliverOverScrollStartIfNeeded();
                        deliverOverScrollStateChangeIfNeeded(OVERSCROLL_STATE_TOUCH_SCROLL);
                        return true;
                    }
                    case OVERSCROLL_STATE_TOUCH_SCROLL: {
                        final float dy = computeOverScrollDeltaOffsetY();
                        if (dy == 0f) return true;

                        final float offset = ViewCompat.getTranslationY(mInnerView);
                        switch (mOverScrollEdge) {
                            case OVERSCROLL_EDGE_TOP: {
                                final float newOffset = offset + dy <= 0f ? 0f : offset + dy;
                                // Translate the current shown child/page to make it scroll just
                                // as if this view was overscrolling.
                                ViewCompat.setTranslationY(mInnerView, newOffset);
                                deliverOverScrollOffsetChangeIfNeeded();

                                if (newOffset < offset) {
                                    // Refresh the touch y cached in our parent class for fear that
                                    // this view will scroll its contents beyond the distance we need
                                    // it to scroll by on the user scrolling the top of the contents
                                    // back to its initial position.
                                    invalidateParentClassCachedTouchY();
                                    // We should end the overscroll on the user dragging the top of the
                                    // contents back to its initial position to enable general scrolling.
                                    if (newOffset == 0f)
                                        endOverScroll();
                                    return true;
                                }
                                // Not consume this event when user scroll this view down,
                                // to enable nested scrolling.
                                break;
                            }
                            case OVERSCROLL_EDGE_BOTTOM: {
                                final float newOffset = offset + dy >= 0f ? 0f : offset + dy;
                                ViewCompat.setTranslationY(mInnerView, newOffset);
                                deliverOverScrollOffsetChangeIfNeeded();

                                if (newOffset > offset) {
                                    invalidateParentClassCachedTouchY();
                                    if (newOffset == 0f)
                                        endOverScroll();
                                    return true;
                                }
                                break;
                            }
                            case OVERSCROLL_EDGE_TOP_OR_BOTTOM: {
                                final float newOffset = offset + dy;
                                ViewCompat.setTranslationY(mInnerView, newOffset);
                                deliverOverScrollOffsetChangeIfNeeded();

                                if (newOffset > 0f && newOffset < offset
                                        || newOffset < 0f && newOffset > offset) {
                                    invalidateParentClassCachedTouchY();
                                    return true;
                                }
                                break;
                            }
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
                    animateVerticalOverScroll(ViewCompat
                            .getTranslationY(mInnerView), 0f, DURATION_SPRING_BACK);
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
        if (mOverScrollState != OVERSCROLL_STATE_TOUCH_SCROLL)
            return 0f;

        final float dy = mTouchY[mTouchY.length - 1] - mTouchY[mTouchY.length - 2];
        // noinspection deprecation
        final float offset = ViewCompat.getTranslationY(mInnerView);
        // Return the displacement each time the user's finger moves from bottom to top
        // after he/she over-scrolled it from top and vice versa.
        if (offset > 0f && dy < 0f || offset < 0f && dy > 0f) {
            return dy;
        } else {
            MarginLayoutParams mlp = (MarginLayoutParams) mInnerView.getLayoutParams();
            final float ratio = Math.abs(offset) /
                    ((getHeight() - getPaddingTop() - getPaddingBottom() - mlp.topMargin - mlp.bottomMargin) * 0.95f);
            return (float) (1d / (2d + Math.tan(Math.PI / 2d * ratio)) * dy);
        }
    }

    @Override
    public float computeOverScrollDeltaOffsetX() {
        return 0f;
    }

    @Override
    public void animateVerticalOverScroll(float from, float to, int duration) {
        final float offset = to - from;
        if (offset == 0f) return;

        MarginLayoutParams mlp = (MarginLayoutParams) mInnerView.getLayoutParams();
        if (mInnerView.getHeight() <= getHeight()
                - getPaddingTop() - getPaddingBottom() - mlp.topMargin - mlp.bottomMargin)
            mOverScrollEdge = OVERSCROLL_EDGE_TOP_OR_BOTTOM;
        else if (offset > 0f) mOverScrollEdge = OVERSCROLL_EDGE_TOP;
        else if (offset < 0f) mOverScrollEdge = OVERSCROLL_EDGE_BOTTOM;

        ViewCompat.animate(mInnerView).setListener(this).setUpdateListener(this)
                .translationY(to).setDuration(duration).setInterpolator(mInterpolator).start();
    }

    @Override
    public void animateHorizontalOverScroll(float from, float to, int duration) {

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
        final float offset = ViewCompat.getTranslationY(mInnerView);
        // Bounce the contents in this view to its initial position
        // (the position before it is over-scrolled) if needed
        if (offset != 0f) {
            animateVerticalOverScroll(offset, 0f, DURATION_SPRING_BACK);
        } else {
            ViewCompat.animate(mInnerView).setListener(null).setUpdateListener(null);
            endOverScroll();
        }
    }

    @Override
    public void onAnimationCancel(View view) {
    }

    @Override
    public void onTopEdgeOverFling(float overHeight, int duration) {
        // noinspection deprecation
        animateVerticalOverScroll(ViewCompat.getTranslationY(mInnerView), overHeight, duration);
    }

    @Override
    public void onBottomEdgeOverFling(float overHeight, int duration) {
        // noinspection deprecation
        animateVerticalOverScroll(ViewCompat.getTranslationY(mInnerView), -overHeight, duration);
    }

    @Override
    public void onStartEdgeOverFling(float overWidth, int duration) {
    }

    @Override
    public void onEndEdgeOverFling(float overWidth, int duration) {
    }

    protected class OverFlyingDetector extends com.liuzhenlin.overscroll.listener.OverFlyingDetector {

        @SuppressWarnings("WeakerAccess")
        public OverFlyingDetector() {
            super(OverScrollView.this, OverScrollView.this);
        }

        @Override
        protected boolean hasViewScrolledToTop() {
            return hasScrolledToTop();
        }

        @Override
        protected boolean hasViewScrolledToBottom() {
            return hasScrolledToBottom();
        }

        @Override
        protected boolean hasViewScrolledToStart() {
            return false;
        }

        @Override
        protected boolean hasViewScrolledToEnd() {
            return false;
        }
    }

    @Override
    public boolean hasScrolledToTop() {
        if (mScrollCallback != null) {
            return mScrollCallback.hasScrolledToTop(this);

        } else if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWICH) {
            return !canScrollVertically(-1);
        }
        return false;
    }

    @Override
    public boolean hasScrolledToBottom() {
        if (mScrollCallback != null) {
            return mScrollCallback.hasScrolledToBottom(this);

        } else if (Build.VERSION.SDK_INT >= ICE_CREAM_SANDWICH) {
            return !canScrollVertically(1);
        }
        return false;
    }

    @Override
    public boolean hasScrolledToStart() {
        return false;
    }

    @Override
    public boolean hasScrolledToEnd() {
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
        final float offset = ViewCompat.getTranslationY(mInnerView);
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

    private Field mLastMotionYField;

    /**
     * Refresh the cached touch Y {@link NestedScrollView#mLastMotionY}
     * of {@link NestedScrollView} to ensure it will scroll up or down
     * within {@code Math.abs(mTouchY[mTouchY.length-1] - mTouchY[mTouchY.length-2])} pixels
     * when it receives touch event again.
     */
    private void invalidateParentClassCachedTouchY() {
        try {
            if (mLastMotionYField == null) {
                mLastMotionYField = NestedScrollView.class.getDeclaredField("mLastMotionY");
                mLastMotionYField.setAccessible(true);
            }
            mLastMotionYField.set(this, (int) (mTouchY[mTouchY.length - 2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}