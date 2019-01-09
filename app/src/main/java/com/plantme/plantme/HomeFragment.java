package com.plantme.plantme;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    Fragment homeDayFragment;
    Fragment homeMonthFragment;

    FragmentManager fragmentManager;
    Fragment activeFragment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeDayFragment = new HomeDayFragment();
        homeMonthFragment = new HomeMonthFragment();

        fragmentManager = ((MainActivity)getContext()).getSupportFragmentManager();

        activeFragment = homeDayFragment;

        fragmentManager.beginTransaction().add(R.id.containerHome, homeDayFragment, "2").hide(homeMonthFragment).commit();
        fragmentManager.beginTransaction().add(R.id.containerHome, homeMonthFragment, "1").commit();

        //this.setDefaultFragment(homeDayFragment);
    }

    /*private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }*/

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.containerHome, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0 :
                //replaceFragment(homeDayFragment);
                fragmentManager.beginTransaction().hide(activeFragment).show(homeDayFragment).commit();
                activeFragment = homeDayFragment;
                break;
            case 1 :
                //replaceFragment(homeMonthFragment);
                fragmentManager.beginTransaction().hide(activeFragment).show(homeMonthFragment).commit();
                activeFragment = homeMonthFragment;
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
