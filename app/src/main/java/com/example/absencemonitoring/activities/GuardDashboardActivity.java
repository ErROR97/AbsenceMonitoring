package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.GuardAdapter;
import com.example.absencemonitoring.instances.GuardList;

import java.util.ArrayList;
import java.util.List;

public class GuardDashboardActivity extends AppCompatActivity {
    RecyclerView guardRv;
    List<GuardList> list;
    GuardAdapter guardAdapter;


    private void init() {

        guardRv = findViewById(R.id.rv_guard);
        list = new ArrayList<>();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_dashboard);

        init();

        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "از موئد گذشته", "۰ ساعت", "به اتمام رسیده"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "در حال انجام", "۲ ساعت", "در حال انجام"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "در حال انجام", "۲ ساعت", "در حال انجام"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "ساعت شروع: ۱۱:۰۰", "۳ ساعت", "شروع نشده"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "ساعت شروع: ۱۱:۰۰", "۳ ساعت", "شروع نشده"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "ساعت شروع: ۱۱:۰۰", "۳ ساعت", "شروع نشده"));
        list.add(new GuardList("متین براهویی", "اداری", "۹۸/۰۸/۰۹", "ساعت شروع: ۱۱:۰۰", "۳ ساعت", "شروع نشده"));
        guardAdapter = new GuardAdapter(this, list);

        guardRv.setLayoutManager(new LinearLayoutManager(this));

        guardRv.setAdapter(guardAdapter);


    }
}
