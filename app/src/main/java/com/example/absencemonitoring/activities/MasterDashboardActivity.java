package com.example.absencemonitoring.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.absencemonitoring.interfaces.RequestDeterminedListener;
import com.example.absencemonitoring.interfaces.SwipeEndFragmentListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;
import com.example.absencemonitoring.utils.Formating;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

import java.util.List;

public class MasterDashboardActivity extends AppCompatActivity implements View.OnTouchListener, SwipeFragmentListener {

    RelativeLayout root;
    TextView nameTxt, roleTxt;

    CardView menuContainer;
    RelativeLayout homeContainer, requestContainer, profileContainer, noticeContainer, archiveContainer, controlingContainer, logoutContainer;
    TextView homeTxt, profileTxt, requestTxt, noticeTxt, archiveTxt, controlTxt, logoutTxt;
    ImageView homeImg, profileImg, requestImg, noticeImg, archiveImg, controlImg, logoutImg;

    CardView requestDetailsContainer, noticeDetailsContainer;
    TextView requestFurlougTxt, requestSportTxt, requestTransportationTxt;
    TextView noticeFurloughTxt, noticeSportTxt;

    TextView txtNumberOfNotices, txtNumberOfFurloughNotices;

    FrameLayout fragmentContainer;



    RelativeLayout previousSelectedContainer;
    ImageView previousSelectedImg;
    TextView previousSelectedTxt;



    UserDetails userDetails;
    ApiHandler apiHandler;



    RequestDeterminedListener requestDeterminedListener;



    int previousSelectedDrawable;
    int menuXdelta, iconXdelta;
    int oldX;
    boolean isMenuOpen = true;
    boolean isMenuTxtFaded = false;



    @SuppressLint("ClickableViewAccessibility")
    public void init() {

        root = findViewById(R.id.activity_master_dashboard);

        nameTxt = findViewById(R.id.txt_name);
        roleTxt = findViewById(R.id.txt_role);

        menuContainer = findViewById(R.id.container_menu);
        homeContainer = findViewById(R.id.container_home);
        profileContainer = findViewById(R.id.container_profile);
        fragmentContainer = findViewById(R.id.container_fragment);
        requestContainer = findViewById(R.id.container_request);
        noticeContainer = findViewById(R.id.container_notice);
        archiveContainer = findViewById(R.id.container_archive);
        controlingContainer = findViewById(R.id.container_controling);
        logoutContainer = findViewById(R.id.container_logout);

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

        requestDetailsContainer = findViewById(R.id.container_request_details);
        requestFurlougTxt = findViewById(R.id.txt_request_furough);
        noticeFurloughTxt = findViewById(R.id.txt_notice_furough);
        requestTransportationTxt = findViewById(R.id.txt_request_transportation);

        noticeDetailsContainer = findViewById(R.id.container_notice_details);
        requestSportTxt = findViewById(R.id.txt_request_sport);
        noticeSportTxt = findViewById(R.id.txt_notice_sport);
        txtNumberOfNotices = findViewById(R.id.txt_number_of_notices);
        txtNumberOfFurloughNotices = findViewById(R.id.txt_number_of_furlough_notices);



        userDetails = new UserDetails(this);
        apiHandler = new ApiHandler(this);



        menuContainer.bringToFront();
        root.setOnTouchListener(this);
        fragmentContainer.setOnTouchListener(this);
        menuContainer.setOnTouchListener(this);
        homeContainer.setOnTouchListener(this);
        profileContainer.setOnTouchListener(this);
        noticeContainer.setOnTouchListener(this);
        archiveContainer.setOnTouchListener(this);
        controlingContainer.setOnTouchListener(this);
        logoutContainer.setOnTouchListener(this);
        requestContainer.setOnTouchListener(this);

        try {
            nameTxt.setText(userDetails.getUserInfo().getString("firstName") + " " + userDetails.getUserInfo().getString("lastName"));
            if (userDetails.getUserInfo().getString("role").trim().equals("master")) {
                roleTxt.setText("مدیر");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        getNumberOfNotice();
        handleFragmentSelection(homeContainer, homeImg, homeTxt, R.drawable.ic_home_selected, R.drawable.ic_home);

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_dashboard);
        init();





        homeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFragmentSelection(homeContainer, homeImg, homeTxt, R.drawable.ic_home_selected, R.drawable.ic_home);
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
                } else {
                    noticeDetailsContainer.setVisibility(View.INVISIBLE);
                }
            }
        });


        archiveContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ArchiveFurloughFragment()).commit();
                handleFragmentSelection(archiveContainer, archiveImg, archiveTxt, R.drawable.ic_archive_selected, R.drawable.ic_archive);
            }
        });



        controlingContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ControlFragment()).commit();
                handleFragmentSelection(controlingContainer, controlImg, controlTxt, R.drawable.ic_control_selected, R.drawable.ic_control);
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
                startActivity(new Intent(MasterDashboardActivity.this, SportReserveActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });



        requestTransportationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MasterDashboardActivity.this, TransportReserveActivity.class));
                requestDetailsContainer.setVisibility(View.INVISIBLE);
            }
        });



        noticeFurloughTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeFurloughFragment()).commit();
                handleFragmentSelection(noticeContainer, noticeImg, noticeTxt, R.drawable.ic_notification_selected, R.drawable.ic_notification);
            }
        });



        noticeSportTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new NoticeSportFragment()).commit();
                handleFragmentSelection(noticeContainer, noticeImg, noticeTxt, R.drawable.ic_notification_selected, R.drawable.ic_notification);
            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 3:
                showSnackbar("درخواست با موققیت ارسال شد");
                getNumberOfNotice();
                break;
            case 4:
                showSnackbar("درخواست تائید شد");
                getNumberOfNotice();
                requestDeterminedListener.onReqDetermined();
                break;
            case 5:
                showSnackbar("درخواست رد شد");
                getNumberOfNotice();
                requestDeterminedListener.onReqDetermined();
                break;
            case 6:
                showSnackbar("اتمام مرخصی به تایید رسید");
                break;
        }
    }





    public void animateMarginRight(final View view, int amount) {
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





    public int getpixel(int dp) {
        return dp * (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }





    private void getNumberOfNotice() {
        txtNumberOfNotices.setVisibility(View.INVISIBLE);
        txtNumberOfFurloughNotices.setVisibility(View.INVISIBLE);

        apiHandler.getNotifReqLeave(userDetails.getUserPersonalId(), new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                if (notifReqLeaveList.size() > 0) {
                    txtNumberOfNotices.setVisibility(View.VISIBLE);
                    txtNumberOfFurloughNotices.setVisibility(View.VISIBLE);

                    txtNumberOfNotices.setText(Formating.englishDigitsToPersian(String.valueOf(notifReqLeaveList.size())));
                    txtNumberOfFurloughNotices.setText(Formating.englishDigitsToPersian(String.valueOf(notifReqLeaveList.size())));
                }
            }
        });
    }





    private void handleFragmentSelection(RelativeLayout container, ImageView icon, TextView text, int newSource, int oldSource) {
        if (previousSelectedContainer != null) {
            previousSelectedContainer.setBackground(null);
            previousSelectedContainer.setBackgroundColor(Color.TRANSPARENT);
            previousSelectedTxt.setTextColor(getResources().getColor(R.color.light_yellow));
            previousSelectedImg.setImageResource(previousSelectedDrawable);
        }

        container.setBackground(getResources().getDrawable(R.drawable.background_selected_menu_item));
        text.setTextColor(getResources().getColor(R.color.red));
        icon.setImageResource(newSource);

        previousSelectedContainer = container;
        previousSelectedTxt = text;
        previousSelectedImg = icon;
        previousSelectedDrawable = oldSource;

        requestDetailsContainer.setVisibility(View.INVISIBLE);
        noticeDetailsContainer.setVisibility(View.INVISIBLE);
    }


    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_LONG);
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





    @Override
    public void onCloseMenu() {
        animateMarginRight(menuContainer, - getpixel(150));
        animateMarginRight(homeImg, getpixel(45));
        animateMarginRight(profileImg, getpixel(45));
        animateMarginRight(requestImg, getpixel(45));
        animateMarginRight(noticeImg, getpixel(45));
        animateMarginRight(archiveImg, getpixel(45));
        animateMarginRight(controlImg, getpixel(45));
        animateMarginRight(logoutImg, getpixel(45));
    }











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

                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                if (layoutParams1.rightMargin > -getpixel(130)) {
                    animateMarginRight(menuContainer, - getpixel(110));
                    animateMarginRight(homeImg, getpixel(25));
                    animateMarginRight(profileImg, getpixel(25));
                    animateMarginRight(requestImg, getpixel(25));
                    animateMarginRight(noticeImg, getpixel(25));
                    animateMarginRight(archiveImg, getpixel(25));
                    animateMarginRight(controlImg, getpixel(25));
                    animateMarginRight(logoutImg, getpixel(25));

                    isMenuOpen = true;
                } else {
                    animateMarginRight(menuContainer, - getpixel(150));
                    animateMarginRight(homeImg, getpixel(45));
                    animateMarginRight(profileImg, getpixel(45));
                    animateMarginRight(requestImg, getpixel(45));
                    animateMarginRight(noticeImg, getpixel(45));
                    animateMarginRight(archiveImg, getpixel(45));
                    animateMarginRight(controlImg, getpixel(45));
                    animateMarginRight(logoutImg, getpixel(45));

                    isMenuOpen = false;
                }

                if (layoutParams1.rightMargin > -getpixel(130) && isMenuTxtFaded) {
                    isMenuTxtFaded = false;
                    fadeAnimation(homeTxt, 0, 1);
                    fadeAnimation(profileTxt, 0, 1);
                    fadeAnimation(requestTxt, 0, 1);
                    fadeAnimation(noticeTxt, 0, 1);
                    fadeAnimation(archiveTxt, 0, 1);
                    fadeAnimation(controlTxt, 0, 1);
                    fadeAnimation(logoutTxt, 0, 1);
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

                if ((X > oldX + 50 || X < oldX - 50) && !isMenuTxtFaded) {
                    isMenuTxtFaded = true;
                    fadeAnimation(homeTxt, 1, 0);
                    fadeAnimation(profileTxt, 1, 0);
                    fadeAnimation(requestTxt, 1, 0);
                    fadeAnimation(noticeTxt, 1, 0);
                    fadeAnimation(archiveTxt, 1, 0);
                    fadeAnimation(controlTxt, 1, 0);
                    fadeAnimation(logoutTxt, 1, 0);
                }

                if ((layoutParams.rightMargin >= -getpixel(150) && X > oldX) || (layoutParams.rightMargin <= -getpixel(100) && X < oldX)) {
                    layoutParams.rightMargin = (((oldX - X ) / 2) + menuXdelta);
                    layoutParams2.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams3.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams4.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams5.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams6.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams7.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams8.rightMargin = (((X - oldX) / 4) + iconXdelta);

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
        root.invalidate();
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

                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) menuContainer.getLayoutParams();
                if (layoutParams1.rightMargin > -getpixel(130)) {
                    animateMarginRight(menuContainer, - getpixel(110));
                    animateMarginRight(homeImg, getpixel(25));
                    animateMarginRight(profileImg, getpixel(25));
                    animateMarginRight(requestImg, getpixel(25));
                    animateMarginRight(noticeImg, getpixel(25));
                    animateMarginRight(archiveImg, getpixel(25));
                    animateMarginRight(controlImg, getpixel(25));
                    animateMarginRight(logoutImg, getpixel(25));

                    isMenuOpen = true;
                } else {
                    animateMarginRight(menuContainer, - getpixel(150));
                    animateMarginRight(homeImg, getpixel(45));
                    animateMarginRight(profileImg, getpixel(45));
                    animateMarginRight(requestImg, getpixel(45));
                    animateMarginRight(noticeImg, getpixel(45));
                    animateMarginRight(archiveImg, getpixel(45));
                    animateMarginRight(controlImg, getpixel(45));
                    animateMarginRight(logoutImg, getpixel(45));

                    isMenuOpen = false;
                }

                if (layoutParams1.rightMargin > -getpixel(130) && isMenuTxtFaded) {
                    isMenuTxtFaded = false;
                    fadeAnimation(homeTxt, 0, 1);
                    fadeAnimation(profileTxt, 0, 1);
                    fadeAnimation(requestTxt, 0, 1);
                    fadeAnimation(noticeTxt, 0, 1);
                    fadeAnimation(archiveTxt, 0, 1);
                    fadeAnimation(controlTxt, 0, 1);
                    fadeAnimation(logoutTxt, 0, 1);
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

                if ((X > oldX + 50 || X < oldX - 50) && !isMenuTxtFaded) {
                    isMenuTxtFaded = true;
                    fadeAnimation(homeTxt, 1, 0);
                    fadeAnimation(profileTxt, 1, 0);
                    fadeAnimation(requestTxt, 1, 0);
                    fadeAnimation(noticeTxt, 1, 0);
                    fadeAnimation(archiveTxt, 1, 0);
                    fadeAnimation(controlTxt, 1, 0);
                    fadeAnimation(logoutTxt, 1, 0);
                }

                if ((layoutParams.rightMargin >= -getpixel(150) && X > oldX) || (layoutParams.rightMargin <= -getpixel(100) && X < oldX)) {
                    layoutParams.rightMargin = (((oldX - X ) / 2) + menuXdelta);
                    layoutParams2.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams3.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams4.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams5.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams6.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams7.rightMargin = (((X - oldX) / 4) + iconXdelta);
                    layoutParams8.rightMargin = (((X - oldX) / 4) + iconXdelta);

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
        root.invalidate();
    }

}
