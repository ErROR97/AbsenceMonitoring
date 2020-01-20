package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.SportlistDetailsActivity;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportmanListAdapter extends RecyclerView.Adapter<SportmanListAdapter.MyViewHolder> {

    Activity activity;
    List<Sport> list;
    String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سهشنبه", "چهارشنبه", "پنجشنبه", "جمعه"};


    public SportmanListAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SportmanListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportmanListAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sportman_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportmanListAdapter.MyViewHolder holder, final int position) {
        final String weekday = weekDays[Arrays.asList(persianWeekDays).indexOf(list.get(position).getActualDate().split(" ")[0])];
        holder.startTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0]));
        holder.endTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]));
        holder.dateTxt.setText(list.get(position).getActualDate());
        try {
            holder.capacityTxt.setText(Formating.englishDigitsToPersian(String.valueOf(12 - Integer.valueOf(list.get(position).getCapacity().get(weekday).toString()))));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (list.get(position).getType().equals("volleyball")) {
            holder.rootItem.setCardBackgroundColor(activity.getResources().getColor(R.color.yellow));
            holder.iconImg.setImageResource(R.drawable.ic_volleyball);
            holder.typeTxt.setText("والیبال");
        } else if (list.get(position).getType().equals("football")) {
            holder.rootItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
            holder.iconImg.setImageResource(R.drawable.ic_football);
            holder.typeTxt.setText("فوتبال");
        } else {
            holder.rootItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_blue));
            holder.iconImg.setImageResource(R.drawable.ic_swimming);
            holder.typeTxt.setText("استخر");
        }


        holder.rootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SportlistDetailsActivity.class);
                try {
                    intent.putExtra("persons", list.get(position).getPersonalIds().get(weekday).toString());
                    intent.putExtra("type", list.get(position).getType());
                    intent.putExtra("time", list.get(position).getTime());
                    intent.putExtra("capacity", list.get(position).getCapacity().get(weekday).toString());
                    intent.putExtra("date", list.get(position).getActualDate());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                activity.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeTxt, startTxt, endTxt, dateTxt, capacityTxt;
        CardView rootItem;
        ImageView iconImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rootItem = itemView.findViewById(R.id.item);
            typeTxt = itemView.findViewById(R.id.txt_type);
            startTxt = itemView.findViewById(R.id.txt_start_time);
            endTxt = itemView.findViewById(R.id.txt_end_time);
            dateTxt = itemView.findViewById(R.id.txt_date);
            capacityTxt = itemView.findViewById(R.id.txt_capacity);
            iconImg = itemView.findViewById(R.id.img_icon);
        }
    }
}
