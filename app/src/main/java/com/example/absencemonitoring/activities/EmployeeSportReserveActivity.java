package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ReserveSportTimeAdapter;
import com.example.absencemonitoring.instances.ReserveSportTime;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSportReserveActivity extends AppCompatActivity {
    ImageView backImg;
    RecyclerView reserveSportTimeRv;
    List<ReserveSportTime> list;
    ReserveSportTimeAdapter reserveSportTimeAdapter;

    private void init() {

        backImg = findViewById(R.id.img_back);
        reserveSportTimeRv = findViewById(R.id.rv_reserve_sport_time);
        list = new ArrayList<>();

        list.add(new ReserveSportTime("۱۶ تا ۱۸", R.drawable.background_check_box_on));
        list.add(new ReserveSportTime("۱۶ تا ۱۸", R.drawable.background_check_box_on));
        list.add(new ReserveSportTime("۱۶ تا ۱۸", R.drawable.background_check_box_on));
        list.add(new ReserveSportTime("۱۶ تا ۱۸", R.drawable.background_check_box_on));

        reserveSportTimeAdapter = new ReserveSportTimeAdapter(this, list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_sport_reserve);

        init();

        reserveSportTimeRv.setLayoutManager(new GridLayoutManager(this, 2));
        reserveSportTimeRv.setAdapter(reserveSportTimeAdapter);
        


        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
