package com.sid.routeinfo.util;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sid.routeinfo.model.RouteTimeData;
import com.sid.routeinfo.ui.RouteTimingAdapter;

import java.util.ArrayList;
import java.util.List;

public class BindingUtils {

    @BindingAdapter({"routeTimeAdapter"})
    public static void routeTimeAdapter(RecyclerView recyclerView, List<RouteTimeData> routeTimeDataList) {
        RouteTimingAdapter adapter = (RouteTimingAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItem();
            adapter.addItems(new ArrayList<>(routeTimeDataList));
        }
    }
}
