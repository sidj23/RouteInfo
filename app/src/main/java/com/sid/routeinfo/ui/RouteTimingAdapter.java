package com.sid.routeinfo.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sid.routeinfo.base.BaseViewHolder;
import com.sid.routeinfo.databinding.ItemRouteTimingBinding;
import com.sid.routeinfo.model.RouteTimeData;

import java.util.ArrayList;
import java.util.List;

public class RouteTimingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<RouteTimeData> routeTimeDataList = new ArrayList<>();

    public RouteTimingAdapter() {

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRouteTimingBinding binding = ItemRouteTimingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RouteTimingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return routeTimeDataList.size();
    }

    public void clearItem() {
        routeTimeDataList.clear();
    }

    public void addItems(List<RouteTimeData> routeTimeDataList) {
        this.routeTimeDataList.addAll(routeTimeDataList);
        notifyDataSetChanged();
    }

    private class RouteTimingViewHolder extends BaseViewHolder {

        private ItemRouteTimingBinding routeTimingBinding;
        private TripDataViewModel tripDataViewModel;

        public RouteTimingViewHolder(ItemRouteTimingBinding routeTimingBinding) {
            super(routeTimingBinding.getRoot());
            this.routeTimingBinding = routeTimingBinding;
        }

        @Override
        public void onBind(int position) {
            tripDataViewModel = new TripDataViewModel(routeTimeDataList.get(position));
            routeTimingBinding.setViewModel(tripDataViewModel);
            routeTimingBinding.executePendingBindings();
        }
    }
}
