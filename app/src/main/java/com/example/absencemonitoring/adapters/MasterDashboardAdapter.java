package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.FurloughDetailsActivity;
import com.example.absencemonitoring.instances.MasterDashboard;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MasterDashboardAdapter extends RecyclerView.Adapter<MasterDashboardAdapter.MyViewHolder> {
    Activity activity;
    List<MasterDashboard> list;

    public MasterDashboardAdapter(Activity activity, List<MasterDashboard> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_master_dashboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.typeTxt.setText(list.get(position).getType());
        holder.nameTxt.setText(list.get(position).getName());
        holder.personalCodeTxt.setText(String.valueOf(list.get(position).getPersonalCode()));
        holder.dateTxt.setText(list.get(position).getDate());
        holder.timeTxt.setText(list.get(position).getTime());

        holder.employeeItem.setOnClickListener(new View.OnClickListener() {
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
        TextView typeTxt, nameTxt, personalCodeTxt, dateTxt, timeTxt;
        CardView employeeItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeItem = itemView.findViewById(R.id.item_employee);
            typeTxt = itemView.findViewById(R.id.txt_type);
            nameTxt = itemView.findViewById(R.id.txt_name);
            personalCodeTxt = itemView.findViewById(R.id.txt_personal_code);
            dateTxt = itemView.findViewById(R.id.txt_date);
            timeTxt = itemView.findViewById(R.id.txt_time);
        }
    }
}
