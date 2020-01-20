package com.example.absencemonitoring.activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ir.huri.jcal.JalaliCalendar;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.SportmanListAdapter;
import com.example.absencemonitoring.adapters.SportmanTimingAdapter;
import com.example.absencemonitoring.fragments.addReserveSportTimeFragment;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.ApiHandler.ResponseListenerGetSport;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.DeleteSportTimeListener;
import com.example.absencemonitoring.interfaces.DismissShadowListener;
import com.example.absencemonitoring.utils.Formating;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SportmanDashboardActivity extends AppCompatActivity implements DismissShadowListener, DeleteSportTimeListener {

    RelativeLayout buttonsAndDateContainer;
    TextView txtList, txtTiming;
    CardView volleyballContainer, footballContainer, swimmingContainer, logoutBtn, addTimeBtn;
    RecyclerView sportmanListRv, sportmanTimingRv;
    CardView shadowContainer, weekContainer;


    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout nothingFoundContainer;
    ProgressBar progressBar;

    RelativeLayout deleteTimeContainer;
    TextView confirmDeleteBtn, cancelDeleteBtn;



    UserDetails userDetails;
    ApiHandler apiHandler;
    ScaleAnimation hideAnimation, hideAnimation1, showAnimation, showAnimation1;



    SportmanTimingAdapter sportmanTimingAdapter;
    SportmanListAdapter sportmanListAdapter;



    List<Sport> volleyballListTiming, footballListTiming, swimmingListTiming;
    List<Sport> volleyballList, footballList, swimmingList;

    boolean isOnInsertRv = false, logoutIsHidden = true, addBtnIsHidden = false;
    String[] jalaliCalendar;
    String type = "volleyball";
    String[] weekDays =  {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    String[] persianWeekDays = {"شنبه", "یکشنبه", "دوشنبه", "سهشنبه", "چهارشنبه", "پنجشنبه", "جمعه"};
    int weekDay;
    int deleteId;
    String deleteDate;





    @SuppressLint("SetTextI18n")
    public void init() {


        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);

        volleyballListTiming = new ArrayList();
        footballListTiming = new ArrayList();
        swimmingListTiming = new ArrayList();

        volleyballList = new ArrayList<>();
        footballList = new ArrayList<>();
        swimmingList = new ArrayList<>();

        jalaliCalendar = new JalaliCalendar(new GregorianCalendar()).toString().split("-");
        weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);


        volleyballContainer = findViewById(R.id.container_volleyball);
        footballContainer = findViewById(R.id.container_football);
        swimmingContainer = findViewById(R.id.container_swimming);
        footballContainer.setAlpha(0.5f);
        swimmingContainer.setAlpha(0.5f);
        sportmanListRv =  findViewById(R.id.rv_sportman_list);
        sportmanTimingRv =  findViewById(R.id.rv_sportman_timing);
        txtList =  findViewById(R.id.txt_list);
        txtTiming =  findViewById(R.id.txt_timing);

        addTimeBtn =  findViewById(R.id.btn_add_time);
        logoutBtn =  findViewById(R.id.btn_logout);

        weekContainer = findViewById(R.id.container_week);
        shadowContainer = findViewById(R.id.container_dialog_focus);


        nothingFoundContainer = findViewById(R.id.container_nothing_found);
        progressBar = findViewById(R.id.progressbar);

        deleteTimeContainer = findViewById(R.id.container_delete_time);
        confirmDeleteBtn = findViewById(R.id.btn_confirm_delete);
        cancelDeleteBtn = findViewById(R.id.btn_cancel_delete);

        sportmanListRv.setLayoutManager(new LinearLayoutManager(this));
        sportmanTimingRv.setLayoutManager(new LinearLayoutManager(this));

        sportmanListRv.setVisibility(View.VISIBLE);
        sportmanTimingRv.setVisibility(View.INVISIBLE);

        getAllTimes();

        buttonsAndDateContainer =  findViewById(R.id.container_buttons_and_date);
        swipeRefreshLayout =  findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        showAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        showAnimation.setDuration(200);
        showAnimation.setFillAfter(true);

        showAnimation1 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        showAnimation1.setDuration(200);
        showAnimation1.setFillAfter(true);

        hideAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        hideAnimation.setDuration(200);
        hideAnimation.setFillAfter(true);

        hideAnimation1 = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        hideAnimation1.setDuration(200);
        hideAnimation1.setFillAfter(true);

    }





    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportman_dashboard);
        init();

        txtList.setTextColor(getResources().getColor(R.color.red));

        txtList.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isOnInsertRv = false;
                sportmanListRv.setVisibility(View.VISIBLE);
                sportmanTimingRv.setVisibility(View.INVISIBLE);
                txtList.setTextColor(getResources().getColor(R.color.red));
                txtTiming.setTextColor(getResources().getColor(R.color.light_yellow));
                getAllTimes();

                if (addBtnIsHidden) {
                    addTimeBtn.startAnimation(hideAnimation1);
                    addBtnIsHidden = false;
                }
            }
        });



        txtTiming.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                isOnInsertRv = true;
                sportmanListRv.setVisibility(View.INVISIBLE);
                sportmanTimingRv.setVisibility(View.VISIBLE);
                txtTiming.setTextColor(getResources().getColor(R.color.red));
                txtList.setTextColor(getResources().getColor(R.color.light_yellow));
                getAllTimes();

                if (!addBtnIsHidden) {
                    addTimeBtn.startAnimation(showAnimation1);
                    addBtnIsHidden = true;
                }
            }
        });




        logoutBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SportmanDashboardActivity.this, LoginActivity.class));
                finish();
                userDetails.deleteUser();
            }
        });



        addTimeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, new addReserveSportTimeFragment()).commit();
                shadowContainer.setVisibility(View.VISIBLE);
                shadowContainer.setClickable(true);
            }
        });



        sportmanListRv.addOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && logoutIsHidden) {
                    logoutBtn.startAnimation(hideAnimation);
                    logoutIsHidden = false;
                } else if (dy < 0 && !logoutIsHidden) {
                    logoutBtn.startAnimation(showAnimation);
                    logoutIsHidden = true;
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    if (buttonsAndDateContainer.getElevation() == 50.0f) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 50, 0);
                        anim.setDuration(200);
                        anim.start();
                    }

                } else if (buttonsAndDateContainer.getElevation() == 0.0f) {
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 0, 50);
                    anim2.setDuration(200);
                    anim2.start();
                }
            }
        });



        sportmanTimingRv.addOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && logoutIsHidden) {
                    logoutBtn.startAnimation(hideAnimation);
                    addTimeBtn.startAnimation(hideAnimation1);
                    logoutIsHidden = false;
                } else if (dy < 0 && !logoutIsHidden) {
                    logoutBtn.startAnimation(showAnimation);
                    addTimeBtn.startAnimation(showAnimation1);
                    logoutIsHidden = true;
                    addBtnIsHidden = true;
                }

                String str = "elevation";
                if (!recyclerView.canScrollVertically(-1)) {
                    if (buttonsAndDateContainer.getElevation() == 50.0f) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, str, new float[]{50.0f, 0.0f});
                        anim.setDuration(200);
                        anim.start();
                    }
                } else if (buttonsAndDateContainer.getElevation() == 0.0f) {
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(buttonsAndDateContainer, str, new float[]{0.0f, 50.0f});
                    anim2.setDuration(200);
                    anim2.start();
                }
            }
        });



        volleyballContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "volleyball";
                volleyballContainer.setAlpha(1);
                footballContainer.setAlpha(.5f);
                swimmingContainer.setAlpha(.5f);

                if (isOnInsertRv) {
                    if (volleyballListTiming.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, volleyballListTiming);
                    sportmanTimingRv.setAdapter(sportmanTimingAdapter);
                } else {
                    if (volleyballList.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, volleyballList);
                    sportmanListRv.setAdapter(sportmanListAdapter);
                }
            }
        });



        footballContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "football";
                footballContainer.setAlpha(1);
                volleyballContainer.setAlpha(.5f);
                swimmingContainer.setAlpha(.5f);

                if (isOnInsertRv) {
                    if (footballListTiming.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, footballListTiming);
                    sportmanTimingRv.setAdapter(sportmanTimingAdapter);
                } else {
                    if (footballList.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, footballList);
                sportmanListRv.setAdapter(sportmanListAdapter);
                }
            }
        });



        swimmingContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                type = "swimming";
                swimmingContainer.setAlpha(1);
                volleyballContainer.setAlpha(.5f);
                footballContainer.setAlpha(.5f);

                if (isOnInsertRv) {
                    if (swimmingListTiming.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, swimmingListTiming);
                    sportmanTimingRv.setAdapter(sportmanTimingAdapter);
                } else {
                    sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, swimmingList);
                    if (swimmingList.size() == 0)
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    else
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    sportmanListRv.setLayoutManager(new LinearLayoutManager(SportmanDashboardActivity.this));
                    sportmanListRv.setAdapter(sportmanListAdapter);
                }
            }
        });


        cancelDeleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                shadowContainer.setVisibility(View.INVISIBLE);
                shadowContainer.setClickable(false);
                deleteTimeContainer.setVisibility(View.INVISIBLE);
            }
        });


        confirmDeleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                apiHandler.deleteSportTime(deleteId, deleteDate, new ApiHandler.ResponseListenerDeleteSportTime() {
                    @Override
                    public void onRecieved(String response) {
                        shadowContainer.setVisibility(View.INVISIBLE);
                        shadowContainer.setClickable(false);
                        deleteTimeContainer.setVisibility(View.INVISIBLE);
                        getAllTimes();
                    }
                });
            }
        });
    }





    public void getAllTimes() {
        progressBar.setVisibility(View.VISIBLE);
        nothingFoundContainer.setVisibility(View.INVISIBLE);
        sportmanListRv.setAdapter(null);
        sportmanTimingRv.setAdapter(null);

        apiHandler.getSport(new ResponseListenerGetSport() {
            @Override
            public void onRecieved(List<Sport> SportList) {
                progressBar.setVisibility(View.INVISIBLE);

                volleyballList = new ArrayList<>();
                footballList = new ArrayList<>();
                swimmingList = new ArrayList<>();

                volleyballListTiming = new ArrayList();
                footballListTiming = new ArrayList();
                swimmingListTiming = new ArrayList();

                for (int i = 0; i < SportList.size(); i++) {
                    for (int j = 0; j < 7; j++) {
                        Sport sport = new Sport();
                        sport.setType(SportList.get(i).getType());
                        sport.setTime(SportList.get(i).getTime());
                        sport.setId(SportList.get(i).getId());
                        sport.setActualDate(getDateOfWeek(j));
                        sport.setCapacity(SportList.get(i).getCapacity());
                        sport.setPersonalIds(SportList.get(i).getPersonalIds());
                        try {
                            if ((SportList.get(i)).getType().equals("volleyball") && !SportList.get(i).getPersonalIds().get(weekDays[j]).equals(""))
                                volleyballList.add(sport);
                            else if ((SportList.get(i)).getType().equals("football") && !SportList.get(i).getPersonalIds().get(weekDays[j]).equals(""))
                                footballList.add(sport);
                            else if ((SportList.get(i)).getType().equals("swimming") && !SportList.get(i).getPersonalIds().get(weekDays[j]).equals(""))
                                swimmingList.add(sport);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


                for (int i = 0; i < SportList.size(); i++) {
                    for (int j = 0; j < 7; j++) {
                        Sport sport = new Sport();
                        sport.setType(SportList.get(i).getType());
                        sport.setTime(SportList.get(i).getTime());
                        sport.setId(SportList.get(i).getId());
                        sport.setActualDate(getDateOfWeek(j));
                        sport.setCapacity(SportList.get(i).getCapacity());
                        if ((SportList.get(i)).getType().equals("volleyball") && SportList.get(i).getDate().toString().contains(weekDays[j])) {
                            volleyballListTiming.add(sport);
                        } else if ((SportList.get(i)).getType().equals("football") && SportList.get(i).getDate().toString().contains(weekDays[j])) {
                            footballListTiming.add(sport);
                        } else if ((SportList.get(i)).getType().equals("swimming") && SportList.get(i).getDate().toString().contains(weekDays[j])) {
                            swimmingListTiming.add(sport);
                        }
                    }
                }


                if (!isOnInsertRv) {
                    switch (type) {
                        case "volleyball":
                            sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, volleyballList);
                            if (volleyballList.size() == 0)
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            else
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            break;
                        case "football":
                            sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, footballList);
                            if (footballList.size() == 0)
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            else
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            break;
                        case "swimming":
                            sportmanListAdapter = new SportmanListAdapter(SportmanDashboardActivity.this, swimmingList);
                            if (swimmingList.size() == 0)
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            else
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            break;
                    }
                    sportmanListRv.setLayoutManager(new LinearLayoutManager(SportmanDashboardActivity.this));
                    sportmanListRv.setAdapter(sportmanListAdapter);
                } else {
                    switch (type) {
                        case "volleyball":
                            sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, volleyballListTiming);
                            if (volleyballListTiming.size() == 0) {
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            } else {
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case "football":
                            sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, footballListTiming);
                            if (footballListTiming.size() == 0) {
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            } else {
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            }
                            break;
                        case "swimming":
                            sportmanTimingAdapter = new SportmanTimingAdapter(SportmanDashboardActivity.this, swimmingListTiming);
                            if (swimmingListTiming.size() == 0) {
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                            } else {
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                            }
                            break;
                    }
                    sportmanTimingRv.setLayoutManager(new LinearLayoutManager(SportmanDashboardActivity.this));
                    sportmanTimingRv.setAdapter(sportmanTimingAdapter);
                }

            }

            @Override
            public void onMessage(String response) {

                if (response.equals("error")) {
                    progressBar.setVisibility(View.INVISIBLE);
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }






    @SuppressLint("SetTextI18n")
    public String getDateOfWeek(int i) {
        String[] jalaliCalendar = new JalaliCalendar(new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + i - weekDay)).toString().split("-");

        return persianWeekDays[i]
                + " "
                + Formating.englishDigitsToPersian(jalaliCalendar[0])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[1])
                + "/"
                + Formating.englishDigitsToPersian(jalaliCalendar[2]);
    }


    public void onShadowDismissed(boolean flag) {
        if (flag) {
            getAllTimes();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_sportman_dashboard), "تایم جدید اضافه شد", Snackbar.LENGTH_LONG);

            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.light_green));
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(9);
            textView.setTextSize(14);
            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));
            textView.setTextColor(getResources().getColor(R.color.black));

            snackbar.show();
        }

        shadowContainer.setVisibility(View.INVISIBLE);
        shadowContainer.setClickable(false);
    }





    @Override
    public void onSportTimeDelete(Sport sport) {
        shadowContainer.setVisibility(View.VISIBLE);
        shadowContainer.setClickable(true);
        deleteTimeContainer.setVisibility(View.VISIBLE);
        deleteId = sport.getId();
        deleteDate = weekDays[Arrays.asList(persianWeekDays).indexOf(sport.getActualDate().split(" ")[0])];
    }
}
