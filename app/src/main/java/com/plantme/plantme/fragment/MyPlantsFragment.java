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
import com.plantme.plantme.adapter.PlantViewAdapter;
import com.plantme.plantme.R;
import com.plantme.plantme.RecyclerTouchListener;
import com.plantme.plantme.adapter.UserPlantViewAdapter;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.User;
import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlantsFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private UserPlantViewAdapter userPlantViewAdapter;
    private SearchView searchView;



    Fragment plantDetailsFragment;

    public MyPlantsFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_plants, container, false);

        mainActivity = (MainActivity) getContext();
        setHasOptionsMenu(true);
        searchView = view.findViewById(R.id.searchView);



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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mainActivity.getMenuInflater().inflate(R.menu.menu_with_filter, menu);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                userPlantViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                userPlantViewAdapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
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
                ((PlantDetailsFragment) plantDetailsFragment).setPlant(userPlant);
                mainActivity.replaceFragment(plantDetailsFragment);

            }

            @Override
            public void onLongClick(View view, int position) {
                UserPlant userPlant = userPlantViewAdapter.getUserPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setPlant(userPlant);
                mainActivity.replaceFragment(plantDetailsFragment);
            }
        }));
    }
}
