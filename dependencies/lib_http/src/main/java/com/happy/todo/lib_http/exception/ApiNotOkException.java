package com.happy.todo.lib_http.exception;

/**
 * 状态码返回不成功，但是需要获取到该状态下的已解析成功的数据
 * Created by Jaminchanks on 2018/2/6.
 */

public class ApiNotOkException extends RuntimeException {
    private String code;
    private String msg;
    private Object data;


    public ApiNotOkException(String code, String msg, Object data){
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
