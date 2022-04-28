package com.happy.todo.lib_common.utils;

import java.util.Map;

/**
 * Created by Jaminchanks on 2018/1/25.
 */

public class PreChecker {

    /**
     * map 非空判断，要求对象不为null，对于String，可以为""
     * @param map
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     */
    public static <K, V> void putToMap(Map<K, V> map, K key, V value) {
        if (map != null && key != null && value != null) {
            map.put(key , value);
        }
    }

    /**
     * 一种不是很优雅的实现方式...
     * 相当于 value == null ? defaultValue : value
     * @param value 原值
     * @param defaultValue 默认值
     * @param <T> 类型
     * @return
     */
    public static <T> T ifNull(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


    /**
     *  传入一个参数函数，对于函数内的任一空异常处理会自动捕获，并忽略掉
     *  类似于kotlin中的 ?.
     * @param action
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T ifNull(T defaultValue, Action<T> action) {
        T value = defaultValue;
        try {
            value = action.getValue();
        } catch (NullPointerException ignored) {
        }

        return value;
    }


    public interface Action<T> {
        T getValue();
    }

}
