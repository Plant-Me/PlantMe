package com.plantme.plantme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.plantme.plantme.Service.PlantMeService;
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

    //final FragmentManager fm = getSupportFragmentManager();

    private Fragment activeFragment;
    private Fragment previousFragment;

    public interface GetPlantCallback {
        void onGetPlant(List<ResultOnePlant> resultOnePlant);
        void onError();
    }
    public interface GetAllPlantCallback {
        void onGetPlant(List<ResultAllPlant> resultAllPlants);
        void onError();
    }
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

        this.setDefaultFragment(homeFragment);
        //Log.d("plante :", "onResponse: " + resultOnePlantList);

        //retofit all plant
      /*  PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

        plantMeService.listPlant().enqueue(new Callback<List<ResultAllPlant>>() {
            @Override
            public void onResponse(Call<List<ResultAllPlant>> call, Response<List<ResultAllPlant>> response) {
                resultAllPlantList = response.body();
                for(int i = 0; i<resultAllPlantList.size();i++){
                    Log.d("list-plante :", "onResponse: " + resultAllPlantList.get(i).toString());
                }

            }

            @Override
            public void onFailure(Call<List<ResultAllPlant>> call, Throwable t) {
                Log.d("list-plante :", "onResponse: fail " + t.getMessage());
            }
        });*/

   /* getOnePlant(1, new GetPlantCallback() {
        @Override
        public void onGetPlant(List<ResultOnePlant> resultOnePlant) {
            /*resultOnePlant.get(0).setIdPlante(resultOnePlantList.get(0).getIdPlante());
            resultOnePlant.get(0).setNomFr(resultOnePlantList.get(0).getNomFr());
            resultOnePlant.get(0).setNomLatin(resultOnePlantList.get(0).getNomLatin());
            resultOnePlant.get(0).setDescription(resultOnePlantList.get(0).getDescription());
            resultOnePlant.get(0).setCouleurFleurs(resultOnePlantList.get(0).getCouleurFleurs());
            resultOnePlant.get(0).setExposition(resultOnePlantList.get(0).getExposition());
            resultOnePlant.get(0).setSol(resultOnePlantList.get(0).getSol());
            resultOnePlant.get(0).setUsageMilieu(resultOnePlantList.get(0).getUsageMilieu());
            resultOnePlant.get(0).setType(resultOnePlantList.get(0).getType());
            resultOnePlant.get(0).setImage(resultOnePlantList.get(0).getImage());
            resultOnePlant.get(0).setFamille(resultOnePlantList.get(0).getFamille());
            resultOnePlant.get(0).setActions(resultOnePlantList.get(0).getActions());*/
            //resultOnePlantList = resultOnePlant;
            Log.d("plante :", "onResponse: " + resultOnePlant);
     /*   }

        @Override
        public void onError() {

        }
    });*/
        getAllPlant( new GetAllPlantCallback() {

            @Override
            public void onGetPlant(List<ResultAllPlant> resultAllPlants) {
                for(int i=0;i<resultAllPlants.size();i++){
                    Log.d("plante :", "onResponse: " + resultAllPlants.get(i));

                }

            }

            @Override
            public void onError() {

            }
        });







                //Liste des plantes
       /* plantList = new ArrayList<>();
        Plant orchidee = new Plant("orchidee", "orchideum", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", "blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", "jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", "rose", "arbre", "exposition", "sol", "verger");

        plantList.add(orchidee);
        plantList.add(bonsai);
        plantList.add(abricotier);
        plantList.add(cerisier);


        //Les couples UserPlant, UserAction, Date
        ArrayList<CoupleActionDate> listCoupleActionDateBichon = new ArrayList<>();
        ArrayList<CoupleActionDate> listCoupleActionDateBonbon = new ArrayList<>();

        //Les UserPlants
        UserPlant monBichon = new UserPlant(orchidee, "Mon Bichon", listCoupleActionDateBichon);
        UserPlant bonbon = new UserPlant(bonsai, "Bonbon", listCoupleActionDateBonbon);

        //Liste des plantes utilisateurs
        plantUserList = new ArrayList<>();
        plantUserList.add(monBichon);
        plantUserList.add(bonbon);


        //Les UserActions
        UserAction arroser = new UserAction("Arroser");
        UserAction tailler = new UserAction("Tailler");

        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(bonbon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 16 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 26 ).getTime()));

        listCoupleActionDate = new ArrayList<>();
        for(UserPlant userPlant : plantUserList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }

//        actionsPlant = new HashMap<>();
//        actionsPlant.put(monBichon.getPlantName(), monBichon.getListCoupleActionDate());
//        actionsPlant.put(bonbon.getPlantName(), bonbon.getListCoupleActionDate());
*/
    }

    private void getOnePlant(final int id, final GetPlantCallback getPlantCallback){

        //retrofit detail plant
        PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

        plantMeService.plantDetail(id).enqueue(new Callback<List<ResultOnePlant>>() {
            @Override
            public void onResponse(Call<List<ResultOnePlant>> call, Response<List<ResultOnePlant>> response) {

                if(response.isSuccessful()){

                    //Log.d("plante :", "onResponse: " + resultOnePlant);
                    getPlantCallback.onGetPlant(response.body());
                    //Log.d("plante :", "onResponse1: " + resultOnePlantList);
                }

            }

            @Override
            public void onFailure(Call<List<ResultOnePlant>> call, Throwable t) {
                Log.d("plante :", "onResponse: fail " + t.getMessage());
            }
        });

    }
    private void getAllPlant( final GetAllPlantCallback getAllPlantCallback){

        //retrofit detail plant
        PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

        plantMeService.listPlant().enqueue(new Callback<List<ResultAllPlant>>() {
            @Override
            public void onResponse(Call<List<ResultAllPlant>> call, Response<List<ResultAllPlant>> response) {
                getAllPlantCallback.onGetPlant(response.body());

            }

            @Override
            public void onFailure(Call<List<ResultAllPlant>> call, Throwable t) {

            }
        });

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
