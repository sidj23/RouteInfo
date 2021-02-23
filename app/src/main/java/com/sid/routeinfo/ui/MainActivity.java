package com.sid.routeinfo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sid.routeinfo.R;
import com.sid.routeinfo.model.RouteInfo;
import com.sid.routeinfo.model.RouteResponse;
import com.sid.routeinfo.model.RouteTimeData;
import com.sid.routeinfo.room.LocalCacheManager;
import com.sid.routeinfo.util.api.ApiInterface;
import com.sid.routeinfo.util.api.ApiUtil;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiInterface apiInterface = ApiUtil.provideRetrofit().create(ApiInterface.class);
        makeApiCall(apiInterface);
    }

    private void makeApiCall(ApiInterface apiInterface) {

        new CompositeDisposable().add(apiInterface.makeGetApiForRouteData()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getDataFromJsonString(response);
                }, throwable -> {
                    //TODO no data available Error
                }));

    }

    private void getDataFromJsonString(String response) {
        try {

            Type routeResponseType = new TypeToken<RouteResponse>() {
            }.getType();
            RouteResponse routeResponse = new Gson().fromJson(response, routeResponseType);

            List<RouteInfo> routeInfoList = routeResponse.getRouteInfo();

            for (RouteInfo routeInfo : routeInfoList) {
                List<RouteTimeData> routeTimeDataList = new ArrayList<>();

                String routeTimeResponse = new JSONObject(response).getJSONObject("routeTimings").getJSONArray(routeInfo.getId()).toString();

                Type type2 = new TypeToken<List<RouteTimeData>>() {
                }.getType();
                routeTimeDataList = new Gson().fromJson(routeTimeResponse, type2);
                routeInfo.setRouteTimeDataList(routeTimeDataList);
            }

            setDataInDb(routeInfoList);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getDataFromDb();
                }
            }, 3000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataFromDb() {
        new CompositeDisposable().add(LocalCacheManager.getInstance(this).getRouteInfoList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.d(MainActivity.class.getSimpleName(), "Size:" + result.size());
                }, throwable -> {
                    //TODO no data available Error
                }));
    }

    private void setDataInDb(List<RouteInfo> routeInfoList) {
        new CompositeDisposable().add(LocalCacheManager.getInstance(this).insertRouteInfoList(routeInfoList)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                }, throwable -> {
                    //TODO no data available Error
                }));
    }
}