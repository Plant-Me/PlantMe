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
import android.widget.Button;
import android.widget.TextView;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.adapter.ActionPlantDetailAdapter;
import com.plantme.plantme.adapter.Calendrier;
import com.plantme.plantme.adapter.CalendrierViewAdapter;
import com.plantme.plantme.model.ActionCalendrier;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlantDetailsFragment extends Fragment {


    private UserPlant userPlant;
    private TextView nickname;
    private TextView nomFrancais;
    private TextView nomLatin;
    private TextView famille;
    private TextView description;
    private TextView descriptionDetail;
    private TextView type;
    private TextView typeDetail;
    private TextView exposition;
    private TextView expositionDetail;
    private TextView usages;
    private TextView usagesDetail;
    private TextView couleurs;
    private TextView couleursDetail;
    private TextView sol;
    private TextView solDetail;
    private TextView calendriers;
    private TextView actions;
    private RecyclerView rvCalendriersDetail;
    private RecyclerView rvActionsDetail;
    private Button buttonShowCalendriers;
    private Button buttonShowActions;

    private boolean areCalendarsShown;
    private boolean areActionsShown;



    View view;

    public PlantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        
        nickname = view.findViewById(R.id.plantDetailNickname);
        nomFrancais = view.findViewById(R.id.plantDetailFrName);
        nomLatin = view.findViewById(R.id.plantDetailsLatinName);
        famille = view.findViewById(R.id.plantDetailFamille);
        description = view.findViewById(R.id.description);
        descriptionDetail = view.findViewById(R.id.plantDetailsDescription);
        type = view.findViewById(R.id.type);
        typeDetail = view.findViewById(R.id.plantDetailsType);
        exposition = view.findViewById(R.id.exposition);
        expositionDetail = view.findViewById(R.id.plantDetailsExposition);
        usages = view.findViewById(R.id.usages);
        usagesDetail = view.findViewById(R.id.plantDetailsUsages);
        couleurs = view.findViewById(R.id.couleurs);
        couleursDetail = view.findViewById(R.id.plantDetailsCouleurs);
        sol = view.findViewById(R.id.sol);
        solDetail = view.findViewById(R.id.plantDetailsSol);
        calendriers = view.findViewById(R.id.calendriers);
        actions = view.findViewById(R.id.actions);
        rvCalendriersDetail = view.findViewById(R.id.plantDetailsRecyclerViewCalendriers);
        rvActionsDetail = view.findViewById(R.id.plantDetailsRecyclerViewActions);
        buttonShowCalendriers = view.findViewById(R.id.buttonShowCalendriers);
        buttonShowActions = view.findViewById(R.id.buttonShowActions);


        bindPlant(userPlant.getPlant());
        bindUserPlant();

        rvCalendriersDetail.setVisibility(View.GONE);
        rvActionsDetail.setVisibility(View.GONE);
        buttonShowCalendriers.setOnClickListener(onClick());
        buttonShowActions.setOnClickListener(onClick());
        areCalendarsShown = false;
        areActionsShown = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_details, container, false);

        this.view = view;



        // Inflate the layout for this fragment
        return view;
    }

    public void bindPlant(Plant plant) {
        nomFrancais.setText(plant.getFrName());
        if (plant.getLtnName().equals("")) {
            nomLatin.setVisibility(View.GONE);
        } else {
            nomLatin.setText(plant.getLtnName());
        }

        if (!plant.getFamillePlante().getNomFrancais().equals("") && !plant.getFamillePlante().getNomLatin().equals("")) {
            famille.setText(plant.getFamillePlante().getNomFrancais() + " / " + plant.getFamillePlante().getNomLatin());
        } else if (!plant.getFamillePlante().getNomFrancais().equals("")) {
            famille.setText(plant.getFamillePlante().getNomFrancais());
        } else if (!plant.getFamillePlante().getNomLatin().equals("")) {
            famille.setText(plant.getFamillePlante().getNomLatin());
        } else {
            famille.setVisibility(View.GONE);
        }

        if (plant.getDescription().equals("")) {
            description.setVisibility(View.GONE);
            descriptionDetail.setVisibility(View.GONE);
        } else {
            descriptionDetail.setText(plant.getDescription());
        }

        if (plant.getType().equals("")) {
            type.setVisibility(View.GONE);
            typeDetail.setVisibility(View.GONE);
        } else {
//        TODO do all types
            typeDetail.setText(plant.getType());
        }

        if (plant.getExposition().equals("")) {
            exposition.setVisibility(View.GONE);
            expositionDetail.setVisibility(View.GONE);
        } else {
            expositionDetail.setText(plant.getExposition());
        }

        if (plant.getUsage().equals("")) {
            usages.setVisibility(View.GONE);
            usagesDetail.setVisibility(View.GONE);
        } else {
            usagesDetail.setText(plant.getUsage());
        }

        if (plant.getFlowerColor().equals("")) {
            couleurs.setVisibility(View.GONE);
            couleursDetail.setVisibility(View.GONE);
        } else {
            couleursDetail.setText(plant.getFlowerColor());
        }

        if (plant.getGround().equals("")) {
            sol.setVisibility(View.GONE);
            solDetail.setVisibility(View.GONE);
        } else {
            solDetail.setText(plant.getGround());
        }

        if (plant.getActionCalendrier().isEmpty()) {
            calendriers.setVisibility(View.GONE);
            buttonShowCalendriers.setVisibility(View.GONE);
            rvCalendriersDetail.setVisibility(View.GONE);
        } else {
            List<ActionCalendrier> actionCalendrierList = plant.getActionCalendrier();
            List<Calendrier> calendrierList = new ArrayList<>();
            List<String> actionlist = new ArrayList<>();
            for (ActionCalendrier actionCalendrier : actionCalendrierList) {
                if (!actionlist.contains(actionCalendrier.getType())) {
                    String currentAction = actionCalendrier.getType();
                    Calendrier newCalendrier = new Calendrier(currentAction);
                    actionlist.add(currentAction);
                    for (ActionCalendrier actionCalendrierbis : actionCalendrierList) {
                        if (actionCalendrierbis.getType().equals(currentAction)) {
                            switch (actionCalendrierbis.getIdMois()) {
                                case 1:
                                    newCalendrier.setJanvier(true);
                                    break;
                                case 2:
                                    newCalendrier.setFevrier(true);
                                    break;
                                case 3:
                                    newCalendrier.setMars(true);
                                    break;
                                case 4:
                                    newCalendrier.setAvril(true);
                                    break;
                                case 5:
                                    newCalendrier.setMai(true);
                                    break;
                                case 6:
                                    newCalendrier.setJuin(true);
                                    break;
                                case 7:
                                    newCalendrier.setJuillet(true);
                                    break;
                                case 8:
                                    newCalendrier.setAout(true);
                                    break;
                                case 9:
                                    newCalendrier.setSeptembre(true);
                                    break;
                                case 10:
                                    newCalendrier.setOctobre(true);
                                    break;
                                case 11:
                                    newCalendrier.setNovembre(true);
                                    break;
                                case 12:
                                    newCalendrier.setDecembre(true);
                                    break;
                            }
                        }
                    }
                    calendrierList.add(newCalendrier);
                }
            }
            CalendrierViewAdapter calendrierViewAdapter = new CalendrierViewAdapter(calendrierList);
            rvCalendriersDetail.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCalendriersDetail.setAdapter(calendrierViewAdapter);
        }

    }

    public void bindUserPlant() {
        nickname.setText(userPlant.getPlantName());
        if(userPlant.getListCoupleActionDate().isEmpty()) {
            actions.setVisibility(View.GONE);
            buttonShowActions.setVisibility(View.GONE);
            rvActionsDetail.setVisibility(View.GONE);
        } else {
            ActionPlantDetailAdapter actionPlantDetailAdapter = new ActionPlantDetailAdapter(userPlant.getListCoupleActionDate());
            rvActionsDetail.setLayoutManager(new LinearLayoutManager(getContext()));
            rvActionsDetail.setAdapter(actionPlantDetailAdapter);
        }
    }

    public void setUserPlant(UserPlant userPlant) {
        if(this.userPlant == null || !this.userPlant.equals(userPlant)) {
            this.userPlant = userPlant;

        }
    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.buttonShowCalendriers:
                        if(areCalendarsShown) {
                            rvCalendriersDetail.setVisibility(View.GONE);
                            buttonShowCalendriers.setBackgroundResource(R.drawable.ic_arrow_down);
                            areCalendarsShown = false;
                        } else {
                            rvCalendriersDetail.setVisibility(View.VISIBLE);
                            buttonShowCalendriers.setBackgroundResource(R.drawable.ic_arrow_up);
                            areCalendarsShown = true;
                        }
                        break;
                    case R.id.buttonShowActions:
                        if(areActionsShown) {
                            rvActionsDetail.setVisibility(View.GONE);
                            buttonShowActions.setBackgroundResource(R.drawable.ic_arrow_down);
                            areActionsShown = false;
                        } else {
                            rvActionsDetail.setVisibility(View.VISIBLE);
                            buttonShowActions.setBackgroundResource(R.drawable.ic_arrow_up);
                            areActionsShown = true;
                        }
                        break;
                }
            }
        };
    }



}
