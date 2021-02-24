package com.sid.routeinfo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sid.routeinfo.R;
import com.sid.routeinfo.model.RouteInfo;
import com.sid.routeinfo.model.RouteResponse;
import com.sid.routeinfo.model.RouteTimeData;
import com.sid.routeinfo.room.LocalCacheManager;
import com.sid.routeinfo.util.NetworkUtil;
import com.sid.routeinfo.util.PreferenceHelper;
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


    private TripDataAdapter tripDataAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ApiInterface apiInterface;
    private PreferenceHelper preferenceHelper;

    private RecyclerView tripRecyclerView;
    private ProgressBar progressBar;
    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiUtil.provideRetrofit().create(ApiInterface.class);
        preferenceHelper = new PreferenceHelper(this);

        tripRecyclerView = findViewById(R.id.am_trip_rv);
        progressBar = findViewById(R.id.am_progress_bar);
        tvNoData = findViewById(R.id.am_no_data_tv);

        setUpDataSource();
    }

    private void setUpDataSource() {
        if (preferenceHelper.isRouteDataAvailable()) {
            getDataFromDb();
            if (NetworkUtil.isNetworkConnected(this))
                makeApiCall();
        } else {
            if (NetworkUtil.isNetworkConnected(this)) {
                makeApiCall();
            } else {
                setNoDataAvailable();
            }
        }

    }

    private void makeApiCall() {

        new CompositeDisposable().add(apiInterface.makeGetApiForRouteData()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getDataFromJsonString(response);
                }, throwable -> {
                    if (!preferenceHelper.isRouteDataAvailable())
                        setNoDataAvailable();
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


        } catch (Exception e) {
            e.printStackTrace();
            if (!preferenceHelper.isRouteDataAvailable())
                setNoDataAvailable();
        }
    }

    private void getDataFromDb() {
        new CompositeDisposable().add(LocalCacheManager.getInstance(this).getRouteInfoList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setDataToRecyclerView(result);
                }, throwable -> {
                    setNoDataAvailable();
                }));
    }

    private void setDataToRecyclerView(List<RouteInfo> routeInfoList) {
        if (routeInfoList != null && routeInfoList.size() > 0) {
            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            tripDataAdapter = new TripDataAdapter(new ArrayList<>(routeInfoList), this);
            tripRecyclerView.setLayoutManager(linearLayoutManager);
            tripRecyclerView.setAdapter(tripDataAdapter);

            progressBar.setVisibility(View.GONE);
            tripRecyclerView.setVisibility(View.VISIBLE);


            setHandlerForMinuteUpdate();


        } else {
            setNoDataAvailable();
        }
    }

    private void setNoDataAvailable() {
        progressBar.setVisibility(View.GONE);
        tripRecyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    private void setHandlerForMinuteUpdate() {
        if (tripDataAdapter != null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    tripDataAdapter.notifyDataSetChanged();
                    handler.postDelayed(this, 60 * 1000);
                }
            }, 60 * 1000);
        }
    }

    private void setDataInDb(List<RouteInfo> routeInfoList) {
        if (routeInfoList != null && routeInfoList.size() > 0) {
            new CompositeDisposable().add(LocalCacheManager.getInstance(this).insertRouteInfoList(routeInfoList)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        preferenceHelper.setIsRouteDataAvailable(true);
                        getDataFromDb();
                    }, throwable -> {
                        setNoDataAvailable();
                    }));
        }
    }
}