package com.sid.routeinfo.util.api;


import io.reactivex.Observable;
import retrofit2.http.GET;

import static com.sid.routeinfo.util.api.ApiEndPoints.ENDPOINT_GET_ROUTE_DATA;

public interface ApiInterface {

    @GET(ENDPOINT_GET_ROUTE_DATA)
    Observable<String> makeGetApiForRouteData();

}
