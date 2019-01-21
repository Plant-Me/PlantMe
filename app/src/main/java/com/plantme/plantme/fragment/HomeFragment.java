package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    MainActivity mainActivity;
    private TabLayout tabLayout;
    private Fragment homeWeekFragment;
    private Fragment homeMonthFragment;
    private Fragment selectActionFragment;

    private int activeTab;
    private boolean isFirstRun = true;


    //    FragmentManager fragmentManager;
    private Fragment activeFragment;

    public HomeFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        homeWeekFragment = new HomeWeekFragment();
        homeMonthFragment = new HomeMonthFragment();
        selectActionFragment = new SelectActionPlantsFragment();

        activeFragment = homeWeekFragment;
        if (getArguments() != null) {

            activeTab = getArguments().getInt("selectedTab");
        } else {
            activeTab = 0;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(this);

        mainActivity = (MainActivity) getActivity();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ActionBar ab = ((MainActivity) getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        setDefaultFragment(activeFragment);
        tabLayout.getTabAt(activeTab).select();

    }

    private void setDefaultFragment(Fragment defaultFragment) {
        this.replaceFragment(defaultFragment);
    }

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.containerHome, destFragment);


        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                replaceFragment(homeWeekFragment);
                activeFragment = homeWeekFragment;
                activeTab = 0;
                break;
            case 1:
                replaceFragment(homeMonthFragment);
                activeFragment = homeMonthFragment;
                activeTab = 1;
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public Fragment getHomeWeekFragment() {
        return homeWeekFragment;
    }

    public Fragment getHomeMonthFragment() {
        return homeMonthFragment;
    }

    public Fragment getSelectActionFragment() {
        return selectActionFragment;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }


    public Fragment getActiveFragment() {
        return activeFragment;
    }

    public void notifyDataSetChanged() {
        ((HomeWeekFragment) homeWeekFragment).notifyDataSetChanged();
        ((HomeMonthFragment) homeMonthFragment).notifyDataSetChanged();
    }


}
