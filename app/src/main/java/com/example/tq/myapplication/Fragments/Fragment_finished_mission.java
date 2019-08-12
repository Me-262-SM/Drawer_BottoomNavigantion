package com.example.tq.myapplication.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tq.myapplication.Model.Prisoner;
import com.example.tq.myapplication.R;
import com.yalantis.phoenix.PullToRefreshView;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_finished_mission extends Fragment {
    private RecyclerView recyclerView;
    private PullToRefreshView mPullToRefreshView;
    private ArrayList<Prisoner> list = new ArrayList<>();

    public Fragment_finished_mission() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_mission, container, false);
        //下拉刷新
        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh_finished);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                },2000);
            }
        });





        return view;
    }



}
