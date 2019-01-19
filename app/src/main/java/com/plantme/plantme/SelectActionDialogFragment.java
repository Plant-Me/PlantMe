package com.plantme.plantme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.plantme.plantme.fragment.SelectActionPlantsFragment;
import com.plantme.plantme.model.UserAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SelectActionDialogFragment extends DialogFragment {

    private List<UserAction> listUserActions;
    private UserAction userAction;
    private int position;
    private MainActivity mainActivity;
    private List<String> listUserActionsNames;
    private SelectActionPlantsFragment selectActionPlantsFragment;

    private Button buttonMinus;
    private Button buttonPlus;


    private int compteurRepetition;

    private Spinner spinnerTypeRepetition;

    private List<String> listType;
    private ArrayAdapter<String> spinnerAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_fragment_select_action, null);
        mainActivity = new MainActivity();
        listUserActionsNames = new ArrayList<>();

        final Spinner spin;
        spin = (Spinner)view.findViewById(R.id.spinnerActions);

        for (UserAction userAction : listUserActions) {
            listUserActionsNames.add(userAction.getActionName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listUserActionsNames);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(dataAdapter);


        buttonMinus = view.findViewById(R.id.addActionDialogMinus);
        buttonPlus = view.findViewById(R.id.addActionDialogPlus);



        spinnerTypeRepetition = view.findViewById(R.id.addActionDialogSpinner);

        listType = new ArrayList<>();
        listType.add("jamais");
        listType.add("tous les jours");
        listType.add("toutes les semaines");
        listType.add("tous les mois");

        buttonMinus.setOnClickListener(onClick());
        buttonPlus.setOnClickListener(onClick());

        spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listType);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeRepetition.setAdapter(spinnerAdapter);

        spinnerTypeRepetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compteurRepetition = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        builder
                .setTitle("Ajouter une action")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String typeRepetition = "";
                        int valeurRepetition = compteurRepetition;
                        switch (spinnerTypeRepetition.getSelectedItemPosition()) {
                            case 0:
                                valeurRepetition = 0;
                                break;
                            case 1:
                                typeRepetition = "Jours";
                                break;
                            case 2:
                                typeRepetition = "Semaines";
                                break;
                            case 3:
                                typeRepetition = "Mois";
                                break;
                        }

                        selectActionPlantsFragment.updateUserActions(spin.getSelectedItem().toString(), typeRepetition, valeurRepetition);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setSpinnerValue() {
         if (compteurRepetition == 1) {
                int selectedPosition = spinnerTypeRepetition.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listType.set(selectedPosition, "tous les jours");
                        break;
                    case 2:
                        listType.set(selectedPosition, "toutes les semaines");
                        break;
                    case 3:
                        listType.set(selectedPosition, "tous les mois");
                        break;
                }
            } else {
                int selectedPosition = spinnerTypeRepetition.getSelectedItemPosition();
                switch (selectedPosition) {
                    case 1:
                        listType.set(selectedPosition, "tous les " + compteurRepetition + " jours");
                        break;
                    case 2:
                        listType.set(selectedPosition, "toutes les " + compteurRepetition + " semaines");
                        break;
                    case 3:
                        listType.set(selectedPosition, "tous les " + compteurRepetition + " mois");
                        break;
                }
            }
            spinnerAdapter.notifyDataSetChanged();

    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.addActionDialogMinus:
                        if (compteurRepetition > 1) {
                            compteurRepetition--;
                            setSpinnerValue();
                        }
                        break;
                    case R.id.addActionDialogPlus:
                        if (spinnerTypeRepetition.getSelectedItemPosition() == 0) {
                            if (compteurRepetition < 10) {
                                compteurRepetition++;
                            }
                        } else if (spinnerTypeRepetition.getSelectedItemPosition() == 1) {
                            if (compteurRepetition < 6) {
                                compteurRepetition++;
                            }
                        } else {
                            if (compteurRepetition < 6) {
                                compteurRepetition++;
                            }
                        }
                        setSpinnerValue();
                        break;
                }
            }
        };
    }

    public void setListUserActions(List<UserAction> listUserActions) {
        this.listUserActions = listUserActions;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setListUserActionsNames(List<String> listUserActionsNames) {
        this.listUserActionsNames = listUserActionsNames;
    }

    public void setSelectActionPlantsFragment(SelectActionPlantsFragment selectActionPlantsFragment) {
        this.selectActionPlantsFragment = selectActionPlantsFragment;
    }
}
