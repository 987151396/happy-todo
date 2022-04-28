package com.happy.todo.lib_common.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.happy.todo.lib_common.utils.AppLogUtil;

/**
 * @Describe：自动添加占位符的输入框
 * @Date： 2019/06/11
 * @Author： dengkewu
 * @Contact：
 */
public class PlaceHolderEditText extends androidx.appcompat.widget.AppCompatEditText {
    //上次输入框中的内容
    private String lastString;
    //光标的位置
    private int selectPosition;
    //输入框内容改变监听
    private TextChangeListener listener;

    //追加字符
    private String item = " ";

    public PlaceHolderEditText(Context context) {
        super(context);
        initView();
    }

    public PlaceHolderEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    public PlaceHolderEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }


            /**
             * 当输入框内容改变时的回调
             * @param s  改变后的字符串
             * @param start 改变之后的光标下标
             * @param before 删除了多少个字符
             * @param count 添加了多少个字符
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //因为重新排序之后setText的存在
                //会导致输入框的内容从0开始输入，这里是为了避免这种情况产生一系列问题
                if (start == 0 && count > 1 && getSelectionStart() == 0) {
                    return;
                }

                String textTrim = getText().toString().trim();
                if (TextUtils.isEmpty(textTrim)) {
                    return;
                }

                //如果 before >0 && count == 0,代表此次操作是删除操作
                if (before > 0 && count == 0) {
                    selectPosition = start;
                    if (TextUtils.isEmpty(lastString)) {
                        return;
                    }
                    //将上次的字符串去空格 和 改变之后的字符串去空格 进行比较
                    //如果一致，代表本次操作删除的是空格
                    if (textTrim.equals(lastString.replaceAll(item, ""))) {
                        //帮助用户删除该删除的字符，而不是空格
                        StringBuilder stringBuilder = new StringBuilder(lastString);
                        stringBuilder.deleteCharAt(start - 1);
                        selectPosition = start - 1;
                        setText(stringBuilder.toString());
                    }
                } else {
                    //此处代表是添加操作
                    //当光标位于空格之前，添加字符时，需要让光标跳过空格，再按照之前的逻辑计算光标位置
                    if ((start + count) % 5 == 0) {
                        selectPosition = start + count + 1;
                    } else {
                        selectPosition = start + count;
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                //获取输入框中的内容,不可以去空格
                String etContent = getText().toString();
                if (TextUtils.isEmpty(etContent)) {
                    if (listener != null) {
                        listener.textChange("");
                    }
                    return;
                }
                //重新拼接字符串
                String newContent = addSpaceByCredit(etContent);
                //保存本次字符串数据
                lastString = newContent;

                //如果有改变，则重新填充
                //防止EditText无限setText()产生死循环
                if (!newContent.equals(etContent)) {
                    setText(newContent);
                    try {
                        //保证光标的位置
                        setSelection(selectPosition > newContent.length() ? newContent.length() : selectPosition);
                    } catch (Exception e) {
                        AppLogUtil.e("超过限制字符");
                    }

                }
                //触发回调内容
                if (listener != null) {
                    listener.textChange(newContent);
                }

            }
        });
    }


    /**
     * 输入框内容回调，当输入框内容改变时会触发
     */
    public interface TextChangeListener {
        void textChange(String text);
    }

    public void setTextChangeListener(TextChangeListener listener) {
        this.listener = listener;

    }

    /**
     * 每4位添加一个空格
     *
     * @param content
     * @return
     */
    public String addSpaceByCredit(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        content = content.replaceAll(item, "");
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        StringBuilder newString = new StringBuilder();
        for (int i = 1; i <= content.length(); i++) {
            if (i % 4 == 0 && i != content.length()) {
                newString.append(content.charAt(i - 1) + item);
            } else {
                newString.append(content.charAt(i - 1));
            }
        }
//        Log.i("mengyuan", "添加空格后："+newString.toString());
        return newString.toString();
    }

    /**
     * 获取追加字符前输入内容
     * @return
     */
    public String getInputText() {
        return getText().toString().replaceAll(item, "");
    }
}
