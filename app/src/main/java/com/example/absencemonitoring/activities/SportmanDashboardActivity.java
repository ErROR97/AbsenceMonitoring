package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.SportmanListAdapter;
import com.example.absencemonitoring.adapters.SportmanTimingAdapter;
import com.example.absencemonitoring.instances.SportmanList;
import com.example.absencemonitoring.instances.SportmanTiming;

import java.util.ArrayList;
import java.util.List;

public class SportmanDashboardActivity extends AppCompatActivity {
    RecyclerView sportmanListRv, sportmanTimingRv;
    List<SportmanList> sportmanList;
    List<SportmanTiming> sportmanTiming;
    SportmanListAdapter sportmanListAdapter;
    SportmanTimingAdapter sportmanTimingAdapter;
    TextView txtList, txtTiming;
    CardView logoutBtn;

    public void init() {
        sportmanListRv = findViewById(R.id.rv_sportman_list);
        sportmanTimingRv = findViewById(R.id.rv_sportman_timing);

        txtList = findViewById(R.id.txt_list);
        txtTiming = findViewById(R.id.txt_timing);
        sportmanList = new ArrayList<>();
        sportmanTiming = new ArrayList<>();

        logoutBtn = findViewById(R.id.btn_logout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportman_dashboard);

        init();

        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("فوتسال", "۲۳:۰۰", "۰۱:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));

        txtList.setTextColor(getResources().getColor(R.color.red));
        sportmanListAdapter = new SportmanListAdapter(this, sportmanList);
        sportmanListRv.setLayoutManager(new LinearLayoutManager(this));
        sportmanListRv.setAdapter(sportmanListAdapter);


        sportmanTiming.add(new SportmanTiming("وقت اول", "۱۸:۰۰", "۲۰:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت دوم", "۲۰:۰۰", "۲۲:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت سوم", "۲۲:۰۰", "۰۰:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت چهارم", "۰۰:۰۰", "۲:۰۰"));

        sportmanTimingAdapter = new SportmanTimingAdapter(this, sportmanTiming);
        sportmanTimingRv.setLayoutManager(new LinearLayoutManager(this));
        sportmanTimingRv.setAdapter(sportmanTimingAdapter);

        txtList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportmanListRv.setVisibility(View.VISIBLE);
                sportmanTimingRv.setVisibility(View.INVISIBLE);
                txtList.setTextColor(getResources().getColor(R.color.red));
                txtTiming.setTextColor(getResources().getColor(R.color.light_yellow));
            }
        });

        txtTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportmanListRv.setVisibility(View.INVISIBLE);
                sportmanTimingRv.setVisibility(View.VISIBLE);
                txtTiming.setTextColor(getResources().getColor(R.color.red));
                txtList.setTextColor(getResources().getColor(R.color.light_yellow));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SportmanDashboardActivity.this, LoginActivity.class));
            }
        });


    }
}
