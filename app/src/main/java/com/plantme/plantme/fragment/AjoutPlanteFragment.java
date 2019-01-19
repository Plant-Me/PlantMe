package com.plantme.plantme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
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

    private Button buttonSauvegarder;

    private int compteurRepetitionArrosage;
    private int compteurRepetitionEngrais;

    private Spinner spinnerTypeRepetitionArrosage;
    private Spinner spinnerTypeRepetitionEngrais;

    private List<String> listTypeArrosage;
    private List<String> listTypeEngrais;
    private ArrayAdapter<String> spinnerAdapterArrosage;
    private ArrayAdapter<String> spinnerAdapterEngrais;

//    private NumberPicker numberPickerEngraisValeurRepetition;
//    private NumberPicker numberPickerEngraisTypeRepetition;


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

        buttonMinusArrosage = view.findViewById(R.id.buttonMinusArrosage);
        buttonPlusArrosage = view.findViewById(R.id.buttonPlusArrosage);

        buttonMinusEngrais = view.findViewById(R.id.buttonMinusEngrais);
        buttonPlusEngrais = view.findViewById(R.id.buttonPlusEngrais);

        buttonSauvegarder = view.findViewById(R.id.buttonSauvegarder);

        spinnerTypeRepetitionArrosage = view.findViewById(R.id.spinnerTypeRepetitionArrosage);
        spinnerTypeRepetitionEngrais = view.findViewById(R.id.spinnerTypeRepetitionEngrais);

//        numberPickerEngraisValeurRepetition = view.findViewById(R.id.numberPickerEngraisValeurRepetition);
//        numberPickerEngraisTypeRepetition = view.findViewById(R.id.numberPickerEngraisTypeRepetition);


        listTypeArrosage = new ArrayList<>();
        listTypeArrosage.add("jamais");
        listTypeArrosage.add("tous les jours");
        listTypeArrosage.add("toutes les semaines");
        listTypeArrosage.add("tous les mois");

        listTypeEngrais = new ArrayList<>();
        listTypeEngrais.add("jamais");
        listTypeEngrais.add("tous les jours");
        listTypeEngrais.add("toutes les semaines");
        listTypeEngrais.add("tous les mois");


        spinnerAdapterArrosage = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listTypeArrosage);
        spinnerAdapterArrosage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeRepetitionArrosage.setAdapter(spinnerAdapterArrosage);

        spinnerTypeRepetitionArrosage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compteurRepetitionArrosage = 1;
                if(position == 0) {
                    buttonMinusArrosage.setVisibility(View.INVISIBLE);
                    buttonPlusArrosage.setVisibility(View.INVISIBLE);
                } else {
                    buttonMinusArrosage.setVisibility(View.VISIBLE);
                    buttonPlusArrosage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAdapterEngrais = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listTypeEngrais);
        spinnerAdapterEngrais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeRepetitionEngrais.setAdapter(spinnerAdapterEngrais);

        spinnerTypeRepetitionEngrais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compteurRepetitionEngrais = 1;
                if(position == 0) {
                    buttonMinusEngrais.setVisibility(View.INVISIBLE);
                    buttonPlusEngrais.setVisibility(View.INVISIBLE);
                } else {
                    buttonMinusEngrais.setVisibility(View.VISIBLE);
                    buttonPlusEngrais.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        nickNameValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus) {
//
//                    InputMethodManager imm =  (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//                }
//            }
//        });

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

        compteurRepetitionArrosage = 1;
        compteurRepetitionEngrais = 1;



        String[] monthlist = new String[]{"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};

        final Calendar today = new GregorianCalendar();

        numberPickerArrosageMonth.setMinValue(1);
        numberPickerArrosageMonth.setMaxValue(today.get(Calendar.MONTH) + 1);
        numberPickerArrosageMonth.setDisplayedValues(monthlist);
        numberPickerArrosageMonth.setValue(today.get(Calendar.MONTH) + 1);
        numberPickerArrosageMonth.setWrapSelectorWheel(false);

        numberPickerEngraisMonth.setMinValue(1);
        numberPickerEngraisMonth.setMaxValue(today.get(Calendar.MONTH) + 1);
        numberPickerEngraisMonth.setDisplayedValues(monthlist);
        numberPickerEngraisMonth.setValue(today.get(Calendar.MONTH) + 1);
        numberPickerEngraisMonth.setWrapSelectorWheel(false);

        numberPickerArrosageYear.setMinValue(today.get(Calendar.YEAR) - 1);
        numberPickerArrosageYear.setMaxValue(today.get(Calendar.YEAR));
        numberPickerArrosageYear.setValue(today.get(Calendar.YEAR));
        numberPickerArrosageYear.setWrapSelectorWheel(false);

        numberPickerEngraisYear.setMinValue(today.get(Calendar.YEAR) - 1);
        numberPickerEngraisYear.setMaxValue(today.get(Calendar.YEAR));
        numberPickerEngraisYear.setValue(today.get(Calendar.YEAR));
        numberPickerEngraisYear.setWrapSelectorWheel(false);

//        switch (numberPickerArrosageMonth.getValue()) {
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//                numberPickerArrosageDay.setMaxValue(31);
//                break;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                numberPickerArrosageDay.setMaxValue(30);
//                break;
//            case 2:
//                if (numberPickerArrosageYear.getValue() % 4 == 0 && numberPickerArrosageYear.getValue() % 100 != 0) {
//                    numberPickerArrosageDay.setMaxValue(29);
//                } else {
//                    numberPickerArrosageDay.setMaxValue(28);
//                }
//                break;
//        }

        numberPickerArrosageDay.setMinValue(1);
        numberPickerArrosageDay.setMaxValue(today.get(Calendar.DAY_OF_MONTH));
        numberPickerArrosageDay.setValue(today.get(Calendar.DAY_OF_MONTH));
        numberPickerArrosageDay.setWrapSelectorWheel(false);

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
        numberPickerEngraisDay.setMaxValue(today.get(Calendar.DAY_OF_MONTH));
        numberPickerEngraisDay.setValue(today.get(Calendar.DAY_OF_MONTH));
        numberPickerEngraisDay.setWrapSelectorWheel(false);


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
                if(newVal < oldVal) {
                    numberPickerArrosageMonth.setMaxValue(12);
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

                    if (numberPickerArrosageMonth.getValue() == 2) {
                        if (newVal % 4 == 0 && newVal % 100 != 0) {
                            numberPickerArrosageDay.setMaxValue(29);
                        } else {
                            numberPickerArrosageDay.setMaxValue(28);
                        }
                    }
                } else {
                    numberPickerArrosageMonth.setMaxValue(today.get(Calendar.MONTH) + 1);
                    Log.d("test", "onValueChange: " + numberPickerArrosageMonth.getValue());
                    if(numberPickerArrosageMonth.getValue() == (today.get(Calendar.MONTH) + 1)) {
                        numberPickerArrosageDay.setMaxValue(today.get(Calendar.DAY_OF_MONTH));
                    }
                }
            }
        });

        numberPickerEngraisYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal < oldVal) {
                    numberPickerEngraisMonth.setMaxValue(12);
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

                    if (numberPickerEngraisMonth.getValue() == 2) {
                        if (newVal % 4 == 0 && newVal % 100 != 0) {
                            numberPickerEngraisDay.setMaxValue(29);
                        } else {
                            numberPickerEngraisDay.setMaxValue(28);
                        }
                    }
                } else {
                    numberPickerEngraisMonth.setMaxValue(today.get(Calendar.MONTH) + 1);
                    Log.d("test", "onValueChange: " + numberPickerEngraisMonth.getValue());
                    if(numberPickerEngraisMonth.getValue() == (today.get(Calendar.MONTH) + 1)) {
                        numberPickerEngraisDay.setMaxValue(today.get(Calendar.DAY_OF_MONTH));
                    }
                }
            }
        });

        List<Integer> jours = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            jours.add(i);
        }

//        numberPickerEngraisValeurRepetition.setMinValue(1);
//        numberPickerEngraisValeurRepetition.setMaxValue(7);
//        numberPickerEngraisValeurRepetition.setWrapSelectorWheel(false);
//
//        numberPickerEngraisTypeRepetition.setMinValue(1);
//        numberPickerEngraisTypeRepetition.setMaxValue(3);
//        numberPickerEngraisTypeRepetition.setDisplayedValues(new String[]{"jours", "semaines", "mois"});
//        numberPickerEngraisValeurRepetition.setWrapSelectorWheel(false);

//        numberPickerEngraisTypeRepetition.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                switch (newVal) {
//                    case 1:
//                        numberPickerEngraisValeurRepetition.setMaxValue(7);
//                        break;
//                    case 2:
//                        numberPickerEngraisValeurRepetition.setMaxValue(4);
//                        break;
//                    case 3:
//                        numberPickerEngraisValeurRepetition.setMaxValue(6);
//                        break;
//                }
//            }
//        });


        return view;
    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonMinusArrosage:
                        if (compteurRepetitionArrosage > 1) {
                            compteurRepetitionArrosage--;
                            setSpinnerValue("Arrosage");
                        }
                        break;
                    case R.id.buttonPlusArrosage:
                        if (spinnerTypeRepetitionArrosage.getSelectedItemPosition() == 0) {
                            if (compteurRepetitionArrosage < 10) {
                                compteurRepetitionArrosage++;
                            }
                        } else if (spinnerTypeRepetitionArrosage.getSelectedItemPosition() == 1) {
                            if (compteurRepetitionArrosage < 6) {
                                compteurRepetitionArrosage++;
                            }
                        } else {
                            if (compteurRepetitionArrosage < 6) {
                                compteurRepetitionArrosage++;
                            }
                        }
                        setSpinnerValue("Arrosage");
                        break;
                    case R.id.buttonMinusEngrais:
                        if (compteurRepetitionEngrais > 1) {
                            compteurRepetitionEngrais--;
                            setSpinnerValue("Engrais");
                        }
                        break;
                    case R.id.buttonPlusEngrais:
                        if (spinnerTypeRepetitionEngrais.getSelectedItemPosition() == 0) {
                            if (compteurRepetitionEngrais < 10) {
                                compteurRepetitionEngrais++;
                            }
                        } else if (spinnerTypeRepetitionEngrais.getSelectedItemPosition() == 1) {
                            if (compteurRepetitionEngrais < 6) {
                                compteurRepetitionEngrais++;
                            }
                        } else {
                            if (compteurRepetitionEngrais < 6) {
                                compteurRepetitionEngrais++;
                            }
                        }
                        setSpinnerValue("Engrais");
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
        MainActivity mainActivity = (MainActivity) getContext();
        List<UserAction> listUserAction = mainActivity.getListUserAction();

        String nickname = nickNameValue.getText().toString();

        if (!nickname.isEmpty()) {

            List<CoupleActionDate> coupleActionDateList = new ArrayList<>();

            UserPlant newUserPlant = new UserPlant(selectedPlant, nickname, coupleActionDateList);

            if (switchArrosage.isChecked()) {
                int selectedDay = numberPickerArrosageDay.getValue();
                int selectedMonth = numberPickerArrosageMonth.getValue();
                int selectedYear = numberPickerArrosageYear.getValue();
                String typeRepetition = "";
                GregorianCalendar selectedDate = new GregorianCalendar(selectedYear, selectedMonth - 1, selectedDay);
                Date nextRepetitionDate = null;
                int valeurRepetition = compteurRepetitionArrosage;
                switch (spinnerTypeRepetitionArrosage.getSelectedItemPosition()) {
                    case 0:
                        valeurRepetition = 0;
                        break;
                    case 1:
                        typeRepetition = "Jours";
                        selectedDate.add(Calendar.DAY_OF_MONTH, compteurRepetitionArrosage);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                    case 2:
                        typeRepetition = "Semaines";
                        selectedDate.add(Calendar.WEEK_OF_YEAR, compteurRepetitionArrosage);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                    case 3:
                        typeRepetition = "Mois";
                        selectedDate.add(Calendar.MONTH, compteurRepetitionArrosage);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                }



                UserAction selectedUserAction = null;
                for (UserAction userAction : listUserAction) {
                    if (userAction.getActionName().equals("Arroser")) {
                        selectedUserAction = userAction;
                    }
                }
                coupleActionDateList.add(new CoupleActionDate(newUserPlant, nickname, selectedUserAction, nextRepetitionDate, typeRepetition, valeurRepetition));
            }

            if (switchEngrais.isChecked()) {
                int selectedDay = numberPickerEngraisDay.getValue();
                int selectedMonth = numberPickerEngraisMonth.getValue();
                int selectedYear = numberPickerEngraisYear.getValue();
                String typeRepetition = "";
                GregorianCalendar selectedDate = new GregorianCalendar(selectedYear, selectedMonth - 1, selectedDay);
                Date nextRepetitionDate = null;
                int valeurRepetition = compteurRepetitionEngrais;
                switch (spinnerTypeRepetitionEngrais.getSelectedItemPosition()) {
                    case 0:
                        valeurRepetition = 0;
                        break;
                    case 1:
                        typeRepetition = "Jours";
                        selectedDate.add(Calendar.DAY_OF_MONTH, compteurRepetitionEngrais);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                    case 2:
                        typeRepetition = "Semaines";
                        selectedDate.add(Calendar.WEEK_OF_YEAR, compteurRepetitionEngrais);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                    case 3:
                        typeRepetition = "Mois";
                        selectedDate.add(Calendar.MONTH, compteurRepetitionEngrais);
                        nextRepetitionDate = selectedDate.getTime();
                        break;
                }


                UserAction selectedUserAction = null;
                for (UserAction userAction : listUserAction) {
                    if (userAction.getActionName().equals("Fertiliser")) {
                        selectedUserAction = userAction;
                    }
                }
                coupleActionDateList.add(new CoupleActionDate(newUserPlant, nickname, selectedUserAction, nextRepetitionDate, typeRepetition, valeurRepetition));
            }

            mainActivity.getPlantUserList().add(newUserPlant);
            mainActivity.replaceFragment(mainActivity.getAllPlantsFragment());
        } else {
            Toast toast = Toast.makeText(getContext(), "Veuillez entrer un nom pour votre plante.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void setSpinnerValue(String typeAction) {
        if (typeAction == "Arrosage") {
            if (compteurRepetitionArrosage == 1) {
                int selectedPosition = spinnerTypeRepetitionArrosage.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listTypeArrosage.set(selectedPosition, "tous les jours");
                        break;
                    case 2:
                        listTypeArrosage.set(selectedPosition, "toutes les semaines");
                        break;
                    case 3:
                        listTypeArrosage.set(selectedPosition, "tous les mois");
                        break;
                }
            } else {
                int selectedPosition = spinnerTypeRepetitionArrosage.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listTypeArrosage.set(selectedPosition, "tous les " + compteurRepetitionArrosage + " jours");
                        break;
                    case 2:
                        listTypeArrosage.set(selectedPosition, "toutes les " + compteurRepetitionArrosage + " semaines");
                        break;
                    case 3:
                        listTypeArrosage.set(selectedPosition, "tous les " + compteurRepetitionArrosage + " mois");
                        break;
                }
            }
            spinnerAdapterArrosage.notifyDataSetChanged();
        } else {
            if (compteurRepetitionEngrais == 1) {
                int selectedPosition = spinnerTypeRepetitionEngrais.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listTypeEngrais.set(selectedPosition, "tous les jours");
                        break;
                    case 2:
                        listTypeEngrais.set(selectedPosition, "toutes les semaines");
                        break;
                    case 3:
                        listTypeEngrais.set(selectedPosition, "tous les mois");
                        break;
                }
            } else {
                int selectedPosition = spinnerTypeRepetitionEngrais.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listTypeEngrais.set(selectedPosition, "tous les " + compteurRepetitionEngrais + " jours");
                        break;
                    case 2:
                        listTypeEngrais.set(selectedPosition, "toutes les " + compteurRepetitionEngrais + " semaines");
                        break;
                    case 3:
                        listTypeEngrais.set(selectedPosition, "tous les " + compteurRepetitionEngrais + " mois");
                        break;
                }
            }
            spinnerAdapterEngrais.notifyDataSetChanged();
        }
    }
}
