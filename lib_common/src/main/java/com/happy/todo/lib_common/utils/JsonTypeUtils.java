package com.happy.todo.lib_common.utils;

import com.alibaba.android.arouter.utils.TextUtils;

/**
 * @功能描述：判断json类型
 * @创建日期： 2018/07/12
 * @作者： dengkewu
 */

public class JsonTypeUtils {
    public enum JSON_TYPE
    {
        /**JSONObject*/
        JSON_TYPE_OBJECT,
        /**JSONArray*/
        JSON_TYPE_ARRAY,
        /**不是JSON格式的字符串*/
        JSON_TYPE_ERROR
    }

    /***
     *
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
     *
     * @param str
     * @return
     */
    public static JSON_TYPE getJSONType(String str,String key) {
        if (TextUtils.isEmpty(str)) {
            return JSON_TYPE.JSON_TYPE_ERROR;
        }
//        int len=key.length()+3;//3表示：" " :
        String[] split = str.split(key);
        if (split.length > 1) {
            String str1 = split[1];
            char[] chars = str1.substring(2, 3).toCharArray();
            char firstChar = chars[0];
            AppLogUtil.e("数据：" + str1);
            if (firstChar == '{') {
                return JSON_TYPE.JSON_TYPE_OBJECT;
            } else if (firstChar == '[') {
                return JSON_TYPE.JSON_TYPE_ARRAY;
            }
        }
            return JSON_TYPE.JSON_TYPE_ERROR;

    }
}
