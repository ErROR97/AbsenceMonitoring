package com.example.absencemonitoring.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.example.absencemonitoring.activities.MasterDashboardActivity;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;
import com.example.absencemonitoring.instances.FurloughArchive;
import com.example.absencemonitoring.interfaces.RequestDeterminedListener;
import com.example.absencemonitoring.interfaces.SwipeEndFragmentListener;
import com.example.absencemonitoring.interfaces.SwipeFragmentListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoticeFurloughFragment extends Fragment implements RequestDeterminedListener {
    private View view;
    private RecyclerView NoticeFurloughRv;
    private NoticeFurloughAdapter noticeFurloughAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RelativeLayout nothingFoundContainer;



    private List<Furlough> list;



    private UserDetails userDetails;
    private ApiHandler apiHandler;



    private GestureDetector gestureDetector;
    private SwipeFragmentListener swipeFragmentListener;
    private String role;
    private String personalId;


    @SuppressLint("ClickableViewAccessibility")
    public void init() {
        NoticeFurloughRv = view.findViewById(R.id.rv_notice_furlough);
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);

        swipeRefreshLayout = view.findViewById(R.id.swipe_notice_furlough);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));


        list = new ArrayList<>();

        apiHandler = new ApiHandler(getActivity());
        userDetails = new UserDetails(getActivity());

        try {
            role = userDetails.getUserInfo().getString("role");
            personalId = userDetails.getUserInfo().getString("personalId");
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        swipeFragmentListener = (SwipeFragmentListener) getActivity();
//
//        gestureDetector = new GestureDetector(getActivity(), new MyGestureListener());
//        NoticeFurloughRv.setOnTouchListener(onTouchListener);

        if (role.equals("employee")) {
            apiHandler.getArchiveEmployee(personalId, new ApiHandler.ResponseListenerArchiveReqLeaveEmployee() {
                @Override
                public void onRevived(List<Furlough> archiveReqLeaveEmployeeList) {
                    progressBar.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < archiveReqLeaveEmployeeList.size(); i++) {
                        if (archiveReqLeaveEmployeeList.get(i).getProgressStatus().equals("1") || archiveReqLeaveEmployeeList.get(i).getProgressStatus().equals("2")) {
                            list.add(archiveReqLeaveEmployeeList.get(i));
                        }
                    }

                    if (list.size() == 0) {
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    } else {
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    }

                    noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), list);
                    NoticeFurloughRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    NoticeFurloughRv.setAdapter(noticeFurloughAdapter);
                }

                @Override
                public void onMessage(String error) {

                }
            });
        } else {
            apiHandler.getNotifReqLeave(userDetails.getUserPersonalId(), new ApiHandler.ResponseListenerNotifReqLeave() {
                @Override
                public void onRevived(List<Furlough> notifReqLeaveList) {
                    progressBar.setVisibility(View.INVISIBLE);

                    if (notifReqLeaveList.size() == 0) {
                        nothingFoundContainer.setVisibility(View.VISIBLE);
                    } else {
                        nothingFoundContainer.setVisibility(View.INVISIBLE);
                    }

                    noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                    NoticeFurloughRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    NoticeFurloughRv.setAdapter(noticeFurloughAdapter);
                }
            });

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_furlough, container, false);

        init();
        try {
            if (userDetails.getUserInfo().getString("role").equals("master")) {
                ((MasterDashboardActivity)getActivity()).setOnDataListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiHandler.getNotifReqLeave(userDetails.getUserPersonalId(), new ApiHandler.ResponseListenerNotifReqLeave() {
                    @Override
                    public void onRevived(List<Furlough> notifReqLeaveList) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (notifReqLeaveList.size() == 0) {
                            nothingFoundContainer.setVisibility(View.VISIBLE);
                        } else {
                            nothingFoundContainer.setVisibility(View.INVISIBLE);
                        }

                        noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                        NoticeFurloughRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        NoticeFurloughRv.setAdapter(noticeFurloughAdapter);
                        noticeFurloughAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        return view;
    }





    @Override
    public void onReqDetermined() {
        progressBar.setVisibility(View.VISIBLE);
        apiHandler.getNotifReqLeave(userDetails.getUserPersonalId(), new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                progressBar.setVisibility(View.INVISIBLE);

                if (notifReqLeaveList.size() == 0) {
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                } else {
                    nothingFoundContainer.setVisibility(View.INVISIBLE);
                }

                noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                NoticeFurloughRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                NoticeFurloughRv.setAdapter(noticeFurloughAdapter);
                noticeFurloughAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
                NoticeFurloughRv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        swipeFragmentListener.onSwipe(NoticeFurloughRv, event2, new SwipeEndFragmentListener() {
                            @Override
                            public void onSwipe() {
                                NoticeFurloughRv.setOnTouchListener(onTouchListener);
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
