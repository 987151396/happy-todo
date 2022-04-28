package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Describe：
 * @Date： 2018/12/25
 * @Author： dengkewu
 * @Contact：
 */
public class TravelerInfoView extends RelativeLayout {

    private boolean mIsChose;
    private int mChoseIconID;
    private String mTitle;
    private String mContent;
    private String mContentHint;
    private View mView;
    private TextView tv_traveler_title;
    private TextView tv_traveler_content;
    private ImageView iv_traveler_chose;
    private ImageView iv_traveler_chose1;
    private EditText et_traveler_content;
    private RelativeLayout rl_traveler_parent;
    private TextView tvMsg;

    public TravelerInfoView(Context context) {
        this(context, null);
    }

    public TravelerInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(attrs);
        initView();
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, com.happy.todo.lib_common.R.styleable.TravelerInfoView);
        mContent = typedArray.getString(com.happy.todo.lib_common.R.styleable.TravelerInfoView_info_content);
        mTitle = typedArray.getString(com.happy.todo.lib_common.R.styleable.TravelerInfoView_info_title);
        mContentHint = typedArray.getString(com.happy.todo.lib_common.R.styleable.TravelerInfoView_info_content_hint);
        mIsChose = typedArray.getBoolean(com.happy.todo.lib_common.R.styleable.TravelerInfoView_info_is_chose, false);
        mChoseIconID = typedArray.getResourceId(R.styleable.TravelerInfoView_info_chose_icon, R.mipmap.hotel_ic_gray_down_arrow);

    }


    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.user_traveler_info_view, this);
        tv_traveler_title = mView.findViewById(R.id.tv_traveler_title);
        tv_traveler_content = mView.findViewById(R.id.tv_traveler_content);
        iv_traveler_chose = mView.findViewById(R.id.iv_traveler_chose);
        iv_traveler_chose1 = mView.findViewById(R.id.iv_traveler_chose1);
        rl_traveler_parent = mView.findViewById(R.id.rl_traveler_parent);
        tvMsg = mView.findViewById(R.id.tv_msg);
        et_traveler_content = mView.findViewById(R.id.et_traveler_content);
        iv_traveler_chose.setImageResource(mChoseIconID);
        iv_traveler_chose.setVisibility(mIsChose ? VISIBLE : GONE);
        tv_traveler_title.setText(mTitle);
        et_traveler_content.setText(mContent);
//        EditTextUtils.setEditTextInhibitInputSpace(et_traveler_content);
        if (mIsChose) {
            et_traveler_content.setFocusable(false);
            et_traveler_content.setVisibility(GONE);
            tv_traveler_content.setVisibility(VISIBLE);
            tv_traveler_content.setText(mContentHint);
            rl_traveler_parent.setBackground(ResourceUtils.getDrawable(R.drawable.plane_round_gray_solid_select));
        } else {
            et_traveler_content.setVisibility(VISIBLE);
            tv_traveler_content.setVisibility(GONE);
            et_traveler_content.setHint(mContentHint);
        }
    }

    public void setChoseIconVisibility(int Visibility){
        iv_traveler_chose.setVisibility(Visibility);
    }

    public TravelerInfoView setChoseImageRes() {
        iv_traveler_chose.setVisibility(GONE);
        iv_traveler_chose1.setVisibility(VISIBLE);
        iv_traveler_chose1.setOnClickListener(v -> {
            if (onClickChoseListener != null) {
                onClickChoseListener.onClick();
            }
        });
//
//      iv_traveler_chose.setImageResource(R.mipmap.icon_pessenger);
        return this;
    }

    public interface onClickChoseListener {
        void onClick();
    }

    private onClickChoseListener onClickChoseListener;

    public void setOnClickChoseListener(TravelerInfoView.onClickChoseListener onClickChoseListener) {
        this.onClickChoseListener = onClickChoseListener;
    }

    public void setHint(String content) {
        et_traveler_content.setHint(content);
        tv_traveler_title.setText(content);
        tv_traveler_content.setText(content);
        tv_traveler_content.setTextColor(ResourceUtils.getColor(R.color.gray_c5c5c5));
    }

    public void setTitle(String content) {
        tv_traveler_title.setText(content);
    }

    public String getText() {
        String content = "";
        if (mIsChose) {
            content = tv_traveler_content.getText().toString();
        } else {
            content = et_traveler_content.getText().toString();
        }
        return content;
    }

    public void setText(String content) {
        if (mIsChose) {
            tv_traveler_content.setText(content);
            tv_traveler_content.setTextColor(ResourceUtils.getColor(R.color.gray_767676));
        } else {
            et_traveler_content.setText(content);
        }
    }


    public EditText getEditText() {
        return et_traveler_content;
    }

    public TextView getHintText() {
        return tv_traveler_content;
    }

    public void setLimint(InputTypeTag type) {
        if (type == InputTypeTag.EMAIL) {
            et_traveler_content.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (type == InputTypeTag.NUMBER) {
            et_traveler_content.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {

            et_traveler_content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String editable = et_traveler_content.getText().toString();
                    String str = stringFilter(editable.toString());
                    if (!editable.equals(str)) {
                        et_traveler_content.setText(str);
                        //设置新的光标所在位置
                        et_traveler_content.setSelection(et_traveler_content.getText().toString().length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    /**
     * 默认货币种类
     */
    public enum InputTypeTag {
        EMAIL,
        NO_CHINESE,
        NUMBER
    }


    public String stringFilter(String str) {
        // 只允许字母、数字、英文空白字符
        String regEx = "[^a-zA-Z0-9\\s]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    /**
     * 信息是否包含中文
     *
     * @param isChinese
     */
    public void setParentBackground(boolean isChinese) {
        if (isChinese) {
            rl_traveler_parent.setBackground(ResourceUtils.getDrawable(R.drawable.plane_round_red_solid));
        } else {
            rl_traveler_parent.setBackground(ResourceUtils.getDrawable(R.drawable.plane_round_gray_solid));
        }
    }
    public void setEmptyEditTextBg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            rl_traveler_parent.setBackground(ResourceUtils.getDrawable(R.drawable.plane_round_red_solid));
            tvMsg.setVisibility(VISIBLE);
            tvMsg.setText(msg);
        } else {
            rl_traveler_parent.setBackground(ResourceUtils.getDrawable(R.drawable.plane_round_gray_solid));
            tvMsg.setVisibility(GONE);
            tvMsg.setText("");
        }
    }
}
