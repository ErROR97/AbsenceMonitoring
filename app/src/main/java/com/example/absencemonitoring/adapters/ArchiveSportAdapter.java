package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Sport;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveSportAdapter extends RecyclerView.Adapter<ArchiveSportAdapter.MyViewHolder> {

    Activity activity;
    List<Sport> list;

    public ArchiveSportAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ArchiveSportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArchiveSportAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_archive_sport, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveSportAdapter.MyViewHolder holder, int position) {
        holder.typeTxt.setText(list.get(position).getType());
        holder.dateTxt.setText(list.get(position).getDate());
        holder.startTxt.setText(list.get(position).getStartTime());
        holder.endTxt.setText(list.get(position).getEndTime());
        holder.iconImg.setImageResource(list.get(position).getIcon());
        holder.archiveSportItem.setCardBackgroundColor(list.get(position).getColor());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, dateTxt, startTxt, endTxt;
        ImageView iconImg;
        CardView archiveSportItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            archiveSportItem = itemView.findViewById(R.id.item_archive_sport);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dateTxt = itemView.findViewById(R.id.txt_date);
            startTxt = itemView.findViewById(R.id.txt_time_start);
            endTxt = itemView.findViewById(R.id.txt_time_end);
            iconImg = itemView.findViewById(R.id.img_icon);
        }
    }
}
