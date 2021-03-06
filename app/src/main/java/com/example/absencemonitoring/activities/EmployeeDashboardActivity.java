package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.fragments.HomeFragment;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.fragments.ArchiveFurloughFragment;
import com.example.absencemonitoring.fragments.NoticeFurloughFragment;
import com.example.absencemonitoring.fragments.NoticeSportFragment;

import org.json.JSONException;

public class EmployeeDashboardActivity extends AppCompatActivity {

    Activity activity;
    CardView menuContainer;
    RelativeLayout homeContainer, requestContainer, profileContainer, noticeContainer, archiveContainer, logoutContainer;
    CardView requestDetailsContainer, noticeDetailsContainer, archiveDetailsContainer;
    TextView requestFurlougTxt, requestSportTxt, requestTransportationTxt;
    TextView noticeFurloughTxt, noticeSportTxt;
    TextView archiveFurloughTxt;
    TextView homeTxt, noticeTxt, archiveTxt, controlTxt;
    ImageView homeImg, noticeImg, archiveImg, controlImg;
    String checkFragment = "";
    TextView previousSelectedTxt;
    ImageView previousSelectedImg;
    int previousSelectedDrawable;


    TextView nameTxt, roleTxt;
    UserDetails userDetails;
    ApiHandler apiHandler;


    public void init() {

        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);

        menuContainer = findViewById(R.id.container_menu);
        menuContainer.bringToFront();

        homeContainer = findViewById(R.id.container_home);
        profileContainer = findViewById(R.id.container_profile);
        requestContainer = findViewById(R.id.container_request);
        noticeContainer = findViewById(R.id.container_notice);
        archiveContainer = findViewById(R.id.container_archive);
        logoutContainer = findViewById(R.id.container_logout);


        requestDetailsContainer = findViewById(R.id.container_request_details);
        noticeDetailsContainer = findViewById(R.id.container_notice_details);
        archiveDetailsContainer = findViewById(R.id.container_archive_details);

        requestFurlougTxt = findViewById(R.id.txt_request_furough);
        requestSportTxt = findViewById(R.id.txt_request_sport);
        requestTransportationTxt = findViewById(R.id.txt_request_transportation);

        noticeFurloughTxt = findViewById(R.id.txt_notice_furough);
        noticeSportTxt = findViewById(R.id.txt_notice_sport);

        archiveFurloughTxt = findViewById(R.id.txt_archive_furlough);

        nameTxt = findViewById(R.id.txt_name);
        roleTxt = findViewById(R.id.txt_role);
        homeTxt = findViewById(R.id.txt_home);
        noticeTxt = findViewById(R.id.txt_notice);
        archiveTxt = findViewById(R.id.txt_archive);
        controlTxt = findViewById(R.id.txt_controling);

        homeImg = findViewById(R.id.img_home);
        noticeImg = findViewById(R.id.img_notice);
        archiveImg = findViewById(R.id.img_archive);
        controlImg = findViewById(R.id.img_controling);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).commit();

        try {
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
            String role = userDetails.getUserInfo().getString("role");
            if (role.trim().equals("employee"))
                roleTxt.setText("کارمند");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        activity = this;

        init();

        homeTxt.setTextColor(getResources().getColor(R.color.red));
        homeImg.setImageResource(R.drawable.ic_home_selected);
        previousSelectedTxt = homeTxt;
        previousSelectedImg = homeImg;
        previousSelectedDrawable = R.drawable.ic_home;



        homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("homeFragment")) {
                    checkFragment = "homeFragment";

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).commit();

                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);


                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    homeTxt.setTextColor(getResources().getColor(R.color.red));
                    homeImg.setImageResource(R.drawable.ic_home_selected);
                    previousSelectedTxt = homeTxt;
                    previousSelectedImg = homeImg;
                    previousSelectedDrawable = R.drawable.ic_home;
                }
            }
        });

        profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeDashboardActivity.this, ProfileActivity.class));
            }
        });



        requestContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestDetailsContainer.getVisibility() == View.INVISIBLE) {
                    requestDetailsContainer.setVisibility(View.VISIBLE);
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);
                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                } else {
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                }
            }
        });



        noticeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noticeDetailsContainer.getVisibility() == View.INVISIBLE) {
                    noticeDetailsContainer.setVisibility(View.VISIBLE);
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                } else {
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);
                }
            }
        });



        archiveContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (archiveDetailsContainer.getVisibility() == View.INVISIBLE) {
                    archiveDetailsContainer.setVisibility(View.VISIBLE);
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);
                } else {
                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                }
            }
        });



        logoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetails.deleteUser();
                startActivity(new Intent(EmployeeDashboardActivity.this, LoginActivity.class));
                finish();
            }
        });



        requestFurlougTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeDashboardActivity.this, EmployeeFurloughActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });



        requestSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeDashboardActivity.this, SportReserveActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });


        requestTransportationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeDashboardActivity.this, TransportReserveActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });


        noticeFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeFurlough")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeFurloughFragment()).commit();
                    checkFragment = "noticeFurlough";

                    noticeDetailsContainer.setVisibility(View.INVISIBLE);

                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    noticeTxt.setTextColor(getResources().getColor(R.color.red));
                    noticeImg.setImageResource(R.drawable.ic_notification_selected);

                    previousSelectedTxt = noticeTxt;
                    previousSelectedImg = noticeImg;
                    previousSelectedDrawable = R.drawable.ic_notification;

                }
            }
        });


        noticeSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeSport")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeSportFragment()).commit();
                    checkFragment = "noticeSport";

                    noticeDetailsContainer.setVisibility(View.INVISIBLE);

                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    noticeTxt.setTextColor(getResources().getColor(R.color.red));
                    noticeImg.setImageResource(R.drawable.ic_notification_selected);

                    previousSelectedTxt = noticeTxt;
                    previousSelectedImg = noticeImg;
                    previousSelectedDrawable = R.drawable.ic_notification;
                }
            }
        });


        archiveFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("archiveFurlough")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ArchiveFurloughFragment()).commit();
                    checkFragment = "archiveFurlough";

                    archiveDetailsContainer.setVisibility(View.INVISIBLE);

                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    archiveTxt.setTextColor(getResources().getColor(R.color.red));
                    archiveImg.setImageResource(R.drawable.ic_archive_selected);

                    previousSelectedTxt = archiveTxt;
                    previousSelectedImg = archiveImg;
                    previousSelectedDrawable = R.drawable.ic_archive;
                }
            }
        });



    }
}
