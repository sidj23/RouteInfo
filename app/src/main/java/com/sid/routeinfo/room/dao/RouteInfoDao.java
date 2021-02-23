package com.sid.routeinfo.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sid.routeinfo.model.RouteInfo;

import java.util.List;

@Dao
public interface RouteInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplaceRouteInfoList(List<RouteInfo> routeInfoList);

    @Query("SELECT * FROM route_info")
    List<RouteInfo> getRouteInfoList();
}
