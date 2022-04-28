package com.happy.todo.lib_common.utils.userutil;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.ResourceUtils;

/**
 * 性别解析工具
 * Created by Jaminchanks on 2018/1/25.
 */

public enum  Gender {
    OTHERS("0", R.string.personal_sex_other, R.mipmap.user_ic_default_avartar),
    MALE("1", R.string.personal_sex_man, R.mipmap.user_ic_default_avartar),
    FEMALE("2", R.string.personal_sex_woman, R.mipmap.user_ic_default_avartar);

    private String code;
    private int stringId;
    private int drawableId;

    Gender(String code, @StringRes int stringId, @DrawableRes int drawableId) {
        this.code = code;
        this.stringId = stringId;
        this.drawableId = drawableId;
    }


    public String getString() {
        return ResourceUtils.getString(stringId);
    }


    /**
     * 文字转代号
     * @param genderStr
     * @return
     */
    public static String parseGenderStr2Code(String genderStr) {
        Gender gender = null;
        for (Gender gender1 : Gender.values()) {
            if (gender1.getString().equals(genderStr)) {
                gender = gender1;
                break;
            }
        }

        if (gender == null) {
            return "";
        } else {
            return gender.code;
        }
    }


    /**
     * 代号转文字
     * @param code
     * @return
     */
    public static String parseGenderCode2Str(String code) {
        Gender gender = null;

        for (Gender gender1 : Gender.values()) {
            if (gender1.code.equals(code)) {
                gender = gender1;
                break;
            }
        }

        if (gender == null) {
            return "";
        } else {
            return gender.getString();
        }
    }


    /**
     * 代码转图片
     * @param code
     * @return
     */
    public static int parseGenderCode2Drawable(String code) {
        Gender gender = null;

        for (Gender gender1 : Gender.values()) {
            if (gender1.code.equals(code)) {
                gender = gender1;
                break;
            }
        }

        if (gender == null) {
            return MALE.drawableId; //默认显示男头像
        } else {
            return gender.drawableId;
        }
    }
}
