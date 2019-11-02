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

public class NoticeFurloughAdapter extends RecyclerView.Adapter<NoticeFurloughAdapter.MyViewHolder> {

    Activity activity;
    List<Furlough> list;

    public NoticeFurloughAdapter(Activity activity, List<Furlough> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeFurloughAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeFurloughAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_notice_furlough, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeFurloughAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getLeaveType());
        holder.amountTxt.setText("3 روز");

        holder.noticeFurlough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, MasterFurloughActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, nameTxt, amountTxt;
        CardView noticeFurlough;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeFurlough = itemView.findViewById(R.id.item_notice_furlough);
            nameTxt = itemView.findViewById(R.id.txt_name);
            typeTxt = itemView.findViewById(R.id.txt_type);
            amountTxt = itemView.findViewById(R.id.txt_amount);
        }
    }
}
