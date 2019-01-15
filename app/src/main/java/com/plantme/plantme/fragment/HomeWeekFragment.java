package com.plantme.plantme.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.ReportActionDialogFragment;
import com.plantme.plantme.SwipeController;
import com.plantme.plantme.SwipeControllerActions;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.adapter.ActionViewAdapter;
import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.model.UserAction;

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

    SwipeController swipeController = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;


    public HomeWeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_week, container, false);

        mainActivity = (MainActivity)getContext();

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
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date nextSevenDays = calendar.getTime();

            if(coupleDateString.equals(dateToday)) {
                listCoupleActionDateToday.add(coupleActionDate);
            } else if (coupleActionDate.getDate().after(today.getTime()) && coupleActionDate.getDate().before(nextSevenDays)) {
                listCoupleActionDateNextDays.add(coupleActionDate);
            }
        }

        actionViewAdapterToday = new ActionViewAdapter(listCoupleActionDateToday);
        actionViewAdapterNextDays = new ActionViewAdapter(listCoupleActionDateNextDays);

        recyclerViewToday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewToday.setAdapter(actionViewAdapterToday);

        recyclerViewNextDays.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNextDays.setAdapter(actionViewAdapterNextDays);



        swipeController = new SwipeController(new SwipeControllerActions() {
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
                removePositionToday(position);
            }
        });

        ItemTouchHelper itemTouchHelperToday = new ItemTouchHelper(swipeController);
        itemTouchHelperToday.attachToRecyclerView(recyclerViewToday);

        recyclerViewToday.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    public void removePositionToday(int position) {
        actionViewAdapterToday.getListCoupleActionDates().remove(position);
//        actionViewAdapterToday.notifyItemRemoved(position);
//        actionViewAdapterToday.notifyItemRangeChanged(position, actionViewAdapterToday.getItemCount());
        actionViewAdapterToday.notifyDataSetChanged();
    }

    public void addPositionNextDays(int position) {
        CoupleActionDate coupleActionDate = actionViewAdapterToday.getListCoupleActionDates().get(position);
        listCoupleActionDateNextDays.add(coupleActionDate);
//        actionViewAdapterNextDays.notifyItemRangeChanged(position, actionViewAdapterNextDays.getItemCount());
        actionViewAdapterNextDays.notifyDataSetChanged();
    }

    public void setNewDate(CoupleActionDate coupleActionDate, int days, int position) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(coupleActionDate.getDate());
        calendar.add(Calendar.DAY_OF_MONTH, days);
//        calendar.add(coupleActionDate.getDate().getDay(), +days);
        Date newDate = calendar.getTime();
        coupleActionDate.setDate(newDate);

        addPositionNextDays(position);
        removePositionToday(position);
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


