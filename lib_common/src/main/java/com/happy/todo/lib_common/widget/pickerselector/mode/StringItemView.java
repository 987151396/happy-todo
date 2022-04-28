package com.happy.todo.lib_common.widget.pickerselector.mode;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;

import com.happy.todo.lib_common.utils.SizeUtil;
import com.happy.todo.lib_common.widget.pickerselector.picker.PicketOptions;
import com.happy.todo.lib_common.widget.pickerselector.view.AutoFitTextView;

/**
 * Created by iTimeTraveler on 2018/9/14.
 */
public class StringItemView extends BasePickerItemView<String> {

    public StringItemView(String data) {
        super(data);
        this.data = data;
    }

    @Override
    public View onCreateView(ViewGroup parent) {
        AutoFitTextView tv = new AutoFitTextView(parent.getContext());
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setPadding(50, 3, 50, 3);
        tv.setTextSize(PicketOptions.DEFAULT_TEXT_SIZE);
        tv.setHeight(SizeUtil.dp2px(44));

        //选中颜色
        int[] colors = new int[] {PicketOptions.SELECTED_TEXT_COLOR, PicketOptions.DEFAULT_TEXT_COLOR};
        int[][] states = {{android.R.attr.state_selected}, {}};
        tv.setTextColor(new ColorStateList(states, colors));
        tv.setText(data, parent.getMeasuredWidth());
        return tv;
    }

    @Override
    public void onBindView(ViewGroup parent, View view, int position) {
        if (view instanceof AutoFitTextView) {
            AutoFitTextView textView = ((AutoFitTextView) view);
            textView.setText(data, parent.getMeasuredWidth());
        }
    }

    @Override
    public String toString() {
        return "StringItemView{" +
                ", data='" + data + '\'' +
                '}';
    }
}
