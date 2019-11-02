package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.SportlistDetailsAdapter;
import com.example.absencemonitoring.instances.SportlistDetails;
import com.example.absencemonitoring.instances.SportmanList;

import java.util.ArrayList;
import java.util.List;

public class SportlistDetailsActivity extends AppCompatActivity {
    RecyclerView rvSportlist;
    List<SportlistDetails> list;
    SportlistDetailsAdapter sportlistDetailsAdapter;

    private void init() {
        rvSportlist = findViewById(R.id.rv_sportlist_details);
        list = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportlist_details);

        init();

        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));
        list.add(new SportlistDetails("متین براهویی", "۹۵۲۵۴۶۳"));

        sportlistDetailsAdapter = new SportlistDetailsAdapter(this, list);
        rvSportlist.setLayoutManager(new LinearLayoutManager(this));

        rvSportlist.setAdapter(sportlistDetailsAdapter);

    }
}
