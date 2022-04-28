package com.happy.todo.lib_http.exception;

/**
 * Description: 处理服务器异常
 * Author: 贰师兄
 * Date: 2017/10/12 下午12:27
 */

public class ServerException extends RuntimeException {
    private String errCode;
    private String message;

    public ServerException(String errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}