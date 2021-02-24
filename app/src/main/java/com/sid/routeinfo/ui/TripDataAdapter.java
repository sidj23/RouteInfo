package com.sid.routeinfo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sid.routeinfo.base.BaseViewHolder;
import com.sid.routeinfo.databinding.ItemRouteInfoBinding;
import com.sid.routeinfo.model.RouteInfo;

import java.util.ArrayList;
import java.util.List;

public class TripDataAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<RouteInfo> routeInfoList = new ArrayList<>();
    private Context context;

    public TripDataAdapter(List<RouteInfo> routeInfoList, Context context) {
        this.routeInfoList = routeInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRouteInfoBinding binding = ItemRouteInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RouteInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return routeInfoList.size();
    }


    private class RouteInfoViewHolder extends BaseViewHolder {

        private ItemRouteInfoBinding routeInfoBinding;
        private TripDataViewModel tripDataViewModel;

        public RouteInfoViewHolder(ItemRouteInfoBinding routeInfoBinding) {
            super(routeInfoBinding.getRoot());
            this.routeInfoBinding = routeInfoBinding;
        }

        @Override
        public void onBind(int position) {
            tripDataViewModel = new TripDataViewModel(routeInfoList.get(position));
            routeInfoBinding.setViewModel(tripDataViewModel);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            RouteTimingAdapter adapter = new RouteTimingAdapter();

            routeInfoBinding.iriRouteTimingRv.setLayoutManager(linearLayoutManager);
            routeInfoBinding.iriRouteTimingRv.setAdapter(adapter);

            routeInfoBinding.executePendingBindings();
        }
    }


}
