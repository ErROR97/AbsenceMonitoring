package com.example.absencemonitoring.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.NoticeSportAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Sport;
import com.example.absencemonitoring.interfaces.SwipeEndFragmentListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoticeSportFragment extends Fragment {
    private View view;
    private RecyclerView noticeSportRv;
    private NoticeSportAdapter noticeSportAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RelativeLayout nothingFoundContainer;



    List<Sport> list;

    private UserDetails userDetails;
    private ApiHandler apiHandler;



    private GestureDetector gestureDetector;
    private SwipeFragmentListener swipeFragmentListener;

    public void init() {
        noticeSportRv = view.findViewById(R.id.rv_notice_sport);
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);

        swipeRefreshLayout = view.findViewById(R.id.swipe_notice_sport);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));


        list = new ArrayList<>();

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());

        progressBar.setVisibility(View.VISIBLE);



//        swipeFragmentListener = (SwipeFragmentListener) getActivity();
//        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
//        noticeSportRv.setOnTouchListener(onTouchListener);



        apiHandler.getSport(new ApiHandler.ResponseListenerGetSport() {
            @Override
            public void onRecieved(List<Sport> sportLIst) {
                for (Sport item : sportLIst) {
                    progressBar.setVisibility(View.INVISIBLE);
                    try {
                        String[] weekDays = item.getDate().get("day").toString().split("-");
                        for (String s : weekDays) {
                            if (item.getPersonalIds().get(s).toString().contains(new UserDetails(getActivity()).getUserInfo().getString("personalId"))) {
                                Sport sport = new Sport();
                                sport.setType(item.getType());
                                sport.setTime(item.getTime());
                                sport.setDate(new JSONObject("{day:" + s + "}"));
                                sport.setCapacity(new JSONObject("{capacity:" + item.getCapacity().getString(s) + "}"));
                                list.add(sport);
                            }
                        }
                        noticeSportAdapter = new NoticeSportAdapter(getActivity(), list);
                        noticeSportRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        noticeSportRv.setAdapter(noticeSportAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                }

            @Override
            public void onMessage(String response) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_sport, container, false);

        init();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = new ArrayList<>();
                apiHandler.getSport(new ApiHandler.ResponseListenerGetSport() {
                    @Override
                    public void onRecieved(List<Sport> sportLIst) {
                        for (Sport item : sportLIst) {
                            progressBar.setVisibility(View.INVISIBLE);
                            try {
                                String[] weekDays = item.getDate().get("day").toString().split("-");
                                for (String s : weekDays) {
                                    if (item.getPersonalIds().get(s).toString().contains(new UserDetails(getActivity()).getUserInfo().getString("personalId"))) {
                                        Sport sport = new Sport();
                                        sport.setType(item.getType());
                                        sport.setTime(item.getTime());
                                        sport.setDate(new JSONObject("{day:" + s + "}"));
                                        sport.setCapacity(new JSONObject("{capacity:" + item.getCapacity().getString(s) + "}"));
                                        list.add(sport);
                                    }
                                }
                                noticeSportAdapter = new NoticeSportAdapter(getActivity(), list);
                                noticeSportRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                noticeSportRv.setAdapter(noticeSportAdapter);
                                noticeSportAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                noticeSportAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }

                        }
                    }

                    @Override
                    public void onMessage(String response) {
                        noticeSportAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        });

        return  view;
    }










    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
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
                noticeSportRv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        swipeFragmentListener.onSwipe(noticeSportRv, event2, new SwipeEndFragmentListener() {
                            @Override
                            public void onSwipe() {
                                noticeSportRv.setOnTouchListener(onTouchListener);
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
