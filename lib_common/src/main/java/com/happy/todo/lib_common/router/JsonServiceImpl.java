package com.happy.todo.lib_common.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 自定义对象传递解析工具
 * Created by Jaminchanks on 2018/6/28.
 */
@Route(path = RouterPath.SERIALIZATION)
public class JsonServiceImpl implements SerializationService {
    private Gson mGson;

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return mGson.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return mGson.toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return mGson.fromJson(input, clazz);
    }

    @Override
    public void init(Context context) {
        mGson = new Gson();
    }
}
