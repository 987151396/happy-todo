package com.happy.todo.lib_http.model;

/**
 * Description:
 * Author: 贰师兄
 * Date: 2017/10/12 下午12:36
 */

public class ApiResult<T> {
    public static final String CODE_SUCCESS = "2000";
//    public static final String CODE_PLANE_CHECK_PRICE = "F_4001"; //机票校验接口返回
    private String msg;
    private String code;
    private T data;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return code.equals(CODE_SUCCESS);
    }

    @Override
    public String toString() {

        return "ApiResult{" +
                "code='" + code + '\'' +
                ", msg='" + (msg == null ? "null" : msg) + '\'' +
                ", data=" + (data == null ? "null" : data) +
                '}';
    }
}
