package com.happy.todo.lib_common.bean;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.happy.todo.lib_common.R;

/**
 * @Describe：
 * @Date： 2018/11/19
 * @Author： dengkewu
 * @Contact：
 */
public class CommonTabEntity implements CustomTabEntity {
    public String title;

    public CommonTabEntity(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    // 随便的，因为不显示
    @Override
    public int getTabSelectedIcon() {
        return R.mipmap.icon_back;
    }

    // 随便的，因为不显示
    @Override
    public int getTabUnselectedIcon() {
        return R.mipmap.icon_back;
    }
}
