package com.example.absencemonitoring.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.NoticeSport;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.huri.jcal.JalaliCalendar;

public class NoticeSportAdapter extends RecyclerView.Adapter<NoticeSportAdapter.MyViewHolder> {

    private Activity activity;
    private List<Sport> list;
    private String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};

    public NoticeSportAdapter(Activity activity, List<Sport> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeSportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeSportAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_notice_sport, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoticeSportAdapter.MyViewHolder holder, int position) {
        int weekDay = 0;
        try {
            weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - Arrays.asList(weekDays).indexOf(list.get(position).getDate().getString("day"));
            holder.numberTxt.setText(Formating.englishDigitsToPersian(String.valueOf(12 - Integer.parseInt(list.get(position).getCapacity().getString("capacity")))));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - weekDay)).toString().split("-");


        handleSportType(list.get(position).getType(), holder.noticeSportItem, holder.iconImg, holder.typeTxt);

        holder.dateTxt.setText(Formating.englishDigitsToPersian(jalaliCalendar[0])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[1])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[2]));

        holder.startTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[0]));
        holder.endTxt.setText(Formating.englishDigitsToPersian(list.get(position).getTime().split("-")[1]));

    }






    @Override
    public int getItemCount() {
        return list.size();
    }





    private void handleSportType(String type, CardView item, ImageView icon, TextView text) {
        switch (type) {
            case "volleyball":
                item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_yellow));
                icon.setImageResource(R.drawable.ic_volleyball);
                text.setText("والیبال");
                break;
            case "football":
                item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_green));
                icon.setImageResource(R.drawable.ic_football);
                text.setText("فوتبال");
                break;
            case "swimming":
                item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_blue));
                icon.setImageResource(R.drawable.ic_swimming);
                text.setText("استخر");
                break;
        }
    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView noticeSportItem;
        ImageView iconImg;
        TextView typeTxt, dateTxt, startTxt, endTxt, numberTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeSportItem = itemView.findViewById(R.id.item_notice_furlough);
            iconImg = itemView.findViewById(R.id.img_icon);
            typeTxt = itemView.findViewById(R.id.txt_type);
            dateTxt = itemView.findViewById(R.id.txt_date);
            startTxt = itemView.findViewById(R.id.txt_start_time);
            endTxt = itemView.findViewById(R.id.txt_end_time);
            numberTxt = itemView.findViewById(R.id.txt_number);
        }
    }
}
