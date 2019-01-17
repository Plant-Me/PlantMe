package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.Plant;
import com.plantme.plantme.model.UserAction;
import com.plantme.plantme.model.UserPlant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AjoutPlanteFragment extends Fragment {

    private Plant selectedPlant;

    private TextView nomFrancaisPlante;
    private TextView nomLatinPlante;
    private TextView nomFamillePlante;

    private EditText nickNameValue;

    private ConstraintLayout datePickerArrosage;
    private ConstraintLayout datePickerEngrais;

    private NumberPicker numberPickerArrosageDay;
    private NumberPicker numberPickerArrosageMonth;
    private NumberPicker numberPickerArrosageYear;
    private NumberPicker numberPickerEngraisDay;
    private NumberPicker numberPickerEngraisMonth;
    private NumberPicker numberPickerEngraisYear;

    private Switch switchArrosage;
    private Switch switchEngrais;

    private Button buttonMinusArrosage;
    private Button buttonPlusArrosage;

    private Button buttonMinusEngrais;
    private Button buttonPlusEngrais;

    private TextView valueOfDaysArrosage;
    private TextView valueOfDaysEngrais;

    private Button buttonSauvegarder;

    private int compteurJoursArrosage;
    private int compteurJoursEngrais;


//    private Wh

    public AjoutPlanteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ajout_plante, container, false);

        nomFrancaisPlante = view.findViewById(R.id.ajouPlanteNomFr);
        nomLatinPlante = view.findViewById(R.id.ajoutPlanteNomLatin);
        nomFamillePlante = view.findViewById(R.id.ajoutPlanteNomFamille);

        nickNameValue = view.findViewById(R.id.nickNameValue);

        datePickerArrosage = view.findViewById(R.id.datePickerArrosage);
        datePickerEngrais = view.findViewById(R.id.datePickerEngrais);

        numberPickerArrosageDay = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageDay);
        numberPickerArrosageMonth = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageMonth);
        numberPickerArrosageYear = view.findViewById(R.id.ajoutPlanteNumberPickerArrosageYear);
        numberPickerEngraisDay = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisDay);
        numberPickerEngraisMonth = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisMonth);
        numberPickerEngraisYear = view.findViewById(R.id.ajoutPlanteNumberPickerEngraisYear);

        switchArrosage = view.findViewById(R.id.switchArrosage);
        switchEngrais = view.findViewById(R.id.switchEngrais);

        valueOfDaysArrosage = view.findViewById(R.id.valueOfDaysArrosage);
        buttonMinusArrosage = view.findViewById(R.id.buttonMinusArrosage);
        buttonPlusArrosage = view.findViewById(R.id.buttonPlusArrosage);

        valueOfDaysEngrais = view.findViewById(R.id.valueOfDaysEngrais);
        buttonMinusEngrais = view.findViewById(R.id.buttonMinusEngrais);
        buttonPlusEngrais = view.findViewById(R.id.buttonPlusEngrais);

        buttonSauvegarder = view.findViewById(R.id.buttonSauvegarder);


        nomFrancaisPlante.setText(selectedPlant.getFrName());
        if (selectedPlant.getLtnName().equals("")) {
            nomLatinPlante.setVisibility(View.GONE);
        } else {
            nomLatinPlante.setText(selectedPlant.getLtnName());
        }

        if (!selectedPlant.getFamillePlante().getNomFrancais().equals("") && !selectedPlant.getFamillePlante().getNomLatin().equals("")) {
            nomFamillePlante.setText(selectedPlant.getFamillePlante().getNomFrancais() + " / " + selectedPlant.getFamillePlante().getNomLatin());
        } else if (!selectedPlant.getFamillePlante().getNomFrancais().equals("")) {
            nomFamillePlante.setText(selectedPlant.getFamillePlante().getNomFrancais());
        } else if (!selectedPlant.getFamillePlante().getNomLatin().equals("")) {
            nomFamillePlante.setText(selectedPlant.getFamillePlante().getNomLatin());
        } else {
            nomFamillePlante.setVisibility(View.GONE);
        }


        datePickerArrosage.setVisibility(View.GONE);
        datePickerEngrais.setVisibility(View.GONE);


        switchArrosage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datePickerArrosage.setVisibility(View.VISIBLE);
                } else {
                    datePickerArrosage.setVisibility(View.GONE);
                }
            }
        });

        switchEngrais.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datePickerEngrais.setVisibility(View.VISIBLE);
                } else {
                    datePickerEngrais.setVisibility(View.GONE);
                }
            }
        });

        buttonSauvegarder.setOnClickListener(onClick());
        buttonMinusArrosage.setOnClickListener(onClick());
        buttonPlusArrosage.setOnClickListener(onClick());
        buttonMinusEngrais.setOnClickListener(onClick());
        buttonPlusEngrais.setOnClickListener(onClick());

        compteurJoursArrosage = 0;
        compteurJoursEngrais = 0;

        valueOfDaysArrosage.setText(compteurJoursArrosage + " Jours");
        valueOfDaysEngrais.setText(compteurJoursEngrais + " Jours");


        String[] monthlist = new String[]{"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};

        Calendar today = new GregorianCalendar();

        numberPickerArrosageMonth.setMinValue(1);
        numberPickerArrosageMonth.setMaxValue(12);
        numberPickerArrosageMonth.setDisplayedValues(monthlist);
        numberPickerArrosageMonth.setValue(today.get(Calendar.MONTH) + 1);

        numberPickerEngraisMonth.setMinValue(1);
        numberPickerEngraisMonth.setMaxValue(12);
        numberPickerEngraisMonth.setDisplayedValues(monthlist);
        numberPickerEngraisMonth.setValue(today.get(Calendar.MONTH) + 1);

        numberPickerArrosageYear.setMinValue(today.get(Calendar.YEAR) - 1);
        numberPickerArrosageYear.setMaxValue(today.get(Calendar.YEAR) + 4);
        numberPickerArrosageYear.setValue(today.get(Calendar.YEAR));

        numberPickerEngraisYear.setMinValue(today.get(Calendar.YEAR) - 1);
        numberPickerEngraisYear.setMaxValue(today.get(Calendar.YEAR) + 4);
        numberPickerEngraisYear.setValue(today.get(Calendar.YEAR));

        switch (numberPickerArrosageMonth.getValue()) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numberPickerArrosageDay.setMaxValue(31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                numberPickerArrosageDay.setMaxValue(30);
                break;
            case 2:
                if (numberPickerArrosageYear.getValue() % 4 == 0 && numberPickerArrosageYear.getValue() % 100 != 0) {
                    numberPickerArrosageDay.setMaxValue(29);
                } else {
                    numberPickerArrosageDay.setMaxValue(28);
                }
                break;
        }

        numberPickerArrosageDay.setMinValue(1);
        numberPickerArrosageDay.setValue(today.get(Calendar.DAY_OF_MONTH));

        switch (numberPickerEngraisMonth.getValue()) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numberPickerEngraisDay.setMaxValue(31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                numberPickerEngraisDay.setMaxValue(30);
                break;
            case 2:
                if (numberPickerEngraisYear.getValue() % 4 == 0 && numberPickerEngraisYear.getValue() % 100 != 0) {
                    numberPickerEngraisDay.setMaxValue(29);
                } else {
                    numberPickerEngraisDay.setMaxValue(28);
                }
                break;
        }

        numberPickerEngraisDay.setMinValue(1);
        numberPickerEngraisDay.setValue(today.get(Calendar.DAY_OF_MONTH));


        numberPickerArrosageDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(getContext(),
                        "selected number " + picker.getValue(), Toast.LENGTH_SHORT);
            }
        });

        numberPickerArrosageMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        numberPickerArrosageDay.setMaxValue(31);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        numberPickerArrosageDay.setMaxValue(30);
                        break;
                    case 2:
                        if (numberPickerArrosageYear.getValue() % 4 == 0 && numberPickerArrosageYear.getValue() % 100 != 0) {
                            numberPickerArrosageDay.setMaxValue(29);
                        } else {
                            numberPickerArrosageDay.setMaxValue(28);
                        }
                        break;
                }
            }
        });

        numberPickerEngraisMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        numberPickerEngraisDay.setMaxValue(31);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        numberPickerEngraisDay.setMaxValue(30);
                        break;
                    case 2:
                        if (numberPickerEngraisYear.getValue() % 4 == 0 && numberPickerEngraisYear.getValue() % 100 != 0) {
                            numberPickerEngraisDay.setMaxValue(29);
                        } else {
                            numberPickerEngraisDay.setMaxValue(28);
                        }
                        break;
                }
            }
        });

        numberPickerArrosageYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (numberPickerArrosageMonth.getValue() == 2) {
                    if (newVal % 4 == 0 && newVal % 100 != 0) {
                        numberPickerArrosageDay.setMaxValue(29);
                    } else {
                        numberPickerArrosageDay.setMaxValue(28);
                    }
                }
            }
        });

        numberPickerEngraisYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (numberPickerEngraisMonth.getValue() == 2) {
                    if (newVal % 4 == 0 && newVal % 100 != 0) {
                        numberPickerEngraisDay.setMaxValue(29);
                    } else {
                        numberPickerEngraisDay.setMaxValue(28);
                    }
                }
            }
        });

        return view;
    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonMinusArrosage:
                        if (compteurJoursArrosage >= 1) {
                            compteurJoursArrosage--;
                            valueOfDaysArrosage.setText(compteurJoursArrosage + " Jours");
                        }
                        break;
                    case R.id.buttonPlusArrosage:
                        compteurJoursArrosage++;
                        valueOfDaysArrosage.setText(compteurJoursArrosage + " Jours");
                        break;
                    case R.id.buttonMinusEngrais:
                        if (compteurJoursEngrais >= 1) {
                            compteurJoursEngrais--;
                            valueOfDaysEngrais.setText(compteurJoursEngrais + " Jours");
                        }
                        break;
                    case R.id.buttonPlusEngrais:
                        compteurJoursEngrais++;
                        valueOfDaysEngrais.setText(compteurJoursEngrais + " Jours");
                        break;
                    case R.id.buttonSauvegarder:
                        addPlant();
                        break;
                }
            }
        };
    }

    public void setSelectedPlant(Plant selectedPlant) {
        this.selectedPlant = selectedPlant;
    }

    public void addPlant() {
        MainActivity mainActivity = (MainActivity)getContext();
        List<UserAction> listUserAction = mainActivity.getListUserAction();

        String nickname = nickNameValue.getText().toString();
        List<CoupleActionDate> coupleActionDateList = new ArrayList<>();



        if (switchArrosage.isChecked()) {
            int selectedDay = numberPickerArrosageDay.getValue();
            int selectedMonth = numberPickerArrosageMonth.getValue();
            int selectedYear = numberPickerArrosageYear.getValue();


            Date selectedDate = new GregorianCalendar(selectedYear, selectedMonth - 1, selectedDay + compteurJoursArrosage ).getTime();

            UserAction selectedUserAction = null;
            for(UserAction userAction : listUserAction) {
                if (userAction.getActionName().equals("Arroser")) {
                    selectedUserAction = userAction;
                }
            }
            coupleActionDateList.add(new CoupleActionDate(nickname, selectedUserAction, selectedDate));
        }

        if (switchEngrais.isChecked()) {
            int selectedDay = numberPickerEngraisDay.getValue();
            int selectedMonth = numberPickerEngraisMonth.getValue();
            int selectedYear = numberPickerEngraisYear.getValue();


            Date selectedDate = new GregorianCalendar(selectedYear, selectedMonth - 1, selectedDay + compteurJoursEngrais).getTime();

            UserAction selectedUserAction = null;
            for(UserAction userAction : listUserAction) {
                if (userAction.getActionName().equals("Fertiliser")) {
                    selectedUserAction = userAction;
                }
            }
            coupleActionDateList.add(new CoupleActionDate(nickname, selectedUserAction, selectedDate));
        }

        UserPlant newUserPlant = new UserPlant(selectedPlant, nickname, coupleActionDateList);
        mainActivity.getPlantUserList().add(newUserPlant);
        mainActivity.replaceFragment(mainActivity.getAllPlantsFragment());
    }
}
