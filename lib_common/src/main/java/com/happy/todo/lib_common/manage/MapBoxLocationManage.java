package com.happy.todo.lib_common.manage;

import com.happy.todo.lib_common.db.table.UserLocation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapBoxLocationManage {
    private static MapBoxLocationManage mapBoxLocationManage;
    private OnStartLocationListener onstartLocationListener;
    private List<WeakReference<OnLocationListener>> mReferenceList;


    private MapBoxLocationManage(){
        mReferenceList = Collections.synchronizedList(new
                ArrayList<WeakReference<OnLocationListener>>());
    }

    public static MapBoxLocationManage getInstance(){
        if(mapBoxLocationManage == null){
            mapBoxLocationManage = new MapBoxLocationManage();
        }
        return mapBoxLocationManage;
    }



    public List<WeakReference<OnLocationListener>> getmReferenceList() {
        return mReferenceList;
    }

    public void setOnstartLocation(OnStartLocationListener listener) {
        this.onstartLocationListener = listener;
    }

    public OnStartLocationListener getOntartLocationListener() {
        return onstartLocationListener;
    }

    //    开始一个定位
    public void startLocation(OnLocationListener onLocationListener) {

        if (mReferenceList != null) {
            mReferenceList.add(new WeakReference<>(onLocationListener));
        }
        if(onstartLocationListener != null){
            onstartLocationListener.startLocation();
        }
    }

    public interface OnLocationListener {
        void onSuccess(UserLocation userLocation);

        void onError(String msg);
    }

    public interface OnStartLocationListener {
        void startLocation();
    }



}
