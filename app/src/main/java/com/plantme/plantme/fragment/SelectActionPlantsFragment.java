package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
//import com.plantme.plantme.adapter.SelectActionPlantsViewAdapter;
import com.plantme.plantme.RecyclerTouchListener;
import com.plantme.plantme.ReportActionDialogFragment;
import com.plantme.plantme.SelectActionDialogFragment;
import com.plantme.plantme.adapter.SelectActionPlantsViewAdapter;
import com.plantme.plantme.adapter.UserPlantViewAdapter;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.UserAction;
import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectActionPlantsFragment extends Fragment {

    private MainActivity mainActivity;

    private RecyclerView recyclerView;
    private SelectActionPlantsViewAdapter selectActionPlantsViewAdapter;
    private List<UserPlant> listUserPlants;
    private List<UserAction> listUserActions;
    private List<String> listUserActionsNames;
    private UserPlant userPlant;
    private Spinner spin;
    private Date selectedDate;

    public SelectActionPlantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_action_plants, container, false);

        mainActivity = (MainActivity) getContext();

        ActionBar ab = ((MainActivity) getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setUpRecyclerView(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rvActions);
        listUserPlants = mainActivity.getPlantUserList();
        listUserActions = mainActivity.getListUserActions();

        selectActionPlantsViewAdapter = new SelectActionPlantsViewAdapter(listUserPlants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(selectActionPlantsViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                userPlant = selectActionPlantsViewAdapter.getUserPlantList().get(position);

                SelectActionDialogFragment dialog = new SelectActionDialogFragment();
                dialog.setListUserActions(listUserActions);
                dialog.setUserAction(listUserActions.get(position));
                dialog.setSelectActionPlantsFragment(SelectActionPlantsFragment.this);
                dialog.setPosition(position);
                dialog.show(((MainActivity) getContext()).getSupportFragmentManager(), "dialog");
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    // Il faut ici créer une nouvelle userAction pour la userPlant récupérée au clic.
    public void updateUserActions(String typeAction, String typeRepetition, int valeurRepetition) {
        UserAction userAction = null;
        for(UserAction userActionSearch : listUserActions) {
            if(userActionSearch.getActionName().equals(typeAction)) {
                userAction = userActionSearch;
            }
        }

        CoupleActionDate coupleActionDate = new CoupleActionDate(userPlant ,userPlant.getPlantName(), userAction, selectedDate, typeRepetition, valeurRepetition);

        List<CoupleActionDate> userPlantListCoupleActionDate = userPlant.getListCoupleActionDate();

        userPlantListCoupleActionDate.add(coupleActionDate);

        ((HomeFragment) mainActivity.getHomeFragment()).notifyDataSetChanged();
    }


    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }
}
