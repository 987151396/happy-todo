package com.happy.todo.lib_common.widget.pickerselector.mode;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by iTimeTraveler on 2018/9/14.
 */

public interface IPickerItemView {

    View onCreateView(ViewGroup parent);

    void onBindView(ViewGroup parent, View view, int position);
}
