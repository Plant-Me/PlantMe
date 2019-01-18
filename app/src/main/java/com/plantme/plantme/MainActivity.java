package com.plantme.plantme;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.plantme.plantme.fragment.AjoutPlanteFragment;
import com.plantme.plantme.fragment.AllPlantsFragment;
import com.plantme.plantme.fragment.GeneralPlantDetailsFragment;
import com.plantme.plantme.fragment.HomeFragment;
import com.plantme.plantme.fragment.MeteoFragment;
import com.plantme.plantme.fragment.MyPlantsFragment;
import com.plantme.plantme.fragment.PlantDetailsFragment;
import com.plantme.plantme.model.ActionCalendrier;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.FamillePlante;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.UserAction;
import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private List<Plant> plantList;
    private List<UserPlant> plantUserList;
    List<CoupleActionDate> listCoupleActionDate;
    private List<UserAction> listUserAction;

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

        this.setDefaultFragment(homeFragment);

        //List des familles
        FamillePlante orchys = new FamillePlante("Orchydee", "orchys");
        FamillePlante bonsaiolo = new FamillePlante("", "bonsaiolo");
        FamillePlante arbre = new FamillePlante("Arbre", "");

        //Liste des plantes
        plantList = new ArrayList<>();
        Plant orchidee = new Plant("orchidee", "orchideum", orchys, "Orchidee Blah", "rose", "fleur", "exposition", "sol", "intérieur");
        Plant bonsai = new Plant("bonsai", "bonsaium", bonsaiolo,"Bonsai Blah","blanche", "arbuste", "exposition", "sol", "intérieur");
        Plant abricotier = new Plant("abricotier", "abricotierum", arbre, "Abricotier Blah","jaune", "arbre", "exposition", "sol", "verger");
        Plant cerisier = new Plant("cerisier", "cerisierum", arbre, "Cerisier Blah","rose", "arbre", "exposition", "sol", "verger");

        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 3, "Mars"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 4, "Avril"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 5, "Mai"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Floraison", 6, "Juin"));


        orchidee.addActionCalendrier(new ActionCalendrier(1, "Taille", 9, "Septembre"));


        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 6, "Juin"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 7, "Juillet"));
        orchidee.addActionCalendrier(new ActionCalendrier(1, "Fructification", 8, "Aout"));

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

        listUserAction = new ArrayList<>();
        //Les UserActions
        UserAction arroser = new UserAction("Arroser");
        UserAction fertiliser = new UserAction("Fertiliser");
        UserAction tailler = new UserAction("Tailler");
        UserAction planter = new UserAction("Planter");
        UserAction tester = new UserAction("Tester");
        listUserAction.add(arroser);
        listUserAction.add(fertiliser);

        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 17 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), fertiliser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), tester, new GregorianCalendar(2019, Calendar.JANUARY, 19 ).getTime(), "Jours", 6));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon.getPlantName(), planter, new GregorianCalendar(2019, Calendar.JANUARY, 16 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15 ).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon.getPlantName(), fertiliser, new GregorianCalendar(2019, Calendar.JANUARY, 26 ).getTime()));

        listCoupleActionDate = new ArrayList<>();
        for(UserPlant userPlant : plantUserList) {
            listCoupleActionDate.addAll(userPlant.getListCoupleActionDate());
        }

//        actionsPlant = new HashMap<>();
//        actionsPlant.put(monBichon.getPlantName(), monBichon.getListCoupleActionDate());
//        actionsPlant.put(bonbon.getPlantName(), bonbon.getListCoupleActionDate());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                replaceFragment(homeFragment);
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
            replaceFragment(allPlantsFragment);
        } else if (activeFragment instanceof AjoutPlanteFragment) {
            replaceFragment(generalDetailPlantsFragment);
        } else if (!(activeFragment instanceof HomeFragment)) {
            replaceFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
        return super.onSupportNavigateUp();
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public List<UserPlant> getPlantUserList() {
        return plantUserList;
    }

//    public Map<String, List<CoupleActionDate>> getActionsPlant() {
//        return actionsPlant;
//    }


    public List<CoupleActionDate> getListCoupleActionDate() {
        return listCoupleActionDate;
    }

    public List<UserAction> getListUserAction() {
        return listUserAction;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
