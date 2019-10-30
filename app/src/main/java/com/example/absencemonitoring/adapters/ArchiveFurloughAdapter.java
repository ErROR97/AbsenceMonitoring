package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.MasterFurloughActivity;
import com.example.absencemonitoring.instances.Furlough;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveFurloughAdapter extends RecyclerView.Adapter<ArchiveFurloughAdapter.MyViewHolder> {

    Activity activity;
    List<Furlough> list;

    public ArchiveFurloughAdapter(Activity activity, List<Furlough> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ArchiveFurloughAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArchiveFurloughAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_archive_furlough, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveFurloughAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getType());
        holder.amountTxt.setText(list.get(position).getAmountTime());
        holder.dateTxt.setText(list.get(position).getStartDate());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, nameTxt, amountTxt, dateTxt;
        CardView noticeFurlough;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeFurlough = itemView.findViewById(R.id.item_archive_furlough);
            nameTxt = itemView.findViewById(R.id.txt_name);
            typeTxt = itemView.findViewById(R.id.txt_type);
            amountTxt = itemView.findViewById(R.id.txt_amount);
            dateTxt = itemView.findViewById(R.id.txt_date);
        }
    }
}
