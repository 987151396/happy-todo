package com.happy.todo.module_user.adapter.base;


/**
 * 带有状态的实体，单选或者多选列表适配器需要用到
 * Created by Jaminchanks on 2018/1/16.
 */

public class Status<T> {
    private T data;
    private boolean isOnStatus;


    public Status(T data, boolean isOnStatus) {
        this.data = data;
        this.isOnStatus = isOnStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOnStatus() {
        return isOnStatus;
    }

    public void setOnStatus(boolean onStatus) {
        isOnStatus = onStatus;
    }


}
