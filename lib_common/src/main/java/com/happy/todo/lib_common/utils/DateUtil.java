package com.happy.todo.lib_common.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.happy.todo.lib_common.R;
import com.happy.todo.lib_common.utils.language.LanguageUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by TEAM iFREE on 2017/2/23.
 */

public class DateUtil {
    private static String[] weeks = new String[]{Utils.getApp().getResources().getString(R.string.week_title1),
            Utils.getApp().getResources().getString(R.string.week_title2),
            Utils.getApp().getResources().getString(R.string.week_title3),
            Utils.getApp().getResources().getString(R.string.week_title4),
            Utils.getApp().getResources().getString(R.string.week_title5),
            Utils.getApp().getResources().getString(R.string.week_title6),
            Utils.getApp().getResources().getString(R.string.week_title7)};

    private DateUtil() {
    }

    public static Long getTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    // 秒数转格式
    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                //                if (hour > 99)
                //                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    /**
     * 十位时间戳转格式 yyy.MM.dd
     */
    public static String timestampToString7(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyy.MM.dd");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 十位时间戳转格式 yyyy-MM-dd
     */
    public static String timestampToString1(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 十位时间戳转格式 yyyy-MM-dd HH:mm:ss
     */
    public static String timestampToString3(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String timestampToStringT(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 十位时间戳转格式 HH:mm:ss
     */
    public static String timestampToString4(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        //设置时区，跳过此步骤会默认设置为"GMT+08:00" 得到的结果会多出来8个小时
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 十位时间戳转格式
     *
     * @param time
     * @return
     */
    public static String timestampToString5(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 十位时间戳转格式
     *
     * @param time
     * @return
     */
    public static String timestampToString6(long time) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long) time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 获取当前日期 MM-dd
     */
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String tsStr = dateFormat.format(getTimestamp());
        return tsStr;
    }


    /**
     * 获取当前日期Date格式
     */
    public static Date getCurrentDate2() {
        Date curDate = new Date(System.currentTimeMillis());
        return curDate;
    }

    /**
     * 获取当天日期Date格式
     */
    public static Date getCurrentDate5() {
        Date curDate = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tsStr = dateFormat.format(curDate.getTime());

        Date parse = null;
        try {
            parse = dateFormat.parse(tsStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat result_form = new SimpleDateFormat("HH:mm");
        String dateTime = "";

        Date curDate1 = new Date(parse.getTime());
//        String ss= result_form.parse(s);
        return curDate1;
    }

    /**
     * 获取当前日期 MM-dd
     */
    public static String getCurrentDate3(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String tsStr = dateFormat.format(date.getTime());
        return tsStr;
    }

    /**
     * 获取当前日期 MM-dd
     */
    public static String getCurrentDate4(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tsStr = dateFormat.format(date.getTime());
        return tsStr;
    }

    /**
     * 获取当前星期几
     */
    public static String getCurrentWeekDay() {
        SimpleDateFormat format = new SimpleDateFormat("E");
        String tsStr = format.format(getTimestamp());
        return tsStr;
    }

    /**
     * 获取下一天日期的月日 MM-dd
     */
    public static String getNextDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String tsStr = dateFormat.format(c.getTime());
        return tsStr;
    }

    /**
     * 获取下一天日期的月日 Date格式
     */
    public static Date getNextDate2() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取日期月日
     *
     * @param day
     * @return
     */
    public static Date getNextDate3(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 获取当前日期是星期几
     */
    public static String getNextWeekDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
//        return weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
        SimpleDateFormat format = new SimpleDateFormat("E");
        String tsStr = format.format(c.getTime());
        return tsStr;
    }

    /**
     * 获取指定天数日期的月日 Date格式
     */
    public static Date getDate(int dayCount) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, dayCount);
        return c.getTime();
    }

    /**
     * 获取指定天数日期的月日 Date格式
     */
    public static Date getDate(Date data, int dayCount) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DAY_OF_MONTH, dayCount);
        return c.getTime();
    }


    /**
     * 获取X天的凌晨12点时间戳
     *
     * @return
     */
    public static Date getDate1(int dayCount) {
        Date currentDate5 = getCurrentDate5();
        long daySecond = 60 * 60 * 24 * dayCount * 1000;
        long time = currentDate5.getTime();

        long dayTime = time + daySecond;
        Date date = new Date(dayTime);
        return date;
    }


    private static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 获取选择的日期的月日和周几  MM月dd日 , E
     */
    public static String getSelectDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_plane2));
        String tsStr = format.format(date.getTime());
        return tsStr;
    }


    /**
     * 获取选择的日期的月日   dd MMM（日期 月份）
     */
    public static String getSelectDay2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_hotel), LanguageUtil.newInstance().getSysLocale(Utils.getApp()));
        String tsStr = format.format(date.getTime());
        return tsStr;
    }

    /**
     * 获取选择的日期的月日   dd MMM yyyy（日期 月份 年份）
     */
    public static String getSelectDay3(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_hotel1), LanguageUtil.newInstance().getSysLocale(Utils.getApp()));
        String tsStr = format.format(date.getTime());
        return tsStr;
    }

    /**
     * 获取选择的日期的月日   HH:mm E,dd MMM(日期 月份）
     */
    public static String getSelectDay4(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(ResourceUtils.getString(R.string.date_type_hotel4), LanguageUtil.newInstance().getSysLocale(Utils.getApp()));
        String tsStr = format.format(date.getTime());
        return tsStr;
    }


    /**
     * 把指定的时间戳转化为可阅读的时间格式
     *
     * @param time
     * @param format
     * @return
     */
    public static String getFormatTime(long time, String format) {
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = time * 1000;
        Timestamp ts = new Timestamp(temp);
        String tsStr = "";
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            tsStr = dateFormat.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }

    /**
     * 根据时间戳计算相差日期
     *
     * @param startTime
     * @param endTime
     * @return
     */
    /**
     * 根据时间戳计算相差日期
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getApartDay(Long startTime, Long endTime) {
        int day = (int) ((endTime - startTime)) / (24 * 60 * 60);
        return day;
    }

    /**
     * 计算两个时间相差x小时x分钟
     */
    public static String getDifferentHours(String startime, String endTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Date startimeDate = null;
        Date endTimeDate = null;
        long between = 0;
        long allMin = 0;
        try {
            startimeDate = formatter.parse(startime);
            endTimeDate = formatter.parse(endTime);
            between = (endTimeDate.getTime() - startimeDate.getTime()) / 1000;//除以1000是为了转换成秒
            allMin = between / 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int hour = (int) (allMin / 60);
        int min = (int) (allMin % 60);

        return hour + ResourceUtils.getString(R.string.plane_search_pause_hour) + min + ResourceUtils.getString(R.string.plane_search_pause_minute);
    }

    /**
     * 根据后台返回时间计算分钟
     *
     * @param startime
     * @param endTime
     * @return
     */
    public static long getDifferentMinute(String startime, String endTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Date startimeDate = null;
        Date endTimeDate = null;
        long between = 0;
        long allMin = 0;
        try {
            startimeDate = formatter.parse(startime);
            endTimeDate = formatter.parse(endTime);
            between = (endTimeDate.getTime() - startimeDate.getTime()) / 1000;//除以1000是为了转换成秒
            allMin = between / 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return allMin;
    }


    /**
     * 判断两个时间戳相差多少x分钟
     *
     * @param startime
     * @param endTime
     * @return
     */
    public static long getDifferentMinute(String startime, String endTime) {
        long between = 0;
        long allMin = 0;
        between = ((Long.parseLong(endTime + "000") - Long.parseLong(startime + "000")) / 1000);//除以1000是为了转换成秒
        allMin = between / 60;

        return allMin;
    }

    /**
     * 判断两个时间戳相差多少x小时x分钟
     */
    public static String getDifferentHours(String endtime, String startTime) {
        long between = 0;
        long allMin = 0;

        between = ((Long.parseLong(endtime + "000") - Long.parseLong(startTime + "000")) / 1000);//除以1000是为了转换成秒
        allMin = between / 60;

        int hour = (int) (allMin / 60);
        int min = (int) (allMin % 60);
        return hour + ResourceUtils.getString(R.string.plane_search_pause_hour) + min + ResourceUtils.getString(R.string.plane_search_pause_minute);

    }

    /**
     * 判断两个日期相差天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }

    /**
     * @return 判断两个时间相差多少天
     */
    public static int getDifferentDays(String startime, String endTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        Date startimeDate = null;
        Date endTimeDate = null;
        try {
            startimeDate = formatter.parse(startime);
            endTimeDate = formatter.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int differentDays = getDifferentDays(startimeDate, endTimeDate);
        return differentDays;
    }

    /**
     * 区分正负值,获取 nYears ， nDays前今天
     *
     * @param nYears
     * @param nDays
     * @return
     */
    public static Date getYearsNDaysAgo(int nYears, int nDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, nYears);
        Date date1 = cal.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE, nDays);
        Date date2 = calendar.getTime();
        return date2;
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        //Calendar：日历
        /*从Calendar对象中或得一个Date对象*/
        Calendar cal = Calendar.getInstance();
        /*把出生日期放入Calendar类型的bir对象中，进行Calendar和Date类型进行转换*/
        Calendar bir = Calendar.getInstance();
        bir.setTime(birthday);
        /*如果生日大于当前日期，则抛出异常：出生日期不能大于当前日期*/
        if (cal.before(birthday)) {
            return 0;
        }
        /*取出当前年月日*/
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);
        /*取出出生年月日*/
        int yearBirth = bir.get(Calendar.YEAR);
        int monthBirth = bir.get(Calendar.MONTH);
        int dayBirth = bir.get(Calendar.DAY_OF_MONTH);
        /*大概年龄是当前年减去出生年*/
        int age = yearNow - yearBirth;
        /*如果出当前月小与出生月，或者当前月等于出生月但是当前日小于出生日，那么年龄age就减一岁*/
        if (monthNow < monthBirth || (monthNow == monthBirth && dayNow < dayBirth)) {
            age--;
        }
        return age;
    }

    /**
     * 获取Date m个月后的时间
     */
    public static Date getMonth(Date date, int lastMonth) {
        Calendar c = Calendar.getInstance();
        //过去三个月
        c.setTime(date);
        c.add(Calendar.MONTH, lastMonth);
        Date newDate = c.getTime();
        return newDate;
    }


    /**
     * * @return 获取当前时区
     */
    public static String getCurrentTimeZone() {
        String id = TimeZone.getDefault().getID();
        return id;
    }

    /**
     * @return 获取当前时区+时间
     */
    public static String getCurrentTime() {
        String displayName = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT) + getCurrentDate5();
        return displayName;
    }

    /**
     * 根据时间戳格式转时间
     *
     * @param time 时间戳
     * @format 时间格式
     */
    public static String timeStamp2date(String time, String format) {
        SimpleDateFormat result_form = new SimpleDateFormat(format);
        String dateTime = "";
        if (time != null) {
            //php返回少三位数
            dateTime = result_form.format(Long.parseLong(time + "000"));
        }
        return dateTime;
    }

    /**
     * 根据时间格式转Date
     *
     * @param time
     * @param format
     * @return
     */
    public static Date date2Date(String time, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());

        Date parse = null;
        try {
            parse = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public static String timeStrToDateStr(String inputFormat, String outputFormat, String timeStr) {
        if (null == timeStr) {
            return null;
        }

        String dateStr = null;
        SimpleDateFormat sdf_input = new SimpleDateFormat(inputFormat);//输入格式
        SimpleDateFormat sdf_target = new SimpleDateFormat(outputFormat); //转化成为的目标格式

        try {
            dateStr = sdf_target.format(sdf_input.parse(timeStr));

        } catch (Exception e) {
        }

        return dateStr;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getStringToDate(String dateString, String pattern) {
        if (TextUtils.isEmpty(dateString))
            return 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (date.getTime()) / 1000;
    }

    public static Calendar getCalendarForLocale(Calendar oldCalendar, Locale locale) {
        if (oldCalendar == null) {
            return Calendar.getInstance(locale);
        } else {
            final long currentTimeMillis = oldCalendar.getTimeInMillis();
            Calendar newCalendar = Calendar.getInstance(locale);
            newCalendar.setTimeInMillis(currentTimeMillis);
            return newCalendar;
        }
    }

    //时间显示运算
    @NonNull
    public static String getRemainTime(double expire_time) {
        int hour = (int) (expire_time / 3600);
        int min = (int) (expire_time / 60 % 60);
        int second = (int) (expire_time % 60);

        String sHour = "";
        if (hour != 0) {
            sHour = String.format("%02d", hour) + ResourceUtils.getString(com.happy.todo.lib_common.R.string.plane_search_pause_hour) + ":";
        } else {
            sHour = "00:";
        }
        String pendingEnter = ResourceUtils.getString(com.happy.todo.lib_common.R.string.home_pending_list_enter, sHour + String.format("%02d", min) + ":" + String.format("%02d", second));
        return pendingEnter;
    }

}