package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.ReserveSportTime;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReserveSportTimeAdapter extends RecyclerView.Adapter<ReserveSportTimeAdapter.MyViewHolder> {

    Activity activity;
    List<ReserveSportTime> list;

    public ReserveSportTimeAdapter(Activity activity, List<ReserveSportTime> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ReserveSportTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReserveSportTimeAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_reserve_sport_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveSportTimeAdapter.MyViewHolder holder, int position) {
        holder.checkTxt.setText(list.get(position).getCheckText());
        holder.checkImg.setBackground(activity.getResources().getDrawable(list.get(position).getCheckImage()));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView checkTxt;
        ImageView checkImg;
        RelativeLayout checkContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkTxt = itemView.findViewById(R.id.txt_check);
            checkImg = itemView.findViewById(R.id.img_check);
            checkContainer = itemView.findViewById(R.id.container_check);
        }
    }
}
