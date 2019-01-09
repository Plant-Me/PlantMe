package com.plantme.plantme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    private BottomNavigationView bottomNavigationView;
    Fragment homeFragment;
    Fragment myPlantsFragment;
    Fragment plantDetailsFragment;

    final FragmentManager fm = getSupportFragmentManager();

    Fragment activeFragment;
    Fragment previousFragment;


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

        activeFragment = homeFragment;

        fm.beginTransaction().add(R.id.mainContainer, myPlantsFragment, "3").hide(myPlantsFragment).commit();
        fm.beginTransaction().add(R.id.mainContainer, plantDetailsFragment, "2").hide(plantDetailsFragment).commit();
        fm.beginTransaction().add(R.id.mainContainer, homeFragment, "1").commit();

//        this.setDefaultFragment(homeFragment);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                previousFragment = activeFragment;
                fm.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                activeFragment = homeFragment;
//                replaceFragment(homeFragment);
                return true;
            case R.id.navigation_meteo:
                return true;
            case R.id.navigation_myPlants:
                previousFragment = activeFragment;
                fm.beginTransaction().hide(activeFragment).show(myPlantsFragment).commit();
                activeFragment = myPlantsFragment;
//                replaceFragment(myPlantsFragment);
                return true;
        }
        return false;
    }

    /*private void setDefaultFragment(Fragment defaultFragment) {
        this.replaceFragment(defaultFragment);
    }*/

    // Replace current Fragment with the destination Fragment.
    /*public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.mainContainer, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }*/

    public Fragment getPlantDetailsFragment() {
        return plantDetailsFragment;
    }

    public void replace(Fragment destFragment) {
        previousFragment = activeFragment;
        fm.beginTransaction().hide(activeFragment).show(destFragment).commit();
        activeFragment = destFragment;
    }

    @Override
    public void onBackPressed() {
        Log.d("", "onBackPressed: test");
        if (activeFragment instanceof HomeFragment) {
            super.onBackPressed();
        } else if (activeFragment instanceof MyPlantsFragment) {
            super.onBackPressed();
        } else if (activeFragment instanceof PlantDetailsFragment) {
            replace(previousFragment);
        }
    }
}
