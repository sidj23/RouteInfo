package com.sid.routeinfo.model.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sid.routeinfo.model.RouteTimeData;

import java.lang.reflect.Type;
import java.util.List;

public class RouteTimeConverter {
    @TypeConverter
    public List<RouteTimeData> storedStringToType(String value) {
        Type type = new TypeToken<List<RouteTimeData>>() {
        }.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public String typeToStoredString(List<RouteTimeData> list) {
        return new Gson().toJson(list);
    }
}
