
package com.happy.todo.lib_common.http;

/**
 * <p>描述：简单的回调,默认可以使用该回调，不用关注其他回调方法</p>
 * 使用该回调默认只需要处理onError，onSuccess两个方法既成功失败<br>
 */
public abstract class SimpleAppCallBack<T> extends BaseAppCallBack<T> {

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
    }

}
