package com.happy.todo.module_main.ui.guide;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.happy.todo.lib_common.image.AppImageLoader;
import com.happy.todo.module_main.R;

import java.util.List;

/**
 * @功能描述：
 * @创建日期： 2018/07/21
 * @作者： dengkewu
 */

public class VpGuideAdapter extends PagerAdapter {
    private Context mContext;
    private List<GuideBean> mList;

    public VpGuideAdapter(Context mContext, List<GuideBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //返回false 所有不显示。返回true部分显示部分不显示。代表了当前的页面是否与给定的键相关联
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.main_item_guide, null);

        ImageView item_iv_guide = (ImageView) view.findViewById(R.id.item_iv_guide);
        TextView tvNotNow = view.findViewById(R.id.tv_not_now);
        Button btnGuideEnter = view.findViewById(R.id.btn_guide_enter);
        TextView item_tv_guide_title = view.findViewById(R.id.item_tv_guide_title);
        TextView item_tv_guide_content = view.findViewById(R.id.item_tv_guide_content);
        AppImageLoader.create(item_iv_guide).with(mContext).load(mList.get(position).getImages()).execute();
        item_tv_guide_title.setText(mList.get(position).getTitle());
        item_tv_guide_content.setText(mList.get(position).getContent());
        btnGuideEnter.setText(mList.get(position).getBtnContent());

        tvNotNow.setOnClickListener(v -> {
            if (onItemOnClickLisenter!=null){
                onItemOnClickLisenter.onCancel(v,position);
            }
        });
        btnGuideEnter.setOnClickListener(v -> {
            if (onItemOnClickLisenter!=null){
                onItemOnClickLisenter.onEnter(v,position);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //销毁
        container.removeView((View) object);

    }

    public interface onItemOnClickLisenter{
        void onEnter(View v,int pos);
        void onCancel(View v,int pos);
    }
    private onItemOnClickLisenter onItemOnClickLisenter;

    public void setOnItemOnClickLisenter(VpGuideAdapter.onItemOnClickLisenter onItemOnClickLisenter) {
        this.onItemOnClickLisenter = onItemOnClickLisenter;
    }
}

