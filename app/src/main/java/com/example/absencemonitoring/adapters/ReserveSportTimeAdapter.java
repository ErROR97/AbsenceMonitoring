package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.ReserveSportTime;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.SendSelectedReserverSportTimeListener;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReserveSportTimeAdapter extends RecyclerView.Adapter<ReserveSportTimeAdapter.MyViewHolder> {

    Activity activity;
    List<Sport> list;
    List<Sport> selectedList;
    boolean[] selected;
    SendSelectedReserverSportTimeListener sendSelectedReserverSportTimeListener;
    String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    int chosenWeekDay;

    public ReserveSportTimeAdapter(Activity activity, List<Sport> list, int chosenWeekDay) {
        this.activity = activity;
        this.list = list;
        this.chosenWeekDay = chosenWeekDay;
        selectedList = new ArrayList<>();
        selected = new boolean[list.size()];
        sendSelectedReserverSportTimeListener = (SendSelectedReserverSportTimeListener) activity;
    }

    @NonNull
    @Override
    public ReserveSportTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReserveSportTimeAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_reserve_sport_time, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReserveSportTimeAdapter.MyViewHolder holder, final int position) {
        String time = Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0])
                + " تا "
                + Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]);
        holder.checkTxt.setText(time);

        try {
            if (list.get(position).getPersonalIds().get(weekDays[chosenWeekDay]).toString().contains(new UserDetails(activity).getUserInfo().getString("personalId"))) {
                holder.checkContainer.setAlpha(.5f);
                holder.checkContainer.setEnabled(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.checkContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selected[position]) {
                    holder.checkContainer.setBackground(activity.getResources().getDrawable(R.drawable.background_checkbox_container_on));
                    holder.checkImg.setBackground(activity.getResources().getDrawable(R.drawable.background_check_box_on));
                    selectedList.add(list.get(position));
                    selected[position] = true;
                    sendSelectedReserverSportTimeListener.onSelectedSend(selectedList);
                } else {
                    holder.checkContainer.setBackground(activity.getResources().getDrawable(R.drawable.background_checkbox_container_off));
                    holder.checkImg.setBackground(activity.getResources().getDrawable(R.drawable.background_check_box_off));
                    Iterator<Sport> iter = selectedList.iterator();
                    while (iter.hasNext()) {
                        Sport p = iter.next();
                        if (p.getId() == list.get(position).getId()) iter.remove();
                    }
                    selected[position] = false;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView checkTxt;
        ImageView checkImg;
        RelativeLayout checkContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkTxt = itemView.findViewById(R.id.txt_check);
            checkImg = itemView.findViewById(R.id.img_check);
            checkContainer = itemView.findViewById(R.id.container_check);
        }
    }
}
