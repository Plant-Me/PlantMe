package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.model.databaseEntity.CoupleActionDate;
import com.plantme.plantme.adapter.ActionViewAdapter;
import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.model.databaseEntity.UserAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.text.format.DateFormat;

import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWeekFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerViewToday;
    private ActionViewAdapter actionViewAdapterToday;
    private RecyclerView recyclerViewNextDays;
    private ActionViewAdapter actionViewAdapterNextDays;
    private List<CoupleActionDate> listCoupleActionDate;
    private List<CoupleActionDate> listCoupleActionDateToday;
    private List<CoupleActionDate> listCoupleActionDateNextDays;
    private DateFormat dateFormat;

    public HomeWeekFragment() {
        // Required empty public constructor

        UserAction actionPlant = new UserAction("Arroser" );

        CoupleActionDate coupleActionDate = new CoupleActionDate("Mon bichon", actionPlant, new Date("14/01/2019"));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_week, container, false);

        mainActivity = (MainActivity)getContext();

        //setUpRecyclerView(view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        super.onViewCreated(view, savedInstanceState);



    }

    private void setUpRecyclerView(View view) {
        recyclerViewToday = view.findViewById(R.id.rvActionsToday);
        recyclerViewNextDays = view.findViewById(R.id.rvNextActions);
        listCoupleActionDate = mainActivity.getListCoupleActionDate();
        listCoupleActionDateToday = new ArrayList<>();
        listCoupleActionDateNextDays = new ArrayList<>();

        for(CoupleActionDate coupleActionDate : listCoupleActionDate) {
            Calendar today = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

            String dateToday = formatter.format(today.getTime());
            Date coupleDate = coupleActionDate.getDate();
            String coupleDateString = formatter.format(coupleDate);

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(today.getTime());
            calendar.add(coupleDate.getDay(), +7);
            Date nextSevenDays = calendar.getTime();

            if(coupleDateString.equals(dateToday)) {
                listCoupleActionDateToday.add(coupleActionDate);
            } else if (coupleActionDate.getDate().after(today.getTime()) && coupleActionDate.getDate().before(nextSevenDays)) {
                listCoupleActionDateNextDays.add(coupleActionDate);
            }
        }


        actionViewAdapterToday = new ActionViewAdapter(listCoupleActionDateToday);
        actionViewAdapterNextDays = new ActionViewAdapter(listCoupleActionDateNextDays);

        //plantDetailsFragment = mainActivity.getPlantDetailsFragment();

        recyclerViewToday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewToday.setAdapter(actionViewAdapterToday);


        recyclerViewNextDays.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNextDays.setAdapter(actionViewAdapterNextDays);

//        recyclerViewToday.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewToday, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Plant plant = actionViewAdapterToday.getPlantList().get(position);
//                ((PlantDetailsFragment) plantDetailsFragment).setPlant(plant);
//                mainActivity.replaceFragment(plantDetailsFragment);
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                Plant plant = actionViewAdapterToday.getPlantList().get(position);
//                ((PlantDetailsFragment) plantDetailsFragment).setPlant(plant);
//                mainActivity.replaceFragment(plantDetailsFragment);
//            }
//        }));
    }
}
