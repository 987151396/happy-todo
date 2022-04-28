package com.happy.todo.lib_common.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.View;

import com.happy.todo.lib_common.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

/**
 * @Describe：切换语言底部dialog（之前的存在内存泄漏问题）
 * @Date： 2019/03/19
 * @Author： dengkewu
 * @Contact：
 */
public class AppBottomSettingWheelDialog extends BottomSheetDialog {
    private WheelView mWheelView;
    private  ArrayWheelAdapter arrayWheelAdapter;

    public AppBottomSettingWheelDialog(@NonNull Context context) {
        this(context,0);

    }

    public AppBottomSettingWheelDialog(@NonNull Context context, int theme) {
        super(context, theme);
        View inflate = View.inflate(getContext(),R.layout.dialog_bottom_setting_wheel,null);
        setContentView(inflate);
        mWheelView = (WheelView) inflate.findViewById(R.id.wheelview);
        arrayWheelAdapter = new ArrayWheelAdapter(getContext());
        mWheelView.setWheelAdapter(arrayWheelAdapter); // 文本数据源
        mWheelView.setLoop(true);
        mWheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        inflate.findViewById(R.id.tv_ok).setOnClickListener(view -> dismiss());
        //禁止下滑关闭
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        arrayWheelAdapter=null;
    }

    public AppBottomSettingWheelDialog setOkClickListener(OnOkClickListener okClickListener) {
        if (okClickListener != null) {
            findViewById(R.id.tv_ok).setOnClickListener(view -> {
                okClickListener.onClick(this, mWheelView.getCurrentPosition());
                dismiss();
            });
        }
        return this;
    }
    public interface OnOkClickListener {
        void onClick(AppBottomSettingWheelDialog dialog, int position);
    }

    /**
     * 设置数据并选中指定
     *
     * @param stringList
     * @param position
     * @return
     */
    public AppBottomSettingWheelDialog setWheelData(List<String> stringList, int position) {
        if (stringList != null && stringList.size() > 0) {
            mWheelView.setWheelData(stringList);
            return setSelection(position);
        }
        return this;
    }

    public AppBottomSettingWheelDialog setSelection(int position) {
        if (position > -1 && mWheelView != null && position < mWheelView.getWheelCount()) {
            mWheelView.setSelection(position);
            mWheelView.post(() -> mWheelView.setVisibility(View.VISIBLE));
        }
        return this;
    }

    /**
     * 设置是否循环，默认为 true
     *
     * @param isLoop
     * @return
     */
    public AppBottomSettingWheelDialog setLoop(boolean isLoop) {
        if (mWheelView != null) {
            mWheelView.setLoop(isLoop);
        }
        return this;
    }

    public AppBottomSettingWheelDialog setWheelData(List<String> stringList) {
        return setWheelData(stringList, 0);
    }

    /**
     * 设置皮肤
     * @param skin
     * @return
     */
    public AppBottomSettingWheelDialog setSkin(WheelView.Skin skin){
        if (mWheelView != null){
            mWheelView.setSkin(skin);
        }
        return this;
    }

    /**
     * 设置文字颜色大小
     * @param style
     * @return
     */
    public AppBottomSettingWheelDialog setStyle(WheelView.WheelViewStyle style){
        if (mWheelView != null){
            mWheelView.setStyle(style);
        }
        return this;
    }
}
