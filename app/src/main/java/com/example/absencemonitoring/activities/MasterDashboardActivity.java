package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.fragments.ArchiveFurloughFragment;
import com.example.absencemonitoring.fragments.ArchiveSportFragment;
import com.example.absencemonitoring.fragments.ControlFragment;
import com.example.absencemonitoring.fragments.NoticeFurloughFragment;
import com.example.absencemonitoring.fragments.NoticeSportFragment;
import com.example.absencemonitoring.fragments.NoticeTransportFragment;

public class MasterDashboardActivity extends AppCompatActivity {

    CardView menuContainer;
    RelativeLayout requestContainer, profileContainer, noticeContainer, archiveContainer, controlingContainer;
    CardView requestDetailsContainer, noticeDetailsContainer, archiveDetailsContainer;
    TextView requestFurlougTxt, requestSportTxt, requestTransportTxt;
    TextView noticeFurloughTxt, noticeSportTxt, noticeTransportTxt;
    TextView archiveFurloughTxt, archiveSportTxt;
    String checkFragment = "";


    public void init() {

        menuContainer = findViewById(R.id.container_menu);
        menuContainer.bringToFront();

        profileContainer = findViewById(R.id.container_profile);
        requestContainer = findViewById(R.id.container_request);
        noticeContainer = findViewById(R.id.container_notice);
        archiveContainer = findViewById(R.id.container_archive);
        controlingContainer = findViewById(R.id.container_controling);


        requestDetailsContainer = findViewById(R.id.container_request_details);
        noticeDetailsContainer = findViewById(R.id.container_notice_details);
        archiveDetailsContainer = findViewById(R.id.container_archive_details);

        requestFurlougTxt = findViewById(R.id.txt_request_furough);
        requestSportTxt = findViewById(R.id.txt_request_sport);
        requestTransportTxt = findViewById(R.id.txt_request_transport);

        noticeFurloughTxt = findViewById(R.id.txt_notice_furough);
        noticeSportTxt = findViewById(R.id.txt_notice_sport);
        noticeTransportTxt = findViewById(R.id.txt_notice_transport);

        archiveFurloughTxt = findViewById(R.id.txt_archive_furlough);
        archiveSportTxt = findViewById(R.id.txt_archive_sport);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_dashboard);

        init();


        profileContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, ProfileActivity.class));
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

        controlingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkFragment.equals("control")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ControlFragment()).commit();
                    checkFragment = "control";
                }
            }
        });


        requestFurlougTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, EmployeeFurloughActivity.class));
                Log.i("shitman", "onClick: " + "its bullshit");
            }
        });


        requestSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, EmployeeSportReserveActivity.class));

            }
        });


        requestTransportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, EmployeeTransportationReserveActivity.class));
            }
        });


        noticeFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeFurlough")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeFurloughFragment()).commit();
                    checkFragment = "noticeFurlough";
                }
            }
        });


        noticeSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeSport")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeSportFragment()).commit();
                    checkFragment = "noticeSport";
                }
            }
        });

        noticeTransportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeTransport")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeTransportFragment()).commit();
                    checkFragment = "noticeTransport";
                }
            }
        });

        archiveFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("archiveFurlough")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ArchiveFurloughFragment()).commit();
                    checkFragment = "archiveFurlough";
                }
            }
        });

        archiveSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("archiveSport")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ArchiveSportFragment()).commit();
                    checkFragment = "archiveSport";
                }
            }
        });
    }
}
