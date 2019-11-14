package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.GuardAdapter;
import com.example.absencemonitoring.instances.GuardList;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GuardDashboardActivity extends AppCompatActivity {
    Activity activity;
    RecyclerView guardRv;
    List<GuardList> list;
    GuardAdapter guardAdapter;
    TextView nameTxt;
    UserDetails userDetails;
    ApiHandler apiHandler;
    CardView logoutCrd;


    private void init() {

        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);

        guardRv = findViewById(R.id.rv_guard);

        nameTxt = findViewById(R.id.txt_name);
        logoutCrd = findViewById(R.id.btn_logout);
        list = new ArrayList<>();

        try {
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_dashboard);
        activity = this;

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

        logoutCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardDashboardActivity.this, LoginActivity.class));
                finish();
                userDetails.deleteUser();
            }
        });


    }
}
