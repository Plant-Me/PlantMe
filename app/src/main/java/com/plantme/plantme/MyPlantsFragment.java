package com.plantme.plantme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.plantme.plantme.model.Plant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlantsFragment extends Fragment implements SearchView.OnQueryTextListener {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private PlantViewAdapter plantViewAdapter;
    private SearchView searchView;

    List<Plant> plantList = new ArrayList<>();

    Fragment plantDetailsFragment;

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

        mainActivity = (MainActivity)getContext();
        plantDetailsFragment = mainActivity.getPlantDetailsFragment();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(plantViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Plant plant = plantViewAdapter.getPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setPlant(plant);
                mainActivity.replace(plantDetailsFragment);

//                FragmentTransaction fragmentTransaction =  getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("selectedPlant", plant);
//                plantDetailsFragment.setArguments(bundle);

//                fragmentTransaction.replace(android.R.id.content, plantDetailsFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {
                /*Plant item = plantViewAdapter.getPlantList().get(position);
                Toast.makeText(getContext(), item.getFrName(), Toast.LENGTH_SHORT).show();*/
            }
        }));
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
