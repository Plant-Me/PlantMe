package com.plantme.plantme.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.Service.PlantMeService;
import com.plantme.plantme.adapter.CalendrierViewAdapter;
import com.plantme.plantme.adapter.PlantViewAdapter;
import com.plantme.plantme.model.Calendrier;
import com.plantme.plantme.model.GlideApp;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.retrofitEntity.Action;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;
import com.plantme.plantme.model.retrofitEntity.ResultOnePlant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import com.plantme.plantme.model.Plant;
//import com.plantme.plantme.model.UserPlant;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralPlantDetailsFragment extends Fragment {

    private MainActivity mainActivity;
    private ResultOnePlant resultOnePlant;
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
    private ImageView imageView;

    private ResultAllPlant resultIntent;
    private int idPlanteToSearch;



    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if ("Data_plant".equals(intent.getAction()) == true)
            {
                //Les données sont passées et peuvent être récupérées via :
                // intent.getSerializableExtra("DATA_EXTRA");
                // intent.getIntExtra("DATA_EXTRA", 2);
                //etc.
                resultIntent = (ResultAllPlant) intent.getSerializableExtra("DATA_EXTRA");
                idPlanteToSearch = resultIntent.getIdPlante();
                Log.d("plante a cherche", "onReceive: " + idPlanteToSearch);
            }else {
                Log.d("plante a cherche", "jai rien recu");
            }
        }
    };
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
        plant = new Plant();
        mainActivity = (MainActivity)getContext();

        ActionBar ab = ((MainActivity) getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        imageView = view.findViewById(R.id.generalDetailImagePlant);
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
                plant.setFrName(resultOnePlant.getNomFr());
                plant.setExposition(resultOnePlant.getExposition());
                plant.setFlowerColor(resultOnePlant.getCouleurFleurs());
                plant.setGround(resultOnePlant.getSol());
                plant.setImageUrl(resultOnePlant.getImage().getUrl());
                plant.setType(resultOnePlant.getTypesToString());
                plant.setUsage(resultOnePlant.getUsageMilieu());
                plant.setActionCalendrier(resultOnePlant.getActionList());
                ajoutPlanteFragment.setSelectedPlant(plant);
                mainActivity.replaceFragment(ajoutPlanteFragment);
            }
        });

        bind();

        //bind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("Data_plant"));
    }



    public void bind() {


                GlideApp.with(imageView).load(resultOnePlant.getImage().getUrl()).override(500,500).placeholder(R.drawable.ic_green_tea).into(imageView);
                nomFrancais.setText(resultOnePlant.getNomFr());
                if (resultOnePlant.getNomLatin().equals("")) {
                    nomLatin.setVisibility(View.GONE);
                } else {
                    nomLatin.setText(resultOnePlant.getNomLatin());
                }

                if (!resultOnePlant.getFamille().getNom().equals("")) {
                    famille.setText(resultOnePlant.getFamille().getNom());
                } else {
                    famille.setVisibility(View.GONE);
                }

                if (resultOnePlant.getDescription().equals("")) {
                    description.setVisibility(View.GONE);
                    descriptionDetail.setVisibility(View.GONE);
                } else {
                    descriptionDetail.setText(resultOnePlant.getDescription());
                }

                if (resultOnePlant.getType().size()==0) {
                    type.setVisibility(View.GONE);
                    typeDetail.setVisibility(View.GONE);
                } else {
//        TODO do all types
                    typeDetail.setText(resultOnePlant.getTypesToString());
                }

                if (resultOnePlant.getExposition().equals("")) {
                    exposition.setVisibility(View.GONE);
                    expositionDetail.setVisibility(View.GONE);
                } else {
                    expositionDetail.setText(resultOnePlant.getExposition());
                }

                if (resultOnePlant.getUsageMilieu().equals("")) {
                    usages.setVisibility(View.GONE);
                    usagesDetail.setVisibility(View.GONE);
                } else {
                    usagesDetail.setText(resultOnePlant.getUsageMilieu());
                }

                if (resultOnePlant.getCouleurFleurs().equals("")) {
                    couleurs.setVisibility(View.GONE);
                    couleursDetail.setVisibility(View.GONE);
                } else {
                    couleursDetail.setText(resultOnePlant.getCouleurFleurs());
                }

                if (resultOnePlant.getSol().equals("")) {
                    sol.setVisibility(View.GONE);
                    solDetail.setVisibility(View.GONE);
                } else {
                    solDetail.setText(resultOnePlant.getSol());
                }if (resultOnePlant.getActions().size()==0) {
                    calendriers.setVisibility(View.GONE);
                    rvCalendriersDetail.setVisibility(View.GONE);
                } else {
                    List<Action> actionCalendrierList = resultOnePlant.getActions();
                    List<Calendrier> calendrierList = new ArrayList<>();
                    List<String> actionlist = new ArrayList<>();
                    for (Action actionCalendrier : actionCalendrierList) {
                        if (!actionlist.contains(actionCalendrier.getType())) {
                            String currentAction = actionCalendrier.getType();
                            Calendrier newCalendrier = new Calendrier(currentAction);
                            actionlist.add(currentAction);
                            for (Action actionCalendrierbis : actionCalendrierList) {
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

   public void setPlant(ResultOnePlant plant) {
        if (this.resultOnePlant == null || !this.resultOnePlant.equals(plant)) {
            this.resultOnePlant = plant;
        }
    }

}

