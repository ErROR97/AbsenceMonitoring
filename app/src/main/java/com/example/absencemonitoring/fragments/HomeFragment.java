package com.example.absencemonitoring.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.renderscript.Script;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.absencemonitoring.R;
import com.example.absencemonitoring.adapters.HomeAdapter;
import com.example.absencemonitoring.adapters.NoticeFurloughAdapter;
import com.example.absencemonitoring.handlers.ApiHandler;
import com.example.absencemonitoring.handlers.UserDetails;
import com.example.absencemonitoring.instances.Furlough;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView rv;
    private HomeAdapter noticeFurloughAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RelativeLayout nothingFoundContainer;



    private List<Furlough> list;



    private UserDetails userDetails;
    private ApiHandler apiHandler;


    private String firstname, lastname;
    private String personalIdMaster;

    void init() {

        userDetails = new UserDetails(getActivity());
        apiHandler = new ApiHandler(getActivity());

        try {
            firstname = userDetails.getUserInfo().getString("firstName");
            lastname = userDetails.getUserInfo().getString("lastName");
            personalIdMaster = userDetails.getUserInfo().getString("personalIdmaster");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rv = view.findViewById(R.id.rv);
        list = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressbar);
        nothingFoundContainer = view.findViewById(R.id.container_nothing_found);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.black));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.light_blue));


        apiHandler.getNotifReqLeave(personalIdMaster, new ApiHandler.ResponseListenerNotifReqLeave() {
            @Override
            public void onRevived(List<Furlough> notifReqLeaveList) {
                if (notifReqLeaveList.size() == 0) {
                    nothingFoundContainer.setVisibility(View.VISIBLE);
                } else {
                    nothingFoundContainer.setVisibility(View.INVISIBLE);
                }
                for (int i = 0; i < notifReqLeaveList.size(); i++) {
                    if (notifReqLeaveList.get(i).getName().equals(firstname + " " + lastname)) {
                        list.add(notifReqLeaveList.get(i));
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);


                noticeFurloughAdapter = new HomeAdapter(getActivity(), list);
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                rv.setAdapter(noticeFurloughAdapter);
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        init();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    apiHandler.getNotifReqLeave(userDetails.getUserInfo().getString("personalIdmaster"), new ApiHandler.ResponseListenerNotifReqLeave() {
                        @Override
                        public void onRevived(List<Furlough> notifReqLeaveList) {
                            progressBar.setVisibility(View.INVISIBLE);
                            list.clear();
                            if (notifReqLeaveList.size() == 0) {
                                nothingFoundContainer.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setRefreshing(false);
                            } else {
                                nothingFoundContainer.setVisibility(View.INVISIBLE);
                                for (int i = 0; i < notifReqLeaveList.size(); i++) {
                                    if (notifReqLeaveList.get(i).getName().equals(firstname + " " + lastname)) {
                                        list.add(notifReqLeaveList.get(i));
                                    }
                                }

                                noticeFurloughAdapter = new HomeAdapter(getActivity(), list);
                                rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                                rv.setAdapter(noticeFurloughAdapter);
                                noticeFurloughAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }


}
