package com.happy.todo.lib_common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 与字符串操作相关的工具类
 * Created by chenmingzhen on 16-6-7.
 */
public class StringUtils {

    public static final String EMPTY = "";

    private static final String TAG = StringUtils.class.getSimpleName();

    private static final String EMAIL_PATTERN = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    private static final String USERNAME_PATTERN = "^[A-Za-z0-9_]+$";

    private static final String PHONE_PATTERN = "^\\+?\\d{4,20}$";


    /**
     * 查看一个字符是否为空
     *
     * @param str 需要检查的字条
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否引用为空
     *
     * @param str
     * @return
     */
    public static String checkNotNull(String str) {
        if (str == null) {
            return EMPTY;
        }
        return str;
    }

    public static int getWordCount(String str) {
        str = str.replaceAll("[^\\x00-\\xff]", "**"); //汉字或全角的情况
        int length = str.length();
        return length;
    }

    /**
     * 生成中间省略的字符串
     * 这个方法是为了使得字符串过长的时候进行中间截取成
     * XXX..XXX.DOC 为什么不用系统的ellipse=middle，使用这个属性在某种命名的文件下会导致系统崩溃，这是Android系统的bug.
     *
     * @param content           字符串长度
     * @param max               允许字符串最大值
     * @param allowChineseCount 允许中文最大值
     * @param start             省略开始于
     * @param end               省略结束于
     * @return
     */
    public static String middleEllipse(String content, int max, int allowChineseCount, int start, int end) {
        Integer index = 0;
        int chineseCount = 0;
        for (int i = 0; i < content.length(); i++) {
            String retContent = content.substring(i, i + 1);
            // 生成一个Pattern,同时编译一个正则表达式
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
            if (isChina) {
                index = index + 2;
                chineseCount++;
            } else {
                index = index + 1;
            }
        }

        if (index < max) {
            return content;
        }
        StringBuffer sb = new StringBuffer();
        if (chineseCount > allowChineseCount) {
            sb.append(content.substring(0, start + 2));
        } else {
            sb.append(content.substring(0, start + 5));
        }
        sb.append("...");
        sb.append(content.substring(content.length() - end, content.length()));
        return sb.toString();
    }

    /**
     * 压缩字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        long time1 = System.currentTimeMillis();
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String rs = out.toString("ISO-8859-1");
        long time2 = System.currentTimeMillis();
        System.out.println("compress String coast time:" + (time2 - time1));
        return rs;
    }

    /**
     * 解压缩字符串
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String uncompress(String str) throws IOException {
        long time1 = System.currentTimeMillis();
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        GZIPInputStream ungzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = ungzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        String rs = out.toString();
        long time2 = System.currentTimeMillis();
        System.out.println("uncompress String coast time:" + (time2 - time1));
        return rs;
    }


    public static String uncompress(byte[] bytes) throws IOException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        // System.out.println("bytes..length:"+bytes.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        String rs = out.toString("GBK");
        return rs;
    }

    /**
     * 防止字符串编码错误
     *
     * @param value
     * @return
     */
    public static String encode(String value) {
        String result = "";
        try {
            result = java.net.URLEncoder.encode(value, "utf-8");
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
        }
        return result;
    }


    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * 将字符串转化为int类型
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static int toInt(String str, int defaultValue) {
        if (isEmpty(str))
            return defaultValue;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            AppLogUtil.d(TAG, e.getMessage());
        }
        return defaultValue;
    }

    /**
     * 将字符串转化为long类型
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static long toLong(String str, long defaultValue) {
        if (isEmpty(str))
            return defaultValue;
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
        }
        return defaultValue;
    }

    /**
     * String转化为float
     *
     * @param value
     * @return
     */
    public static float toFloat(String value) {
        if (isEmpty(value))
            return 0;
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            AppLogUtil.d(TAG, e.getMessage());
        }
        return 0;
    }

    /**
     * 字符在去除空格
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }


    /**
     * 验证是否是邮箱
     */
    public static boolean isEmail(String email) {
        try {
            Pattern p = Pattern.compile(EMAIL_PATTERN);
            Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 验证是否手机号码
     */
    public static boolean isPhone(String phone) {
        try {
            Pattern p = Pattern.compile(PHONE_PATTERN);
            Matcher m = p.matcher(phone);
            return m.matches();
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 判断是否为固定电话，需要加入区号
     */
    public static boolean isCall(String call) {
        try {
            String regExp = "^\\d{3,4}-\\d{7,8}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(call);
            return m.matches();
        } catch (Exception e) {
            AppLogUtil.d(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 判断是否为正确的邮政编码
     */
    public static boolean isPostCode(String postCode) {
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(postCode).matches();
    }

    /**
     * 有效用户名
     *
     * @param username
     * @return
     */
    public static boolean validateUsername(String username) {
        return Pattern.matches(USERNAME_PATTERN, username);
    }


    /**
     * 有效的密码
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        return password.length() > 5;
    }

    /**
     * 判断信息是否为空类型,默认返回不空  true为空,false为不空。如果是纯空字符组成的字符串也是返回true
     *
     * @author liaofucheng
     * @date 2016年6月12日
     */
    public static boolean isNull(Object value) {
        try {
            return (value == null || String.valueOf(value).trim().length() == 0) ? true : false;
        } catch (Exception e) {
            AppLogUtil.e(TAG, e);
        }
        return false;
    }


    /*
     * 将Object类型转换成字符串，如果为null，返回的是""
     * @return String
     * @param
     * @author liaofucheng
     * @date 2016/7/14 9:34
     */
    public static String object2String(Object object) {
        try {
            if (object != null) {
                return object.toString();
            }
        } catch (Exception e) {
            AppLogUtil.e(TAG, e);
        }
        return "";
    }

    /**
     * 数字转中文数字
     *
     * @param d
     */
    public static String numberToChinese(int d) {
        String ss[] = new String[]{"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};
        String s = String.valueOf(d);
        StringBuffer sb = new StringBuffer();
        try {
            String sss = String.valueOf(sb);
            int i = 0;
            for (int j = sss.length(); j > 0; j--) {
                sb = sb.insert(j, ss[i++]);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 分钟转天或者转小时
     *
     * @param minutes
     * @param returnType 1=返回天带2位小数，2=返回小时+分钟，returnType不传则智能返回(未测试)
     * @returns {String}
     * @author konglingxian
     */
    public static String minuteToStr(double minutes, int returnType) {
        try {
            String str = "";
            if (minutes == 0)
                return "0分钟";
            double millisecond = minutes * 60 * 1000;
            double day = millisecond / 86400000;
            double hour = (millisecond % 86400000) / 3600000;
            double minute = (millisecond % 86400000 % 3600000) / 60000;
            DecimalFormat dcmFmt = new DecimalFormat("0.0");
            if (day > 1) {
                str = (int) day + "天";
                if (hour > 0) {
                    str += dcmFmt.format(hour) + "小时";
                }
            } else {
                if (hour > 1) {
                    str += (int) hour + "小时";
                }
            }
            if (minute > 0) {
                str += (int) minute + "分钟";
            }

            //返回*天*小时
            if (returnType == 1) {
                str = "";
                if (day > 1) {
                    str = (int) day + "天";
                    if (hour > 0) {
                        str += dcmFmt.format(hour) + "小时";
                    }
                }
            }

            //返回*小时*分钟
            if (returnType == 2) {
                str = "";
                if (day > 0 && hour > 0) {
                    str += (int) (hour + (int) day * 24) + "小时";
                }
                if (minute > 0) {
                    str += (int) minute + "分钟";
                }
            }

            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转全角和半角
     *
     * @param src
     * @return
     */
    public static String toSemiangle(String src) {
        char[] c = src.toCharArray();
        for (int index = 0; index < c.length; index++) {
            if (c[index] == 12288) {// 全角空格
                c[index] = (char) 32;
            } else if (c[index] > 65280 && c[index] < 65375) {// 其他全角字符
                c[index] = (char) (c[index] - 65248);
            }
        }
        return new String(c);
    }

    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }

    public static boolean isNnull(String value) {
        return (value == null || value.trim().length() == 0) ? true : false;
    }

    /**
     * 有效的邮箱
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        String EMAIL_PATTERN = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches();
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean matches = pattern.matcher(str).matches();
        return matches;
    }


    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }

    // 去除字符串中的空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }


    /**
     * 替换手机号码的中间字符
     *
     * @param replacement 替换者
     * @return
     */
    public static String replaceMidChar(String src, String replacement) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }

        int length = src.length();
        String exp;
        if (length <= 1) {
            return replacement;
        } else if (length == 2) {
            exp = "(?<=\\d)\\d(?=\\d{0})";
        } else if (length < 6) {
            exp = "(?<=\\d)\\d(?=\\d)";
        } else {
            exp = "(?<=\\d{2})\\d(?=\\d{2})";
        }

        return src.replaceAll(exp, replacement);
    }

    /**
     * 替换银行卡号的中间字符
     *
     * @param replacement 替换者
     * @return
     */
    public static String replaceCardMidChar(String src, String replacement) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }

        int length = src.length();
        String exp  = "(?<=\\d{6})\\d(?=\\d{4})";
        /*if (length <= 1) {
            return replacement;
        } else if (length == 2) {
            exp = "(?<=\\d)\\d(?=\\d{0})";
        } else if (length < 6) {
            exp = "(?<=\\d)\\d(?=\\d)";
        } else {
            exp = "(?<=\\d{2})\\d(?=\\d{2})";
        }*/

        return src.replaceAll(exp, replacement);
    }


    /**
     * 隐藏邮箱的中间部分
     *
     * @return
     */
    public static String replaceMidEmail(String src, String replacement) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }
        if (!src.contains("@")) {
            return replaceMidChar(src, replacement);
        }

        //分割后会丢失@
        String[] parts = src.split("@");
        String part1 = parts[0];
        String part2 = "";


        if (parts.length > 1) {
            part2 = parts[parts.length - 1];  //截取到最后一个@
        }

        String part1Result = replaceMidChar(part1, replacement);
        return part1Result + "@" + part2;
    }

    /**
     * 提取字符串中的数字。避免后台返回格式错误
     *
     * @param str
     * @return
     */
    public static String getNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        String regEx = "[^0-9.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String trim = m.replaceAll("").trim();
        if (TextUtils.isEmpty(trim)) {
            return "0";
        }
        return trim;
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param count  保留位数
     * @return
     */
    public static String setRounding(Object number, int count) {
        NumberFormat integerInstance = NumberFormat.getIntegerInstance();
        integerInstance.setMaximumFractionDigits(count);
        integerInstance.setMinimumFractionDigits(count);
        integerInstance.setRoundingMode(RoundingMode.UP);
        String format = integerInstance.format(number);
        return format;
    }

    /**
     * 去除结尾字符
     *
     * @param original 源字符串
     * @param chr      去除字符
     * @return
     */
    public static String removeEndStr(String original, String chr) {
        String address = "";
        if (!TextUtils.isEmpty(original)) {
            String substring = original.trim().substring(original.trim().length() - 1, original.trim().length());
            if (substring.equals(",")) {
                String trim = original.trim();
                int indexlast = trim.lastIndexOf(chr);
                address = trim.substring(0, indexlast) + trim.substring(indexlast + 1, trim.length());
            } else {
                address = original.trim();
            }
        }

        return address;
    }

    public static boolean isJSONString(String res) {
        Gson gson = new Gson();
        try {
            gson.fromJson(res, Object.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }
}
