package com.example.absencemonitoring;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    Activity activity;
    List<Employee> list;

    public EmployeeAdapter(Activity activity, List<Employee> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.positionTxt.setText(list.get(position).getPosition());
        if (!list.get(position).isStatus()) {
//            holder.employeeItem.setAlpha(.6f);
            holder.statusImg.setImageDrawable(activity.getResources().getDrawable(R.drawable.background_status_passive));
        }
        holder.employeeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, EmployeeActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, positionTxt;
        ImageView statusImg;
        CardView employeeItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.txt_name);
            positionTxt = itemView.findViewById(R.id.txt_position);
            employeeItem = itemView.findViewById(R.id.item_employee);
            statusImg = itemView.findViewById(R.id.img_status);
        }
    }
}
