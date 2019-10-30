package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.instances.ControlFurlough;
import com.example.absencemonitoring.activities.FurloughDetailsActivity;
import com.example.absencemonitoring.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ControlAdapter extends RecyclerView.Adapter<ControlAdapter.MyViewHolder> {

    Activity activity;
    List<ControlFurlough> list;

    public ControlAdapter(Activity activity, List<ControlFurlough> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ControlAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ControlAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_control, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ControlAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getType());
        holder.dayTxt.setText(list.get(position).getRemainderDay());
        holder.hourTxt.setText(list.get(position).getRemainderHour());
        holder.minuteTxt.setText(list.get(position).getRemainderMinute());

        holder.controlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, FurloughDetailsActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, nameTxt, dayTxt, hourTxt, minuteTxt;
        CardView controlItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            controlItem = itemView.findViewById(R.id.item_control);
            nameTxt = itemView.findViewById(R.id.txt_name);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dayTxt = itemView.findViewById(R.id.txt_remainder_day);
            hourTxt = itemView.findViewById(R.id.txt_remainder_hour);
            minuteTxt = itemView.findViewById(R.id.txt_remainder_minutes);
        }
    }
}
