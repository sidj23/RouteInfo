package com.sid.routeinfo.util.api;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.sid.routeinfo.util.AppConstants.BASE_URL;
import static com.sid.routeinfo.util.AppConstants.CUSTOM_CONNECT_TIMEOUT;
import static com.sid.routeinfo.util.AppConstants.CUSTOM_READ_TIMEOUT;

public class ApiUtil {

    private static OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(CUSTOM_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CUSTOM_READ_TIMEOUT, TimeUnit.SECONDS);
        return client.build();
    }

    public static Retrofit provideRetrofit() {

        OkHttpClient okHttpClient = provideOkhttpClient();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }


}
