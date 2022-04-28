package com.happy.todo.lib_common.http;


import com.happy.todo.lib_common.http.api.AppApi;
import com.happy.todo.lib_common.manage.LoginManage;
import com.happy.todo.lib_common.utils.AppLogUtil;
import com.happy.todo.lib_common.utils.DateUtil;
import com.happy.todo.lib_common.utils.EncodeUtil;
import com.happy.todo.lib_common.utils.EncryptUtil;
import com.happy.todo.lib_common.utils.ListUtils;
import com.happy.todo.lib_http.interceptor.BaseDynamicInterceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        if (dynamicMap == null) {
            dynamicMap = new TreeMap<>();
        }

        String random = DateUtil.getTimestamp() + "" + (10 + (int) (Math.random() * (99 - 10 + 1)));
        dynamicMap.put("random", random);

        //token最终不作为请求参数放在请求列表
        TreeMap<String, String> signParamMap = new TreeMap<>(dynamicMap);
        if (LoginManage.isLogin()) {
            signParamMap.put("token", LoginManage.getUserToken());
        }

        String signStr = spliceParam(signParamMap);

        String sign = signData(signStr);
        dynamicMap.put("sign", sign);
        return dynamicMap; //dynamicMap:是原有的全局参数+局部参数+新增的动态参数

    }

    private String spliceParam(TreeMap<String, String> treeMap) {
        StringBuilder signStr = new StringBuilder();

        //            signStr.append(entry.getKey()).append("=").append(EncodeUtil.urlDecode(entry.getValue())).append("&");
        List<Map.Entry<String, String>> paramList = new ArrayList<>(treeMap.entrySet());

        Collections.sort(paramList, (o1, o2) -> { //按参数升序
            String paramName1 = o1.getKey();
            String paramName2 = o2.getKey();
            int paramNameLength1 = paramName1.length();
            int paramNameLength2 = paramName2.length();
            int minParamWordSize = paramNameLength1 > paramNameLength2 ? paramNameLength2 : paramNameLength1;
            for (int i = 0; i < minParamWordSize; i++) {
                if (paramName1.charAt(i) == paramName2.charAt(i)) {
                    if (i == minParamWordSize -1) {
                        return Integer.compare(paramNameLength1, paramNameLength2);
                    }
                }  else if (paramName1.charAt(i) > paramName2.charAt(i)) {
                    return 1;
                } else {
                    return -1;
                }
            }

            return 0;
        });

        if (!ListUtils.isEmpty(paramList)) {
            for (int i = 0; i < paramList.size(); i++) {
                Map.Entry<String, String> entry = paramList.get(i);
                signStr.append(entry.getKey()).append("=").append(EncodeUtil.urlDecode(entry.getValue()));
                if (i < paramList.size() - 1) {
                    signStr.append("&");
                }
            }
        }

        return signStr.toString();
    }

    private String signData(String data) {
        AppLogUtil.e("sign加密前="+data);
        // 开始签名
        return EncryptUtil.md5(EncryptUtil.md5(data + AppApi.API_SIGN_KEY).toLowerCase() +
                AppApi.API_SIGN_KEY).toLowerCase();
    }

}
