package com.plantme.plantme.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.adapter.ActionCalendarViewAdapter;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.UserPlant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMonthFragment extends Fragment {

    private MainActivity mainActivity;

    private CalendarView calendarView;
    private RecyclerView recyclerView;
    private ActionCalendarViewAdapter actionCalendarViewAdapter;
    private List<CoupleActionDate> listCoupleActionDate;
    private List<CoupleActionDate> listCoupleActionDateOfDay;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
    private String dateToday;
    private FloatingActionButton floatingActionButton;
    private GregorianCalendar date;
    private Calendar today;


    private  List<UserPlant> userPlantList;
    public HomeMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_month, container, false);

        mainActivity = (MainActivity)getContext();

        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                listCoupleActionDate.clear();
                for(UserPlant userPlant : userPlantList) {
                    listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
                }
                listCoupleActionDateOfDay.clear();
                date = new GregorianCalendar(year, month, dayOfMonth);
                String formattedDate = formatter.format(date.getTime());

                if(formattedDate.equals(dateToday) || (date.before(today)) ) {
                    floatingActionButton.setVisibility(View.GONE);
                } else {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    setActionFloatingButton(floatingActionButton);
                }

                //On associe une action au recyclerview en fonction de la date de l'action
                for (CoupleActionDate coupleActionDate : listCoupleActionDate) {
                    Date coupleDate = coupleActionDate.getDateActuelle();
                    String coupleDateString = formatter.format(coupleDate);

                    if (coupleDateString.equals(formattedDate) && !coupleActionDate.isValidated()) {
                        listCoupleActionDateOfDay.add(coupleActionDate);
                    }
                }

                if (listCoupleActionDateOfDay.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }

                actionCalendarViewAdapter.notifyDataSetChanged();

            }
        });


        setUpRecyclerView(view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rvCalendarActions);
        floatingActionButton = view.findViewById(R.id.floatingActionButtonAddAction);

//        listCoupleActionDate = mainActivity.getListCoupleActionDate();
        userPlantList = mainActivity.getPlantUserList();
        listCoupleActionDateOfDay = new ArrayList<>();


        listCoupleActionDate = new ArrayList<>();
        for(UserPlant userPlant : userPlantList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }

        //On associe une action au recyclerview en fonction de la date de l'action
        for (CoupleActionDate coupleActionDate : listCoupleActionDate) {
            today = new GregorianCalendar();
            dateToday = formatter.format(today.getTime());
            Date coupleDate = coupleActionDate.getDateActuelle();
            String coupleDateString = formatter.format(coupleDate);

            if (coupleDateString.equals(dateToday) && coupleActionDate.isValidated() == false) {
                listCoupleActionDateOfDay.add(coupleActionDate);
            }
        }

        actionCalendarViewAdapter = new ActionCalendarViewAdapter(listCoupleActionDateOfDay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(actionCalendarViewAdapter);

        if (listCoupleActionDateOfDay.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        }

        floatingActionButton.setVisibility(View.GONE);
    }

    public void setActionFloatingButton(FloatingActionButton floatingButton) {
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectActionPlantsFragment selectActionPlantsFragment = (SelectActionPlantsFragment)mainActivity.getSelectActionFragment();

                selectActionPlantsFragment.setSelectedDate(date.getTime());
                mainActivity.replaceFragment(selectActionPlantsFragment);
            }
        });
    }

    public void notifyDataSetChanged() {
        actionCalendarViewAdapter.notifyDataSetChanged();
    }

    /*public void
    public ActionCalendarViewAdapter getActionCalendarViewAdapter() {
        return actionCalendarViewAdapter;
    }*/
}
