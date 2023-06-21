package com.roydon.community.baidu;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MyLocationListener extends BDAbstractLocationListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLocate = true;

    //构造方法用于传递地图控件
    public MyLocationListener(MapView mMapView,BaiduMap mBaiduMap) {
        this.mMapView = mMapView;
        this.mBaiduMap = mBaiduMap;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        if (isFirstLocate) {

            isFirstLocate = false;
            //给地图设置状态
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(ll));
        }

        //mapView 销毁后不在处理新接收的位置
        if (location == null || mMapView == null){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        mBaiduMap.setMyLocationData(locData);
    }
}
