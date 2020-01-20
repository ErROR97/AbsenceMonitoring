package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.instances.Transport;
import com.example.absencemonitoring.utils.DateTime;
import com.example.absencemonitoring.utils.Formating;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.huri.jcal.JalaliCalendar;

public class TransportRequestAdapter extends RecyclerView.Adapter<TransportRequestAdapter.MyViewHolder> {

    Activity activity;
    List<Transport> list;
    final String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};
    private final String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    private int currentDayOfWeek;

    public TransportRequestAdapter(Activity activity, List<Transport> list) {
        this.activity = activity;
        this.list = list;
        currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }


    @NonNull
    @Override
    public TransportRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransportRequestAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_transport_request, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull TransportRequestAdapter.MyViewHolder holder, int position) {
        if (list.get(position).getShift().equals("morning")) {
            holder.item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_blue));
        } else {
            holder.item.setCardBackgroundColor(activity.getResources().getColor(R.color.light_orange));
        }
        holder.dateTxt.setText(getDateFromWeekDay(Arrays.asList(weekDays).indexOf(list.get(position).getDate())));
        holder.nameTxt.setText(list.get(position).getName());
        holder.personalIdTxt.setText(Formating.englishDigitsToPersian(list.get(position).getPersonalId()));
        holder.addressTxt.setText("آدرس: " + list.get(position).getAddress());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }




    public String getDateFromWeekDay(int i) {
        int diffrence = i - currentDayOfWeek;
        String[] jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + diffrence)).toString().split("-");

        return persianWeekDays[i]
                + " "
                + Formating.englishDigitsToPersian(jalaliCalendar[0])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[1])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[2]);
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView item;
        TextView dateTxt, nameTxt, personalIdTxt, addressTxt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            dateTxt = itemView.findViewById(R.id.txt_date);
            nameTxt = itemView.findViewById(R.id.txt_name);
            personalIdTxt = itemView.findViewById(R.id.txt_personal_id);
            addressTxt = itemView.findViewById(R.id.txt_address);
        }
    }
}
