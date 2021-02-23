package com.sid.routeinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteResponse {
    @SerializedName("routeInfo")
    @Expose
    private List<RouteInfo> routeInfo = null;
    @SerializedName("routeTimings")
    @Expose
    private RouteTimings routeTimings;

    public List<RouteInfo> getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(List<RouteInfo> routeInfo) {
        this.routeInfo = routeInfo;
    }

    public RouteTimings getRouteTimings() {
        return routeTimings;
    }

    public void setRouteTimings(RouteTimings routeTimings) {
        this.routeTimings = routeTimings;
    }

}
