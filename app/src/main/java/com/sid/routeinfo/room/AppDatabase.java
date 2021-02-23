package com.sid.routeinfo.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sid.routeinfo.model.RouteInfo;
import com.sid.routeinfo.room.dao.RouteInfoDao;

@Database(
        entities = {
                RouteInfo.class
        },
        version = 9,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RouteInfoDao routeInfoDao();
}
