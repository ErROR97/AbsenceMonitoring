package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.Utils.Formating;
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
    public void onBindViewHolder(@NonNull final NoticeFurloughAdapter.MyViewHolder holder, final int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.typeTxt.setText(list.get(position).getLeaveType());
        holder.amountTxt.setText(Formating.englishDigitsToPersian(DateTime.calculateAmountIsDayOrHour(list.get(position).getTimeLeave())));


        holder.noticeFurlough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MasterFurloughActivity.class);
                intent.putExtra("fullName", list.get(position).getName());
                intent.putExtra("personalIdemployee", list.get(position).getPersonalIdemployee());
                intent.putExtra("leaveType", list.get(position).getLeaveType());
                intent.putExtra("startTime", list.get(position).getStartTime());
                intent.putExtra("timeLeave", list.get(position).getTimeLeave());
                intent.putExtra("startDate", list.get(position).getStartDate());
                intent.putExtra("descriptionLeave", list.get(position).getDescriptionLeave());
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("currentDate", list.get(position).getCurrentDate());
                intent.putExtra("dayOrTime", DateTime.calculateAmountIsDayOrHour(list.get(position).getTimeLeave()));

                activity.startActivityForResult(intent, 4);
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
