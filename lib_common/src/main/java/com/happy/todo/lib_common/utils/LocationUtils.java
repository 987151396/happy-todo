package com.happy.todo.lib_common.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class LocationUtils {

    private static final String TAG = "LocationUtils";

    /**
     * http://ip-api.com/json/58.192.32.1?fields=520191&lang=en
     * 根据ip获取位置信息
     *
     * @param ip
     * @return {"accuracy":50,"as":"AS4538 China Education and Research Network Center",
     * "city":"Nanjing","country":"China","countryCode":"CN","isp":
     * "China Education and Research Network Center","lat":32.0617,"lon":118.7778,"mobile":false,
     * "org":"China Education and Research Network Center","proxy":false,"query":"58.192.32.1",
     * "region":"JS","regionName":"Jiangsu","status":"success","timezone":"Asia/Shanghai","zip":""}
     */
    public static JSONObject Ip2Location(String ip) {
        JSONObject jsonObject = null;

        String urlStr = "http://ip-api.com/json/" + ip + "?fields=520191&lang=en";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);//读取超时
            urlConnection.setConnectTimeout(5000); // 连接超时
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                InputStream is = urlConnection.getInputStream();

                BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = buff.readLine()) != null) {
                    builder.append(line);
                }
                buff.close();//内部会关闭InputStream
                urlConnection.disconnect();

                String res = builder.toString();

                AppLogUtil.d(TAG, "Ip2Location: res -- "+res);
                if (StringUtils.isJSONString(res)){
                    jsonObject = new JSONObject(res);
                }
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * 根据ip通过百度api去获取城市
     * @param ip
     * @return
     */
    public static String Ip2LocationByBaiduApi(String ip){
        try {
            URL url = new URL("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line = null;
            StringBuffer res = new StringBuffer();
            while ((line = reader.readLine())!=null){
                res.append(line);
            }
            reader.close();
            String ipAddr = res.toString();
            if (StringUtils.isJSONString(ipAddr)){
                JSONObject jsonObject = new JSONObject(ipAddr);
                if ("1".equals(jsonObject.get("ret").toString())){
                    return jsonObject.get("city").toString();
                }else {
                    return "读取失败";
                }

            }else {
                return "访问后得到的不是json数据, res -- "+ipAddr;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "读取失败 e -- "+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "读取失败 e -- "+e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            return "读取失败 e -- "+e.getMessage();
        }
    }

    /**
     * 获取外网IP地址
     * @return
     */
    public static String GetNetIp(){
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36"); //设置浏览器ua 保证不出现503

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");

                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    /*IP = data.getString("ip") + "(" + data.getString("country")
                            + data.getString("area") + "区"
                            + data.getString("region") + data.getString("city")
                            + data.getString("isp") + ")";*/
                    IP = data.getString("country_id");

                    AppLogUtil.d(TAG, "您的IP地址是：" + IP);
                    AppLogUtil.d(TAG, "data：" + data.toString());
                } else {
                    IP = "";
                    AppLogUtil.d(TAG, "IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                AppLogUtil.d(TAG, "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            AppLogUtil.d(TAG, "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        return IP;
    }
}
