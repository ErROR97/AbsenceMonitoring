package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.SportlistDetailsAdapter;
import com.example.absencemonitoring.instances.SportlistDetails;
import com.example.absencemonitoring.utils.Formating;

import java.util.ArrayList;
import java.util.List;

public class SportlistDetailsActivity extends AppCompatActivity {
    RecyclerView rvSportlist;
    List<SportlistDetails> list;
    SportlistDetailsAdapter sportlistDetailsAdapter;
    String persons, type, time, capacity;
    RelativeLayout headerContainer;
    TextView dateTxt;
    ImageView iconImg;
    TextView startTimeTxt, endTimeTxt, capacityTxt;

    private void init() {
        rvSportlist = findViewById(R.id.rv_sportlist_details);
        headerContainer = findViewById(R.id.container_header);
        iconImg = findViewById(R.id.img_icon);
        startTimeTxt = findViewById(R.id.txt_start_time);
        endTimeTxt = findViewById(R.id.txt_end_time);
        capacityTxt = findViewById(R.id.txt_capacity);
        dateTxt = findViewById(R.id.txt_date);

        list = new ArrayList<>();

        persons = getIntent().getStringExtra("persons");
        persons = persons.substring(0, persons.length() - 1);
        type = getIntent().getStringExtra("type");
        time = getIntent().getStringExtra("time");
        capacity = getIntent().getStringExtra("capacity");

        dateTxt.setText(getIntent().getStringExtra("date"));

        for (int i = 0; i < persons.split("-").length; i++) {
            list.add(new SportlistDetails(persons.split("-")[i].split(">")[1], persons.split("-")[i].split(">")[0]));
        }

        sportlistDetailsAdapter = new SportlistDetailsAdapter(this, list);
        rvSportlist.setLayoutManager(new LinearLayoutManager(this));

        rvSportlist.setAdapter(sportlistDetailsAdapter);

        if (type.equals("volleyball")) {
            headerContainer.setBackgroundResource(R.color.yellow);
            iconImg.setImageResource(R.drawable.ic_volleyball);
            capacityTxt.setText(Formating.englishDigitsToPersian(String.valueOf(12 - Integer.parseInt(capacity))));
        } else if (type.equals("football")) {
            headerContainer.setBackgroundResource(R.color.light_green);
            iconImg.setImageResource(R.drawable.ic_football);
            capacityTxt.setText(Formating.englishDigitsToPersian(String.valueOf(12 - Integer.parseInt(capacity))));
        } else {
            headerContainer.setBackgroundResource(R.color.light_blue);
            iconImg.setImageResource(R.drawable.ic_swimming);
            capacityTxt.setText(Formating.englishDigitsToPersian(String.valueOf(50 - Integer.parseInt(capacity))));
        }

        startTimeTxt.setText(Formating.englishDigitsToPersian(time.split("-")[0]));
        endTimeTxt.setText(Formating.englishDigitsToPersian(time.split("-")[1]));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportlist_details);

        init();

    }
}
