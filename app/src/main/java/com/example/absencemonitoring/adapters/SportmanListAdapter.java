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
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportmanListAdapter extends RecyclerView.Adapter<SportmanListAdapter.MyViewHolder> {

    Activity activity;
    List<Sport> list;
    int chosenWeekDay;
    String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};


    public SportmanListAdapter(Activity activity, List<Sport> list, int chosenWeekDay) {
        this.activity = activity;
        this.list = list;
        this.chosenWeekDay = chosenWeekDay;
    }

    @NonNull
    @Override
    public SportmanListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportmanListAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sportman_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportmanListAdapter.MyViewHolder holder, final int position) {
        holder.startTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0]));
        holder.endTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]));

        if (list.get(position).getType().equals("volleyball")) {
            holder.sportmanListItem.setCardBackgroundColor(activity.getResources().getColor(R.color.yellow));
            holder.iconImg.setImageResource(R.drawable.ic_volleyball);
            holder.typeTxt.setText("والیبال");
        } else if (list.get(position).getType().equals("football")) {
            holder.sportmanListItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
            holder.iconImg.setImageResource(R.drawable.ic_football);
            holder.typeTxt.setText("فوتبال");
        } else {
            holder.sportmanListItem.setCardBackgroundColor(activity.getResources().getColor(R.color.light_blue));
            holder.iconImg.setImageResource(R.drawable.ic_swimming);
            holder.typeTxt.setText("استخر");
        }

        holder.sportmanListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SportlistDetailsActivity.class);
                try {
                    intent.putExtra("persons", list.get(position).getPersonalIds().get(weekDays[chosenWeekDay]).toString());
                    intent.putExtra("type", list.get(position).getType());
                    intent.putExtra("time", list.get(position).getTime());
                    intent.putExtra("capacity", list.get(position).getCapacity().get(weekDays[chosenWeekDay]).toString());
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
        TextView typeTxt, startTxt, endTxt, numberTxt;
        CardView sportmanListItem;
        ImageView iconImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sportmanListItem = itemView.findViewById(R.id.item_sportman_list);
            typeTxt = itemView.findViewById(R.id.txt_type);
            startTxt = itemView.findViewById(R.id.txt_start_time);
            endTxt = itemView.findViewById(R.id.txt_end_time);
            numberTxt = itemView.findViewById(R.id.txt_number);
            iconImg = itemView.findViewById(R.id.img_icon);
        }
    }
}
