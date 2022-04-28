package com.happy.todo.lib_common.utils;


import com.happy.todo.lib_common.utils.encode.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @功能描述：
 * @创建日期： 2018/08/09
 * @作者： dengkewu
 */

public class Base64Utils {
    public static String encode(String str){
        return android.util.Base64.encodeToString(str.getBytes(), android.util.Base64.NO_WRAP);
    }

    /*public static String encode2(String str){
        String encode="";
        try {
            String encodedString = Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            encode = URLEncoder.encode(encodedString, "utf-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encode;
    }*/

    /*public static String base64_encode(String str){
        String encode="";
        try {
            String encodedString = Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            String encode1 = URLEncoder.encode(encodedString, "utf-8");
            //调用两次encode特殊字符放在url才不会被转义
            encode = URLEncoder.encode(encode1);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encode;
    }*/
    public static String base64_encode(String str){
        String encode="";
        try {
            String encodedString = Base64.encode(str.getBytes(), Base64.DEFAULT_ENCODING);
            String encode1 = URLEncoder.encode(encodedString, "utf-8");
            //调用两次encode特殊字符放在url才不会被转义
            //encode = URLEncoder.encode(encode1);
            encode = encode1;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encode;
    }
}
