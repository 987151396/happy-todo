package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.EditTextUtils;
import com.happy.todo.lib_common.utils.KeyboardUtil;
import com.happy.todo.lib_common.utils.ResourceUtils;
import com.happy.todo.lib_common.widget.input.AutoClearContentEditText;
import com.happy.todo.lib_common.widget.input.PasswordEditText;

import java.util.regex.Pattern;

/**
 * @功能描述：
 * @创建日期： 2018/11/07
 * @作者： dengkewu
 */
public class CommonEditText extends RelativeLayout {

    private String mErrorText;
    private String mHint;
    private int mShowType;
    private View mView;
    private String mContent;
    private RelativeLayout mCetRlContentParent;
    private LinearLayout mCetLlMsgCode;
    private LinearLayout mCetLlAreaCode;
    private PasswordEditText mCetEtPassword;
    private AutoClearContentEditText mCetEtClear;
    private AppCompatEditText mCetEtDefault;
    private TextView mCetTvErrorHint;
    private TextView mCetTvSendMsgCode;
    private TextView mCetEtAreaCode;
    private AutoClearContentEditText mCetEtNumber;
    private AppCompatEditText mCetEtMsgCode;

    private int mMinLine;
    private int mMaxLine;
    private int mMaxLength;
    private boolean mIsError;
    private boolean mIsSignLine;
    private Context mContext;
    public CommonEditText(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public CommonEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(attrs);
        this.mContext = context;
        initView();
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.commonEditText);
        mErrorText = typedArray.getString(R.styleable.commonEditText_error_text);
        mHint = typedArray.getString(R.styleable.commonEditText_hint);
        mShowType = typedArray.getInteger(R.styleable.commonEditText_show_type, 0);
        mContent = typedArray.getString(R.styleable.commonEditText_text);
        mMinLine = typedArray.getInteger(R.styleable.commonEditText_min_line, 1);
        mMaxLine = typedArray.getInteger(R.styleable.commonEditText_max_line, 1);
        mMaxLength = typedArray.getInteger(R.styleable.commonEditText_max_length, 100);
        mIsError = typedArray.getBoolean(R.styleable.commonEditText_is_error, false);
        mIsSignLine = typedArray.getBoolean(R.styleable.commonEditText_is_signline, true);
    }


    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.common_edit_text, this);
        mCetRlContentParent = mView.findViewById(R.id.cet_rl_content_parent);
        mCetLlMsgCode = mView.findViewById(R.id.cet_ll_msg_code);
        mCetLlAreaCode = mView.findViewById(R.id.cet_ll_area_code);
        mCetEtPassword = mView.findViewById(R.id.cet_et_password);
        mCetEtClear = mView.findViewById(R.id.cet_et_clear);
        mCetEtDefault = mView.findViewById(R.id.cet_et_default);
        mCetTvErrorHint = mView.findViewById(R.id.cet_tv_error_hint);
        mCetTvSendMsgCode = mView.findViewById(R.id.cet_tv_send_msg_code);
        mCetEtAreaCode = mView.findViewById(R.id.cet_et_area_code);
        mCetEtNumber = mView.findViewById(R.id.cet_et_number);
        mCetEtMsgCode = mView.findViewById(R.id.cet_et_msg_code);

        //设置显示
        showType();

        //设置editText属性
        setEditText();

        if (mShowType == 4) {
            mCetTvSendMsgCode.setOnClickListener(v -> {
                if (onClickListener != null) {
                    onClickListener.onSendMsgClick();
                }
            });
        }
        mCetEtAreaCode.setOnClickListener(v -> {
            if (onAreaCodeClickListener != null) {
                onAreaCodeClickListener.onAreaCodeClick();
            }
        });
    }

    private void setEditText() {
        mCetEtDefault.setMaxLines(mMaxLine);
        mCetEtPassword.setMaxLines(mMaxLine);
        mCetEtClear.setMaxLines(mMaxLine);
        mCetEtMsgCode.setMaxLines(mMaxLine);
        mCetEtNumber.setMaxLines(mMaxLine);

        mCetEtDefault.setMinLines(mMinLine);
        mCetEtPassword.setMinLines(mMinLine);
        mCetEtClear.setMaxLines(mMinLine);
        mCetEtMsgCode.setMaxLines(mMinLine);
        mCetEtNumber.setMaxLines(mMinLine);

        mCetEtMsgCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mCetEtNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mCetEtClear.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mCetEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mCetEtDefault.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});

        mCetEtPassword.setText(mContent);
        mCetEtClear.setText(mContent);
        mCetEtNumber.setText(mContent);
        mCetEtMsgCode.setText(mContent);
        mCetEtDefault.setText(mContent);

        mCetEtDefault.setHint(mHint);
        mCetEtPassword.setHint(mHint);
        mCetEtClear.setHint(mHint);
        mCetEtMsgCode.setHint(mHint);
        mCetEtNumber.setHint(mHint);

        if (mIsSignLine) {
            mCetEtClear.setSingleLine(true);
        } else {
            mCetEtClear.setSingleLine(false);
        }

    }


    /**
     * 选择显示那种状态0为正常 1为带清除按钮 2为密码 3为带有区域码 4为带有验证码
     */
    private void showType() {
        if (mShowType == 0) {
            mCetEtDefault.setVisibility(VISIBLE);
            mCetEtPassword.setVisibility(GONE);
            mCetEtClear.setVisibility(GONE);
            mCetLlMsgCode.setVisibility(GONE);
            mCetLlAreaCode.setVisibility(GONE);
        } else if (mShowType == 1) {
            mCetEtDefault.setVisibility(GONE);
            mCetEtPassword.setVisibility(GONE);
            mCetEtClear.setVisibility(VISIBLE);
            mCetLlMsgCode.setVisibility(GONE);
            mCetLlAreaCode.setVisibility(GONE);
        } else if (mShowType == 2) {
            mCetEtDefault.setVisibility(GONE);
            mCetEtPassword.setVisibility(VISIBLE);
            mCetEtClear.setVisibility(GONE);
            mCetLlMsgCode.setVisibility(GONE);
            mCetLlAreaCode.setVisibility(GONE);

            //限制密码输入内容
            InputFilter[] inputFilters = {
                    (source, start, end, dest, dstart, dend) -> {
                        String regex = "^[\u4E00-\u9FA5]+$";
                        boolean isChinese = Pattern.matches(regex, source.toString());
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(start)) || isChinese) {
                                return "";
                            }

                        }
                        return null;
                    }
            };
            mCetEtPassword.setFilters(inputFilters);
            mCetEtPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //如果EditText中的数据不为空，且长度大于指定的最大长度
                    if (!TextUtils.isEmpty(s) && s.length() > 20) {
                        //删除指定长度之后的数据
                        s.delete(20, mCetEtPassword.getSelectionEnd());
                        KeyboardUtil.hideKeyboard(mCetEtPassword);
                    }
                }
            });



        } else if (mShowType == 3) {
            mCetEtDefault.setVisibility(GONE);
            mCetEtPassword.setVisibility(GONE);
            mCetEtClear.setVisibility(GONE);
            mCetLlAreaCode.setVisibility(VISIBLE);
            mCetLlMsgCode.setVisibility(GONE);
        } else if (mShowType == 4) {
            mCetEtDefault.setVisibility(GONE);
            mCetEtPassword.setVisibility(GONE);
            mCetEtClear.setVisibility(GONE);
            mCetLlAreaCode.setVisibility(GONE);
            mCetLlMsgCode.setVisibility(VISIBLE);
        }

        //如果提示错误
        if (mIsError) {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_red));
            mCetTvErrorHint.setText(mErrorText);
            mCetTvErrorHint.setVisibility(VISIBLE);
        } else {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_gray));
            mCetTvErrorHint.setVisibility(INVISIBLE);
        }
    }

    /**
     * 点击事件回调抽象类
     */
    public interface onSendMsgClickListener {
        void onSendMsgClick();
    }

    public interface onAreaCodeClickListener {
        void onAreaCodeClick();
    }

    private onSendMsgClickListener onClickListener;

    public void setOnSendMsgClickListener(onSendMsgClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private onAreaCodeClickListener onAreaCodeClickListener;

    public void setOnAreaCodeClickListener(onAreaCodeClickListener onClickListener1) {
        this.onAreaCodeClickListener = onClickListener1;
    }

    /*********************************外部使用方法******************************/

    /**
     * 设置验证码按钮显示内容
     */
    public void setSendMsgText(String content) {
        mCetTvSendMsgCode.setText(content);
    }

    /**
     * 设置提示内容
     */
    public void setHintText(String hint) {
        mCetEtDefault.setHint(hint);
        mCetEtPassword.setHint(hint);
        mCetEtClear.setHint(hint);
        mCetEtNumber.setHint(hint);
        mCetEtMsgCode.setHint(hint);
    }

    public void setNotEditable(boolean value){
        switch (mShowType) {
            case 0:

                break;
            case 1:
                mCetEtClear.setFocusable(value);
                mCetEtClear.setFocusableInTouchMode(value);
                mCetEtClear.setTextColor(value ? getResources().getColor(R.color.black_2D2D2D) : getResources().getColor(R.color.gray_949494));
                mCetEtClear.setHideDrawableRight(value);
                break;
            case 3:
                mCetEtNumber.setFocusable(value);
                mCetEtNumber.setFocusableInTouchMode(value);
                mCetEtNumber.setTextColor(value ? getResources().getColor(R.color.black_2D2D2D) : getResources().getColor(R.color.gray_949494));
                mCetEtNumber.setHideDrawableRight(value);

                mCetEtAreaCode.setEnabled(value);
                mCetEtAreaCode.setTextColor(value ? getResources().getColor(R.color.black_2D2D2D) : getResources().getColor(R.color.gray_949494));
                Drawable drawable = value ? ResourceUtils.getDrawable(R.mipmap.user_ic_down_little) : ResourceUtils.getDrawable(R.mipmap.user_ic_down_little_gray);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mCetEtAreaCode.setCompoundDrawables(null,null,drawable,null);
                break;
        }
    }

    /**
     * 获取输入框内容
     *
     * @return
     */
    public String getText() {
        String content = "";
//        0为正常 1为带清除按钮 2为密码 3为带有区域码 4为带有验证码
        switch (mShowType) {
            case 0:
                content = mCetEtDefault.getText().toString();
                break;
            case 1:
                content = mCetEtClear.getText().toString();
                break;
            case 2:
                content = mCetEtPassword.getText().toString();
                break;
            case 3:
                content = mCetEtNumber.getText().toString();
                break;
            case 4:
                content = mCetEtMsgCode.getText().toString();
                break;
        }
        return content;
    }

    /**
     * 设置输入框显示内容
     *
     * @param text
     */
    public void setText(String text) {
        switch (mShowType) {
            case 0:
                mCetEtDefault.setText(text);
                break;
            case 1:
                mCetEtClear.setText(text);
                break;
            case 2:
                mCetEtPassword.setText(text);
                break;
            case 3:
                mCetEtNumber.setText(text);
                break;
            case 4:
                mCetEtMsgCode.setText(text);
                break;
        }
    }

    /**
     * 设置错误提示
     *
     * @param isError
     */
    public void setShowError(boolean isError, String hint) {
        if (isError) {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_red));
            mCetTvErrorHint.setText(hint);
            mCetTvErrorHint.setVisibility(VISIBLE);
        } else {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_gray));
            mCetTvErrorHint.setVisibility(INVISIBLE);
        }
    }
    /**
     * 设置错误提示
     *
     * @param isError
     */
    public void setShowError(boolean isError) {
        if (isError) {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_red));
            mCetTvErrorHint.setVisibility(VISIBLE);
        } else {
            mCetRlContentParent.setBackground(ResourceUtils.getDrawable(R.drawable.shape_radius_common_edit_gray));
            mCetTvErrorHint.setVisibility(INVISIBLE);
        }
    }


    /**
     * 获取区域码
     */
    public String getAreaCode() {
        return mCetEtAreaCode.getText().toString();
    }

    /**
     * 获取验证码输入框
     */
    public EditText getMsgEdit() {
        return mCetEtMsgCode;
    }

    /**
     * 获取验证码
     */
    public String getMsgCode() {
        return mCetEtMsgCode.getText().toString();
    }

    /**
     * 获取区域码
     */
    public void setAreaCode(String content) {
        mCetEtAreaCode.setText(content);
    }

    /**
     * 设置验证码按钮是否可以点击
     *
     * @param clickable
     */
    public void setSendMsgClickable(boolean clickable) {
        if (clickable) {
            mCetTvSendMsgCode.setBackgroundDrawable(ResourceUtils.getDrawable(R.drawable.shape_common_edit_send_msg));
            mCetTvSendMsgCode.setTextColor(ResourceUtils.getColor(R.color.white));
        } else {
            mCetTvSendMsgCode.setBackgroundDrawable(ResourceUtils.getDrawable(R.drawable.shape_common_edit_send_msg_gray));
            mCetTvSendMsgCode.setTextColor(ResourceUtils.getColor(R.color.gray_c5c5c5));
        }
        mCetTvSendMsgCode.setClickable(clickable);
    }

    /**
     * 设置光标位置
     * @param index
     */
    public void setSelection(int index) {
        try {
            switch (mShowType) {
                case 0:
                    mCetEtDefault.setSelection(index);
                    break;
                case 1:
                    mCetEtClear.setSelection(index);
                    break;
                case 2:
                    mCetEtPassword.setSelection(index);
                    break;
                case 3:
                    mCetEtNumber.setSelection(index);
                    break;
                case 4:
                    mCetEtMsgCode.setSelection(index);
                    break;
            }
        }catch (Exception e){
            AppLogUtil.e("设置光标位置异常="+e.getMessage());
        }

    }

    public void setLimitInputSpace(){
        switch (mShowType) {
            case 0:
                EditTextUtils.setEditTextInhibitInputSpace(mCetEtDefault);
                break;
            case 1:
                EditTextUtils.setEditTextInhibitInputSpace(mCetEtClear);
                break;
            case 2:
                EditTextUtils.setEditTextInhibitInputSpace(mCetEtPassword);

                break;
            case 3:
                EditTextUtils.setEditTextInhibitInputSpace(mCetEtNumber);
                break;
            case 4:
                EditTextUtils.setEditTextInhibitInputSpace(mCetEtMsgCode);
                break;
        }

    }

    public void setEmailInputType(){
        switch (mShowType) {
            case 0:
                mCetEtDefault.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 1:
                mCetEtClear.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 2:
                mCetEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 3:
                mCetEtNumber.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 4:
                mCetEtMsgCode.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
        }

    }

    public void setNumberInputType(){
        switch (mShowType) {
            case 0:
                mCetEtDefault.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 1:
                mCetEtClear.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 2:
                mCetEtPassword.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 3:
                mCetEtNumber.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 4:
                mCetEtMsgCode.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }

    }

    public CommonEditText setMaxLength(int maxLength){
        mCetEtMsgCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mCetEtNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mCetEtClear.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mCetEtPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mCetEtDefault.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }

    public PasswordEditText getEditText(){
       return mCetEtPassword;
    }
}
