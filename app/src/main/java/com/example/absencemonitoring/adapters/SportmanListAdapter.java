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
import com.example.absencemonitoring.instances.NoticeSport;
import com.example.absencemonitoring.instances.SportmanList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportmanListAdapter extends RecyclerView.Adapter<SportmanListAdapter.MyViewHolder> {

    Activity activity;
    List<SportmanList> list;

    public SportmanListAdapter(Activity activity, List<SportmanList> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SportmanListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportmanListAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sportman_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportmanListAdapter.MyViewHolder holder, int position) {
        holder.typeTxt.setText(list.get(position).getType());
        holder.startTxt.setText(list.get(position).getStartTime());
        holder.endTxt.setText(list.get(position).getEndTime());
        holder.numberTxt.setText(list.get(position).getNumber());
        holder.sportmanListItem.setCardBackgroundColor(activity.getResources().getColor(list.get(position).getColor()));
        holder.iconImg.setImageResource(list.get(position).getIcon());

        holder.sportmanListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, SportlistDetailsActivity.class));

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, startTxt, endTxt, numberTxt;
        CardView sportmanListItem;
        ImageView iconImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sportmanListItem = itemView.findViewById(R.id.item_sportman_list);
            typeTxt = itemView.findViewById(R.id.txt_type);
            startTxt = itemView.findViewById(R.id.txt_time_start);
            endTxt = itemView.findViewById(R.id.txt_time_end);
            numberTxt = itemView.findViewById(R.id.txt_number);
            iconImg = itemView.findViewById(R.id.img_icon);
        }
    }
}
