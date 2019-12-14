package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.SportlistDetailsActivity;
import com.example.absencemonitoring.instances.SportmanList;
import com.example.absencemonitoring.instances.SportmanTiming;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportmanTimingAdapter extends RecyclerView.Adapter<SportmanTimingAdapter.MyViewHolder> {

    Activity activity;
    List<SportmanTiming> list;

    public SportmanTimingAdapter(Activity activity, List<SportmanTiming> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SportmanTimingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportmanTimingAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sportman_timing, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportmanTimingAdapter.MyViewHolder holder, int position) {
        holder.timeTxt.setText(list.get(position).getTime());
        holder.startTxt.setText(list.get(position).getStartTime());
        holder.endTxt.setText(list.get(position).getEndTime());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timeTxt, startTxt, endTxt;
        CardView editBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTxt = itemView.findViewById(R.id.txt_time);
            startTxt = itemView.findViewById(R.id.txt_start_time);
            endTxt = itemView.findViewById(R.id.txt_end_time);
            editBtn = itemView.findViewById(R.id.btn_delete);
        }
    }
}
