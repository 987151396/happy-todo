package com.happy.todo.lib_common.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.happy.todo.lib_common.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.internal.InternalClassics;

/**
 * 默认的上拉加载更多布局设置
 * Created by Jaminchanks on 2018/8/29.
 */
public class DefaultRefreshFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {

    private ProgressBar mProgressBar;
    private TextView mTvTip;
    private Context mContext;


    public DefaultRefreshFooter(Context context) {
        this(context, null, 0);
    }

    public DefaultRefreshFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }


    private void initView(Context context) {
        this.mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer, this);
        mProgressBar = rootView.findViewById(R.id.pgb_load_more);
        mTvTip = rootView.findViewById(R.id.tv_load_more);
    }


    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (noMoreData) {
            Toast.makeText(mContext, R.string.refresh_no_more_data, Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
