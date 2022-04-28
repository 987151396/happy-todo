package com.happy.todo.lib_common.mvp.base;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 错误消息提示限定
 */
@IntDef(value = {ToastType.NONE, ToastType.DEFAULT, ToastType.TOP_ALTER, ToastType.CUSTOMER})
@Retention(RetentionPolicy.SOURCE)
public @interface ErrorMsgType{

}