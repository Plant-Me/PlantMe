package com.plantme.plantme.fragment;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.R;
import com.plantme.plantme.ReportActionDialogFragment;
import com.plantme.plantme.SwipeController;
import com.plantme.plantme.SwipeControllerActions;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.adapter.ActionWeekViewAdapter;


import com.plantme.plantme.MainActivity;


import com.plantme.plantme.model.UserAction;
import com.plantme.plantme.model.UserPlant;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
    private ActionWeekViewAdapter actionWeekViewAdapterToday;
    private RecyclerView recyclerViewNextDays;
    private ActionWeekViewAdapter actionWeekViewAdapterNextDays;
    private RecyclerView recyclerViewPast;
    private ActionWeekViewAdapter actionWeekViewAdapterPast;

    private List<CoupleActionDate> listCoupleActionDate;
    private List<CoupleActionDate> listCoupleActionDateToday;
    private List<CoupleActionDate> listCoupleActionDateNextDays;
    private List<CoupleActionDate> listCoupleActionDatePast;
    private TextView tvNextDays;
    private TextView tvPastDays;

    private View view;

    SwipeController swipeControllerToday = null;
    SwipeController swipeControllerPast = null;

    private RecyclerView.ViewHolder currentItemViewHolder = null;


    public HomeWeekFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_week, container, false);

        mainActivity = (MainActivity) getContext();
        tvNextDays = view.findViewById(R.id.nextDays);
        tvPastDays = view.findViewById(R.id.pastDays);

        //setUpRecyclerView(view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ActionBar ab = ((MainActivity) getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);

        super.onViewCreated(view, savedInstanceState);



    }


    private void setUpRecyclerView(View view) {
        recyclerViewToday = view.findViewById(R.id.rvActionsToday);
        recyclerViewNextDays = view.findViewById(R.id.rvNextActions);
        recyclerViewPast = view.findViewById(R.id.rvPastActions);
//        listCoupleActionDate = mainActivity.getListCoupleActionDate();
        List<UserPlant> userPlantList = mainActivity.getPlantUserList();

        listCoupleActionDate = new ArrayList<>();
        for (UserPlant userPlant : userPlantList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }
        listCoupleActionDateToday = new ArrayList<>();
        listCoupleActionDateNextDays = new ArrayList<>();
        listCoupleActionDatePast = new ArrayList<>();

        //On associe une action à un recyclerView en fonction de la date de l'action
        for (CoupleActionDate coupleActionDate : listCoupleActionDate) {
            Calendar today = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

            String dateToday = formatter.format(today.getTime());
            Date coupleDate = coupleActionDate.getDateActuelle();
            String coupleDateString = formatter.format(coupleDate);

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(today.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date nextSevenDays = calendar.getTime();

            if (coupleDateString.equals(dateToday)) {
                listCoupleActionDateToday.add(coupleActionDate);
            } else if (coupleActionDate.getDateActuelle().after(today.getTime()) && coupleActionDate.getDateActuelle().before(nextSevenDays)) {
                listCoupleActionDateNextDays.add(coupleActionDate);
            } else if (coupleActionDate.getDateActuelle().before(today.getTime())) {
                listCoupleActionDatePast.add(coupleActionDate);
            }
        }

        Collections.sort(listCoupleActionDateToday);
        Collections.sort(listCoupleActionDatePast);
        Collections.sort(listCoupleActionDateNextDays);

        actionWeekViewAdapterToday = new ActionWeekViewAdapter(listCoupleActionDateToday);
        recyclerViewToday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewToday.setAdapter(actionWeekViewAdapterToday);

        actionWeekViewAdapterNextDays = new ActionWeekViewAdapter(listCoupleActionDateNextDays);
        recyclerViewNextDays.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNextDays.setAdapter(actionWeekViewAdapterNextDays);

        actionWeekViewAdapterPast = new ActionWeekViewAdapter(listCoupleActionDatePast);
        recyclerViewPast.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPast.setAdapter(actionWeekViewAdapterPast);

        if (listCoupleActionDateNextDays.isEmpty()) {
            tvNextDays.setVisibility(View.GONE);
            recyclerViewNextDays.setVisibility(View.GONE);
        }

        if (listCoupleActionDatePast.isEmpty()) {
            tvPastDays.setVisibility(View.GONE);
            recyclerViewPast.setVisibility(View.GONE);
        } else {
            swipeControllerPast = new SwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                }

                @Override
                public void onLeftClicked(int position) {
                    removeAction(actionWeekViewAdapterPast, position);
                }
            }, false);

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
                dialog.show(((MainActivity) getContext()).getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onLeftClicked(int position) {
                removeAction(actionWeekViewAdapterToday, position);
            }
        }, true);

        ItemTouchHelper itemTouchHelperToday = new ItemTouchHelper(swipeControllerToday);
        itemTouchHelperToday.attachToRecyclerView(recyclerViewToday);

        recyclerViewToday.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeControllerToday.onDraw(c);
            }
        });
    }

    public void removePositionToday(ActionWeekViewAdapter actionWeekViewAdapter, int position) {
        actionWeekViewAdapter.getListCoupleActionDates().remove(position);
        actionWeekViewAdapter.notifyDataSetChanged();
    }

    public void addPositionNextDays(int position) {
        CoupleActionDate coupleActionDate = actionWeekViewAdapterToday.getListCoupleActionDates().get(position);
        listCoupleActionDateNextDays.add(coupleActionDate);
        tvNextDays.setVisibility(View.VISIBLE);
        recyclerViewNextDays.setVisibility(View.VISIBLE);
        actionWeekViewAdapterNextDays.notifyDataSetChanged();
    }

    public void setNewDate(CoupleActionDate coupleActionDate, int days, int position) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(coupleActionDate.getDateActuelle());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date newDate = calendar.getTime();
        coupleActionDate.setDateActuelle(newDate);

        addPositionNextDays(position);
        removePositionToday(actionWeekViewAdapterToday, position);
    }

    public void removeAction(ActionWeekViewAdapter actionWeekViewAdapter, int position) {
        CoupleActionDate coupleActionDate = actionWeekViewAdapter.getListCoupleActionDates().get(position);
        if (!coupleActionDate.getRepetition().equals("")) {
            createNextRepetition(coupleActionDate, position);
        }

        coupleActionDate.getUserPlant().getListCoupleActionDate().remove(coupleActionDate);
        actionWeekViewAdapter.getListCoupleActionDates().remove(position);

        actionWeekViewAdapter.notifyDataSetChanged();
        notifyDataSetChanged();

        if (actionWeekViewAdapterNextDays.getListCoupleActionDates().size() > 0) {
            tvNextDays.setVisibility(View.VISIBLE);
            recyclerViewNextDays.setVisibility(View.VISIBLE);
        }
    }

    public void createNextRepetition(CoupleActionDate coupleActionDate, int position) {
        String typeRepetition = coupleActionDate.getTypeRepetition();
        int valeurRepetition = coupleActionDate.getValeurRepetition();
        Date dateInitialeRepetiton = coupleActionDate.getDateInitiale();
        UserPlant userPlant = coupleActionDate.getUserPlant();
        UserAction userAction = coupleActionDate.getUserAction();

        Calendar today = new GregorianCalendar();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateInitialeRepetiton);

        switch (typeRepetition) {
            case "Jours":
                today.add(Calendar.DAY_OF_MONTH, valeurRepetition);
                do {
                    calendar.add(Calendar.DAY_OF_MONTH, valeurRepetition);
                } while (calendar.getTime().before(today.getTime()));
                break;
            case "Semaines":
                today.add(Calendar.WEEK_OF_YEAR, valeurRepetition);
                do {
                    calendar.add(Calendar.WEEK_OF_YEAR, valeurRepetition);
                } while (calendar.getTime().before(today.getTime()));
                break;
            case "Mois":
                today.add(Calendar.MONTH, valeurRepetition);
                do {
                    calendar.add(Calendar.MONTH, valeurRepetition);
                } while (calendar.getTime().before(today.getTime()));
                break;
        }

        Date newDate = calendar.getTime();
        CoupleActionDate nextRepetitionAction = new CoupleActionDate(userPlant, userPlant.getPlantName(), userAction, newDate, typeRepetition, valeurRepetition);
        userPlant.getListCoupleActionDate().add(nextRepetitionAction);
    }


    public void updateUserActions(CoupleActionDate coupleActionDate, String report, int position) {
        switch (report) {
            case "1 day":
                setNewDate(coupleActionDate, 1, position);
                break;
            case "2 days":
                setNewDate(coupleActionDate, 2, position);
                break;
            case "3 days":
                setNewDate(coupleActionDate, 3, position);
                break;
            case "4 days":
                setNewDate(coupleActionDate, 4, position);
                break;
            case "5 days":
                setNewDate(coupleActionDate, 5, position);
                break;
            case "6 days":
                setNewDate(coupleActionDate, 6, position);
                break;
            case "7 days":
                setNewDate(coupleActionDate, 7, position);
                break;
        }
    }

    public void notifyDataSetChanged() {
        updateListsFromMain();
        actionWeekViewAdapterPast.notifyDataSetChanged();
        actionWeekViewAdapterNextDays.notifyDataSetChanged();
        actionWeekViewAdapterToday.notifyDataSetChanged();
    }


    public void updateListsFromMain() {
        List<UserPlant> userPlantList = mainActivity.getPlantUserList();

        listCoupleActionDate.clear();
        for (UserPlant userPlant : userPlantList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }
        listCoupleActionDateToday.clear();
        listCoupleActionDateNextDays.clear();
        listCoupleActionDatePast.clear();

        //On associe une action à un recyclerView en fonction de la date de l'action
        for (CoupleActionDate coupleActionDate : listCoupleActionDate) {
            Calendar today = new GregorianCalendar();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

            String dateToday = formatter.format(today.getTime());
            Date coupleDate = coupleActionDate.getDateActuelle();
            String coupleDateString = formatter.format(coupleDate);

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(today.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            Date nextSevenDays = calendar.getTime();

            if (coupleDateString.equals(dateToday)) {
                listCoupleActionDateToday.add(coupleActionDate);
            } else if (coupleActionDate.getDateActuelle().after(today.getTime()) && coupleActionDate.getDateActuelle().before(nextSevenDays)) {
                listCoupleActionDateNextDays.add(coupleActionDate);
            } else if (coupleActionDate.getDateActuelle().before(today.getTime())) {
                listCoupleActionDatePast.add(coupleActionDate);
            }
        }

        Collections.sort(listCoupleActionDateToday);
        Collections.sort(listCoupleActionDatePast);
        Collections.sort(listCoupleActionDateNextDays);
    }


}


