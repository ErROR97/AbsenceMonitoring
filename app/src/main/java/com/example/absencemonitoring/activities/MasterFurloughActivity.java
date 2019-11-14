package com.example.absencemonitoring.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.Utils.CustomTypefaceSpan;
import com.example.absencemonitoring.Utils.DateTime;
import com.example.absencemonitoring.Utils.Formating;
import com.example.absencemonitoring.instances.Furlough;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MasterFurloughActivity extends AppCompatActivity {

    Furlough furlough;
    TextView nameTxt, typeTxt;
    TextView currentDateTxt, furloughCodeTxt, decriptionTxt;
    CardView acceptBtn, rejectBtn;

    String dayOrTime = "";
    String amount = "";
    RelativeLayout darkenBackground;
    CardView rejectDescriptionContainer;
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
        Log.i("boz", "init: "+furlough.getDescriptionLeave());

        amount = getIntent().getStringExtra("dayOrTime").split(" ")[0];
        dayOrTime = getIntent().getStringExtra("dayOrTime").split(" ")[1];


        typeTxt = findViewById(R.id.txt_type);
        nameTxt = findViewById(R.id.txt_name);
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

        currentDateTxt.setText(Formating.englishDigitsToPersian(furlough.getCurrentDate()));
        furloughCodeTxt.setText(Formating.englishDigitsToPersian(String.valueOf(furlough.getId())));

        typeTxt.setText("درخواست مرخصی " + furlough.getLeaveType());
        decriptionTxt.setText("پیوست: " + furlough.getDescriptionLeave());

        if (dayOrTime.equals("روز")) {
            String firstWord = "   احتراما اینجانب ";
            String secondWord =furlough.getName();
            String thirdWord = " مشمول در واحد ";
            String fourthWord = "حراست";
            String fifthWord = " درخواست مرخصی ";
            String sixthWord = "روزانه";
            String seventhWord = " به مدت ";
            String eighthWord = Formating.englishDigitsToPersian(amount) + " روز";
            String ninthWord = " از تاریخ ";
            String tenthWord = Formating.englishDigitsToPersian(furlough.getStartDate());
            String eleventhWord = " تا تاریخ ";
            String twelfthWord = Formating.englishDigitsToPersian(DateTime.calculateEndDate(furlough.getStartDate(), furlough.getTimeLeave()));
            String thirteenthWord = " را دارم. لذا خواهشمند است در صورت امکان مساعدت لازم مبذول فرمائید.";

            int tillFirstWord = firstWord.length();
            int tillSecondWord = firstWord.length() + secondWord.length();
            int tillThirdWord = firstWord.length() + secondWord.length() + thirdWord.length();
            int tillFourthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length();
            int tillFifthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length();
            int tillSixthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length();
            int tillSeventhWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length();
            int tillEighthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length();
            int tillNinthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length();
            int tillTenthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length();
            int tillEleventhWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length();
            int tillTwelfthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length() + twelfthWord.length();
            int tillThirteenthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length() + twelfthWord.length() + thirteenthWord.length();

            SpannableStringBuilder spannable = new SpannableStringBuilder(firstWord + secondWord + thirdWord + fourthWord + fifthWord + sixthWord + seventhWord + eighthWord + ninthWord + tenthWord + eleventhWord + twelfthWord + thirteenthWord);

            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), 0, tillFirstWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillFirstWord, tillSecondWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillSecondWord, tillThirdWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillThirdWord, tillFourthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillFourthWord, tillFifthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillFifthWord, tillSixthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillSixthWord, tillSeventhWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillSeventhWord, tillEighthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillEighthWord, tillNinthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillNinthWord, tillTenthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillTenthWord, tillEleventhWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillEleventhWord, tillTwelfthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillTwelfthWord, tillThirteenthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            nameTxt.setText(spannable);
        } else {
            String firstWord = "   احتراما اینجانب ";
            String secondWord =furlough.getName();
            String thirdWord = " مشمول در واحد ";
            String fourthWord = "حراست";
            String fifthWord = " درخواست مرخصی ";
            String sixthWord = "ساعتی";
            String seventhWord = " به مدت ";
            String eighthWord = Formating.englishDigitsToPersian(amount) + " ساعت";
            String ninthWord = " از ساعت ";
            String tenthWord = Formating.englishDigitsToPersian(furlough.getStartTime().split(":")[1] + ":" + furlough.getStartTime().split(":")[2]);
            String eleventhWord = " تا ساعت ";
            String twelfthWord = Formating.englishDigitsToPersian(DateTime.calculateEndTime(furlough.getStartTime(), furlough.getTimeLeave()));
            String thirteenthWord = " را دارم. لذا خواهشمند است در صورت امکان مساعدت لازم مبذول فرمائید.";

            int tillFirstWord = firstWord.length();
            int tillSecondWord = firstWord.length() + secondWord.length();
            int tillThirdWord = firstWord.length() + secondWord.length() + thirdWord.length();
            int tillFourthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length();
            int tillFifthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length();
            int tillSixthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length();
            int tillSeventhWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length();
            int tillEighthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length();
            int tillNinthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length();
            int tillTenthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length();
            int tillEleventhWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length();
            int tillTwelfthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length() + twelfthWord.length();
            int tillThirteenthWord = firstWord.length() + secondWord.length() + thirdWord.length() + fourthWord.length() + fifthWord.length() + sixthWord.length() + seventhWord.length() + eighthWord.length() + ninthWord.length() + tenthWord.length() + eleventhWord.length() + twelfthWord.length() + thirteenthWord.length();

            SpannableStringBuilder spannable = new SpannableStringBuilder(firstWord + secondWord + thirdWord + fourthWord + fifthWord + sixthWord + seventhWord + eighthWord + ninthWord + tenthWord + eleventhWord + twelfthWord + thirteenthWord);

            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), 0, tillFirstWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillFirstWord, tillSecondWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillSecondWord, tillThirdWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillThirdWord, tillFourthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillFourthWord, tillFifthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillFifthWord, tillSixthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillSixthWord, tillSeventhWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillSeventhWord, tillEighthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillEighthWord, tillNinthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillNinthWord, tillTenthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillTenthWord, tillEleventhWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_medium.ttf")), tillEleventhWord, tillTwelfthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getResources().getAssets(), "fonts/iransansmobile_light.ttf")), tillTwelfthWord, tillThirteenthWord, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            nameTxt.setText(spannable);
        }

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptLbl.setVisibility(View.INVISIBLE);
                acceptProgressbar.setVisibility(View.VISIBLE);
                acceptBtn.setEnabled(false);
                rejectBtn.setEnabled(false);
                Log.i("khar", "onClick: "+furlough.getDescriptionLeave());
                try {
                    apiHandler.acceptRejectReqLeave(true,
                            furlough.getPersonalIdemployee(),
                            userDetails.getUserInfo().getString("personalIdmaster"),
                            furlough.getLeaveType(),
                            "تائید شد",
                            furlough.getDescriptionLeave(),
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectLbl.setVisibility(View.INVISIBLE);
                rejectProgressbar.setVisibility(View.VISIBLE);
                cancelRejectBtn.setEnabled(false);
                sendRejectBtn.setEnabled(false);

                try {
                    apiHandler.acceptRejectReqLeave(false,
                            furlough.getPersonalIdemployee(),
                            userDetails.getUserInfo().getString("personalIdmaster"),
                            furlough.getLeaveType(),
                            rejectDescriptionEt.getText().toString(),
                            furlough.getDescriptionLeave(),
                            furlough.getCurrentDate(),
                            new ApiHandler.responseListenerAcceptRejectReqLeave() {
                                @Override
                                public void onRecived(String response) {
                                    if (response.trim().equals("success")) {
                                        setResult(5);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
