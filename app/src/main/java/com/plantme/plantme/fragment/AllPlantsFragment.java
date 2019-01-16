package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.RecyclerTouchListener;
import com.plantme.plantme.adapter.PlantViewAdapter;
import com.plantme.plantme.adapter.UserPlantViewAdapter;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.UserPlant;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllPlantsFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private PlantViewAdapter plantViewAdapter;
    private SearchView searchView;


    private Fragment generalPlantDetailsFragment;

    public AllPlantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_plants, container, false);

        mainActivity = (MainActivity) getContext();
        setHasOptionsMenu(true);
        searchView = view.findViewById(R.id.searchViewAllPlants);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("", "onQueryTextChange: " + s);
                plantViewAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: " + s);
                plantViewAdapter.getFilter().filter(s);
                return false;
            }
        });

        setUpRecyclerView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewAllPlants);
        plantViewAdapter = new PlantViewAdapter(mainActivity.getPlantList());

        generalPlantDetailsFragment = mainActivity.getGeneralDetailPlantsFragment();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(plantViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Plant plant = plantViewAdapter.getPlantList().get(position);
                ((GeneralPlantDetailsFragment) generalPlantDetailsFragment).setPlant(plant);
                mainActivity.replaceFragment(generalPlantDetailsFragment);

            }

            @Override
            public void onLongClick(View view, int position) {
                Plant plant = plantViewAdapter.getPlantList().get(position);
                ((GeneralPlantDetailsFragment) generalPlantDetailsFragment).setPlant(plant);
                mainActivity.replaceFragment(generalPlantDetailsFragment);
            }
        }));
    }



}
