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
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
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
public class GeneralPlantDetailsFragment extends Fragment {

    private MainActivity mainActivity;
    private Plant plant;
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
    private RecyclerView rvCalendriersDetail;
    private Button buttonAjoutPlante;


    public GeneralPlantDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_plant_details, container, false);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity)getContext();

        ActionBar ab = ((MainActivity) getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        nomFrancais = view.findViewById(R.id.generalDetailFrName);
        nomLatin = view.findViewById(R.id.generalDetailsLatinName);
        famille = view.findViewById(R.id.generalDetailFamille);
        description = view.findViewById(R.id.description);
        descriptionDetail = view.findViewById(R.id.generalDetailsDescription);
        type = view.findViewById(R.id.type);
        typeDetail = view.findViewById(R.id.generalDetailsType);
        exposition = view.findViewById(R.id.exposition);
        expositionDetail = view.findViewById(R.id.generalDetailsExposition);
        usages = view.findViewById(R.id.usages);
        usagesDetail = view.findViewById(R.id.generalDetailsUsages);
        couleurs = view.findViewById(R.id.couleurs);
        couleursDetail = view.findViewById(R.id.generalDetailsCouleurs);
        sol = view.findViewById(R.id.sol);
        solDetail = view.findViewById(R.id.generalDetailsSol);
        calendriers = view.findViewById(R.id.calendriers);
        rvCalendriersDetail = view.findViewById(R.id.generalDetailsRecyclerView);

        buttonAjoutPlante = view.findViewById(R.id.buttonAjoutPlant);
        buttonAjoutPlante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjoutPlanteFragment ajoutPlanteFragment = (AjoutPlanteFragment)mainActivity.getAjoutPlanteFragment();
                ajoutPlanteFragment = new AjoutPlanteFragment();
                ajoutPlanteFragment.setSelectedPlant(plant);
                mainActivity.replaceFragment(ajoutPlanteFragment);
            }
        });

        bind(plant);
    }

    public void bind(Plant plant) {
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

    public void setPlant(Plant plant) {
        if (this.plant == null || !this.plant.equals(plant)) {
            this.plant = plant;
        }
    }

}
