package com.plantme.plantme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.plantme.plantme.model.Plant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private PlantViewAdapter plantViewAdapter;
    List<Plant> plantList = new ArrayList<>();
    private SearchView searchView;

    public MyPlantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_plants, container, false);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        Plant orchidee = new Plant("orchidee", "orchideum", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", "blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", "jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", "rose", "arbre", "exposition", "sol", "verger");

        plantList.add(orchidee);
        plantList.add(bonsai);
        plantList.add(abricotier);
        plantList.add(cerisier);

        setUpRecyclerView(view);

        // Inflate the layout for this fragment
        return view;

    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewPlants);
        plantViewAdapter = new PlantViewAdapter(plantList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(plantViewAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.d("searchview", "searchview :  " + s);
        return false;
    }
}
