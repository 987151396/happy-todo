/*
package com.ifreegroup.app.ebbly.module_main.ui.manage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.ifreegroup.app.ebbly.lib_common.db.DBHelper;
import com.ifreegroup.app.ebbly.lib_common.db.table.UserLocation;
import com.ifreegroup.app.ebbly.lib_common.manage.LoginManage;
import com.ifreegroup.app.ebbly.lib_common.utils.AppLogUtil;
import com.ifreegroup.app.ebbly.lib_common.utils.ListUtils;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenContext;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapBoxLocationManage implements PermissionsListener{
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    private static MapBoxLocationManage mapBoxLocationManage;
    private List<WeakReference<OnLocationListener>> mReferenceList;
    private Builder myBuilder;
    private PermissionsManager permissionsManager;

    public static class Builder{
        private MapboxMap mapboxMap;
        private Context myContext;
        private Activity activity;
        private OnLocationCallback callback;

        MapboxMap getMapBoxMap() {
            return mapboxMap;
        }

        public Builder setMapboxMap(MapboxMap mp) {
            this.mapboxMap = mp;
            return this;
        }

        public Builder setActivity(Activity a) {
            this.activity = a;
            this.myContext = a;
            return this;
        }

        public Builder build(){
            callback = new OnLocationCallback(activity);
            mapBoxLocationManage.setBuilder(this);
            return this;
        }
    }

    private void setBuilder(Builder builder){
        AppLogUtil.OY_D("setBuilder");
        this.myBuilder = builder;
    }

    private MapBoxLocationManage(){
        mReferenceList = Collections.synchronizedList(new
                ArrayList<WeakReference<OnLocationListener>>());
    }

    public static MapBoxLocationManage getInstance(){
        if(mapBoxLocationManage == null){
            synchronized (MapBoxLocationManage.class){
                if(mapBoxLocationManage == null){
                    mapBoxLocationManage = new MapBoxLocationManage();
                }
            }
        }
        return mapBoxLocationManage;
    }

    public PermissionsManager getPermissionsManager() {
        if(permissionsManager == null){
            permissionsManager = new PermissionsManager(this);
        }
        return permissionsManager;
    }

    @SuppressWarnings({"MissingPermission"})
    public boolean enableLocationComponent(MapboxMap mapboxMap, Style loadedMapStyle,Context context){
        AppLogUtil.d("ouyang","enableLocationComponent");
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(context, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();
            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);
            initLocationEngine();
            return true;
        }
        return false;
    }


    public List<WeakReference<OnLocationListener>> getmReferenceList() {
        return mReferenceList;
    }

    //    开始一个定位
    public void startLocation(OnLocationListener onLocationListener) {
        if (mReferenceList != null) {
            mReferenceList.add(new WeakReference<>(onLocationListener));
        }
        enableLocationComponent(myBuilder.getMapBoxMap(),myBuilder.getMapBoxMap().getStyle(),myBuilder.myContext);
    }

    */
/**
     * Set up the LocationEngine and the parameters for querying the device's location
     *//*

    @SuppressLint("MissingPermission")
    public void initLocationEngine() {
        AppLogUtil.d("ouyang","initLocationEngine");
        LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(myBuilder.myContext);
        locationEngine.getLastLocation(myBuilder.callback);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        AppLogUtil.d("ouyang","onExplanationNeeded");
    }

    @Override
    public void onPermissionResult(boolean granted) {
        AppLogUtil.d("ouyang","onPermissionResult : " + granted);
        if (granted) {
            if (myBuilder.mapboxMap.getStyle() != null) {
                if(!mapBoxLocationManage.enableLocationComponent(myBuilder.mapboxMap,myBuilder.mapboxMap.getStyle(),myBuilder.myContext)){
                    getPermissionsManager().requestLocationPermissions(myBuilder.activity);
                }
            }
        }

    }

    public interface OnLocationListener {
        void onSuccess(UserLocation userLocation);

        void onError(String msg);
    }


    public static class OnLocationCallback implements LocationEngineCallback<LocationEngineResult> {
        private final WeakReference<Activity> activityWeakReference;
        OnLocationCallback(Activity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }
        */
/**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         * @param result the LocationEngineResult object which has the last known location within it.
         *//*

        @SuppressLint("CheckResult")
        @Override
        public void onSuccess(LocationEngineResult result) {
            Activity activity = activityWeakReference.get();
            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }
                AppLogUtil.OY_D("location : " + location.toString());
                AppLogUtil.OY_D( location.getLongitude()+" - " + location.getLatitude());
                AppLogUtil.OY_D(  location.getProvider());
                MapboxGeocoding reverseGeocode = MapboxGeocoding.builder()
                        .accessToken(Mapbox.getAccessToken())
                        .query(Point.fromLngLat(location.getLongitude(), location.getLatitude()))
                        //.country(Locale.getDefault())
                        .languages(Locale.getDefault().getLanguage())
                        .languages("en")
                        .geocodingTypes(GeocodingCriteria.TYPE_PLACE)
                        .build();

                reverseGeocode.enqueueCall(new Callback<GeocodingResponse>() {
                    @Override
                    public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                        List<CarmenFeature> results = response.body().features();
                        AppLogUtil.d("ouyang", "response: " + response.body().toJson());

                        if (results.size() > 0) {
                            //Point firstResultPoint = results.get(0).center();
                            CarmenFeature carmenFeature = results.get(0);
                            ArrayList<CarmenContext> carmenContexts = (ArrayList<CarmenContext>) results.get(0).context();
                            UserLocation userLocation = new UserLocation();
                            userLocation.setLat(location.getLatitude() + "");
                            userLocation.setLon(location.getLongitude() + "");
                            userLocation.setProvince(location.getProvider());
                            userLocation.setCity(carmenFeature.text());
                            //userLocation.setCountry();
                            userLocation.setAddress(carmenFeature.placeName());
                            userLocation.setDate(new Date(location.getTime()));
                            if (!ListUtils.isEmpty(carmenContexts) && carmenContexts.size() > 1) {
                                AppLogUtil.d("ouyang", "cityCode: " + carmenContexts.get(0).shortCode());
                                userLocation.setCountry(carmenContexts.get(1).shortCode());
                            }
                            if (LoginManage.isLogin()) {
                                userLocation.setUser_id(LoginManage.getUserId());
                            }
                            // 将定位信息保存到数据库
                            Flowable.create((FlowableOnSubscribe<UserLocation>) emitter -> {
                                        DBHelper.getInstance().getEbblyDatabase().getUserLocationDao()
                                                .insert(userLocation);
                                        emitter.onNext(userLocation);
                                        emitter.onComplete();
                                    },
                                    BackpressureStrategy.BUFFER)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()).subscribe(userLocation1 -> {
                                // 进行回调
                                AppLogUtil.d("ouyang","进行回调");
                                if (mapBoxLocationManage.getmReferenceList() != null) {
                                    Iterator<WeakReference<OnLocationListener>> sListIterator =
                                            mapBoxLocationManage.getmReferenceList().iterator();
                                    while (sListIterator.hasNext()) {
                                        WeakReference<MapBoxLocationManage.OnLocationListener> onLocationListenerWeakReference
                                                = sListIterator.next();
                                        if (onLocationListenerWeakReference.get() == null) {
                                            sListIterator.remove();
                                        } else {
                                            AppLogUtil.d("ouyang","onSuccess");
                                            onLocationListenerWeakReference.get().onSuccess(userLocation1);
                                            sListIterator.remove();
                                        }
                                    }
                                }

                            });
                        } else {
                            // No result for your request were found.
                            noResultFoundOnError("onResponse: No result found");
                        }
                    }

                    @Override
                    public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                        noResultFoundOnError(throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
            }
        }

        */
/**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         *//*

        @Override
        public void onFailure(@NonNull Exception exception) {
            noResultFoundOnError(exception.getMessage());
        }

        private void noResultFoundOnError(String errMsg) {
            //showToast(errMsg);
            AppLogUtil.OY_D(" noResultFoundOnError : " + errMsg);
            Activity activity = activityWeakReference.get();
            if (activity != null) {
                if (mapBoxLocationManage.getmReferenceList() != null) {
                    Iterator<WeakReference<MapBoxLocationManage.OnLocationListener>> sListIterator =
                            mapBoxLocationManage.getmReferenceList().iterator();
                    while (sListIterator.hasNext()) {
                        WeakReference<MapBoxLocationManage.OnLocationListener> onLocationListenerWeakReference
                                = sListIterator.next();
                        if (onLocationListenerWeakReference.get() == null) {
                            sListIterator.remove();
                        } else {
                            onLocationListenerWeakReference.get().onError(errMsg);
                            sListIterator.remove();
                        }
                    }
                }
            }
        }

    }

}
*/
