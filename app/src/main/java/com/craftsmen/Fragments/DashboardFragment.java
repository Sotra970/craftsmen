package com.craftsmen.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.craftsmen.Models.Model;
import com.craftsmen.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2/22/2017.
 */

public class DashboardFragment extends Fragment {
    //    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    ArrayList<Model> data = new ArrayList<>();
    View layout;

    public DashboardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_dashboard, container, false);

            recyclerView = (RecyclerView) layout.findViewById(R.id.craftsman_recyclerview);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false)  ;
//        recyclerView.setLayoutManager(linearLayoutManager);
//        adapter = new RecyclerViewAdapter(getContext(),data) ;
//        recyclerView.setAdapter(adapter);
//        getData();
        }
            return layout;
        }
    }
//    public void getData(){
//        for (int i =0 ; i<5 ; i++){
//            Model temp = new Model();
//            temp.setTitle("title1");
//            temp.setPic(R.drawable.craftsmen);
//            data.add(temp);
//        }
//        adapter.notifyDataSetChanged();
//    }


