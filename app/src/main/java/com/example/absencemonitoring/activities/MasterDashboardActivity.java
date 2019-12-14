package com.example.absencemonitoring.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.fragments.ArchiveFurloughFragment;
import com.example.absencemonitoring.fragments.ControlFragment;
import com.example.absencemonitoring.fragments.NoticeFurloughFragment;
import com.example.absencemonitoring.fragments.NoticeSportFragment;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.interfaces.SwipeEndFragmentListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;
import com.example.absencemonitoring.utils.Formating;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.List;

public class MasterDashboardActivity extends AppCompatActivity implements View.OnTouchListener, SwipeFragmentListener {

    CardView menuContainer;
    RelativeLayout homeContainer, requestContainer, profileContainer, noticeContainer, archiveContainer, controlingContainer, logoutContainer;
    TextView homeTxt, profileTxt, requestTxt, noticeTxt, archiveTxt, controlTxt, logoutTxt;
    ImageView homeImg, profileImg, requestImg, noticeImg, archiveImg, controlImg, logoutImg;
    CardView requestDetailsContainer, noticeDetailsContainer, archiveDetailsContainer;
    TextView requestFurlougTxt, requestSportTxt;
    TextView noticeFurloughTxt, noticeSportTxt;
    TextView archiveFurloughTxt;
    TextView nameTxt, roleTxt;
    String checkFragment = "";
    TextView previousSelectedTxt;
    TextView txtNumberOfNotices, txtNumberOfFurloughNotices;

    RelativeLayout previousSelectedContainer;
    ImageView previousSelectedImg;
    FrameLayout fragmentContainer;

    RelativeLayout masterDashboardActivity, headerContainer;

    UserDetails userDetails;
    Activity activity;
    RequestDeterminedListener requestDeterminedListener;
    ApiHandler apiHandler;
    int previousSelectedDrawable;
    int menuXdelta, iconXdelta;
    int oldX;

    boolean isMenuOpen = true;



    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        userDetails = new UserDetails(activity);
        apiHandler = new ApiHandler(activity);

        masterDashboardActivity = findViewById(R.id.activity_master_dashboard);
        masterDashboardActivity.setOnTouchListener(this);

        fragmentContainer = findViewById(R.id.container_fragment);
        fragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        fragmentContainer.setOnTouchListener(this);

        headerContainer = findViewById(R.id.container_header);
        nameTxt = findViewById(R.id.txt_name);
        roleTxt = findViewById(R.id.txt_role);

        menuContainer = findViewById(R.id.container_menu);
        menuContainer.bringToFront();
        menuContainer.setOnTouchListener(this);

        homeContainer = findViewById(R.id.container_home);
        homeContainer.setOnTouchListener(this);

        profileContainer = findViewById(R.id.container_profile);
        profileContainer.setOnTouchListener(this);

        requestContainer = findViewById(R.id.container_request);
        requestContainer.setOnTouchListener(this);

        noticeContainer = findViewById(R.id.container_notice);
        noticeContainer.setOnTouchListener(this);

        archiveContainer = findViewById(R.id.container_archive);
        archiveContainer.setOnTouchListener(this);

        controlingContainer = findViewById(R.id.container_controling);
        controlingContainer.setOnTouchListener(this);

        logoutContainer = findViewById(R.id.container_logout);
        logoutContainer.setOnTouchListener(this);


        requestDetailsContainer = findViewById(R.id.container_request_details);
        noticeDetailsContainer = findViewById(R.id.container_notice_details);
        archiveDetailsContainer = findViewById(R.id.container_archive_details);

        requestFurlougTxt = findViewById(R.id.txt_request_furough);
        requestSportTxt = findViewById(R.id.txt_request_sport);

        noticeFurloughTxt = findViewById(R.id.txt_notice_furough);
        noticeSportTxt = findViewById(R.id.txt_notice_sport);

        archiveFurloughTxt = findViewById(R.id.txt_archive_furlough);


        homeTxt = findViewById(R.id.txt_home);
        profileTxt = findViewById(R.id.txt_profile);
        requestTxt = findViewById(R.id.txt_request);
        noticeTxt = findViewById(R.id.txt_notice);
        archiveTxt = findViewById(R.id.txt_archive);
        controlTxt = findViewById(R.id.txt_controling);
        logoutTxt = findViewById(R.id.txt_logout);

        homeImg = findViewById(R.id.img_home);
        profileImg = findViewById(R.id.img_profile);
        requestImg = findViewById(R.id.img_request);
        noticeImg = findViewById(R.id.img_notice);
        archiveImg = findViewById(R.id.img_archive);
        controlImg = findViewById(R.id.img_controling);
        logoutImg = findViewById(R.id.img_logout);

        txtNumberOfNotices = findViewById(R.id.txt_number_of_notices);
        txtNumberOfFurloughNotices = findViewById(R.id.txt_number_of_furlough_notices);



        String role = null;
        try {
            role = userDetails.getUserInfo().getString("role");
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (role.trim().equals("master"))
            roleTxt.setText("مدیر");

        /*apiHandler.getUserInfo(userDetails.getUserDetails(), new ApiHandler.responseListenerGetInfo() {
            @Override
            public void onRecived(String response) {
                if(response.trim().equals("Success")){
                    try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_dashboard);
        activity = this;
        init();

        homeContainer.setBackground(getResources().getDrawable(R.drawable.background_home_container));
        homeTxt.setTextColor(getResources().getColor(R.color.red));
        homeImg.setImageResource(R.drawable.ic_home_selected);
        previousSelectedContainer = homeContainer;
        previousSelectedTxt = homeTxt;
        previousSelectedImg = homeImg;
        previousSelectedDrawable = R.drawable.ic_home;

        apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                if (notifReqLeaveList.size() > 0) {
                    txtNumberOfNotices.setVisibility(View.VISIBLE);
                    txtNumberOfFurloughNotices.setVisibility(View.INVISIBLE);

                    txtNumberOfNotices.setText(Formating.englishDigitsToPersian(String.valueOf(notifReqLeaveList.size())));
                    txtNumberOfFurloughNotices.setText(Formating.englishDigitsToPersian(String.valueOf(notifReqLeaveList.size())));
                }
            }
        });



        homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("homeFragment")) {
                    checkFragment = "homeFragment";

                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);


                    previousSelectedContainer.setBackground(null);
                    previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    homeContainer.setBackground(getResources().getDrawable(R.drawable.background_home_container));
                    homeTxt.setTextColor(getResources().getColor(R.color.red));
                    homeImg.setImageResource(R.drawable.ic_home_selected);

                    previousSelectedContainer = homeContainer;
                    previousSelectedTxt = homeTxt;
                    previousSelectedImg = homeImg;
                    previousSelectedDrawable = R.drawable.ic_home;
                }
            }
        });

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

                    archiveDetailsContainer.setVisibility(View.INVISIBLE);
                    requestDetailsContainer.setVisibility(View.INVISIBLE);
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);

                    previousSelectedContainer.setBackground(null);
                    previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    controlingContainer.setBackgroundColor(getResources().getColor(R.color.blacker));
                    controlTxt.setTextColor(getResources().getColor(R.color.red));
                    controlImg.setImageResource(R.drawable.ic_control_selected);

                    previousSelectedContainer = controlingContainer;
                    previousSelectedTxt = controlTxt;
                    previousSelectedImg = controlImg;
                    previousSelectedDrawable = R.drawable.ic_control;
                }
            }
        });

        logoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDetails.deleteUser();
                startActivity(new Intent(MasterDashboardActivity.this, LoginActivity.class));
                finish();
            }
        });


        requestFurlougTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MasterDashboardActivity.this, EmployeeFurloughActivity.class), 2);
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });


        requestSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, EmployeeSportReserveActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });


        noticeFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFragment.equals("noticeFurlough")) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeFurloughFragment()).commit();
                    checkFragment = "noticeFurlough";
//                    aminateMarginRight();

                    noticeDetailsContainer.setVisibility(View.INVISIBLE);

                    previousSelectedContainer.setBackground(null);
                    previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    noticeContainer.setBackgroundColor(getResources().getColor(R.color.blacker));
                    noticeTxt.setTextColor(getResources().getColor(R.color.red));
                    noticeImg.setImageResource(R.drawable.ic_notification_selected);

                    previousSelectedContainer = noticeContainer;
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

                    previousSelectedContainer.setBackground(null);
                    previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    noticeContainer.setBackgroundColor(getResources().getColor(R.color.blacker));
                    noticeTxt.setTextColor(getResources().getColor(R.color.red));
                    noticeImg.setImageResource(R.drawable.ic_notification_selected);

                    previousSelectedContainer = noticeContainer;
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

                    previousSelectedContainer.setBackground(null);
                    previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
                    previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
                    previousSelectedImg.setImageResource(previousSelectedDrawable);

                    archiveContainer.setBackgroundColor(getResources().getColor(R.color.blacker));
                    archiveTxt.setTextColor(getResources().getColor(R.color.red));
                    archiveImg.setImageResource(R.drawable.ic_archive_selected);

                    previousSelectedContainer = archiveContainer;
                    previousSelectedTxt = archiveTxt;
                    previousSelectedImg = archiveImg;
                    previousSelectedDrawable = R.drawable.ic_archive;
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 3) {

            Snackbar snackbar = Snackbar.make(masterDashboardActivity, "درخواست با موفقیت ارسال شد", Snackbar.LENGTH_LONG);

            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.light_green));
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(9);
            textView.setTextSize(14);
            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));
            textView.setTextColor(getResources().getColor(R.color.black));

            snackbar.show();
        } else if (resultCode == 4) {
            requestDeterminedListener.onReqDetermined();

            Snackbar snackbar = Snackbar.make(masterDashboardActivity, "درخواست تائید شد", Snackbar.LENGTH_LONG);

            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.light_green));
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(9);
            textView.setTextSize(14);
            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));
            textView.setTextColor(getResources().getColor(R.color.black));

            snackbar.show();
        } else if (resultCode == 5) {
            requestDeterminedListener.onReqDetermined();

            Snackbar snackbar = Snackbar.make(masterDashboardActivity, "درخواست رد شد", Snackbar.LENGTH_LONG);

            ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.red));
            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setMaxLines(9);
            textView.setTextSize(14);
            textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.iransansmobile_medium));
            textView.setTextColor(getResources().getColor(R.color.black));

            snackbar.show();
        } else if (resultCode == 6) {
            Snackbar snackbar = Snackbar.make(masterDashboardActivity, "اتمام مرخصی تائید شد", Snackbar.LENGTH_LONG);

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
    }

    public void aminateMarginRight(final View view, int amount) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.rightMargin, amount);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.rightMargin = (Integer) valueAnimator.getAnimatedValue();
                view.requestLayout();
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    public void fadeAnimation(View view, float from, float to) {
        AlphaAnimation fadeAnimation = new AlphaAnimation(from, to);
        fadeAnimation.setDuration(300);
        fadeAnimation.setFillAfter(true);
        view.startAnimation(fadeAnimation);
    }

//    public void expandMenu() {
//        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.rightMargin, amount);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                layoutParams.rightMargin = (Integer) valueAnimator.getAnimatedValue();
//                menuContainer.requestLayout();
//            }
//        });
//        valueAnimator.setDuration(300);
//        valueAnimator.start();
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                RelativeLayout.LayoutParams lParams1 = (RelativeLayout.LayoutParams) homeImg.getLayoutParams();
                menuXdelta = lParams.rightMargin;
                iconXdelta = lParams1.rightMargin;
                oldX = X;
                if (isMenuOpen) {
                    fadeAnimation(homeTxt, 1, 0);
                    fadeAnimation(profileTxt, 1, 0);
                    fadeAnimation(requestTxt, 1, 0);
                    fadeAnimation(noticeTxt, 1, 0);
                    fadeAnimation(archiveTxt, 1, 0);
                    fadeAnimation(controlTxt, 1, 0);
                    fadeAnimation(logoutTxt, 1, 0);
                }

                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                if (layoutParams1.rightMargin > -getpixel(130)) {
                    aminateMarginRight(menuContainer, - getpixel(110));
                    aminateMarginRight(homeImg, getpixel(25));
                    aminateMarginRight(profileImg, getpixel(25));
                    aminateMarginRight(requestImg, getpixel(25));
                    aminateMarginRight(noticeImg, getpixel(25));
                    aminateMarginRight(archiveImg, getpixel(25));
                    aminateMarginRight(controlImg, getpixel(25));
                    aminateMarginRight(logoutImg, getpixel(25));

                    fadeAnimation(homeTxt, 0, 1);
                    fadeAnimation(profileTxt, 0, 1);
                    fadeAnimation(requestTxt, 0, 1);
                    fadeAnimation(noticeTxt, 0, 1);
                    fadeAnimation(archiveTxt, 0, 1);
                    fadeAnimation(controlTxt, 0, 1);
                    fadeAnimation(logoutTxt, 0, 1);
                    isMenuOpen = true;
                } else {
                    aminateMarginRight(menuContainer, - getpixel(150));
                    aminateMarginRight(homeImg, getpixel(45));
                    aminateMarginRight(profileImg, getpixel(45));
                    aminateMarginRight(requestImg, getpixel(45));
                    aminateMarginRight(noticeImg, getpixel(45));
                    aminateMarginRight(archiveImg, getpixel(45));
                    aminateMarginRight(controlImg, getpixel(45));
                    aminateMarginRight(logoutImg, getpixel(45));
                    isMenuOpen = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) homeImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) profileImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) requestImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) noticeImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) archiveImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) controlImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams8 = (RelativeLayout.LayoutParams) logoutImg.getLayoutParams();

                if ((layoutParams.rightMargin >= -getpixel(150) && X > oldX) || (layoutParams.rightMargin <= -getpixel(100) && X < oldX)) {
                    layoutParams.rightMargin = (((oldX - X ) / 2) + menuXdelta);
                    layoutParams2.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams3.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams4.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams5.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams6.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams7.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams8.rightMargin = (((X - oldX) / 4) + iconXdelta);

                    Log.i("gaaav", "gaaav" + layoutParams.rightMargin);

                    menuContainer.setLayoutParams(layoutParams);
                    homeImg.setLayoutParams(layoutParams2);
                    profileImg.setLayoutParams(layoutParams3);
                    requestImg.setLayoutParams(layoutParams4);
                    noticeImg.setLayoutParams(layoutParams5);
                    archiveImg.setLayoutParams(layoutParams6);
                    controlImg.setLayoutParams(layoutParams7);
                    logoutImg.setLayoutParams(layoutParams8);
                }

                break;
        }
        masterDashboardActivity.invalidate();
        return false;
    }


    public void setOnDataListener(RequestDeterminedListener requestDeterminedListener) {
        this.requestDeterminedListener = requestDeterminedListener;
    }

    @Override
    public void onSwipe(View view, MotionEvent event, SwipeEndFragmentListener swipeEndFragmentListener) {
        final int X = (int) event.getRawX();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                RelativeLayout.LayoutParams lParams1 = (RelativeLayout.LayoutParams) homeImg.getLayoutParams();
                menuXdelta = lParams.rightMargin;
                iconXdelta = lParams1.rightMargin;
                oldX = X;
                if (isMenuOpen) {
                    fadeAnimation(homeTxt, 1, 0);
                    fadeAnimation(profileTxt, 1, 0);
                    fadeAnimation(requestTxt, 1, 0);
                    fadeAnimation(noticeTxt, 1, 0);
                    fadeAnimation(archiveTxt, 1, 0);
                    fadeAnimation(controlTxt, 1, 0);
                    fadeAnimation(logoutTxt, 1, 0);
                }

                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                if (layoutParams1.rightMargin > -getpixel(130)) {
                    swipeEndFragmentListener.onSwipe();
                    aminateMarginRight(menuContainer, - getpixel(110));
                    aminateMarginRight(homeImg, getpixel(25));
                    aminateMarginRight(profileImg, getpixel(25));
                    aminateMarginRight(requestImg, getpixel(25));
                    aminateMarginRight(noticeImg, getpixel(25));
                    aminateMarginRight(archiveImg, getpixel(25));
                    aminateMarginRight(controlImg, getpixel(25));
                    aminateMarginRight(logoutImg, getpixel(25));

                    fadeAnimation(homeTxt, 0, 1);
                    fadeAnimation(profileTxt, 0, 1);
                    fadeAnimation(requestTxt, 0, 1);
                    fadeAnimation(noticeTxt, 0, 1);
                    fadeAnimation(archiveTxt, 0, 1);
                    fadeAnimation(controlTxt, 0, 1);
                    fadeAnimation(logoutTxt, 0, 1);
                    isMenuOpen = true;
                } else {
                    swipeEndFragmentListener.onSwipe();
                    aminateMarginRight(menuContainer, - getpixel(150));
                    aminateMarginRight(homeImg, getpixel(45));
                    aminateMarginRight(profileImg, getpixel(45));
                    aminateMarginRight(requestImg, getpixel(45));
                    aminateMarginRight(noticeImg, getpixel(45));
                    aminateMarginRight(archiveImg, getpixel(45));
                    aminateMarginRight(controlImg, getpixel(45));
                    aminateMarginRight(logoutImg, getpixel(45));
                    isMenuOpen = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) homeImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) profileImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) requestImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) noticeImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) archiveImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) controlImg.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams8 = (RelativeLayout.LayoutParams) logoutImg.getLayoutParams();

                if ((layoutParams.rightMargin >= -getpixel(150) && X > oldX) || (layoutParams.rightMargin <= -getpixel(100) && X < oldX)) {
                    layoutParams.rightMargin = (((oldX - X ) / 2) + menuXdelta);
                    layoutParams2.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams3.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams4.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams5.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams6.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams7.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams8.rightMargin = (((X - oldX) / 4) + iconXdelta);

                    Log.i("gaaav", "gaaav" + layoutParams.rightMargin);

                    menuContainer.setLayoutParams(layoutParams);
                    homeImg.setLayoutParams(layoutParams2);
                    profileImg.setLayoutParams(layoutParams3);
                    requestImg.setLayoutParams(layoutParams4);
                    noticeImg.setLayoutParams(layoutParams5);
                    archiveImg.setLayoutParams(layoutParams6);
                    controlImg.setLayoutParams(layoutParams7);
                    logoutImg.setLayoutParams(layoutParams8);
                }

                break;
        }
        masterDashboardActivity.invalidate();
    }

    @Override
    public void onCloseMenu() {
        aminateMarginRight(menuContainer, - getpixel(150));
        aminateMarginRight(homeImg, getpixel(45));
        aminateMarginRight(profileImg, getpixel(45));
        aminateMarginRight(requestImg, getpixel(45));
        aminateMarginRight(noticeImg, getpixel(45));
        aminateMarginRight(archiveImg, getpixel(45));
        aminateMarginRight(controlImg, getpixel(45));
        aminateMarginRight(logoutImg, getpixel(45));

    }



    public interface RequestDeterminedListener {
        void onReqDetermined();
    }

    public int getpixel(int dp) {
        return dp * (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public int getdp(int pixel) {
        return pixel / (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }


}
