package com.happy.todo.lib_common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.happy.todo.lib_common.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @作者: TwoSX
 * @时间: 2018/1/13 下午1:29
 * @描述: 底部时间选择弹窗
 */
public class AppBottomWheelDialog extends BasePopupWindow {

    private WheelView mWheelView;
    private  ArrayWheelAdapter arrayWheelAdapter;

    public AppBottomWheelDialog(Context context) {
        super(context);
        setOnBeforeShowCallback((popupRootView, anchorView, hasShowAnima) -> {
            setBlurBackgroundEnable(true);
            return true;
        });
        mWheelView = (WheelView) findViewById(R.id.wheelview);
        arrayWheelAdapter = new ArrayWheelAdapter(getContext());
        mWheelView.setWheelAdapter(arrayWheelAdapter); // 文本数据源
        mWheelView.setLoop(true);
        mWheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        findViewById(R.id.tv_ok).setOnClickListener(view -> dismiss());
    }


    /**
     * 设置皮肤
     * @param skin
     * @return
     */
    public AppBottomWheelDialog setSkin(WheelView.Skin skin){
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
    public AppBottomWheelDialog setStyle(WheelView.WheelViewStyle style){
        if (mWheelView != null){
            mWheelView.setStyle(style);
        }
        return this;
    }

    /**
     * 设置是否循环，默认为 true
     *
     * @param isLoop
     * @return
     */
    public AppBottomWheelDialog setLoop(boolean isLoop) {
        if (mWheelView != null) {
            mWheelView.setLoop(isLoop);
        }
        return this;
    }

    public AppBottomWheelDialog setWheelData(List<String> stringList) {
        return setWheelData(stringList, 0);
    }

    /**
     * 设置数据并选中指定
     *
     * @param stringList
     * @param position
     * @return
     */
    public AppBottomWheelDialog setWheelData(List<String> stringList, int position) {
        if (stringList != null && stringList.size() > 0) {
            mWheelView.setWheelData(stringList);
            return setSelection(position);
        }
        return this;
    }

    public AppBottomWheelDialog setSelection(int position) {
        if (position > -1 && mWheelView != null && position < mWheelView.getWheelCount()) {
            mWheelView.setSelection(position);
            mWheelView.post(() -> mWheelView.setVisibility(View.VISIBLE));
        }
        return this;
    }

    public int getSelection() {
        if (mWheelView != null)
            return mWheelView.getCurrentPosition() == -1 ? 0 : mWheelView.getCurrentPosition();
        return 0;
    }

    public AppBottomWheelDialog setOkClickListener(OnOkClickListener okClickListener) {
        if (okClickListener != null) {
            findViewById(R.id.tv_ok).setOnClickListener(view -> {
                okClickListener.onClick(this, mWheelView.getCurrentPosition());
                dismiss();
            });
        }
        return this;
    }


    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 200);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 200);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.dialog_bottom_wheel);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_content);
    }

    public interface OnOkClickListener {
        void onClick(AppBottomWheelDialog dialog, int position);
    }

    @Override
    public void dismiss() {
        //避免内存泄漏
        arrayWheelAdapter=null;
        super.dismiss();
    }
}
