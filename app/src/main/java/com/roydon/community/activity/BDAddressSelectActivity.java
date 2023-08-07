package com.roydon.community.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.roydon.community.R;

public class BDAddressSelectActivity extends AppCompatActivity {

    LocationClient mLocationClient;  // 定位客户端
    MapView mapView;  // Android Widget地图控件
    BaiduMap baiduMap;
    boolean isFirstLocate = true;

    TextView tv_Lat;  // 纬度
    TextView tv_Lon;  // 经度
    TextView tvReginCode;  // 编码
    TextView tvRealAddress;  // 地址
//    EditText etSearchAddress;

    private ImageView ivRelocated;

    Button btnConfirmAddress;

    private String regionCode;

    BDLocation getlocation;

    // 当前定位模式
    private MyLocationConfiguration.LocationMode locationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果没有定位权限，动态请求用户允许使用该权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            requestLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "没有定位权限！", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    requestLocation();
                }
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {  // 初始化

        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LocationClient.setAgreePrivacy(true);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new BDAddressSelectActivity.MyLocationListener());
        setContentView(R.layout.activity_bdaddress_select);

        mapView = findViewById(R.id.bd_map_view);
        //关闭缩放按钮
//        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        tv_Lat = findViewById(R.id.tv_lat);
        tv_Lon = findViewById(R.id.tv_lon);
        tvReginCode = findViewById(R.id.tv_regin_code);
        tvRealAddress = findViewById(R.id.tv_real_address);
        ivRelocated = findViewById(R.id.iv_relocated);
        btnConfirmAddress = findViewById(R.id.btn_confirm_address);

        LocationClientOption option = new LocationClientOption();
        // 设置扫描时间间隔
        option.setScanSpan(1000);
        option.setOpenGps(true); // 打开gps
        // 设置定位模式，三选一（Battery_Saving、Device_Sensors）
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置需要地址信息
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");//设置坐标系，默认“gcj02”
        // 保存定位参数
        mLocationClient.setLocOption(option);

        // 重新定位按钮
        ivRelocated.setOnClickListener(v -> {
            clickReLocation();
        });

        // 确认地址返回给上个activity
        btnConfirmAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserAddressAddActivity.class);
            intent.putExtra("regionCode", regionCode);
            intent.putExtra("realAddress", tvRealAddress.getText().toString());
            setResult(200, intent);
            finish();
        });
    }

    // 内部类，百度位置监听器
    private class MyLocationListener implements BDLocationListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            国家：location.getCountry()-getCountryCode()
//            省：  location.getProvince()-getCityCode()
//            市：  location.getCity());
//            区：  location.getDistrict()
//            街道：location.getStreet()-getStreetNumber()
//            location.getTime();               //获取定位时间
//            location.getLocationID();         //获取定位唯一ID，v7.2版本新增，用于排查定位问题
//            location.getLocType();            //获取定位类型
//            location.getRadius();             //获取定位精准度
//            location.getAddrStr();            //获取地址信息
//            location.getLocationDescribe();   //获取当前位置描述信息
//            location.getPoiList();            //获取当前位置周边POI信息
            tv_Lat.setText(bdLocation.getLatitude() + "");
            tv_Lon.setText(bdLocation.getLongitude() + "");
            tvReginCode.setText(bdLocation.getAdCode() + "");
            tvRealAddress.setText(bdLocation.getAddrStr());
            regionCode = bdLocation.getAdCode();

            getlocation = bdLocation;

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
                Log.e("Tag", "离线定位成功，离线定位结果也是有效的");
                navigateTo(bdLocation);
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                Log.e("Tag", "服务端网络定位失败,错误代码：" + bdLocation.getLocType());
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                Log.e("Tag", "网络不通导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                Log.e("Tag", "无法获取有效定位依据导致定位失败");
            } else {
                Log.e("Tag", "未知原因，请向百度地图SDK论坛求助，location.getLocType()错误代码：" + bdLocation.getLocType());
            }
        }
    }

    private MyLocationData myLocationData;

    /**
     * 左下角“定位”点击事件(点击后重新定位)
     */
    public void clickReLocation() {
        LatLng latLng = new LatLng(getlocation.getLatitude(), getlocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(20.0f); // 设置地图缩放
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        myLocationData = new MyLocationData.Builder()
                .accuracy(getlocation.getRadius())// 设置定位数据的精度信息，单位：米
                .direction(0)// 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(getlocation.getLatitude())
                .longitude(getlocation.getLongitude())
                .build();
        baiduMap.setMyLocationData(myLocationData);
        // ------------------  以下是可选部分 ------------------
        // 自定义地图样式，可选
        // 更换定位图标，这里的图片是放在 drawble 文件下的
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_baidu_geo);
//        // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
//        locationMode = MyLocationConfiguration.LocationMode.NORMAL;
//        // 定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色以及精度圈边框颜色5个属性（此处只设置了前三个）。
//        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
//        // 使自定义的配置生效
//        baiduMap.setMyLocationConfiguration(mLocationConfiguration);
        // ------------------  可选部分结束 ------------------
    }

    private void navigateTo(BDLocation bdLocation) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())// 设置定位数据的精度信息，单位：米
                    .direction(0)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(myLocationData);
            // ------------------  以下是可选部分 ------------------
            // 自定义地图样式，可选
            // 更换定位图标，这里的图片是放在 drawble 文件下的
//            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_baidu_geo);
//            // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
//            locationMode = MyLocationConfiguration.LocationMode.NORMAL;
//            // 定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色以及精度圈边框颜色5个属性（此处只设置了前三个）。
//            MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
//            // 使自定义的配置生效
//            baiduMap.setMyLocationConfiguration(mLocationConfiguration);
            // ------------------  可选部分结束 ------------------
            isFirstLocate = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
    }

//    private MapView mMapView = null;
//    private BaiduMap mBaiduMap = null;
//    private LocationClient mLocationClient = null;
//    private TextView mtextView;
//    // 是否是第一次定位
//    private boolean isFirstLocate = true;
//    // 当前定位模式
//    private MyLocationConfiguration.LocationMode locationMode;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
//        SDKInitializer.initialize(getApplicationContext());
//        SDKInitializer.setCoordType(CoordType.BD09LL);
//        LocationClient.setAgreePrivacy(true);
//        setContentView(R.layout.activity_bdaddress_select);
//
//        //获取地图控件引用
//        mMapView = findViewById(R.id.bd_map_view);
//        //获取文本显示控件
//        mtextView = findViewById(R.id.tv_real_address);
//        // 得到地图
//        mBaiduMap = mMapView.getMap();
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        //定位初始化
//        mLocationClient = new LocationClient(this);
//
//        //通过LocationClientOption设置LocationClient相关参数
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        // 可选，设置地址信息
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址描述
//        option.setIsNeedLocationDescribe(true);
//
//        //设置locationClientOption
//        mLocationClient.setLocOption(option);
//
//        //注册LocationListener监听器
//        MyLocationListener myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);
//        //开启地图定位图层
//        mLocationClient.start();
//    }
//
//    // 继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView
//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mMapView == null){
//                return;
//            }
//
//            // 如果是第一次定位
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            if (isFirstLocate) {
//                isFirstLocate = false;
//                //给地图设置状态
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(ll));
//            }
//
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//
//            // ------------------  以下是可选部分 ------------------
//            // 自定义地图样式，可选
//            // 更换定位图标，这里的图片是放在 drawble 文件下的
//            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_loading);
//            // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
//            locationMode = MyLocationConfiguration.LocationMode.NORMAL;
//            // 定位模式、是否开启方向、设置自定义定位图标、精度圈填充颜色以及精度圈边框颜色5个属性（此处只设置了前三个）。
//            MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(locationMode,true,mCurrentMarker);
//            // 使自定义的配置生效
//            mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);
//            // ------------------  可选部分结束 ------------------
//
//            // 显示当前信息
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("\n经度：" + location.getLatitude());
//            stringBuilder.append("\n纬度："+ location.getLongitude());
//            stringBuilder.append("\n状态码："+ location.getLocType());
//            stringBuilder.append("\n国家：" + location.getCountry());
//            stringBuilder.append("\n城市："+ location.getCity());
//            stringBuilder.append("\n区：" + location.getDistrict());
//            stringBuilder.append("\n街道：" + location.getStreet());
//            stringBuilder.append("\n地址：" + location.getAddrStr());
//
//            mtextView.setText(stringBuilder.toString());
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        mMapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        mLocationClient.stop();
//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//        mMapView = null;
//        super.onDestroy();
//    }

}
