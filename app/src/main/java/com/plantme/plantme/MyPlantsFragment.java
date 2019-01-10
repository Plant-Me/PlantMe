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
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.model.Plant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPlantsFragment extends Fragment {

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private PlantViewAdapter plantViewAdapter;
    private SearchView searchView;


    List<Plant> plantList;

    Fragment plantDetailsFragment;

    public MyPlantsFragment() {
        // Required empty public constructor

        plantList = new ArrayList<>();
        Plant orchidee = new Plant("orchidee", "orchideum", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", "blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", "jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", "rose", "arbre", "exposition", "sol", "verger");

        plantList.add(orchidee);
        plantList.add(bonsai);
        plantList.add(abricotier);
        plantList.add(cerisier);
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

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mainActivity.getMenuInflater().inflate(R.menu.menu_with_filter, menu);

        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager = (SearchManager) mainActivity.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(mainActivity.getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);*/

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                Log.d("", "onQueryTextChange: " + query);
                plantViewAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                Log.d("", "onQueryTextChange: " + query);
                plantViewAdapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewPlants);
        plantViewAdapter = new PlantViewAdapter(plantList);

        plantDetailsFragment = mainActivity.getPlantDetailsFragment();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(plantViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Plant plant = plantViewAdapter.getPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setPlant(plant);
                mainActivity.replaceFragment(plantDetailsFragment);

            }

            @Override
            public void onLongClick(View view, int position) {
                Plant plant = plantViewAdapter.getPlantList().get(position);
                ((PlantDetailsFragment) plantDetailsFragment).setPlant(plant);
                mainActivity.replaceFragment(plantDetailsFragment);
            }
        }));
    }
}
