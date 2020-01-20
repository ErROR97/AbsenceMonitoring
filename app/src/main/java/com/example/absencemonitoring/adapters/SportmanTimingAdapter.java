package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.DeleteSportTimeListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;
import com.example.absencemonitoring.utils.Formating;

import java.util.List;

public class SportmanTimingAdapter extends RecyclerView.Adapter<SportmanTimingAdapter.MyViewHolder> {
    Activity activity;
    List<Sport> list;
    DeleteSportTimeListener deleteSportTimeListener;

    public SportmanTimingAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
        deleteSportTimeListener = (DeleteSportTimeListener) activity;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.activity).inflate(R.layout.item_sportman_timing, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        switch (list.get(position).getType()) {
            case "volleyball":
                holder.item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_yellow));
                break;
            case "football":
                holder.item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
                break;
            case "swimming":
                holder.item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_blue));
                break;
        }

        holder.dateTxt.setText(list.get(position).getActualDate());
        holder.startTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0]));
        holder.endTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]));

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSportTimeListener.onSportTimeDelete(list.get(position));
            }
        });
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder {
        CardView item;
        TextView dateTxt, startTxt, endTxt;
        CardView deleteBtn;


        public MyViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            dateTxt =  itemView.findViewById(R.id.txt_date);
            startTxt =  itemView.findViewById(R.id.txt_start_time);
            endTxt =  itemView.findViewById(R.id.txt_end_time);
            deleteBtn = itemView.findViewById(R.id.btn_delete);
        }
    }
}
