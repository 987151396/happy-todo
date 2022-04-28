package com.happy.todo.lib_common.widget.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.SizeUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @作者: TwoSX
 * @时间: 2017/12/12 下午8:23
 * @描述: 自定义密码编辑框，显示隐藏功能
 */
public class PasswordEditText extends AppCompatEditText {

    private static final String TAG = "DEBUG-WCL: " + PasswordEditText.class.getSimpleName();

    // 模式的显示图标
    @DrawableRes
    //private int mHidePwdIcon = 1;
    private int mHidePwdIcon = R.mipmap.icon_password_visible;

    // 模式的加密图标
    @DrawableRes
    //private int mShowPwdIcon = 1;
    private int mShowPwdIcon = R.mipmap.icon_password_invisible;

    private boolean mIsShowPwdIcon; // 是否显示指示器

    private Drawable mDrawableSide; // 显示隐藏指示器
    // 保存设置的所有输入限制
    private List<InputFilter> inputFilters;
    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs, 0);
        inputFilters=new ArrayList<>();
    }
    /**
     * 设置允许输入的最大字符数
     */
    public void setMaxLengthFilter(int maxLength) {
        inputFilters.add(new InputFilter.LengthFilter(maxLength));
        setFilters(inputFilters.toArray(new InputFilter[inputFilters.size()]));
    }

    @TargetApi(21)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inputFilters=new ArrayList<>();
        initFields(attrs, defStyleAttr);
    }

    // 初始化布局
    public void initFields(AttributeSet attrs, int defStyleAttr) {

        // 密码状态
        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // 有文字时显示指示器
                    showPasswordVisibilityIndicator(true);
                } else {
                    mIsShowPwdIcon = false;
                    restorePasswordIconVisibility(mIsShowPwdIcon);
                    showPasswordVisibilityIndicator(false); // 隐藏指示器
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 存储状态
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable state = super.onSaveInstanceState();
        return new PwdSavedState(state, mIsShowPwdIcon);
    }

    // 恢复状态
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        PwdSavedState savedState = (PwdSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mIsShowPwdIcon = savedState.isShowingIcon();
        restorePasswordIconVisibility(mIsShowPwdIcon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDrawableSide == null) {
            return super.onTouchEvent(event);
        }
        final Rect bounds = mDrawableSide.getBounds();
        final int x = (int) event.getRawX(); // 点击的位置

        int iconX = (int) getTopRightCorner().x;

        // Icon的位置
        int leftIcon = iconX - bounds.width();

        // Log.e(CurrencyTag, "x: " + x + ", leftIcon: " + leftIcon);

        // 大于Icon的位置, 才能触发点击
        if (x >= leftIcon) {
            togglePasswordIconVisibility(); // 变换状态
            event.setAction(MotionEvent.ACTION_CANCEL);
            return false;
        }
        return super.onTouchEvent(event);
    }

    // 获取上右角的距离
    public PointF getTopRightCorner() {
        float src[] = new float[8];
        float[] dst = new float[]{0, 0, getWidth(), 0, 0, getHeight(), getWidth(), getHeight()};
        getMatrix().mapPoints(src, dst);
        return new PointF(getX() + src[2], getY() + src[3]);
    }

    // 显示密码提示标志
    private void showPasswordVisibilityIndicator(boolean shouldShowIcon) {
        if (shouldShowIcon) {
            Drawable drawable = mIsShowPwdIcon ? ContextCompat.getDrawable(getContext(),
                    mHidePwdIcon) : ContextCompat.getDrawable(getContext(), mShowPwdIcon);

            drawable.setBounds(0, 0, SizeUtil.dp2px(20), drawable.getIntrinsicHeight() * SizeUtil
                    .dp2px(20) / drawable.getIntrinsicWidth());
            // 在最右侧显示图像
            setCompoundDrawables(getCompoundDrawables()[0], null, drawable, null);
            mDrawableSide = drawable;
        } else {
            // 不显示周边的图像
            setCompoundDrawables(getCompoundDrawables()[0], null, null, null);
            mDrawableSide = null;
        }
    }

    // 变换状态
    private void togglePasswordIconVisibility() {
        mIsShowPwdIcon = !mIsShowPwdIcon;
        restorePasswordIconVisibility(mIsShowPwdIcon);
        showPasswordVisibilityIndicator(true);
    }

    // 设置密码指示器的状态
    private void restorePasswordIconVisibility(boolean isShowPwd) {
        if (isShowPwd) {
            // 可视密码输入
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo
                    .TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            // 非可视密码状态
            setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // 移动光标
        setSelection(getText().length());
    }

    // 存储密码状态, 显示Icon的位置
    protected static class PwdSavedState extends BaseSavedState {

        private final boolean mShowingIcon;

        private PwdSavedState(Parcelable superState, boolean showingIcon) {
            super(superState);
            mShowingIcon = showingIcon;
        }

        private PwdSavedState(Parcel in) {
            super(in);
            mShowingIcon = in.readByte() != 0;
        }

        public boolean isShowingIcon() {
            return mShowingIcon;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeByte((byte) (mShowingIcon ? 1 : 0));
        }

        public static final Creator<PwdSavedState> CREATOR = new
                Creator<PwdSavedState>() {
            public PwdSavedState createFromParcel(Parcel in) {
                return new PwdSavedState(in);
            }

            public PwdSavedState[] newArray(int size) {
                return new PwdSavedState[size];
            }
        };
    }

    /**
     * 输入法
     *
     * @param outAttrs
     * @return
     */
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new mInputConnecttion(super.onCreateInputConnection(outAttrs),
                false);
    }

    class mInputConnecttion extends InputConnectionWrapper implements InputConnection {

        public mInputConnecttion(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        /**
         * 对输入的内容进行拦截
         *
         * @param text
         * @param newCursorPosition
         * @return
         */
        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            // 只能输入汉字
            if (text.toString().matches("[\u4e00-\u9fa5]+")) {
                return false;
            }
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean setSelection(int start, int end) {
            return super.setSelection(start, end);
        }




    }
}
