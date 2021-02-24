package com.sid.routeinfo.util;

import android.content.Context;
import android.content.SharedPreferences;

import static com.sid.routeinfo.util.AppConstants.PREF_NAME;

public class PreferenceHelper {

    private static final String PREF_KEY_ROUTE_DATA_AVAILABLE = "PREF_KEY_ROUTE_DATA_AVAILABLE";

    private final SharedPreferences mPrefs;

    public PreferenceHelper(Context context) {
        this.mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setIsRouteDataAvailable(Boolean isRouteDataAvailable) {
        mPrefs.edit().putBoolean(PREF_KEY_ROUTE_DATA_AVAILABLE, isRouteDataAvailable).apply();
    }

    public Boolean isRouteDataAvailable() {
        return mPrefs.getBoolean(PREF_KEY_ROUTE_DATA_AVAILABLE, false);
    }

}
