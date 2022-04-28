package com.happy.todo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jaminchanks on 2018/2/27.
 */

public class TestJsonClass {
    private String a;
    @SerializedName(value = "_")
    private String __;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String get_() {
        return __;
    }

    public void set_(String __) {
        this.__ = __;
    }
}
