package com.roydon.community.listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        // 处理位置更新事件
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        // 在这里可以更新UI或执行其他操作
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // 当位置提供者状态改变时调用
    }

    @Override
    public void onProviderEnabled(String provider) {
        // 当位置提供者启用时调用
    }

    @Override
    public void onProviderDisabled(String provider) {
        // 当位置提供者禁用时调用
    }
}
