package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.instances.FurloughArchive;
import com.example.absencemonitoring.utils.Formating;

import org.json.JSONException;

import java.text.Format;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ImageView backImg;
    TextView fullnameTxt, roleTxt, personalIdTxt, departmentTxt;

    TextView spentFurloughTxt, totalFurloughTxt;

    CardView changePasswordBtn;



    UserDetails userDetails;
    ApiHandler apiHandler;


    String personalId;

    private void init() {
        backImg = findViewById(R.id.img_back);

        fullnameTxt = findViewById(R.id.txt_fullname);
        roleTxt = findViewById(R.id.txt_role);
        personalIdTxt = findViewById(R.id.txt_personal_id);
        departmentTxt = findViewById(R.id.txt_department);

        spentFurloughTxt = findViewById(R.id.txt_spent_furlough);
        totalFurloughTxt = findViewById(R.id.txt_total_furlough);

        changePasswordBtn = findViewById(R.id.btn_change_password);



        userDetails = new UserDetails(this);
        apiHandler = new ApiHandler(this);


        try {
            fullnameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
            roleTxt.setText(determineRoleInPersian(userDetails.getUserInfo().getString("role")));
            personalId = userDetails.getUserInfo().getString("personalId");
            personalIdTxt.setText(Formating.englishDigitsToPersian(personalId));
            departmentTxt.setText(userDetails.getUserInfo().getString("department"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        obtainSpentFurlough();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();



        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

            }
        });
    }





    private String determineRoleInPersian(String role) {
        String persianRole;
        switch (role) {
            case "master":
                persianRole =  "مدیر";
                break;
            case "employee":
                persianRole = "کارمند";
                break;
            default:
                persianRole = "نا مشخصی";
        }
        return persianRole;
    }





    private void obtainSpentFurlough() {
        final float[] totalTime = new float[1];
        apiHandler.getArchiveEmployee(personalId, new ApiHandler.ResponseListenerArchiveReqLeaveEmployee() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onRevived(List<Furlough> archiveReqLeaveEmployeeList) {
                for (int i = 0; i < archiveReqLeaveEmployeeList.size(); i++) {
                    totalTime[0] += TimeToMinCalculator(archiveReqLeaveEmployeeList.get(i).getTimeLeave());
                }
                spentFurloughTxt.setText(Formating.englishDigitsToPersian(String.format("%.1f", 30 - (totalTime[0] / 1440.0))));
            }

            @Override
            public void onMessage(String error) {
                Log.i("alright", "onMessage: " + error);
            }
        });
    }





    private float TimeToMinCalculator(String amountTime) {
        int mins = Integer.parseInt(amountTime.split(":")[2]);
        int hours = Integer.parseInt(amountTime.split(":")[1]);
        int days = Integer.parseInt(amountTime.split(":")[0]);


        return mins + hours * 60 + days * 1440;
    }
}
