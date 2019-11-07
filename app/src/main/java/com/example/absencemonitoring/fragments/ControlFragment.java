package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.instances.ControlFurlough;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.ControlAdapter;
import com.example.absencemonitoring.instances.Furlough;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    View view;
    RecyclerView rv;
    ProgressBar progressBar;
    RelativeLayout nothingFoundContainer;
    SwipeRefreshLayout swipeRefreshLayout;

    ControlAdapter controlAdapter;
    ApiHandler apiHandler;
    UserDetails userDetails;
    Handler handler;
    Runnable runnable;

    public void init() {
        apiHandler = new ApiHandler(getActivity());
        userDetails = new UserDetails(getActivity());
        handler = new Handler();

        rv = view.findViewById(R.id.rv_control_furlough);
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);
        swipeRefreshLayout = view.findViewById(R.id.swipe_control);

        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));

        apiHandler.getControlReqLeave("9537063", new ApiHandler.responseListenerControlReqLeave() {
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
                        return Boolean.compare(abc2.isStarted(), abc1.isStarted());
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

        apiHandler.getControlReqLeave("9537063", new ApiHandler.responseListenerControlReqLeave() {
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
                        return Boolean.compare(abc2.isStarted(), abc1.isStarted());
                    }
                });

                controlAdapter = new ControlAdapter(getActivity(), controlLeaveList);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(controlAdapter);
                swipeRefreshLayout.setRefreshing(false);
                updateListRemainingTime();
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
}
