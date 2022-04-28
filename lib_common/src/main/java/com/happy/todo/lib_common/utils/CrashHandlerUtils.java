package com.happy.todo.lib_common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.gson.Gson;
import com.happy.todo.lib_common.bean.ErrorInfoBean;

import com.happy.todo.lib_http.https.HttpsUtils;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @Describe：崩溃信息工具类
 * @Date： 2019/03/04
 * @Author： dengkewu
 * @Contact：
 */
public class CrashHandlerUtils implements Thread.UncaughtExceptionHandler {
    //系统默认的异常处理器
    private Thread.UncaughtExceptionHandler defaultCrashHandler;
    private static CrashHandlerUtils crashHandler = new CrashHandlerUtils();
    private Context mContext;

    String space="--";

    //机器人url地址
    private static final String url = "https://oapi.dingtalk.com/robot/send?access_token=16164524effa919927dd9314bc92510aaebc9d849491c31fb36f3a949f4b3f35";

    //私有化构造函数
    private CrashHandlerUtils() {
    }

    //获取实例
    public static CrashHandlerUtils getInstance() {
        return crashHandler;
    }

    public void init(Context context) {
        //TODO 先开启debug模式通知，方便处理bug
        if (!AppUtil.isDebug()) {
        mContext = context;
        defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置系统的默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //把错误信息保存在sp中，然后在下次进入页面的时候再上传错误信息
        saveErrorInfo(throwable);
        if (defaultCrashHandler != null) {
            //如果在自定义异常处理器之前，系统有自己的默认异常处理器的话，调用它来处理异常信息
            defaultCrashHandler.uncaughtException(thread, throwable);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    public void sendError() {
        //TODO 先开启debug模式通知，方便处理bug
        if (!AppUtil.isDebug()) {
        String data = (String) SPUtil.newInstance().get("errorInfo", "");
        if (!data.equals("")) {
            ErrorInfoBean errorInfoBean = new ErrorInfoBean();
            //设置消息类型
            errorInfoBean.setMsgtype("link");
            ErrorInfoBean.LinkBean linkBean = new ErrorInfoBean.LinkBean();
            linkBean.setMessageUrl("https://bugly.qq.com/v2/crash-reporting/crashes/39bba8207c?pid=1");
            linkBean.setPicUrl("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2376684129,460330055&fm=58&s=98225E3285305C21006D98C60200D0B2&bpow=121&bpoh=75");
            linkBean.setTextX(data);
            linkBean.setTitle("Android崩溃日志");
            errorInfoBean.setLink(linkBean);
            post(url, new Gson().toJson(errorInfoBean));

            ErrorInfoBean errorInfoBean1 = new ErrorInfoBean();
            //设置消息类型
            errorInfoBean1.setMsgtype("text");
            //设置@的对象
            ErrorInfoBean.AtBean atBean = new ErrorInfoBean.AtBean();
            atBean.setIsAtAll(false);
            List<String> mobiles = new ArrayList<>();
            mobiles.add("18272399463");
            atBean.setAtMobiles(mobiles);
            errorInfoBean1.setAt(atBean);
            //设置消息
            ErrorInfoBean.TextBean textBean = new ErrorInfoBean.TextBean();
            textBean.setContent("app崩溃啦，请尽快处理");
            errorInfoBean1.setText(textBean);
            post(url, new Gson().toJson(errorInfoBean1));

        }
        }
    }

    private void saveErrorInfo(Throwable throwable) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getAppInfo(mContext));
        stringBuffer.append("崩溃时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append(space);
        stringBuffer.append("手机系统：").append(Build.VERSION.RELEASE).append(space);
        stringBuffer.append("手机型号：").append(Build.MODEL).append(space);
        stringBuffer.append("崩溃信息：").append(throwable).append(space);
        SPUtil.newInstance().saveObject("errorInfo", stringBuffer.toString());
    }

    /**
     * 获取应用程序信息
     */
    public String getAppInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return "应用版本：" + packageInfo.versionName + space;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpsUtils.PubSSLParams pubSSLParams = HttpsUtils.getPubSSLParams();
        builder.sslSocketFactory(pubSSLParams.sslSocketFactory, pubSSLParams.trustManager);
        builder.hostnameVerifier(pubSSLParams.do_not_verify);
        OkHttpClient client = builder.build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                AppLogUtil.e("app崩溃信息" + json);
                SPUtil.newInstance().remove("errorInfo");
            }
        });

    }


}
