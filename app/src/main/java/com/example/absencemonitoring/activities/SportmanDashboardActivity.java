package com.example.absencemonitoring.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.absencemonitoring.fragments.addReserveSportTimeFragment;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.SportmanListAdapter;
import com.example.absencemonitoring.adapters.SportmanTimingAdapter;
import com.example.absencemonitoring.instances.SportmanList;
import com.example.absencemonitoring.instances.SportmanTiming;

import java.util.ArrayList;
import java.util.List;

public class SportmanDashboardActivity extends AppCompatActivity {


    RelativeLayout buttonsAndDateContainer;
    RecyclerView sportmanListRv, sportmanTimingRv;
    List<SportmanList> sportmanList;
    List<SportmanTiming> sportmanTiming;
    SportmanListAdapter sportmanListAdapter;
    SportmanTimingAdapter sportmanTimingAdapter;
    TextView txtList, txtTiming;
    CardView logoutBtn, addTimeBtn;
    FrameLayout fragmentContainer;

    SwipeRefreshLayout swipeRefreshLayout;

    ApiHandler apiHandler;
    UserDetails userDetails;

    ScaleAnimation hideAnimation, showAnimation, hideAnimation1, showAnimation1;
    boolean logoutIsHidden = true, addBtnIsHidden = false;



    public void init() {

        apiHandler = new ApiHandler(this);
        userDetails = new UserDetails(this);


        sportmanListRv = findViewById(R.id.rv_sportman_list);
        sportmanTimingRv = findViewById(R.id.rv_sportman_timing);


        txtList = findViewById(R.id.txt_list);
        txtTiming = findViewById(R.id.txt_timing);
        sportmanList = new ArrayList<>();
        sportmanTiming = new ArrayList<>();

        addTimeBtn = findViewById(R.id.btn_add_time);

        logoutBtn = findViewById(R.id.btn_logout);

        buttonsAndDateContainer = findViewById(R.id.container_buttons_and_date);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);

        hideAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        hideAnimation.setDuration(200);
        hideAnimation.setFillAfter(true);

        showAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        showAnimation.setDuration(200);
        showAnimation.setFillAfter(true);

        hideAnimation1 = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        hideAnimation1.setDuration(200);
        hideAnimation1.setFillAfter(true);

        showAnimation1 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        showAnimation1.setDuration(200);
        showAnimation1.setFillAfter(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportman_dashboard);

        init();

        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("والیبال", "۲۱:۰۰", "۲۳:۰۰", "۱۸", R.color.yellow, R.drawable.ic_volleyball));
        sportmanList.add(new SportmanList("فوتسال", "۲۳:۰۰", "۰۱:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));
        sportmanList.add(new SportmanList("فوتسال", "۰۱:۰۰", "۰۳:۰۰", "۱۸", R.color.green, R.drawable.ic_football));

        txtList.setTextColor(getResources().getColor(R.color.red));
        sportmanListAdapter = new SportmanListAdapter(this, sportmanList);
        sportmanListRv.setLayoutManager(new LinearLayoutManager(this));
        sportmanListRv.setAdapter(sportmanListAdapter);


        sportmanTiming.add(new SportmanTiming("وقت اول", "۱۸:۰۰", "۲۰:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت دوم", "۲۰:۰۰", "۲۲:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت سوم", "۲۲:۰۰", "۰۰:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت چهارم", "۰۰:۰۰", "۲:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت چهارم", "۰۰:۰۰", "۲:۰۰"));
        sportmanTiming.add(new SportmanTiming("وقت چهارم", "۰۰:۰۰", "۲:۰۰"));

        sportmanTimingAdapter = new SportmanTimingAdapter(this, sportmanTiming);
        sportmanTimingRv.setLayoutManager(new LinearLayoutManager(this));
        sportmanTimingRv.setAdapter(sportmanTimingAdapter);

        txtList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportmanListRv.setVisibility(View.VISIBLE);
                sportmanTimingRv.setVisibility(View.INVISIBLE);
                txtList.setTextColor(getResources().getColor(R.color.red));
                txtTiming.setTextColor(getResources().getColor(R.color.light_yellow));
                if (addBtnIsHidden == true) {
                    addTimeBtn.startAnimation(hideAnimation1);
                    addBtnIsHidden = false;
                }
            }
        });

        txtTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportmanListRv.setVisibility(View.INVISIBLE);
                sportmanTimingRv.setVisibility(View.VISIBLE);
                txtTiming.setTextColor(getResources().getColor(R.color.red));
                txtList.setTextColor(getResources().getColor(R.color.light_yellow));
                if (addBtnIsHidden == false) {
                    addTimeBtn.startAnimation(showAnimation1);
                    addBtnIsHidden = true;
                }
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SportmanDashboardActivity.this, LoginActivity.class));
                finish();
                userDetails.deleteUser();
            }
        });

        addTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.container_focus).setVisibility(View.VISIBLE);
                findViewById(R.id.container_frame).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, new addReserveSportTimeFragment()).commit();
            }
        });



        sportmanListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && logoutIsHidden) {
                    logoutBtn.startAnimation(hideAnimation);
                    logoutIsHidden = false;
                } else if (dy < 0 && !logoutIsHidden) {
                    logoutBtn.startAnimation(showAnimation);
                    logoutIsHidden = true;
                }


                if (!recyclerView.canScrollVertically(-1)) {
                    if (buttonsAndDateContainer.getElevation() == 50) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 50, 0);
                        anim.setDuration(200);
                        anim.start();
                    }
                } else {
                    if (buttonsAndDateContainer.getElevation() == 0) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 0, 50);
                        anim.setDuration(200);
                        anim.start();
                    }
                }
            }
        });

        sportmanTimingRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && logoutIsHidden) {
                    logoutBtn.startAnimation(hideAnimation);
                    addTimeBtn.startAnimation(hideAnimation1);
                    logoutIsHidden = false;
                    addBtnIsHidden = false;
                } else if (dy < 0 && !logoutIsHidden) {
                    logoutBtn.startAnimation(showAnimation);
                    addTimeBtn.startAnimation(showAnimation1);
                    logoutIsHidden = true;
                    addBtnIsHidden = true;
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    if (buttonsAndDateContainer.getElevation() == 50) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 50, 0);
                        anim.setDuration(200);
                        anim.start();
                    }
                } else {
                    if (buttonsAndDateContainer.getElevation() == 0) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(buttonsAndDateContainer, "elevation", 0, 50);
                        anim.setDuration(200);
                        anim.start();
                    }
                }
            }
        });





    }



    public void animateMarginTop(final View view, int amount, int duration) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, amount);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.topMargin = (Integer) valueAnimator.getAnimatedValue();
                view.requestLayout();
            }
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public int getpixel(int dp) {
        return dp * (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public int getdp(int pixel) {
        return pixel / (int) getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
