package com.plantme.plantme.fragment;


import android.drm.DrmStore;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.ReportActionDialogFragment;
import com.plantme.plantme.SwipeController;
import com.plantme.plantme.SwipeControllerActions;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.adapter.ActionViewAdapter;
import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWeekFragment extends Fragment {

    private MainActivity mainActivity;

    private RecyclerView recyclerViewToday;
    private ActionViewAdapter actionViewAdapterToday;
    private RecyclerView recyclerViewNextDays;
    private ActionViewAdapter actionViewAdapterNextDays;
    private RecyclerView recyclerViewPast;
    private ActionViewAdapter actionViewAdapterPast;

    private List<CoupleActionDate> listCoupleActionDate;
    private List<CoupleActionDate> listCoupleActionDateToday;
    private List<CoupleActionDate> listCoupleActionDateNextDays;
    private List<CoupleActionDate> listCoupleActionDatePast;
    private TextView tvNextDays;
    private TextView tvPastDays;

    SwipeController swipeControllerToday = null;
    SwipeController swipeControllerPast = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;


    public HomeWeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_week, container, false);

        mainActivity = (MainActivity)getContext();
        tvNextDays = view.findViewById(R.id.nextDays);

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

    private void setUpRecyclerView(View view) {
        recyclerViewToday = view.findViewById(R.id.rvActionsToday);
        recyclerViewNextDays = view.findViewById(R.id.rvNextActions);
        recyclerViewPast = view.findViewById(R.id.rvPastActions);
        listCoupleActionDate = mainActivity.getListCoupleActionDate();
        listCoupleActionDateToday = new ArrayList<>();
        listCoupleActionDateNextDays = new ArrayList<>();
        listCoupleActionDatePast = new ArrayList<>();

        //On associe une action à un recyclerView en fonction de la date de l'action
        for(CoupleActionDate coupleActionDate : listCoupleActionDate) {
            Calendar today = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

            String dateToday = formatter.format(today.getTime());
            Date coupleDate = coupleActionDate.getDate();
            String coupleDateString = formatter.format(coupleDate);

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(today.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date nextSevenDays = calendar.getTime();

            if(coupleDateString.equals(dateToday)) {
                listCoupleActionDateToday.add(coupleActionDate);
            } else if (coupleActionDate.getDate().after(today.getTime()) && coupleActionDate.getDate().before(nextSevenDays)) {
                listCoupleActionDateNextDays.add(coupleActionDate);
            } else if (coupleActionDate.getDate().before(today.getTime()) && coupleActionDate.isValidated() == false) {
                listCoupleActionDatePast.add(coupleActionDate);
            }
        }

        actionViewAdapterToday = new ActionViewAdapter(listCoupleActionDateToday);
        recyclerViewToday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewToday.setAdapter(actionViewAdapterToday);

        actionViewAdapterNextDays = new ActionViewAdapter(listCoupleActionDateNextDays);
        recyclerViewNextDays.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNextDays.setAdapter(actionViewAdapterNextDays);

        actionViewAdapterPast = new ActionViewAdapter(listCoupleActionDatePast);
        recyclerViewPast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPast.setAdapter(actionViewAdapterPast);

        if(listCoupleActionDateNextDays.isEmpty()) {
            tvNextDays.setVisibility(View.GONE);
            recyclerViewNextDays.setVisibility(View.GONE);
        }

        if(listCoupleActionDatePast.isEmpty()) {
            tvPastDays.setVisibility(View.GONE);
            recyclerViewPast.setVisibility(View.GONE);
        } else {
            swipeControllerPast = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                }

                @Override
                public void onLeftClicked(int position) {
                    removePositionToday(actionViewAdapterPast, position);
                    CoupleActionDate coupleActionDate = listCoupleActionDatePast.get(position);
                    coupleActionDate.setValidated(true);
                }
            }, false);
//            swipeControllerPast.setActionsToday(false);

            ItemTouchHelper itemTouchHelperPastDays = new ItemTouchHelper(swipeControllerPast);
            itemTouchHelperPastDays.attachToRecyclerView(recyclerViewPast);

            recyclerViewPast.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    swipeControllerPast.onDraw(c);
                }
            });

        }

        swipeControllerToday = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                ReportActionDialogFragment dialog = new ReportActionDialogFragment();
                dialog.setCoupleActionDate(listCoupleActionDateToday.get(position));
                dialog.setHomeWeekFragment(HomeWeekFragment.this);
                dialog.setPosition(position);
                dialog.show(((MainActivity)getContext()).getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onLeftClicked(int position) {
                removePositionToday(actionViewAdapterToday, position);
                CoupleActionDate coupleActionDate = listCoupleActionDateToday.get(position);
                coupleActionDate.setValidated(true);
            }
        }, true);
//        swipeControllerToday.setActionsToday(true);

        ItemTouchHelper itemTouchHelperToday = new ItemTouchHelper(swipeControllerToday);
        itemTouchHelperToday.attachToRecyclerView(recyclerViewToday);

        recyclerViewToday.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeControllerToday.onDraw(c);
            }
        });
    }

    public void removePositionToday(ActionViewAdapter actionViewAdapter, int position) {
        actionViewAdapter.getListCoupleActionDates().remove(position);
        actionViewAdapter.notifyDataSetChanged();
    }

    public void addPositionNextDays(int position) {
        CoupleActionDate coupleActionDate = actionViewAdapterToday.getListCoupleActionDates().get(position);
        listCoupleActionDateNextDays.add(coupleActionDate);
        tvNextDays.setVisibility(View.VISIBLE);
        recyclerViewNextDays.setVisibility(View.VISIBLE);
        actionViewAdapterNextDays.notifyDataSetChanged();
    }

    public void setNewDate(CoupleActionDate coupleActionDate, int days, int position) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(coupleActionDate.getDate());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date newDate = calendar.getTime();
        coupleActionDate.setDate(newDate);

        addPositionNextDays(position);
        removePositionToday(actionViewAdapterToday, position);
    }



    public void updateUserActions(CoupleActionDate coupleActionDate, String report, int position) {
        switch (report) {
            case "1 day" :
                setNewDate(coupleActionDate, 1, position);
                break;
            case "2 days" :
                setNewDate(coupleActionDate, 2, position);
                break;
            case "3 days" :
                setNewDate(coupleActionDate, 3, position);
                break;
            case "4 days" :
                setNewDate(coupleActionDate, 4, position);
                break;
            case "5 days" :
                setNewDate(coupleActionDate, 5, position);
                break;
            case "6 days" :
                setNewDate(coupleActionDate, 6, position);
                break;
            case "7 days" :
                setNewDate(coupleActionDate, 7, position);
                break;
        }
    }
}


