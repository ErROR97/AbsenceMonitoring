package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.NoticeTransport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeTransportAdapter extends RecyclerView.Adapter<NoticeTransportAdapter.MyViewHolder> {

    Activity activity;
    List<NoticeTransport> list;

    public NoticeTransportAdapter(Activity activity, List<NoticeTransport> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeTransportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeTransportAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_notice_transport, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeTransportAdapter.MyViewHolder holder, int position) {
        holder.typeTxt.setText(list.get(position).getType());
        holder.dateTxt.setText(list.get(position).getDate());
        holder.timeTxt.setText(list.get(position).getTime());
        holder.addressTxt.setText(list.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, dateTxt, timeTxt, addressTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dateTxt = itemView.findViewById(R.id.txt_date);
            timeTxt = itemView.findViewById(R.id.txt_time);
            addressTxt = itemView.findViewById(R.id.txt_address);
        }
    }
}
