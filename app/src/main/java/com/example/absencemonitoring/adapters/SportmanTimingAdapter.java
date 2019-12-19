package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.utils.Formating;

import java.util.List;

public class SportmanTimingAdapter extends RecyclerView.Adapter<SportmanTimingAdapter.MyViewHolder> {
    Activity activity;
    List<Sport> list;

    public SportmanTimingAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.activity).inflate(R.layout.item_sportman_timing, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.startTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0]));
        holder.endTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]));
    }

    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends ViewHolder {
        CardView editBtn;
        TextView endTxt;
        TextView startTxt;
        TextView timeTxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.timeTxt =  itemView.findViewById(R.id.txt_time);
            this.startTxt =  itemView.findViewById(R.id.txt_start_time);
            this.endTxt =  itemView.findViewById(R.id.txt_end_time);
            this.editBtn =  itemView.findViewById(R.id.btn_delete);
        }
    }
}
