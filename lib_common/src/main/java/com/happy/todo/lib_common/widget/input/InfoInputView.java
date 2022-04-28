package com.happy.todo.lib_common.widget.input;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.KeyboardUtil;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.utils.ViewUtil;

/**
 * 个人信息输入布局
 * Created by Jaminchanks on 2018/1/11.
 */

public class InfoInputView extends LinearLayout{

    private Context mContext;
    LinearLayout mLlyInputResult;
    TextView mTvNameUpper; //占位位置
    TextView mTvInputName;
    TextView mTvInputValue;
    AutoClearContentEditText mEtRealInputArea;
    private boolean mIsInputEnable;

    private String mTextSavingRuntime; //实时保存输入内容

    Animator mAnimator;
    private static final int ANIMATION_DURATION = 100;

    public InfoInputView(Context context) {
        super(context);
        this.mContext = context;
    }

    public InfoInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
    }

    private void initView(@Nullable AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.InfoInputView);

        int colorInputName = typedArray.getColor(R.styleable.InfoInputView_inputNameColor,ResourceUtils.getColor(R.color.black));
        int colorInputValue = typedArray.getColor(R.styleable.InfoInputView_inputValueColor,ResourceUtils.getColor(R.color.black_484848));
        String name = typedArray.getString(R.styleable.InfoInputView_inputName);
        mTextSavingRuntime = typedArray.getString(R.styleable.InfoInputView_inputValue);
        String hint = typedArray.getString(R.styleable.InfoInputView_inputHint);
        mIsInputEnable = typedArray.getBoolean(R.styleable.InfoInputView_inputEnable, true);
        boolean isShowRightArrow = typedArray.getBoolean(R.styleable.InfoInputView_inputShowRightArrow, false);
        boolean isShowBottomLine = typedArray.getBoolean(R.styleable.InfoInputView_inoutShowBottomLine, true);
        typedArray.recycle();

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_info_input_layout, this);

        mTvNameUpper = view.findViewById(R.id.tv_name_upper);

        //输入框1，只可点击不可输入
        mLlyInputResult = view.findViewById(R.id.lly_input_result);
        mTvInputName = view.findViewById(R.id.tv_input_name);
        mTvInputValue = view.findViewById(R.id.tv_input_value);

        //输入框2，可以点击
        mEtRealInputArea = view.findViewById(R.id.et_input_area1);

        //底部分割线
        View divider = view.findViewById(R.id.view_divider);
        if (!isShowBottomLine) {
            divider.setVisibility(INVISIBLE);
        }

        mLlyInputResult.setVerticalGravity(VISIBLE);
        mTvNameUpper.setVisibility(GONE);
        mEtRealInputArea.setVisibility(GONE);

        mTvInputName.setText(name);
        mTvInputValue.setText(mTextSavingRuntime);

        mTvInputName.setTextColor(colorInputName);
        mTvInputValue.setTextColor(colorInputValue);
        //可输入状态
        if (mIsInputEnable) {
            mTvNameUpper.setText(name);
            mEtRealInputArea.setHint(isBlank(hint) ?
                    ResourceUtils.getString(R.string.please_input, name) : hint);
        } else {
//            mTvInputValue.setText("");
            //是否显示右箭头
            if (isShowRightArrow) {
                ViewUtil.setDrawableRight(mTvInputValue, R.mipmap.icon_arrow_right_black);
            }
        }

        //处理默认点击事件
        setListener();
    }


    /**
     * 是否是会显示输入框的那种类型
     * @param enable
     */
    public void setInputEnable(boolean enable) {
        this.mIsInputEnable = enable;
    }


    public boolean getInputEnableState() {
        return mIsInputEnable;
    }

    private void setListener() {
        setOnClickListener(v -> {});

        mEtRealInputArea.setOnFocusChangeListener((v, hasFocus) -> {
            AppLogUtil.d("输入框获得点击事件");
            showInputArea(hasFocus);
        });

        //实时更新值内容
        mEtRealInputArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextSavingRuntime = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    /**
     *
     * @param isInputEnable
     */
    private void startAnimator(boolean isInputEnable) {
        if (!isInputEnable && mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            return;
        }

        //原先大小
        float textSize = mTvInputName.getTextSize();
        float upperTextSize = mTvNameUpper.getTextSize();

        //y轴上的变化
        PropertyValuesHolder pvhTranslateY = PropertyValuesHolder.ofFloat("translationY",
                mLlyInputResult.getTop() + mLlyInputResult.getPaddingTop(), 0f);
        //大小上的变化
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX",
                textSize / upperTextSize, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY",
                textSize / upperTextSize, 1f);

        Animator mAnimator = ObjectAnimator.ofPropertyValuesHolder(mTvNameUpper, pvhTranslateY, pvhScaleY);
        mAnimator.setDuration(ANIMATION_DURATION)
                .start();
    }


    /**
     *  显示输入区域
     *  1.执行动画
     *  2.mLlyInputResult 消失
     *  3.mEtRealInputArea 出现
     * @param isShow
     */
    public void showInputArea(boolean isShow) {
        mTvNameUpper.setVisibility(isShow ? VISIBLE : GONE);

        startAnimator(isShow);

        mLlyInputResult.setVisibility(isShow ? GONE : VISIBLE);
        mEtRealInputArea.setVisibility(isShow ? VISIBLE : GONE);

        if (isShow) {
            CharSequence text = mTvInputValue.getText();
            mEtRealInputArea.setText(text);
            mEtRealInputArea.requestFocus();
            mEtRealInputArea.setSelection(text.length());

            KeyboardUtil.showKeyboard(mEtRealInputArea, false);
        } else {
            mTvInputValue.setText(mEtRealInputArea.getText());
            KeyboardUtil.hideKeyboard(mEtRealInputArea); //收起键盘
        }

    }


    /**
     * 设置title
     * @param value
     */
    public void setInputName(String value) {
        if (value == null) {
            value = "";
        }
        mTvInputName.setText(value);
    }


    /**
     * 获取title
     * @return
     */
    public CharSequence getInputName() {
        return mTvInputName.getText();
    }


    /**
     * 是否显示右边的箭头
     * @param isShow
     */
    public void showRightArrow(boolean isShow) {
        if (isShow) {
            ViewUtil.setDrawableRight(mTvInputValue, R.mipmap.icon_arrow_right_black);
        } else {
            ViewUtil.setDrawableRight(mTvInputValue, 0);
        }
    }


    /**
     * 设置显示的值
     * @param value
     */
    public void setValue(String value) {
        if (value == null) {
            value = "";
        }

        mTextSavingRuntime = value;
        mTvInputValue.setText(mTextSavingRuntime);
    }


    public String getValue() {
        return mTextSavingRuntime;
    }



    /**
     * 获取editText， 方便自定义功能
     * @return
     */
    public AutoClearContentEditText getInputEditText() {
        return mEtRealInputArea;
    }


    /**
     * 获取结果文本控件
     * @return
     */
    public TextView getInputResultValueView() {
        return mTvInputValue;
    }


    /**
     *
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);

         mLlyInputResult.setOnClickListener(v -> {
             if (mIsInputEnable) {
                 showInputArea(true);

             } else {
                 mLlyInputResult.setFocusable(true);
                 mLlyInputResult.setFocusableInTouchMode(true);
                 mLlyInputResult.requestFocus();
                 //自动取消焦点，避免一直处于获取焦点状态
                 mLlyInputResult.post(() -> {
                     mLlyInputResult.clearFocus();
                     mLlyInputResult.setFocusable(false);
                     mLlyInputResult.setFocusableInTouchMode(false);
                 });

             }

             if (l != null) {
                 l.onClick(v);
             }
         });
    }
    public static boolean isBlank(String var0) {
        int var1;
        if (var0 != null && (var1 = var0.length()) != 0) {
            for(int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isWhitespace(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
