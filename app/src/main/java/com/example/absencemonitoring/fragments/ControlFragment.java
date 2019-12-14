package com.example.absencemonitoring.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ControlAdapter;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.interfaces.SwipeEndFragmentListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ControlFragment extends Fragment {
    private View view;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private RelativeLayout nothingFoundContainer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ControlAdapter controlAdapter;
    private ApiHandler apiHandler;
    private UserDetails userDetails;
    private Handler handler;
    private Runnable runnable;
    GestureDetector gestureDetector;

    SwipeFragmentListener swipeFragmentListener;

    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        apiHandler = new ApiHandler(getActivity());
        userDetails = new UserDetails(getActivity());
        handler = new Handler();

        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());


        swipeFragmentListener = (SwipeFragmentListener) getActivity();

        rv = view.findViewById(R.id.rv_control_furlough);
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);
        swipeRefreshLayout = view.findViewById(R.id.swipe_control);

        rv.setOnTouchListener(onTouchListener);


        rv.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 0f, 100f, 0));

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));




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

                controlAdapter = new ControlAdapter(getActivity(), controlLeaveList);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(controlAdapter);
                updateListRemainingTime();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_control, container, false);

        init();

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

                        controlAdapter = new ControlAdapter(getActivity(), controlLeaveList);
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv.setAdapter(controlAdapter);
                        updateListRemainingTime();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });





        return view;
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

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return gestureDetector.onTouchEvent(event);
        }
    };

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {

            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            return false;
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onFling(MotionEvent event1, final MotionEvent event2, float velocityX, float velocityY) {

            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                rv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                            swipeFragmentListener.onSwipe(rv, event2, new SwipeEndFragmentListener() {
                                @Override
                                public void onSwipe() {
                                rv.setOnTouchListener(onTouchListener);
                                }
                            });
                        return true;
                    }
                });

                return true;
            } else {
                return false;
            }
        }

    }

}

