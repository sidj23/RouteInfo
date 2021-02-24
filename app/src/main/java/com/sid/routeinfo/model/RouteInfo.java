package com.sid.routeinfo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sid.routeinfo.model.converters.RouteTimeConverter;

import java.util.List;

@Entity(tableName = "route_info")
public class RouteInfo {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("tripDuration")
    @Expose
    private String tripDuration;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("icon")
    @Expose
    private String icon;

    @TypeConverters(RouteTimeConverter.class)
    private List<RouteTimeData> routeTimeDataList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(String tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<RouteTimeData> getRouteTimeDataList() {
        return routeTimeDataList;
    }

    public void setRouteTimeDataList(List<RouteTimeData> routeTimeDataList) {
        this.routeTimeDataList = routeTimeDataList;
    }
}
