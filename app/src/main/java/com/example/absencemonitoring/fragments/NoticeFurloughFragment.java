package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.activities.MasterDashboardActivity;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoticeFurloughFragment extends Fragment implements MasterDashboardActivity.RequestDeterminedListener {
    View view;
    RecyclerView rv;
    NoticeFurloughAdapter noticeFurloughAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    UserDetails userDetails;
    ApiHandler apiHandler;
    ProgressBar progressBar;
    RelativeLayout nothingFoundContainer;

    public void init() {
        rv = view.findViewById(R.id.rv_notice_furlough);
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);

        swipeRefreshLayout = view.findViewById(R.id.swipe_notice_furlough);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());


        apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                progressBar.setVisibility(View.INVISIBLE);

                if (notifReqLeaveList.size() == 0) {
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                } else {
                    nothingFoundContainer.setVisibility(View.INVISIBLE);
                }

                noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(noticeFurloughAdapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_furlough, container, false);

        init();
        ((MasterDashboardActivity)getActivity()).setOnDataListener(this);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.ResponseListenerNotifReqLeave() {
                    @Override
                    public void onRevived(List<Furlough> notifReqLeaveList) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (notifReqLeaveList.size() == 0) {
                            nothingFoundContainer.setVisibility(View.VISIBLE);
                        } else {
                            nothingFoundContainer.setVisibility(View.INVISIBLE);
                        }

                        noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rv.setAdapter(noticeFurloughAdapter);
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
        apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                progressBar.setVisibility(View.INVISIBLE);

                if (notifReqLeaveList.size() == 0) {
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                } else {
                    nothingFoundContainer.setVisibility(View.INVISIBLE);
                }

                noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(noticeFurloughAdapter);
                noticeFurloughAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
