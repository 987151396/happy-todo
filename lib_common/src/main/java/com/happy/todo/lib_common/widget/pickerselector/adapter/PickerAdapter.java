package com.happy.todo.lib_common.widget.pickerselector.adapter;

import com.happy.todo.lib_common.widget.pickerselector.picker.WheelPicker;

/**
 * Created by iTimeTraveler on 2018/8/12.
 */

public abstract class PickerAdapter implements IPickerAdapter {

    /**
     * 多少列
     * @param wheelPicker
     */
    public abstract int numberOfComponentsInWheelPicker(WheelPicker wheelPicker);

    /**
     * 某列有多少行
     * @param component
     */
    public abstract int numberOfRowsInComponent(int component);

    /**
     * 某列宽度
     * @param component
     */
    public int widthForComponent(int component) {
        return 0;
    }

    /**
     * 某列行高
     * @param component
     */
    public int rowHeightForComponent(int component) {
        return 0;
    }

    @Override
    public String labelOfComponent(int component) {
        return null;
    }
}
