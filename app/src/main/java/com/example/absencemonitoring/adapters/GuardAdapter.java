package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.FurloughDetailsActivity;
import com.example.absencemonitoring.instances.GuardList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class                                                                                                              GuardAdapter extends RecyclerView.Adapter<GuardAdapter.MyViewHolder> {

    Activity activity;
    List<GuardList> list;

    public GuardAdapter(Activity activity, List<GuardList> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public GuardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuardAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_guard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuardAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getType());
        holder.dateTxt.setText(list.get(position).getDate());
        holder.startTimeTxt.setText(list.get(position).getStartTime());
        holder.amountTimeTxt.setText(list.get(position).getAmountTime());


        if (list.get(position).getStatus().equals("شروع نشده")) {
            holder.guardItem.setCardBackgroundColor(activity.getResources().getColor(R.color.yellow));
        } else if (list.get(position).getStatus().equals("در حال انجام")) {
            holder.guardItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
        } else {
            holder.guardItem.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
        }

        holder.guardItem.setOnClickListener(new View.OnClickListener() {
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
        CardView guardItem;
        TextView nameTxt, typeTxt, dateTxt, startTimeTxt, amountTimeTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            guardItem = itemView.findViewById(R.id.item_guard);
            nameTxt = itemView.findViewById(R.id.txt_name);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dateTxt = itemView.findViewById(R.id.txt_date);
            startTimeTxt = itemView.findViewById(R.id.txt_start);
            amountTimeTxt = itemView.findViewById(R.id.txt_amount);
        }
    }
}
