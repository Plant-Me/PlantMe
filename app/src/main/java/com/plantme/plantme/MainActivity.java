package com.plantme.plantme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.plantme.plantme.Service.PlantMeService;
import com.plantme.plantme.fragment.AjoutPlanteFragment;
import com.plantme.plantme.fragment.AllPlantsFragment;
import com.plantme.plantme.fragment.GeneralPlantDetailsFragment;
import com.plantme.plantme.fragment.HomeFragment;
import com.plantme.plantme.fragment.MeteoFragment;
import com.plantme.plantme.fragment.MyPlantsFragment;
import com.plantme.plantme.fragment.PlantDetailsFragment;
import com.plantme.plantme.model.databaseEntity.CoupleActionDate;
import com.plantme.plantme.model.databaseEntity.UserPlant;
import com.plantme.plantme.model.retrofitEntity.Action;
import com.plantme.plantme.model.retrofitEntity.Famille;
import com.plantme.plantme.model.retrofitEntity.Image;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;
import com.plantme.plantme.model.retrofitEntity.ResultOnePlant;
import com.plantme.plantme.model.retrofitEntity.Type;
import com.plantme.plantme.model.ActionCalendrier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    //private List<Plant> plantList;
    //private List<UserPlant> plantUserList;
    List<CoupleActionDate> listCoupleActionDate;
    List<ResultAllPlant> resultAllPlantList;
    List<ResultOnePlant> resultOnePlantList;
    ResultOnePlant resultOnePlant;

    private boolean wasInitialized = false;

    private BottomNavigationView bottomNavigationView;
    private Fragment homeFragment;
    private Fragment myPlantsFragment;
    private Fragment plantDetailsFragment;
    private Fragment meteoFragment;
    private Fragment allPlantsFragment;
    private Fragment generalDetailPlantsFragment;
    private Fragment ajoutPlanteFragment;

    //final FragmentManager fm = getSupportFragmentManager();

    private Fragment activeFragment;
    private Fragment previousFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        homeFragment = new HomeFragment();
        myPlantsFragment = new MyPlantsFragment();
        plantDetailsFragment = new PlantDetailsFragment();
        meteoFragment = new MeteoFragment();
        allPlantsFragment = new AllPlantsFragment();
        generalDetailPlantsFragment = new GeneralPlantDetailsFragment();
        ajoutPlanteFragment = new AjoutPlanteFragment();

        this.setDefaultFragment(homeFragment);
        //Log.d("plante :", "onResponse: " + resultOnePlantList);


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                replaceFragment(homeFragment);
                Log.d("plante :", "onResponse: " + resultOnePlant);
                return true;
            case R.id.navigation_meteo:
                replaceFragment(meteoFragment);
                return true;
            case R.id.navigation_myPlants:
                replaceFragment(myPlantsFragment);
                return true;
        }
        return false;
    }

    private void setDefaultFragment(Fragment defaultFragment) {
        this.replaceFragment(defaultFragment);
    }

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment) {


        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        if (!wasInitialized) {
            wasInitialized = true;
            fragmentTransaction.replace(R.id.mainContainer, destFragment);
        } else {
            fragmentTransaction.replace(R.id.mainContainer, destFragment);
        }

        // Commit the Fragment replace action.
        previousFragment = activeFragment;
        activeFragment = destFragment;
        fragmentTransaction.commit();

    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
    public Fragment getPlantDetailsFragment() {
        return plantDetailsFragment;
    }

    public Fragment getAllPlantsFragment() {
        return allPlantsFragment;
    }

    public Fragment getGeneralDetailPlantsFragment() {
        return generalDetailPlantsFragment;
    }

    public Fragment getAjoutPlanteFragment() {
        return ajoutPlanteFragment;
    }

    /*public void replace(Fragment destFragment) {
        previousFragment = activeFragment;
        fm.beginTransaction().hide(activeFragment).show(destFragment).commit();
        activeFragment = destFragment;
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_with_filter, menu);

        return true;
    }

    @Override
    public void onBackPressed() {

        if (activeFragment instanceof PlantDetailsFragment) {
            replaceFragment(previousFragment);
        } else if (activeFragment instanceof AllPlantsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (activeFragment instanceof GeneralPlantDetailsFragment) {
            replaceFragment(previousFragment);
        } else if (!(activeFragment instanceof HomeFragment)) {
            replaceFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }

//        switch (bottomNavigationView.getSelectedItemId()){
//            case 2 :{
//                if(manager.getBackStackEntryCount() >0){
//                    manager.getBackStackEntryAt(manager.getBackStackEntryAt())
//                }
//                bottomNavigationView.setSelectedItemId(2);
//            }
//        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (activeFragment instanceof PlantDetailsFragment) {
            replaceFragment(previousFragment);
        } else if (activeFragment instanceof AllPlantsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (activeFragment instanceof GeneralPlantDetailsFragment) {
            replaceFragment(previousFragment);
        } else if (!(activeFragment instanceof HomeFragment)) {
            replaceFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
        return super.onSupportNavigateUp();
    }

   /* public List<Plant> getPlantList() {
        return plantList;
    }

    public List<UserPlant> getPlantUserList() {
        return plantUserList;
    }*/

//    public Map<String, List<CoupleActionDate>> getActionsPlant() {
//        return actionsPlant;
//    }


    public List<CoupleActionDate> getListCoupleActionDate() {
        return listCoupleActionDate;
    }
}
