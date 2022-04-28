package com.happy.todo.lib_common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.happy.todo.lib_common.utils.ToastUtil;
import com.happy.todo.lib_common.widget.dialog.AppLoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者: TwoSX
 * @时间: 2017/12/3 下午8:57
 * @描述: 所有 Fragment 的基类
 */
public abstract class BaseFragment extends Fragment {
    protected String mPathName;
    protected View mRootView;
    private Unbinder mUnbinder;
    private AppLoadingDialog mLoadingDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPathName = getClass().getSimpleName();
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    protected abstract void initView();


    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    protected abstract int getLayoutId();


    /**
     * 弹出Toast
     */
    public void showToast(String str) {
        if (!TextUtils.isEmpty(str)) {
            ToastUtil.showShort(str);
        }
    }

    /**
     * 弹出Toast
     */
    public void showToast(int str) {
        ToastUtil.showShort(str);
    }


    /**
     * 显示loading 窗口
     * @param msg 标题
     */
    public void showLoading(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppLoadingDialog.Builder(getActivity())
                    .setTxt(msg)
                    .create();
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }


    /**
     * 取消loading 窗口
     */
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }
    /**
     * 设置可取消
     */
    public void setDialogIsCalcel(boolean isCancel){
        mLoadingDialog.setCancelable(isCancel);
    }
    /**
     * 显示loading 窗口
     * @param msg 标题
     */
    public void showLoading(String msg,boolean isCancel) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new AppLoadingDialog.Builder(getActivity())
                    .setTxt(msg)
                    .create();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
        setDialogIsCalcel(isCancel);

    }
}

