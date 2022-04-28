package com.happy.todo.lib_common.utils;

import java.math.BigDecimal;

/**
 * @Describe：地图距离计算工具类
 * @Date： 2019/04/24
 * @Author： dengkewu
 * @Contact：
 */
public class MapUtils {

    private static final double EARTH_RADIUS = 637_1393.00D;
    private static final double RADIAN = Math.PI / 180.00D;
    private static final double HALF = 0.5D;
    private static double lotions1;


    /**
     * 比如
     * point1 30，120
     * point2 31，121
     * 两点之间距离为146784.74米，约146.78公里
     * @return 返回double 类型的距离，向上进行四舍五入，距离精确到厘米，单位为米
     */
    public static double distanceOf(double lat1,double lng1,double lat2,double lng2) {
        try {
            double x, y, a, b, distance;
            lat1 *= RADIAN;
            lat2 *= RADIAN;
            x = lat1 - lat2;
            y = lng1 - lng2;
            y *= RADIAN;
            a = Math.sin(x * HALF);
            b = Math.sin(y * HALF);
            distance = EARTH_RADIUS * Math.asin(Math.sqrt(a * a + Math.cos(lat1) * Math.cos(lat2) * b * b)) / HALF;
            double location = new BigDecimal(distance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            //与ios统一
            lotions1 = location-0.6;
        }catch (Exception e){

        }
        return lotions1;
    }


}