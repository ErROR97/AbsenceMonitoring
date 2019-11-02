package com.example.absencemonitoring.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.instances.SportlistDetails;
import com.example.absencemonitoring.instances.SportmanList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportlistDetailsAdapter extends RecyclerView.Adapter<SportlistDetailsAdapter.MyViewHolder> {

    Activity activity;
    List<SportlistDetails> list;

    public SportlistDetailsAdapter(Activity activity, List<SportlistDetails> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SportlistDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportlistDetailsAdapter.MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sportlist_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SportlistDetailsAdapter.MyViewHolder holder, int position) {
        holder.nameTxt.setText(list.get(position).getName());
        holder.idTxt.setText(list.get(position).getPersonalId());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, idTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.txt_name);
            idTxt = itemView.findViewById(R.id.txt_id);
        }
    }
}
