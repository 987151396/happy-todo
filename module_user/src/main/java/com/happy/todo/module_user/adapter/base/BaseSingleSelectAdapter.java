package com.happy.todo.module_user.adapter.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.happy.todo.lib_common.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 单选adapter
 * Created by Jaminchanks on 2018/1/16.
 */

public abstract class BaseSingleSelectAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<Status<T>, K>{
    private Status<T> mLastSelectedItem = null;

    private OnItemSelectedListener mItemSelectedListener;

    public BaseSingleSelectAdapter(int layoutResId, @Nullable List<Status<T>> data) {
        super(layoutResId, data);

        setOnItemClickListener((adapter, view, position) -> setItemOnStatus(position, true));
    }

    public BaseSingleSelectAdapter(@Nullable List<Status<T>> data) {
        this(0, data);
    }

    public BaseSingleSelectAdapter(int layoutResId) {
        this(layoutResId, null);
    }

    /**
     * 获取被选择的项
     * @return
     */
    public T getCurrentSelectedItem() {
        if (mLastSelectedItem != null) {
            return mLastSelectedItem.getData();
        } else {
            return null;
        }
    }


    @Override
    protected final void convert(K helper, Status<T> item) {
        //处理与选择状态无关的视图变化
        convertView(helper, item.getData());
        //处理与选择状态相关的视图变化
        selectView(helper, item.getData(), item.isOnStatus());
    }


    /**
     * 设置与状态无关的视图变化
     * @param helper
     * @param item
     */
    protected abstract void convertView(K helper, T item);

    /**
     * 设置处于状态之下的视图变化
     */
    protected abstract void selectView(K helper, T item, boolean isInStatus);


    /**
     * 手动设置某项被选中
     * @param position
     * @param isInvokeClickEvent 是否触发点击事件
     */
    public final void setItemOnStatus(int position, boolean isInvokeClickEvent) {
        List<Status<T>> list =  getData();
        Status<T> currentItem = list.get(position);

        if (mLastSelectedItem == currentItem) {
            return;
        }

        //刷新上条记录
        if (mLastSelectedItem != null) {
            mLastSelectedItem.setOnStatus(false);
        }

        currentItem.setOnStatus(true);
        mLastSelectedItem = currentItem;

        if (isInvokeClickEvent && mItemSelectedListener != null) {
            mItemSelectedListener.onSelected(position);
        }
        notifyDataSetChanged();
    }

    /**
     * 自定义的item点击事件
     * @param listener
     */
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.mItemSelectedListener = listener;
    }

    /**
     * 增加的点击事件
     */
    public interface OnItemSelectedListener {
        void onSelected(int position);
    }


    /**
     * 一般建议使用 {@link #setNewData1(List)}
     * @param data
     */
    @Override
    public final void setNewData(@Nullable List<Status<T>> data) {
        super.setNewData(data);
    }


    /**
     * 使用{@link #replaceData1(Collection)}
     * @param data
     */
    @Deprecated
    @Override
    public final void replaceData(@NonNull Collection<? extends Status<T>> data) {
        super.replaceData(data);
    }

    /**
     * 使用{@link #addData1(Object)}
     * @param data
     */
    @Deprecated
    @Override
    public final void addData(@NonNull Status<T> data) {
        super.addData(data);
    }


    /**
     * 使用{@link #setData1(int, Object)}
     * @param index
     * @param data
     */
    @Deprecated
    @Override
    public final void setData(int index, @NonNull Status<T> data) {
        super.setData(index, data);
    }



    public void setNewData1(@Nullable List<T> data) {
        super.setNewData(toStatusList(data));
    }


    public void replaceData1(@NonNull Collection<? extends T> data) {
        List<T> sourceList = new ArrayList<>();
        if (data.size() != 0) {
            sourceList.addAll(data);
        }

        super.replaceData(toStatusList(sourceList));
    }


    public void addData1(@NonNull T data) {
        super.addData(new Status<>(data, false));
    }


    public void setData1(int index, @NonNull T data) {
        super.setData(index, new Status<>(data, false));
    }


    /**
     * 转换工具类
     **/
    private List<Status<T>> toStatusList(List<? extends T> sourceList) {
        List<Status<T>> resultList = new ArrayList<>();
        if (!ListUtils.isEmpty(sourceList)) {
            for (int i = 0; i < sourceList.size(); i++) {
                resultList.add(new Status<T>(sourceList.get(i), false));
            }
        }
        return resultList;
    }


}
