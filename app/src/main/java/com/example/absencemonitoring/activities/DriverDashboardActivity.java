package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.TransportRequestAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Transport;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DriverDashboardActivity extends AppCompatActivity {
    TextView nameTxt;
    CardView morningBtn, afternoonBtn;
    RecyclerView transportRv;
    CardView logoutBtn;
    ProgressBar progressBar;

    ApiHandler apiHandler;
    UserDetails userDetails;
    List<Transport> morningList;
    List<Transport> afternoonList;

    TransportRequestAdapter transportRequestAdapter;


    private void init() {
        nameTxt = findViewById(R.id.txt_name);
        morningBtn = findViewById(R.id.btn_morning_shift);
        afternoonBtn = findViewById(R.id.btn_afternoon_shift);

        transportRv = findViewById(R.id.rv_transport_reqs);
        logoutBtn = findViewById(R.id.btn_logout);
        progressBar = findViewById(R.id.progressbar);

        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);
        morningList = new ArrayList<>();
        afternoonList = new ArrayList<>();

        transportRv.setLayoutManager(new LinearLayoutManager(this));

        try {
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        apiHandler.getTransport(new ApiHandler.ResponseListenerGetTransport() {
            @Override
            public void onRecieved(List<Transport> transportList) {
                for (int i = 0; i < transportList.size(); i++) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (transportList.get(i).getShift().equals("morning")) {
                        morningList.add(transportList.get(i));
                    } else {
                        afternoonList.add(transportList.get(i));
                    }
                }
                transportRequestAdapter = new TransportRequestAdapter( DriverDashboardActivity.this, morningList);
                transportRv.setAdapter(transportRequestAdapter);

            }

            @Override
            public void onMessage(String response) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        init();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetails.deleteUser();
                startActivity(new Intent(DriverDashboardActivity.this, LoginActivity.class));
                finish();
            }
        });



        morningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transportRequestAdapter = new TransportRequestAdapter( DriverDashboardActivity.this, morningList);
                transportRv.setAdapter(transportRequestAdapter);
                morningBtn.setAlpha(1);
                afternoonBtn.setAlpha(.5f);
            }
        });



        afternoonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transportRequestAdapter = new TransportRequestAdapter( DriverDashboardActivity.this, afternoonList);
                transportRv.setAdapter(transportRequestAdapter);
                afternoonBtn.setAlpha(1);
                morningBtn.setAlpha(.5f);
            }
        });
    }


    private void updateLists() {
        morningList = new ArrayList<>();
        afternoonList = new ArrayList<>();
        apiHandler.getTransport(new ApiHandler.ResponseListenerGetTransport() {
            @Override
            public void onRecieved(List<Transport> transportList) {
                for (int i = 0; i < transportList.size(); i++) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (transportList.get(i).getShift().equals("morning")) {
                        morningList.add(transportList.get(i));
                    } else {
                        afternoonList.add(transportList.get(i));
                    }
                }

            }

            @Override
            public void onMessage(String response) {

            }
        });
    }
}
