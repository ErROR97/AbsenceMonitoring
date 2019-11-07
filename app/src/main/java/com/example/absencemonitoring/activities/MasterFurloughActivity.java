package com.example.absencemonitoring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.fragments.NoticeFurloughFragment;
import com.example.absencemonitoring.instances.Furlough;

public class MasterFurloughActivity extends AppCompatActivity {

    Furlough furlough;
    TextView typeTxt, nameTxt, amountTypeTxt, amountTxt, dayOrHourTxt, startDateOrTimeLbl, startDateOrTimeTxt, endDateOrTimeLbl, endDateOrTimeTxt;
    TextView currentDateTxt, furloughCodeTxt, decriptionTxt;
    CardView acceptBtn, rejectBtn;

    String dayOrTime = "";
    String amount = "";
    RelativeLayout darkenBackground;
    RelativeLayout rejectDescriptionContainer;
    EditText rejectDescriptionEt;
    CardView sendRejectBtn, cancelRejectBtn;
    ProgressBar acceptProgressbar, rejectProgressbar;
    TextView acceptLbl, rejectLbl;

    ApiHandler apiHandler;
    UserDetails userDetails;


    private void init() {
        userDetails = new UserDetails(this);
        apiHandler = new ApiHandler(this);


        furlough = new Furlough();
        furlough.setName(getIntent().getStringExtra("fullName"));
        furlough.setPersonalIdemployee(getIntent().getStringExtra("personalIdemployee"));
        furlough.setLeaveType(getIntent().getStringExtra("leaveType"));
        furlough.setStartTime(getIntent().getStringExtra("startTime"));
        furlough.setTimeLeave(getIntent().getStringExtra("timeLeave"));
        furlough.setStartDate(getIntent().getStringExtra("startDate"));
        furlough.setDescriptionLeave(getIntent().getStringExtra("descriptionLeave"));
        furlough.setId(getIntent().getIntExtra("id", 0));
        furlough.setCurrentDate(getIntent().getStringExtra("currentDate"));

        amount = getIntent().getStringExtra("dayOrTime").split(" ")[0];
        dayOrTime = getIntent().getStringExtra("dayOrTime").split(" ")[1];


        typeTxt = findViewById(R.id.txt_type);
        nameTxt = findViewById(R.id.txt_name);
        amountTypeTxt = findViewById(R.id.txt_amount_type);
        amountTxt = findViewById(R.id.txt_amount);
        dayOrHourTxt = findViewById(R.id.txt_day_or_hour);
        startDateOrTimeLbl = findViewById(R.id.lbl_start_date_or_time);
        startDateOrTimeTxt = findViewById(R.id.txt_start_date_or_time);
        endDateOrTimeLbl = findViewById(R.id.lbl_end_date_or_time);
        endDateOrTimeTxt = findViewById(R.id.txt_end_date_or_time);
        currentDateTxt = findViewById(R.id.txt_current_date);
        furloughCodeTxt = findViewById(R.id.txt_furlough_code);
        decriptionTxt = findViewById(R.id.txt_description);
        acceptBtn = findViewById(R.id.btn_accept);
        rejectBtn = findViewById(R.id.btn_reject);

        acceptProgressbar = findViewById(R.id.progressbar_accept);
        rejectProgressbar = findViewById(R.id.progressbar_reject);
        acceptLbl = findViewById(R.id.lbl_accept);
        rejectLbl = findViewById(R.id.lbl_reject);

        darkenBackground = findViewById(R.id.darken_background);
        rejectDescriptionContainer = findViewById(R.id.container_reject_description);
        rejectDescriptionEt = findViewById(R.id.et_reject_description);
        sendRejectBtn = findViewById(R.id.btn_send_reject);
        cancelRejectBtn = findViewById(R.id.btn_cancel_reject);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_furlough);

        init();

        DateTime.calculateRemainingTime(furlough.getStartDate(), furlough.getStartTime(), furlough.getTimeLeave());

        currentDateTxt.setText(furlough.getCurrentDate());
        furloughCodeTxt.setText(String.valueOf(furlough.getId()));

        typeTxt.setText("درخواست مرخصی " + furlough.getLeaveType());
        amountTxt.setText(amount);
        decriptionTxt.setText("پیوست: " + furlough.getDescriptionLeave());

        nameTxt.setText(furlough.getName());
        if (dayOrTime.equals("روز")) {
            amountTypeTxt.setText("روزانه");
            dayOrHourTxt.setText("روز");
            endDateOrTimeLbl.setText("تا تاریخ");
            startDateOrTimeTxt.setText(furlough.getStartDate());
            endDateOrTimeTxt.setText(DateTime.calculateEndDate(furlough.getStartDate(), furlough.getTimeLeave()));
        } else {
            amountTypeTxt.setText("ساعتی");
            dayOrHourTxt.setText("ساعت");
            startDateOrTimeLbl.setText("از ساعت");
            endDateOrTimeLbl.setText("تا ساعت");
            startDateOrTimeTxt.setText(furlough.getStartTime().split(":")[1] + ":" + furlough.getStartTime().split(":")[2]);
            endDateOrTimeTxt.setText(DateTime.calculateEndTime(furlough.getStartTime(), furlough.getTimeLeave()));
        }

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptLbl.setVisibility(View.INVISIBLE);
                acceptProgressbar.setVisibility(View.VISIBLE);
                acceptBtn.setEnabled(false);
                rejectBtn.setEnabled(false);
                apiHandler.acceptRejectReqLeave(true,
                        furlough.getPersonalIdemployee(),
                        "9537063",
                        furlough.getLeaveType(),
                        "",
                        furlough.getCurrentDate(),
                        new ApiHandler.responseListenerAcceptRejectReqLeave() {
                            @Override
                            public void onRecived(String response) {
                                if (response.trim().equals("success")) {
                                    acceptBtn.setEnabled(true);
                                    rejectBtn.setEnabled(true);
                                    acceptLbl.setVisibility(View.VISIBLE);
                                    acceptProgressbar.setVisibility(View.INVISIBLE);
                                    setResult(4);
                                    finish();
                                }
                            }
                        });
            }
        });
        //:/تنمتمتم

        sendRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectLbl.setVisibility(View.INVISIBLE);
                rejectProgressbar.setVisibility(View.VISIBLE);
                cancelRejectBtn.setEnabled(false);
                sendRejectBtn.setEnabled(false);

                apiHandler.acceptRejectReqLeave(false,
                        furlough.getPersonalIdemployee(),
                        "9537063",
                        furlough.getLeaveType(),
                        rejectDescriptionEt.getText().toString(),
                        furlough.getCurrentDate(),
                        new ApiHandler.responseListenerAcceptRejectReqLeave() {
                            @Override
                            public void onRecived(String response) {
                                if (response.trim().equals("success")) {
                                    setResult(5);
                                    finish();
                                }
                            }
                        });
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptBtn.setEnabled(false);
                rejectBtn.setEnabled(false);
                darkenBackground.setVisibility(View.VISIBLE);
                rejectDescriptionContainer.setVisibility(View.VISIBLE);
            }
        });

        cancelRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptBtn.setEnabled(true);
                rejectBtn.setEnabled(true);
                darkenBackground.setVisibility(View.INVISIBLE);
                rejectDescriptionContainer.setVisibility(View.INVISIBLE);
                rejectDescriptionEt.setText("");
            }
        });



    }


}
