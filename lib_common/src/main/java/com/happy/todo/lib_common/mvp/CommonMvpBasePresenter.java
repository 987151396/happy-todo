package com.happy.todo.lib_common.mvp;

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 同一管理所有presenter的detachView事件,避免每次都编写重复的销毁请求相关的代码
 * Created by Jaminchanks on 2018/6/15.
 */
public class CommonMvpBasePresenter<V extends MvpView> extends MvpNullObjectBasePresenter<V> implements MvpPresenter<V> {
    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    private CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable... disposables) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.addAll(disposables);
    }


    @Override
    public void detachView() {
        super.detachView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}