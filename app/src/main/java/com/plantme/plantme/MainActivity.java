package com.plantme.plantme;

import android.content.Context;
import android.graphics.Rect;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.plantme.plantme.Service.PlantMeService;
import com.plantme.plantme.fragment.AjoutPlanteFragment;
import com.plantme.plantme.fragment.AllPlantsFragment;
import com.plantme.plantme.fragment.GeneralPlantDetailsFragment;
import com.plantme.plantme.fragment.HomeFragment;
import com.plantme.plantme.fragment.MeteoFragment;
import com.plantme.plantme.fragment.MyPlantsFragment;
import com.plantme.plantme.fragment.PlantDetailsFragment;
import com.plantme.plantme.model.ActionCalendrier;
import com.plantme.plantme.fragment.SelectActionPlantsFragment;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.FamillePlante;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.PlanteUtilisateur;
import com.plantme.plantme.model.UserAction;
import com.plantme.plantme.model.UserPlant;
import com.plantme.plantme.model.retrofitEntity.ActionUtilisateur;
import com.plantme.plantme.model.retrofitEntity.ResponseLastId;
import com.plantme.plantme.model.retrofitEntity.ResultOnePlant;
import com.plantme.plantme.model.retrofitEntity.UtilisateurAllPlant;

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
    private List<Plant> plantList;
    private List<PlanteUtilisateur> planteUtilisateurList;
    private List<UserPlant> plantUserList;
    private List<CoupleActionDate> listCoupleActionDate;
    private List<UserAction> listUserActions;
    private List<UserAction> listUserAction;
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
    private Fragment selectActionFragment;
    private Fragment homeMonthFragment;

    private Fragment activeFragment;
    private Fragment previousFragment;
    public static boolean isAppRunning;

    @Override
    protected void onStop() {

        Log.d("STOP", "onStop: je stop");
        populatePlanteUtilisateurList();
        Log.d("STOP", "onStop: je stop" + planteUtilisateurList.get(0).getNom_personnel());
        saveUserPlantIntoDatabase();
        super.onStop();
    }
    public interface GetAllUserPlantCallback {
        void onGetPlant(List<UtilisateurAllPlant> utilisateurAllPlants);
        void onError();
    }





    private void saveUserPlantIntoDatabase(){

        PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

            plantMeService.addAllPlantUserInDatabase(planteUtilisateurList).enqueue(new Callback<ResponseLastId>() {
                @Override
                public void onResponse(Call<ResponseLastId> call, Response<ResponseLastId> response) {
                    if(response.isSuccessful()){
                        Log.d("ResponseLastId", "onResponse: " + response.body().getLastId());
                    }

                }

                @Override
                public void onFailure(Call<ResponseLastId> call, Throwable t) {

                }
            });





    }
    private void getAllUserPlant(final GetAllUserPlantCallback getAllUserPlantCallback){

        PlantMeService plantMeService = new Retrofit.Builder()
                .baseUrl(PlantMeService.ENDPOINT)

                //convertie le json automatiquement
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlantMeService.class);

        plantMeService.listUtilisateurAllPlant(3).enqueue(new Callback<List<UtilisateurAllPlant>>() {
            @Override
            public void onResponse(Call<List<UtilisateurAllPlant>> call, Response<List<UtilisateurAllPlant>> response) {
                if(response.isSuccessful()){
                    getAllUserPlantCallback.onGetPlant(response.body());
                }else{
                    getAllUserPlantCallback.onError();
                }
            }

            @Override
            public void onFailure(Call<List<UtilisateurAllPlant>> call, Throwable t) {

            }
        });

    }

    private void populatePlanteUtilisateurList(){
        for(int i = 0;i<plantUserList.size();i++){
                List<ActionUtilisateur> actionUtilisateurList = new ArrayList<>();
                for(CoupleActionDate coupleActionDate : plantUserList.get(i).getListCoupleActionDate()) {
                    ActionUtilisateur actionUtilisateur = new ActionUtilisateur(coupleActionDate.getUserAction().getIdAction(), coupleActionDate.getUserAction().getActionName(), coupleActionDate.getDateActuelle(), coupleActionDate.getDateInitiale(), coupleActionDate.getTypeRepetition(), coupleActionDate.getValeurRepetition());
                    actionUtilisateurList.add(actionUtilisateur);
                }

                planteUtilisateurList.add(new PlanteUtilisateur(plantUserList.get(i).getId(),3,plantUserList.get(i).getPlant().getIdPlant(),plantUserList.get(i).getPlantName(), actionUtilisateurList));
            }


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


        allPlantsFragment = new AllPlantsFragment();
        generalDetailPlantsFragment = new GeneralPlantDetailsFragment();

        selectActionFragment = new SelectActionPlantsFragment();

        this.setDefaultFragment(homeFragment);
        //Log.d("plante :", "onResponse: " + resultOnePlantList);

        initializeDatas();

        //List des familles
        FamillePlante orchys = new FamillePlante("Orchydee", "orchys");
        FamillePlante bonsaiolo = new FamillePlante("", "bonsaiolo");
        FamillePlante arbre = new FamillePlante("Arbre", "");

        //Liste des plantes
        plantList = new ArrayList<>();
        planteUtilisateurList=new ArrayList<>();
        /*Plant orchidee = new Plant("orchidee", "orchideum", orchys, "Orchidee Blah", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", bonsaiolo,"Bonsai Blah","blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", arbre, "Abricotier Blah","jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", arbre, "Cerisier Blah","rose", "arbre", "exposition", "sol", "verger");*/
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channel2 = "2";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH);
       /* orchidee.addActionCalendrier(new ActionCalendrier(1, "Taille", 9, "Septembre"));

            notificationChannel.setDescription("This is BNT");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN);

        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 6, "Juin"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 7, "Juillet"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 8, "Aout"));

            notificationChannel.setDescription("This is bTV");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);
        plantList.add(orchidee);
        plantList.add(bonsai);
        plantList.add(abricotier);
        plantList.add(cerisier);*/

        }

        //Les couples UserPlant, UserAction, Date
       /* ArrayList<CoupleActionDate> listCoupleActionDateBichon = new ArrayList<>();
        ArrayList<CoupleActionDate> listCoupleActionDateBonbon = new ArrayList<>();

        //Les UserPlants
        UserPlant monBichon = new UserPlant(orchidee, "Mon Bichon", listCoupleActionDateBichon);
        UserPlant bonbon = new UserPlant(bonsai, "Bonbon", listCoupleActionDateBonbon);*/

        //Liste des plantes utilisateurs
        plantUserList = new ArrayList<>();
       // plantUserList.add(monBichon);
        //plantUserList.add(bonbon);

        listUserAction = new ArrayList<>();
        //Les UserActions
        UserAction arroser = new UserAction("Arroser");
        UserAction fertiliser = new UserAction("Fertiliser");
        UserAction rempoter = new UserAction("Rempoter");
        UserAction tailler = new UserAction("Tailler");
        UserAction recolter = new UserAction("Recolter");
        listUserAction.add(arroser);
        listUserAction.add(fertiliser);
        listUserAction.add(rempoter);
        listUserAction.add(tailler);
        listUserAction.add(recolter);

       /* listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(bonbon.getPlantName(), fertiliser, new GregorianCalendar(2019, Calendar.JANUARY, 16 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), fertiliser, new GregorianCalendar(2019, Calendar.JANUARY, 26 ).getTime()));*/


//        actionsPlant = new HashMap<>();
//        actionsPlant.put(monBichon.getPlantName(), monBichon.getListCoupleActionDate());
//        actionsPlant.put(bonbon.getPlantName(), bonbon.getListCoupleActionDate());
        populateUserListFromDb();
        Log.d("", "onCreate: ");
    }

    private void populateUserListFromDb(){

        getAllUserPlant(new GetAllUserPlantCallback() {

            @Override
            public void onGetPlant(List<UtilisateurAllPlant> utilisateurAllPlants) {
                Log.d("test", "onGetPlant: " + utilisateurAllPlants.get(0));
                for(int i=0;i<utilisateurAllPlants.size();i++){
                    Plant newPlant = new Plant(utilisateurAllPlants.get(i).getIdPlante(), utilisateurAllPlants.get(i).getImage().getUrl(), utilisateurAllPlants.get(i).getNomFr(),
                            utilisateurAllPlants.get(i).getNomLatin(), new FamillePlante(utilisateurAllPlants.get(i).getFamille().getNom(),utilisateurAllPlants.get(i).getNomLatin()),
                            utilisateurAllPlants.get(i).getDescription(), utilisateurAllPlants.get(i).getCouleurFleurs(), utilisateurAllPlants.get(i).getTypesToString(),
                            utilisateurAllPlants.get(i).getExposition(), utilisateurAllPlants.get(i).getSol(), utilisateurAllPlants.get(i).getUsageMilieu(),
                            utilisateurAllPlants.get(i).getActionList());
                    if(!plantList.contains(newPlant)) {
                        plantList.add(newPlant);
                    }
                    int positionPlant = -1;
                    for(int j = 0; j<plantList.size(); j++) {
                        if(plantList.get(j).getIdPlant() == newPlant.getIdPlant()) {
                            positionPlant = j;
                        }
                    }
                    List<CoupleActionDate> coupleActionDates = new ArrayList<>();
                    UserPlant newUserPlant = new UserPlant(utilisateurAllPlants.get(i).getIdPlanteUtilisateur(),plantList.get(positionPlant),utilisateurAllPlants.get(i).getNomPersonnel(), coupleActionDates);
                    for(int k = 0; k<utilisateurAllPlants.get(i).getActionUtilisateurs().size(); k++) {
                        Integer actionUtilisateur = utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getIdActionUtilisateur();
                        if(actionUtilisateur!= null){
                            UserAction userAction = new UserAction(actionUtilisateur, utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getNomAction());
                            int positionAction = -1;
                            for(int l = 0; l<listUserAction.size(); l++) {
                                if(listUserAction.get(l).getActionName().equals(userAction.getActionName())) {
                                    positionAction = l;
                                }
                            }
                            CoupleActionDate coupleActionDate = new CoupleActionDate(newUserPlant, utilisateurAllPlants.get(i).getNomPersonnel(), listUserAction.get(positionAction), utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getDate(),utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getDateInitiale(), utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getTypeRepetition(), utilisateurAllPlants.get(i).getActionUtilisateurs().get(k).getValeurRepetition());
                            coupleActionDates.add(coupleActionDate);
                        }

                    }

                    plantUserList.add(newUserPlant);
                }

            }

            @Override
            public void onError() {

            }
        });
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                homeFragment = new HomeFragment();
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
        Fragment currentFragment = getVisibleFragment();
        if (currentFragment instanceof PlantDetailsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (currentFragment instanceof AllPlantsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (currentFragment instanceof GeneralPlantDetailsFragment) {
            replaceFragment(allPlantsFragment);
        } else if (currentFragment instanceof AjoutPlanteFragment) {
            replaceFragment(generalDetailPlantsFragment);
        } else if (currentFragment instanceof SelectActionPlantsFragment) {
            Bundle args = new Bundle();
            args.putInt("selectedTab", 1);
            homeFragment = new HomeFragment();
            homeFragment.setArguments(args);
            replaceFragment(homeFragment);
        } else if (!(currentFragment instanceof HomeFragment)) {
            replaceFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment currentFragment = getVisibleFragment();
        if (currentFragment instanceof PlantDetailsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (currentFragment instanceof AllPlantsFragment) {
            replaceFragment(myPlantsFragment);
        } else if (currentFragment instanceof GeneralPlantDetailsFragment) {
            replaceFragment(allPlantsFragment);
        } else if (currentFragment instanceof AjoutPlanteFragment) {
            replaceFragment(generalDetailPlantsFragment);
        } else if (!(currentFragment instanceof HomeFragment)) {
            replaceFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            currentFragment = ((HomeFragment) homeFragment).getVisibleFragment();
            if (currentFragment instanceof SelectActionPlantsFragment) {
                ((HomeFragment) homeFragment).replaceFragment(((HomeFragment) homeFragment).getHomeMonthFragment());
            } else {
                super.onSupportNavigateUp();
            }
        }
        return super.onSupportNavigateUp();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }



    public void initializeDatas() {

        //List des familles
        FamillePlante orchys = new FamillePlante("Orchydee", "orchys");
        FamillePlante bonsaiolo = new FamillePlante("", "bonsaiolo");
        FamillePlante arbre = new FamillePlante("Arbre", "");

        //Liste des plantes
        plantList = new ArrayList<>();
        plantList = new ArrayList<>();
        Plant orchidee = new Plant("orchidee", "orchideum", orchys, "Orchidee Blah", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", bonsaiolo, "Bonsai Blah", "blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", arbre, "Abricotier Blah", "jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", arbre, "Cerisier Blah", "rose", "arbre", "exposition", "sol", "verger");

        plantList.add(orchidee);
        plantList.add(bonsai);
        plantList.add(abricotier);
        plantList.add(cerisier);


        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 3, "Mars"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 4, "Avril"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 5, "Mai"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 6, "Juin"));


        orchidee.addActionCalendrier(new ActionCalendrier(1, "Taille", 9, "Septembre"));


        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 6, "Juin"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 7, "Juillet"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 8, "Aout"));

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
        UserAction planter = new UserAction("Planter");
        UserAction fertiliser = new UserAction("Fertiliser");
        UserAction rempoter = new UserAction("Rempoter");

        listUserActions = new ArrayList<>();
        listUserActions.add(arroser);
        listUserActions.add(tailler);
        listUserActions.add(planter);
        listUserActions.add(fertiliser);
        listUserActions.add(rempoter);


        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 16).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 17).getTime(), "Semaines", 3));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), planter, new GregorianCalendar(2019, Calendar.JANUARY, 18).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), fertiliser, new GregorianCalendar(2019, Calendar.JANUARY, 19).getTime()));

        CoupleActionDate coupleActionDateTest = new CoupleActionDate(monBichon, monBichon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 20).getTime(), "Jours", 10);
        coupleActionDateTest.setDateInitiale(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        listCoupleActionDateBichon.add(coupleActionDateTest);
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 18).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), planter, new GregorianCalendar(2019, Calendar.JANUARY, 20).getTime(), "Mois", 1));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 20).getTime()));

        listCoupleActionDate = new ArrayList<>();
        for (UserPlant userPlant : plantUserList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning = false;
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public List<UserPlant> getPlantUserList() {
        return plantUserList;
    }

    public List<CoupleActionDate> getListCoupleActionDate() {
        return listCoupleActionDate;
    }

    public List<UserAction> getListUserAction() {
        return listUserActions;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public List<UserAction> getListUserActions() {
        return listUserActions;
    }

    public Fragment getHomeFragment() {
        return homeFragment;
    }


    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public Fragment getPlantDetailsFragment() {
        return plantDetailsFragment;
    }

    public Fragment getSelectActionFragment() {
        return selectActionFragment;
    }

    public Fragment getHomeMonthFragment() {
        return homeMonthFragment;
    }

    public Fragment getAllPlantsFragment() {
        return allPlantsFragment;
    }

    public Fragment getAjoutPlanteFragment() {
        return ajoutPlanteFragment;
    }

    public Fragment getMyPlantsFragment() {
        return myPlantsFragment;
    }

    public Fragment getGeneralDetailPlantsFragment() {
        return generalDetailPlantsFragment;
    }

    //        public final void createNotification(){
//            //Récupération du notification Manager
//            final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//            //Création de la notification avec spécification de l'icône de la notification et le texte qui apparait à la création de la notification
//            final Notification notification = new Notification(R.drawable.ic_baseline_wb_sunny_24px, "notificationTitle", System.currentTimeMillis());
//
//            //Définition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
//            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//
//            //Récupération du titre et description de la notification
//            final String notificationTitle = getResources().getString(R.string.notification_title);
//            final String notificationDesc = getResources().getString(R.string.notification_desc);
//
//            //Notification & Vibration
//            //notification.setLatestEventInfo(this, notificationTitle, notificationDesc, pendingIntent);
//            notification.vibrate = new long[] {0,200,100,200,100,200};
//
//            notificationManager.notify(1, notification);
//        }

}
