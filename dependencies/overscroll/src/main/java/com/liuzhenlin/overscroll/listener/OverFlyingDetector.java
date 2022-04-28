/*
 * Created on 2017/12/19.
 * Copyright © 2017 刘振林. All rights reserved.
 */

package com.liuzhenlin.overscroll.listener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

/**
 * @author <a href="mailto:2233788867@qq.com">刘振林</a>
 */
@SuppressWarnings("WeakerAccess")
public class OverFlyingDetector extends GestureDetector.SimpleOnGestureListener {
    /** The view this detector monitors */
    private final View mView;

    private final OnOverFlyingListener mOnOverFlyingListener;

    private final OverFlyingHandler mHandler;

    private final GestureDetectorCompat mGestureDetector;

    /** Distance to travel before drag may begin */
    private final float mTouchSlop;

    /**
     * The horizontal displacement in pixels the user's finger travels, which will be positive
     * if it swipes from start to end in horizontal.
     */
    private float mDeltaX;
    /**
     * The vertical displacement in pixels the user's finger travels, which will be positive
     * if it swipes from top to bottom.
     */
    private float mDeltaY;

    /** @see #getOverFlyingMinimumVelocity() */
    private final float mOverFlyingMinimumVelocity; // 800 dp/s
    /** @see #getOverFlyingMaximumVelocity() */
    private final float mOverFlyingMaximumVelocity; // 8000 dp/s

    /**
     * The ratio of the distance this overflying can travel {@link #mOverFlyingDistX,#mOverFlyingDistY}
     * to the velocity of this fling measured in pixels per second along the x/y axis.
     */
    private static final float RATIO_OVER_DIST_TO_VELOCITY = 1f / 100f;

    /** The distance this overflying can travel horizontally */
    private float mOverFlyingDistX;
    /** The distance this overflying can travel vertically */
    private float mOverFlyingDistY;

    /** @see #getOverFlyingMinimumDuration() */
    private static final int BASE_DURATION_OVERFLYING = 64; // ms

    /** @return the minimum velocity for this flying to be detected as overflying */
    public float getOverFlyingMinimumVelocity() {
        return mOverFlyingMinimumVelocity;
    }

    /** @return the maximum velocity that a flying gesture can produce */
    public float getOverFlyingMaximumVelocity() {
        return mOverFlyingMaximumVelocity;
    }

    /** @return the minimum distance this overflying will travel */
    public float getOverFlyingMinimumDistance() {
        return mOverFlyingMinimumVelocity * RATIO_OVER_DIST_TO_VELOCITY;
    }

    /** @return the maximum distance this overflying can travel */
    public float getOverFlyingMaximumDistance() {
        return mOverFlyingMaximumVelocity * RATIO_OVER_DIST_TO_VELOCITY;
    }

    /** @return the minimum duration of this overflying */
    public static int getOverFlyingMinimumDuration() {
        return BASE_DURATION_OVERFLYING;
    }

    /**
     * @return The maximum duration of this overflying
     */
    public static int getOverFlyingMaximumDuration() {
        return BASE_DURATION_OVERFLYING * 2;
    }

    /**
     * Creates a GestureDetector with the supplied listener.
     * You may only use this constructor from a {@link android.os.Looper} thread.
     *
     * @param view     the view this detector works for
     * @param listener the listener invoked for all the callbacks, this must be nonnull
     */
    public OverFlyingDetector(@NonNull View view, @NonNull OnOverFlyingListener listener) {
        this(view, listener, null);
    }

    /**
     * Creates a OverFlyingDetector with the supplied listener that runs deferred events on the
     * thread associated with the supplied {@link android.os.Handler}.
     *
     * @param view     the view this detector works for
     * @param listener the listener invoked for all the callbacks, this must be nonnull
     * @param handler  the handler to use for running deferred listener events.
     */
    public OverFlyingDetector(@NonNull View view, @NonNull OnOverFlyingListener listener,
                              @Nullable Handler handler) {
        Context context = view.getContext();
        mView = view;
        mOnOverFlyingListener = listener;
        mHandler = handler == null ? new OverFlyingHandler() : new OverFlyingHandler(handler);
        mGestureDetector = new GestureDetectorCompat(context, this, mHandler);

        final float dp = context.getResources().getDisplayMetrics().density;
        mTouchSlop = ViewConfiguration.getTouchSlop() * dp;
        mOverFlyingMaximumVelocity = 8000f * dp;
        mOverFlyingMinimumVelocity = mOverFlyingMaximumVelocity / 10f;
    }

    /**
     * @return Whether this gesture has been handled as overflying or not
     */
    public final boolean handleOverFling(MotionEvent ev) {
        return mGestureDetector.onTouchEvent(ev);
    }

    /*
     * Only when this view{@link #mView} flings its contents to one of the ends will OverFlying
     * be triggered, estimated by using a calculation strategy:
     * 1. Get the amount of the user's finger displacement and the speeds of this flying
     * (both in vertical and horizontal) to analyze the fling gesture, in which the case may be
     * that there exists flying but it can not be detected as overflying.
     * 2. If necessary, continuously send a message through the handler {@link #mHandler}
     * every 10 milliseconds to determine if it flings to a certain end or not.
     */
    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent upEvent, float velocityX, float velocityY) {
        mDeltaX = isViewLayoutRtl() ? downEvent.getX() - upEvent.getX() : upEvent.getX() - downEvent.getX();
        mDeltaY = upEvent.getY() - downEvent.getY();
        final float absDX = Math.abs(mDeltaX);
        final float absDY = Math.abs(mDeltaY);

        final float absVy = Math.abs(velocityY);
        final float absVx = Math.abs(velocityX);
        // flying vertically
        if (absDY > absDX && absDY > mTouchSlop && absVy >= mOverFlyingMinimumVelocity) {
            mOverFlyingDistY = absVy * RATIO_OVER_DIST_TO_VELOCITY;
            mHandler.sendEmptyMessage(OverFlyingHandler.MSG_START_COMPUTE_FLYING);

            // flying horizontally
        } else if (absDX > absDY && absDX > mTouchSlop && absVx >= mOverFlyingMinimumVelocity) {
            mOverFlyingDistX = absVx * RATIO_OVER_DIST_TO_VELOCITY;
            mHandler.sendEmptyMessage(OverFlyingHandler.MSG_START_COMPUTE_FLYING);
        } else
            return false;
        return true;
    }

    @SuppressLint("HandlerLeak")
    private class OverFlyingHandler extends Handler {
        /** A 'message what' to be sent to start computing */
        private static final int MSG_START_COMPUTE_FLYING = 0;
        /** A 'message what' to be sent to continue computing */
        private static final int MSG_CONTINUE_COMPUTE_FLYING = 1;
        /** A 'message what' to be sent to stop computing */
        private static final int MSG_STOP_COMPUTE_FLYING = 2;

        /** The maximum count of the calculations */
        private static final int MAX_COMPUTE_TIMES = 100;
        /** Time interval for each new calculation to be started */
        private static final int TIME_INTERVAL_COMPUTE = 10; // ms

        /** The current compute times */
        private int mCurrComputeTimes = 0;

        public OverFlyingHandler() {
        }

        public OverFlyingHandler(Handler handler) {
            super(handler.getLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_COMPUTE_FLYING:
                    // First stop the last period computes
                    removeCallbacksAndMessages(null);
                    mCurrComputeTimes = -1; // Here is no break, writing -1 for easy counting.
                case MSG_CONTINUE_COMPUTE_FLYING:
                    mCurrComputeTimes++;

                    // overfly at the top
                    if (mDeltaY > 0 && hasViewScrolledToTop()) {
                        mOnOverFlyingListener.onTopEdgeOverFling(mOverFlyingDistY,
                                computeOverFlyingDuration(mOverFlyingDistY));
                        break;

                        // overfly at the bottom
                    } else if (mDeltaY < 0 && hasViewScrolledToBottom()) {
                        mOnOverFlyingListener.onBottomEdgeOverFling(mOverFlyingDistY,
                                computeOverFlyingDuration(mOverFlyingDistY));
                        break;

                        // overfly at the horizontal start
                    } else if (mDeltaX > 0 && hasViewScrolledToStart()) {
                        mOnOverFlyingListener.onStartEdgeOverFling(mOverFlyingDistX,
                                computeOverFlyingDuration(mOverFlyingDistX));
                        break;

                        // overfly at the end of horizontal
                    } else if (mDeltaX < 0 && hasViewScrolledToEnd()) {
                        mOnOverFlyingListener.onEndEdgeOverFling(mOverFlyingDistX,
                                computeOverFlyingDuration(mOverFlyingDistX));
                        break;
                    }

                    // Since the calculations haven't timed out, continue to send a message
                    // to start a new compute.
                    if (mCurrComputeTimes < MAX_COMPUTE_TIMES)
                        sendEmptyMessageDelayed(MSG_CONTINUE_COMPUTE_FLYING, TIME_INTERVAL_COMPUTE);
                    break;
                case MSG_STOP_COMPUTE_FLYING:
                    removeCallbacksAndMessages(null);
                    break;
            }
        }

        private int computeOverFlyingDuration(float dist) {
            final float ratio = dist / getOverFlyingMaximumDistance();
            return (int) (BASE_DURATION_OVERFLYING * (1 + ratio) + 0.5f);
        }
    }

    /**
     * @return Whether the view {@link #mView} has scrolled its contents to the horizontal start.
     */
    protected boolean hasViewScrolledToStart() {
        // noinspection deprecation
        return !ViewCompat.canScrollHorizontally(mView, isViewLayoutRtl() ? 1 : -1);
    }

    /**
     * @return Whether the view {@link #mView} has scrolled its contents to the end of horizontal.
     */
    protected boolean hasViewScrolledToEnd() {
        // noinspection deprecation
        return !ViewCompat.canScrollHorizontally(mView, isViewLayoutRtl() ? -1 : 1);
    }

    /**
     * @return Whether the view {@link #mView} has scrolled its contents to the top.
     */
    protected boolean hasViewScrolledToTop() {
        // noinspection deprecation
        return !ViewCompat.canScrollVertically(mView, -1);
    }

    /**
     * @return Whether the view {@link #mView} has scrolled its contents to the bottom.
     */
    protected boolean hasViewScrolledToBottom() {
        // noinspection deprecation
        return !ViewCompat.canScrollVertically(mView, 1);
    }

    /**
     * @return Whether the horizontal layout direction of {@link #mView} is from right to left
     */
    protected boolean isViewLayoutRtl() {
        return Build.VERSION.SDK_INT >= JELLY_BEAN_MR1 && mView.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    public interface OnOverFlyingListener {
        void onTopEdgeOverFling(float overHeight, int duration);

        void onBottomEdgeOverFling(float overHeight, int duration);

        void onStartEdgeOverFling(float overWidth, int duration);

        void onEndEdgeOverFling(float overWidth, int duration);
    }

    public static class SimpleOnOverFlyingListener implements OnOverFlyingListener {

        @Override
        public void onTopEdgeOverFling(float overHeight, int duration) {

        }

        @Override
        public void onBottomEdgeOverFling(float overHeight, int duration) {

        }

        @Override
        public void onStartEdgeOverFling(float overWidth, int duration) {

        }

        @Override
        public void onEndEdgeOverFling(float overWidth, int duration) {

        }
    }
}
