package com.sid.routeinfo.ui;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.sid.routeinfo.model.RouteInfo;
import com.sid.routeinfo.model.RouteTimeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripDataViewModel {

    public ObservableField<String> name;
    public ObservableField<String> source;
    public ObservableField<String> destination;
    public ObservableField<String> duration;
    public ObservableField<String> timings;
    public ObservableField<String> noTimeAvailableText;

    public ObservableField<String> startTime;
    public ObservableField<String> availSeats;
    public ObservableField<String> totalSeats;

    public ObservableBoolean isRouteTimingAvailable;

    public ObservableList<RouteTimeData> timingObservableList = new ObservableArrayList<>();


    public TripDataViewModel(RouteInfo routeInfo) {
        name = new ObservableField<>(routeInfo.getName());
        source = new ObservableField<>(routeInfo.getSource());
        destination = new ObservableField<>(routeInfo.getDestination());
        duration = new ObservableField<>(routeInfo.getTripDuration());
        timings = new ObservableField<>("Route Timings");
        noTimeAvailableText = new ObservableField<>("No Route Timing Available");

        isRouteTimingAvailable = new ObservableBoolean(false);

        if (routeInfo.getRouteTimeDataList() != null && routeInfo.getRouteTimeDataList().size() > 0) {
            List<RouteTimeData> finalTimeDataList = getFutureTimeSortedList(routeInfo.getRouteTimeDataList());

            if (finalTimeDataList != null && finalTimeDataList.size() > 0) {
                timingObservableList.clear();
                timingObservableList.addAll(finalTimeDataList);
                isRouteTimingAvailable.set(true);
            }
        }

    }

    public TripDataViewModel(RouteTimeData routeTimeData) {
        startTime = new ObservableField<>(routeTimeData.getTripStartTime());
        availSeats = new ObservableField<>(routeTimeData.getAvaiable() + "");
        totalSeats = new ObservableField<>(routeTimeData.getTotalSeats() + "");
    }

    private List<RouteTimeData> getFutureTimeSortedList(List<RouteTimeData> routeTimeDataList) {

        List<RouteTimeData> finalList = new ArrayList<>();

        for (RouteTimeData routeTimeData : routeTimeDataList) {
            String startTime = routeTimeData.getTripStartTime();
            if (isEligibleForFutureTiming(startTime))
                finalList.add(routeTimeData);
        }

        return finalList;
    }

    private Boolean isEligibleForFutureTiming(String startTime) {
        try {
            long currentTime = System.currentTimeMillis();
            Date currentDate = new Date(currentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);
            currentCalendar.add(Calendar.DATE, 1);

            Date startTimeDate = new SimpleDateFormat("HH:mm").parse(startTime);
            Calendar startTimeCalendar = Calendar.getInstance();
            startTimeCalendar.setTime(startTimeDate);
            startTimeCalendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
            startTimeCalendar.add(Calendar.DATE, 1);

            return startTimeCalendar.getTime().after(currentCalendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
