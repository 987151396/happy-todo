package com.happy.todo.lib_common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.happy.todo.lib_common.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Describe：
 * @Date： 2018/11/29
 * @Author： dengkewu
 * @Contact：
 */
public class CountryUtil {
    @SuppressLint("CheckResult")
    public void startSearchCountryShort(String countryName) {
        Observable.create((ObservableOnSubscribe<JSONArray>) e -> {

            InputStream stream = ResourceUtils.getResources().openRawResource(R.raw.country);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String jsonStr = "", line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    jsonStr += line;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray data = jsonObject.getJSONArray("data");

            e.onNext(data);
            e.onComplete();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String short_name = jsonObject1.getString("short_name");
                        String en_name = jsonObject1.getString("en_name");
                        String name = jsonObject1.getString("name");
                        String tw_name = jsonObject1.getString("tw_name");
                        if (!TextUtils.isEmpty(countryName) && (countryName.toLowerCase().equalsIgnoreCase(en_name.toLowerCase())
                        || countryName.equals(name) || countryName.equals(tw_name))) {
                            if (myOnFinishListener != null) {
                                myOnFinishListener.onSearch(short_name);
                                break;
                            }
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void startSearchCountry(String countryCode) {
        Observable.create((ObservableOnSubscribe<JSONArray>) e -> {

            InputStream stream = ResourceUtils.getResources().openRawResource(R.raw.country);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String jsonStr = "", line = "";

            try {
                while ((line = reader.readLine()) != null) {
                    jsonStr += line;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray data = jsonObject.getJSONArray("data");
            e.onNext(data);
            e.onComplete();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);
                        String short_name = jsonObject1.getString("short_name");
                        String en_name = jsonObject1.getString("en_name");
                        String name = jsonObject1.getString("name");
                        String tw_name = jsonObject1.getString("tw_name");
                        if (!TextUtils.isEmpty(countryCode) && countryCode.toLowerCase().equalsIgnoreCase(short_name.toLowerCase())) {
                            if (myOnFinishListener != null) {
                                //myOnFinishListener.onSearchByShortName(hotelCountryBean);
                                break;
                            }
                        }
                    }
                });
    }

    public interface onFinishListener {
        void onSearch(String country);
        //default void onSearchByShortName(HotelCountryBean countryBean) {}
    }

    private onFinishListener myOnFinishListener;

    public void setOnFinishListener(CountryUtil.onFinishListener onFinishListener) {
        this.myOnFinishListener = onFinishListener;
    }
}
