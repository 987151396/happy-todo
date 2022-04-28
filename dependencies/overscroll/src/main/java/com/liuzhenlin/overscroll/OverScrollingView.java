/*
 * Created on 2018/04/21.
 * Copyright © 2018 刘振林. All rights reserved.
 */

package com.liuzhenlin.overscroll;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is an interface that can be implemented by Views to provide overscroll related APIs.
 *
 * @author <a href="mailto:2233788867@qq.com">刘振林</a>
 */
public interface OverScrollingView {
    /** This view is not currently overscrolling, so there's no edge of overscroll. */
    int OVERSCROLL_EDGE_UNSPECIFIED = 0;

    /** This view is currently overscrolling at the top edge. */
    int OVERSCROLL_EDGE_TOP = 1;

    /** This view is currently overscrolling at the bottom edge. */
    int OVERSCROLL_EDGE_BOTTOM = 2;

    /**
     * This view is vertically overscrolling but the edge of overscroll may be hard to say.
     * For example, while the contents in this view is not beyond the borders of the view
     * (its height is not greater than the height of the view), then the user can drag this view
     * to make it overscroll at any vertical edge at any time if the user want.
     */
    int OVERSCROLL_EDGE_TOP_OR_BOTTOM = OVERSCROLL_EDGE_TOP | OVERSCROLL_EDGE_BOTTOM;

    /** This view is currently overscrolling at the horizontal start. */
    int OVERSCROLL_EDGE_START = 4;

    /** This view is currently overscrolling at the end of horizontal. */
    int OVERSCROLL_EDGE_END = 8;

    /**
     * This view is horizontally overscrolling but the edge of overscroll may be hard to say.
     * For example, while the contents in this view is not beyond the borders of the view
     * (its width is not greater than the width of the view), then the user can drag this view
     * to make it overscroll at any horizontal edge at any time if the user want.
     */
    int OVERSCROLL_EDGE_START_OR_END = OVERSCROLL_EDGE_START | OVERSCROLL_EDGE_END;

    @IntDef({
            OVERSCROLL_EDGE_UNSPECIFIED,
            OVERSCROLL_EDGE_TOP, OVERSCROLL_EDGE_BOTTOM, OVERSCROLL_EDGE_TOP_OR_BOTTOM,
            OVERSCROLL_EDGE_START, OVERSCROLL_EDGE_END, OVERSCROLL_EDGE_START_OR_END
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface OverScrollEdge {
    }

    /** This view is not currently overscrolling. */
    int OVERSCROLL_STATE_IDLE = 0;

    /** This view is currently overscrolling and is being dragged by user. */
    int OVERSCROLL_STATE_TOUCH_SCROLL = 1;

    /**
     * This view is currently overscrolling but is not under outside control.
     * For example, it is being translated by the animator.
     */
    int OVERSCROLL_STATE_AUTO_SCROLL = 2;

    @IntDef({
            OVERSCROLL_STATE_IDLE, OVERSCROLL_STATE_TOUCH_SCROLL, OVERSCROLL_STATE_AUTO_SCROLL
    })
    @Retention(RetentionPolicy.SOURCE)
    @interface OverScrollState {
    }

    boolean isOverScrollEnabled();

    void setOverScrollEnabled(boolean enabled);

    /**
     * Handle the overscroll if needed while the user is scrolling this view.
     *
     * @param ev the touch event from {@link View#onTouchEvent(MotionEvent)}
     * @return whether the touch event has been consumed or not
     */
    boolean handleOverScroll(@NonNull MotionEvent ev);

    /**
     * @return the amount of offset in vertical that each scroll of the user can generated,
     * as computed in pixels.
     */
    float computeOverScrollDeltaOffsetY();

    /**
     * @return the amount of offset in horizontal that each scroll of the user can generated,
     * as computed in pixels.
     */
    float computeOverScrollDeltaOffsetX();

    /**
     * Smoothly overscroll this view in vertical through the animator.
     *
     * @param from     the current offset of overscroll in vertical
     * @param to       the final offset of overscroll in vertical
     * @param duration The time in milliseconds this animation will last for
     */
    void animateVerticalOverScroll(float from, float to, int duration);

    /**
     * Smoothly overscroll this view in horizontal through the animator.
     *
     * @param from     the current offset of overscroll in horizontal
     * @param to       the final offset of overscroll in horizontal
     * @param duration The time in milliseconds this animation will last for
     */
    void animateHorizontalOverScroll(float from, float to, int duration);

    /**
     * @return Whether the view implemented from this interface has scrolled its contents
     * to the top.
     */
    boolean hasScrolledToTop();

    /**
     * @return Whether the view implemented from this interface has scrolled its contents
     * to the bottom.
     */
    boolean hasScrolledToBottom();

    /**
     * @return Whether the view implemented from this interface has scrolled its contents
     * to the horizontal start.
     */
    boolean hasScrolledToStart();

    /**
     * @return Whether the view implemented from this interface has scrolled its contents
     * to the end of horizontal.
     */
    boolean hasScrolledToEnd();

    /**
     * Set a callback to override the methods of the subclass of {@link OverScrollingView}.
     * Non-null callback will return the value provided by the callback and ignore all internal logic.
     *
     * @param callback Callback that should be called when
     *                 {@link  #hasScrolledToTop ()} or {@link  #hasScrolledToBottom ()} or
     *                 {@link  #hasScrolledToStart ()} or {@link  #hasScrolledToEnd ()}
     *                 is called.
     */
    void setOnScrollCallback(@Nullable OnScrollCallback callback);

    /**
     * Classes that wish to override {@link #hasScrolledToTop ()}, {@link #hasScrolledToBottom()},
     * {@link #hasScrolledToStart ()} and {@link #hasScrolledToEnd()}
     * method behaviors should implement this interface.
     */
    interface OnScrollCallback {
        /**
         * Callback that will be called when
         * {@link OverScrollingView#hasScrolledToTop ()} method
         * is called to allow the implementer to override its behavior.
         *
         * @param parent the view that this callback is overriding.
         * @return Whether the parent layout has scrolled its children to the top.
         */
        boolean hasScrolledToTop(@NonNull OverScrollingView parent);

        /**
         * Callback that will be called when
         * {@link OverScrollingView#hasScrolledToBottom ()} method
         * is called to allow the implementer to override its behavior.
         *
         * @param parent the view that this callback is overriding.
         * @return Whether the parent layout has scrolled its children to the bottom.
         */
        boolean hasScrolledToBottom(@NonNull OverScrollingView parent);

        /**
         * Callback that will be called when
         * {@link OverScrollingView#hasScrolledToStart ()} method
         * is called to allow the implementer to override its behavior.
         *
         * @param parent the view that this callback is overriding.
         * @return Whether the parent layout has scrolled its children to the horizontal start.
         */
        boolean hasScrolledToStart(@NonNull OverScrollingView parent);

        /**
         * Callback that will be called when
         * {@link OverScrollingView#hasScrolledToEnd ()} method
         * is called to allow the implementer to override its behavior.
         *
         * @param parent the view that this callback is overriding.
         * @return Whether the parent layout has scrolled its children to the end of horizontal.
         */
        boolean hasScrolledToEnd(@NonNull OverScrollingView parent);
    }

    /**
     * Classes that wish to override {@link #hasScrolledToTop ()} and {@link #hasScrolledToBottom()}
     * or {@link #hasScrolledToStart ()} and {@link #hasScrolledToEnd()} method behaviors
     * but they do not want to implement all the methods defined in {@link OnScrollCallback}
     * since some of them may be useless, in which case they can inherit from this class.
     */
    class SimpleOnScrollCallback implements OnScrollCallback {

        /** {@inheritDoc} */
        @Override
        public boolean hasScrolledToTop(@NonNull OverScrollingView parent) {
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasScrolledToBottom(@NonNull OverScrollingView parent) {
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasScrolledToStart(@NonNull OverScrollingView parent) {
            return false;
        }

        /** {@inheritDoc} */
        @Override
        public boolean hasScrolledToEnd(@NonNull OverScrollingView parent) {
            return false;
        }
    }

    void addOnOverScrollListener(@NonNull OnOverScrollListener listener);

    void removeOnOverScrollListener(@Nullable OnOverScrollListener listener);

    void clearOnOverScrollListeners();

    /**
     * Classes that wish to monitor the events of overscroll should implement this interface.
     */
    interface OnOverScrollListener {
        /**
         * Callback that will be called on this view starting to overscroll.
         *
         * @param parent the view implemented from {@link OverScrollingView}
         * @param view   the view (may be one child of {@param parent}) that the overscroll works at
         * @param edge   the edge of overscroll, maybe one of
         *               {@link #OVERSCROLL_EDGE_TOP},
         *               {@link #OVERSCROLL_EDGE_BOTTOM},
         *               {@link #OVERSCROLL_EDGE_TOP_OR_BOTTOM}
         *               {@link #OVERSCROLL_EDGE_START},
         *               {@link #OVERSCROLL_EDGE_END},
         *               {@link #OVERSCROLL_EDGE_START_OR_END}
         */
        void onOverScrollStart(@NonNull OverScrollingView parent, @NonNull View view, @OverScrollEdge int edge);

        /**
         * Callback that will be called on this view finishing to overscroll.
         *
         * @param parent the view implemented from {@link OverScrollingView}
         * @param view   the view (may be one child of {@param parent}) that the overscroll works at
         * @param edge   the edge of overscroll, maybe one of
         *               {@link #OVERSCROLL_EDGE_TOP},
         *               {@link #OVERSCROLL_EDGE_BOTTOM},
         *               {@link #OVERSCROLL_EDGE_TOP_OR_BOTTOM}
         *               {@link #OVERSCROLL_EDGE_START},
         *               {@link #OVERSCROLL_EDGE_END},
         *               {@link #OVERSCROLL_EDGE_START_OR_END}
         */
        void onOverScrollEnd(@NonNull OverScrollingView parent, @NonNull View view, @OverScrollEdge int edge);

        /**
         * Callback that will be called when the overscroll offset of this view changes.
         *
         * @param parent the view implemented from {@link OverScrollingView}
         * @param view   the view (may be one child of {@param parent}) that the overscroll works at
         * @param offset the current offset of overscroll
         */
        void onOverScrollOffsetChange(@NonNull OverScrollingView parent, @NonNull View view, float offset);

        /**
         * Callback that will be called when the overscroll state of this view changes.
         *
         * @param parent the view implemented from {@link OverScrollingView}
         * @param view   the view (may be one child of {@param parent}) that the overscroll works at
         * @param state  the current state of overscroll
         * @see #OVERSCROLL_STATE_IDLE
         * @see #OVERSCROLL_STATE_TOUCH_SCROLL
         * @see #OVERSCROLL_STATE_AUTO_SCROLL
         */
        void onOverScrollStateChange(@NonNull OverScrollingView parent, @NonNull View view, @OverScrollState int state);
    }
}