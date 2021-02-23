package com.sid.routeinfo.room;

import android.content.Context;

import androidx.room.Room;

import com.sid.routeinfo.model.RouteInfo;

import java.util.List;

import io.reactivex.Observable;

public class LocalCacheManager {
    private static final String DB_NAME = "route-db-name";
    private static LocalCacheManager newInstance;
    private Context context;
    private AppDatabase db;

    public LocalCacheManager(Context context) {
        this.context = context;
        db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public static LocalCacheManager getInstance(Context context) {
        if (newInstance == null) {
            newInstance = new LocalCacheManager(context);
        }
        return newInstance;
    }

    public Observable<Boolean> insertRouteInfoList(List<RouteInfo> routeInfoList) {
        return Observable.fromCallable(() -> {
            db.routeInfoDao().insertOrReplaceRouteInfoList(routeInfoList);
            return true;
        });
    }

    public Observable<List<RouteInfo>> getRouteInfoList() {
        return Observable.fromCallable(() -> db.routeInfoDao().getRouteInfoList());
    }

}
