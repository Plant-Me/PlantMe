package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.MainActivity;

import com.plantme.plantme.R;
import com.plantme.plantme.RecyclerTouchListener;
import com.plantme.plantme.adapter.UserPlantViewAdapter;
import com.plantme.plantme.model.UserPlant;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlantsFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private UserPlantViewAdapter userPlantViewAdapter;
    private SearchView searchView;

    private FloatingActionButton addPlantButton;



    private Fragment plantDetailsFragment;
    private Fragment allPlantFragment;

    public MyPlantsFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_plants, container, false);

        mainActivity = (MainActivity) getContext();
        setHasOptionsMenu(true);
        searchView = view.findViewById(R.id.searchView);
        addPlantButton = view.findViewById(R.id.buttonAddPlant);
        allPlantFragment = mainActivity.getAllPlantsFragment();

        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.replaceFragment(allPlantFragment);
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("", "onQueryTextChange: " + s);
                userPlantViewAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //CharSequence charSequence = searchView.getQuery();
                Log.d("", "onQueryTextChange: " + s);
                userPlantViewAdapter.getFilter().filter(s);
                return false;
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

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewPlants);
        userPlantViewAdapter = new UserPlantViewAdapter(mainActivity.getPlantUserList());

        plantDetailsFragment = mainActivity.getPlantDetailsFragment();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userPlantViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserPlant userPlant = userPlantViewAdapter.getUserPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setUserPlant(userPlant);
                mainActivity.replaceFragment(plantDetailsFragment);

            }

            @Override
            public void onLongClick(View view, int position) {
                UserPlant userPlant = userPlantViewAdapter.getUserPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setUserPlant(userPlant);
                mainActivity.replaceFragment(plantDetailsFragment);
            }
        }));
    }
}
