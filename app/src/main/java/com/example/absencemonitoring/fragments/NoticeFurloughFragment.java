package com.example.absencemonitoring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;


import com.example.absencemonitoring.Handlers.ApiHandler;
import com.example.absencemonitoring.Handlers.UserDetails;
import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.instances.Furlough;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NoticeFurloughFragment extends Fragment {
    View view;
    RecyclerView rv;
    NoticeFurloughAdapter noticeFurloughAdapter;
    UserDetails userDetails;
    ApiHandler apiHandler;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout noticeFurloughFragment;
    boolean isHide = false;
    HeaderHiderListener headerHiderListener;

    public void init() {

        noticeFurloughFragment = view.findViewById(R.id.fragment_notice_furlough);

        rv = view.findViewById(R.id.rv_notice_furlough);
        swipeRefreshLayout = view.findViewById(R.id.swipe_notice_furlough);

        headerHiderListener = (HeaderHiderListener) getActivity();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_yellow));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());


        apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.responseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiHandler.getNotifReqLeave(userDetails.getUserDetails(), new ApiHandler.responseListenerNotifReqLeave() {
                    @Override
                    public void onRevived(List<Furlough> notifReqLeaveList) {
                        noticeFurloughAdapter = new NoticeFurloughAdapter(getActivity(), notifReqLeaveList);
                        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                        noticeFurloughAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        rv.setAdapter(noticeFurloughAdapter);
                    }
                });
            }
        });

//        noticeFurloughFragment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int y = (int) event.getRawY();
//                return false;
//            }
//        });

        return view;
    }

    public interface HeaderHiderListener {
        public void onHide(String move);
    }
}
