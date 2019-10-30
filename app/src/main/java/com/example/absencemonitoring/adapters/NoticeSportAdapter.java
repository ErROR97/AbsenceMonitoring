package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Sport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeSportAdapter extends RecyclerView.Adapter<NoticeSportAdapter.MyViewHolder> {

    Activity activity;
    List<Sport> list;

    public NoticeSportAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeSportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeSportAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_notice_sport, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeSportAdapter.MyViewHolder holder, int position) {
        holder.typeTxt.setText(list.get(position).getType());
        holder.dateTxt.setText(list.get(position).getDate());
        holder.startTxt.setText(list.get(position).getStartTime());
        holder.endTxt.setText(list.get(position).getEndTime());
        holder.numberTxt.setText(list.get(position).getNumber());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, dateTxt, startTxt, endTxt, numberTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dateTxt = itemView.findViewById(R.id.txt_date);
            startTxt = itemView.findViewById(R.id.txt_time_start);
            endTxt = itemView.findViewById(R.id.txt_time_end);
            numberTxt = itemView.findViewById(R.id.txt_number);
        }
    }
}
