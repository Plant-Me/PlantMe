package com.plantme.plantme.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.plantme.plantme.Service.PlantMeService;
import com.plantme.plantme.adapter.PlantViewAdapter;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;
//import com.plantme.plantme.adapter.PlantViewAdapter;
//import com.plantme.plantme.adapter.UserPlantViewAdapter;
//import com.plantme.plantme.model.Plant;
//import com.plantme.plantme.model.UserPlant;


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

    public interface GetAllPlantCallback {
        void onGetPlant(List<ResultAllPlant> resultAllPlants);
        void onError();
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



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(view);
    }

    private void getAllPlant(final GetAllPlantCallback getAllPlantCallback){

        PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

        plantMeService.listPlant().enqueue(new Callback<List<ResultAllPlant>>() {
            @Override
            public void onResponse(Call<List<ResultAllPlant>> call, Response<List<ResultAllPlant>> response) {
                if(response.isSuccessful()){
                    getAllPlantCallback.onGetPlant(response.body());
                }else{
                    getAllPlantCallback.onError();
                }


            }

            @Override
            public void onFailure(Call<List<ResultAllPlant>> call, Throwable t) {
                Log.d("list-plante :", "onResponse: fail " + t.getMessage());
            }
        });
    }



    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewAllPlants);

        getAllPlant(new GetAllPlantCallback() {
            @Override
            public void onGetPlant(List<ResultAllPlant> resultAllPlants) {
                plantViewAdapter = new PlantViewAdapter(resultAllPlants);
                recyclerView.setAdapter(plantViewAdapter);
                plantViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });


        generalPlantDetailsFragment = mainActivity.getGeneralDetailPlantsFragment();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //Plant plant = plantViewAdapter.getPlantList().get(position);
                //((GeneralPlantDetailsFragment) generalPlantDetailsFragment).setPlant(plant);
                final Intent intent = new Intent("Data_plant");
                intent.putExtra("DATA_EXTRA", plantViewAdapter.getPlantList().get(position));
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                Log.d(TAG, "onClick: " +plantViewAdapter.getPlantList().get(position));

            }

            @Override
            public void onLongClick(View view, int position) {
                //Plant plant = plantViewAdapter.getPlantList().get(position);
                //((GeneralPlantDetailsFragment) generalPlantDetailsFragment).setPlant(plant);
                final Intent intent = new Intent("Data_plant");
                intent.putExtra("DATA_EXTRA", plantViewAdapter.getPlantList().get(position));
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                mainActivity.replaceFragment(generalPlantDetailsFragment);
            }
        }));
    }

    public void callback() {
        mainActivity.replaceFragment(generalPlantDetailsFragment);
    }


}
