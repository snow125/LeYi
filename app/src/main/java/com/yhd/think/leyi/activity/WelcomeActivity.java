package com.yhd.think.leyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yhd.think.leyi.R;
import com.yhd.think.leyi.tools.ToastTool;

import org.json.JSONObject;

public class WelcomeActivity extends BaseActivity {

    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initBAIDU();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //start();
            }
        },5000);*/
    }

    private void start(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void initBAIDU(){
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener/*new MyLocationListener()*/);
        mLocationClient.setAK("K5qWr0jb0czG8zQYVfYYcyVU");
        LocationClientOption option = new LocationClientOption();
        /*//option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setOpenGps(true);
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        //option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setAddrType("all");
        //option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向*/

        option.setOpenGps(true);
        option.setAddrType("all");
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);
        option.disableCache(true);
        option.setPoiNumber(5);
        option.setPoiDistance(1000);
        option.setPoiExtraInfo(true);

        mLocationClient.setLocOption(option);
        mLocationClient.start();

        //DistanceUtil.getDistance();

    }

    public void json(View view){
        Log.e("123","this");
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,
                "http://110.lyapp2015.sinaapp.com/110/lyapp2015/getAll.action?nowPage=1&pageSize=3",null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    String str = jsonObject.optString("requsetType");
                    Log.e("123",str+"------");
                    //ToastTool.showToast(WelcomeActivity.this,str);
                }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("123","-----"+volleyError+"-----");
            }
        });
        addRequestToQueen(jor);

        //Log.e("123", "-----" + mLocationClient.requestLocation());
    }

    /*public static final String removeBOM(String data) {
        if(data.startsWith("\ufeff")){
            return data.substring(1);
        }else{
            return data;
        }
    }*/

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null){
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");

                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());

                }

                //if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                    //sb.append(location.getAddrStr());
                    /*sb.append(location.getCity());
                    sb.append(location.getDistrict());*/
                //}



                //Log.e("123","baidu-------"+sb.toString());

                String address = location.getAddrStr();

               // Log.e("123","null------"+address);

                if (address==null || address.trim().equals("")){
                    address = "定位失败";
                }else{
                    /*application.setCurrentAddress(address);
                    application.setLatitude(location.getLatitude());
                    application.setLongitude(location.getLongitude());
                    application.setRealCity(location.getCity());
                    application.setDistrict(location.getDistrict());*/
                }

            }
        }

        @Override
        public void onReceivePoi(BDLocation bdLocation) {

        }

        /*@Override
        public void onReceivePoi(BDLocation bdLocation) {

        }*/
    }

}
