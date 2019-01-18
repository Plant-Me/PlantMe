package com.plantme.plantme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.plantme.plantme.fragment.HomeFragment;
import com.plantme.plantme.fragment.MeteoFragment;
import com.plantme.plantme.fragment.MyPlantsFragment;
import com.plantme.plantme.fragment.PlantDetailsFragment;
import com.plantme.plantme.fragment.SelectActionPlantsFragment;
import com.plantme.plantme.model.CoupleActionDate;
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
    private List<CoupleActionDate> listCoupleActionDate;
    private List<UserAction> listUserActions;

    private boolean wasInitialized = false;

    private BottomNavigationView bottomNavigationView;
    private Fragment homeFragment;
    private Fragment myPlantsFragment;
    private Fragment plantDetailsFragment;
    private Fragment meteoFragment;
    private Fragment selectActionFragment;
    private Fragment homeMonthFragment;

    private Fragment activeFragment;
    private Fragment previousFragment;
    public static boolean isAppRunning;


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
        selectActionFragment = new SelectActionPlantsFragment();
        this.setDefaultFragment(homeFragment);

        initializeDatas();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channel2 = "2";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    "Channel 1",NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("This is BNT");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationChannel notificationChannel2 = new NotificationChannel(channel2,
                    "Channel 2",NotificationManager.IMPORTANCE_MIN);

            notificationChannel.setDescription("This is bTV");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel2);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                homeFragment = new HomeFragment();
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

    @Override
    protected void onStop() {
        super.onStop();
        //startService(new Intent(this, NotificationService.class));
    }

    public void initializeDatas() {
        //Liste des plantes
        plantList = new ArrayList<>();
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
        UserAction planter = new UserAction("Planter");
        UserAction fertiliser = new UserAction("Fertiliser");
        UserAction rempoter = new UserAction("Rempoter");

        listUserActions = new ArrayList<>();
        listUserActions.add(arroser);
        listUserActions.add(tailler);
        listUserActions.add(planter);
        listUserActions.add(fertiliser);
        listUserActions.add(rempoter);


        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 14).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 15).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 16).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 17).getTime()));
        listCoupleActionDateBichon.add(new CoupleActionDate(monBichon, monBichon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 18).getTime(), "Jours", 3));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 18).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), arroser, new GregorianCalendar(2019, Calendar.JANUARY, 18).getTime()));
        listCoupleActionDateBonbon.add(new CoupleActionDate(bonbon, bonbon.getPlantName(), tailler, new GregorianCalendar(2019, Calendar.JANUARY, 15).getTime()));

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
