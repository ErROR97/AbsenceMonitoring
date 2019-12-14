package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.adapters.ControlAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.GuardAdapter;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.instances.GuardList;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GuardDashboardActivity extends AppCompatActivity {
    Activity activity;
    RecyclerView rv;
    ControlAdapter controlAdapter;
    TextView nameTxt;
    CardView logoutCrd;
    ProgressBar progressBar;
    RelativeLayout nothingFoundContainer;
    SwipeRefreshLayout swipeRefreshLayout;


    private Handler handler;
    private Runnable runnable;
    UserDetails userDetails;
    ApiHandler apiHandler;


    private void init() {

        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);
        handler = new Handler();


        nameTxt = findViewById(R.id.txt_name);
        logoutCrd = findViewById(R.id.btn_logout);

        rv = findViewById(R.id.rv_guard_furlough);
        progressBar = findViewById(R.id.progressbar);
        nothingFoundContainer = findViewById(R.id.container_nothing_found);
        swipeRefreshLayout = findViewById(R.id.swipe_guard);


        try {
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHandler.getControlReqLeave("9537063", new ApiHandler.ResponseListenerControlReqLeave() {
            @Override
            public void onRevived(List<Furlough> controlLeaveList) {
                progressBar.setVisibility(View.INVISIBLE);

                if (controlLeaveList.size() == 0) {
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                } else {
                    nothingFoundContainer.setVisibility(View.INVISIBLE);
                }

                Collections.sort(controlLeaveList, new Comparator<Furlough>() {
                    @Override
                    public int compare(Furlough abc1, Furlough abc2) {
                        return Integer.compare(abc2.getStarted(), abc1.getStarted());
                    }
                });

                controlAdapter = new ControlAdapter(GuardDashboardActivity.this, controlLeaveList);
                rv.setLayoutManager(new LinearLayoutManager(GuardDashboardActivity.this));
                rv.setAdapter(controlAdapter);
                updateListRemainingTime();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_dashboard);
        activity = this;

        init();

        logoutCrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardDashboardActivity.this, LoginActivity.class));
                finish();
                userDetails.deleteUser();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                apiHandler.getControlReqLeave("9537063", new ApiHandler.ResponseListenerControlReqLeave() {
                    @Override
                    public void onRevived(List<Furlough> controlLeaveList) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (controlLeaveList.size() == 0) {
                            nothingFoundContainer.setVisibility(View.VISIBLE);
                        } else {
                            nothingFoundContainer.setVisibility(View.INVISIBLE);
                        }

                        Collections.sort(controlLeaveList, new Comparator<Furlough>() {
                            @Override
                            public int compare(Furlough abc1, Furlough abc2) {
                                return Integer.compare(abc2.getStarted(), abc1.getStarted());
                            }
                        });

                        controlAdapter = new ControlAdapter(GuardDashboardActivity.this, controlLeaveList);
                        rv.setLayoutManager(new LinearLayoutManager(GuardDashboardActivity.this));
                        rv.setAdapter(controlAdapter);
                        updateListRemainingTime();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });



    }


    private void updateListRemainingTime() {

        runnable = new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int secs = calendar.get(Calendar.SECOND);

                if (secs == 0) {
                    controlAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(runnable, 1000);
            }
        };
        runnable.run();
    }
}
