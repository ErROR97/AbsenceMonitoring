package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.Utils.Formating;
import com.example.absencemonitoring.instances.ControlFurlough;
import com.example.absencemonitoring.activities.FurloughDetailsActivity;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Furlough;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ControlAdapter extends RecyclerView.Adapter<ControlAdapter.MyViewHolder> {

    Activity activity;
    List<Furlough> list;

    public ControlAdapter(Activity activity, List<Furlough> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ControlAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ControlAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_control, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ControlAdapter.MyViewHolder holder, final int position) {
        String remaindTime;

        if (list.get(position).isStarted()) {

            remaindTime = DateTime.calculateRemainingTime(list.get(position).getStartDate(), list.get(position).getStartTime(), list.get(position).getTimeLeave());

            holder.controlItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
            holder.dayTxt.setTextColor(activity.getResources().getColor(R.color.light_yellow));
            holder.hourTxt.setTextColor(activity.getResources().getColor(R.color.light_yellow));
            holder.minuteTxt.setTextColor(activity.getResources().getColor(R.color.light_yellow));
            holder.dayLbl.setTextColor(activity.getResources().getColor(R.color.light_yellow));
            holder.hourLbl.setTextColor(activity.getResources().getColor(R.color.light_yellow));
            holder.minuteLbl.setTextColor(activity.getResources().getColor(R.color.light_yellow));
        } else {

            remaindTime = String.format("%02d", Integer.parseInt(list.get(position).getTimeLeave().split(":")[0]))
            + ":" + String.format("%02d", Integer.parseInt(list.get(position).getTimeLeave().split(":")[1]))
            + ":" + String.format("%02d", Integer.parseInt(list.get(position).getTimeLeave().split(":")[2]));

            holder.controlItem.setCardBackgroundColor(activity.getResources().getColor(R.color.yellow));
            holder.dayTxt.setTextColor(activity.getResources().getColor(R.color.light_green));
            holder.hourTxt.setTextColor(activity.getResources().getColor(R.color.light_green));
            holder.minuteTxt.setTextColor(activity.getResources().getColor(R.color.light_green));
            holder.dayLbl.setTextColor(activity.getResources().getColor(R.color.light_green));
            holder.hourLbl.setTextColor(activity.getResources().getColor(R.color.light_green));
            holder.minuteLbl.setTextColor(activity.getResources().getColor(R.color.light_green));

        }
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getLeaveType());
        holder.dayTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[0]));
        holder.hourTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[1]));
        holder.minuteTxt.setText(Formating.englishDigitsToPersian(remaindTime.split(":")[2]));

        holder.controlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FurloughDetailsActivity.class);
                intent.putExtra("name", list.get(position).getName());
                intent.putExtra("personalId", list.get(position).getPersonalIdemployee());
                intent.putExtra("type", list.get(position).getLeaveType());
                intent.putExtra("startDate", list.get(position).getStartDate());
                intent.putExtra("startTime", list.get(position).getStartTime());
                intent.putExtra("amountTime", list.get(position).getTimeLeave());
                intent.putExtra("isStarted", list.get(position).isStarted());
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, nameTxt, dayTxt, hourTxt, minuteTxt;
        TextView dayLbl, hourLbl, minuteLbl;
        CardView controlItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            controlItem = itemView.findViewById(R.id.item_control);
            nameTxt = itemView.findViewById(R.id.txt_name);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dayTxt = itemView.findViewById(R.id.txt_remainder_day);
            hourTxt = itemView.findViewById(R.id.txt_remainder_hour);
            minuteTxt = itemView.findViewById(R.id.txt_remainder_minutes);

            dayLbl = itemView.findViewById(R.id.lbl_day);
            hourLbl = itemView.findViewById(R.id.lbl_hour);
            minuteLbl = itemView.findViewById(R.id.lbl_minute);
        }
    }
}
