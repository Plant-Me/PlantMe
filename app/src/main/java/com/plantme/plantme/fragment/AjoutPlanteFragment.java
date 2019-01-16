package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.plantme.plantme.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutPlanteFragment extends Fragment {

    private NumberPicker numberPickerArrosageDay;
    private NumberPicker numberPickerArrosageMonth;
    private NumberPicker numberPickerArrosageYear;
    private NumberPicker numberPickerEngraisDay;
    private NumberPicker numberPickerEngraisMonth;
    private NumberPicker numberPickerEngraisYear;
    private List<Integer> dayList;
    private List<Integer> yearList;
    private List<String > monthList;


    public AjoutPlanteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ajout_plante, container, false);

        numberPickerArrosageDay = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageDay);
        numberPickerArrosageMonth = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageMonth);
        numberPickerArrosageYear = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageYear);
        numberPickerEngraisDay = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisDay);
        numberPickerEngraisMonth = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisMonth);
        numberPickerEngraisYear = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisYear);




        numberPickerArrosageDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(getContext(),
                        "selected number "+picker.getValue(), Toast.LENGTH_SHORT);
            }
        });
        return view;
    }



}
