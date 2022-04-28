package com.happy.todo.lib_common.widget.pickerselector.mode;

/**
 * Created by iTimeTraveler on 2019/3/20.
 */
public abstract class BasePickerItemView<T> implements IPickerItemView {

    protected T data;

    public BasePickerItemView(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
