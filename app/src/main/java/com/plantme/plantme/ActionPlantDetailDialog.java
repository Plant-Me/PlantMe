package com.plantme.plantme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.plantme.plantme.fragment.HomeWeekFragment;
import com.plantme.plantme.model.CoupleActionDate;

import java.util.ArrayList;
import java.util.List;

public class ActionPlantDetailDialog extends DialogFragment {

    private CoupleActionDate coupleActionDate;
    private HomeWeekFragment homeWeekFragment;

    private int compteurRepetition;

    private List<String> listTypeRepetition;
    private Spinner spinnerRepetitions;
    private ArrayAdapter<String> spinnerAdapter;

    private Button buttonMinus;
    private Button buttonPlus;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_action_plant_detail, null);


        spinnerRepetitions = view.findViewById(R.id.actionDialogSpinnerTypeRepetition);
        buttonMinus = view.findViewById(R.id.actionDialogButtonMinus);
        buttonPlus = view.findViewById(R.id.actionDialogButtonPlus);

        buttonMinus.setOnClickListener(onClick());
        buttonPlus.setOnClickListener(onClick());

        listTypeRepetition = new ArrayList<>();
        listTypeRepetition.add("jours");
        listTypeRepetition.add("semaines");
        listTypeRepetition.add("mois");


        spinnerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, listTypeRepetition);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRepetitions.setAdapter(spinnerAdapter);

        builder
                .setTitle("Title")
                .setView(view)
                .setPositiveButton("Report", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String typeRepetition = "";
                        int valeurRepetition = compteurRepetition;
                        switch (spinnerRepetitions.getSelectedItemPosition()) {
                            case 0:
                                typeRepetition = "Jours";
                                break;
                            case 1:
                                typeRepetition = "Semaines";
                                break;
                            case 2:
                                typeRepetition = "Mois";
                                break;
                        }

                        coupleActionDate.setRepetition(typeRepetition, valeurRepetition);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.actionDialogButtonMinus:
                        if (compteurRepetition > 1) {
                            compteurRepetition--;
                            setSpinnerValue();
                        }
                        break;
                    case R.id.actionDialogButtonPlus:
                        if (spinnerRepetitions.getSelectedItemPosition() == 0) {
                            if (compteurRepetition < 7) {
                                compteurRepetition++;
                            }
                        } else if (spinnerRepetitions.getSelectedItemPosition() == 1) {
                            if (compteurRepetition < 4) {
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

    public void setSpinnerValue() {
        if (compteurRepetition == 1) {
            int selectedPosition = spinnerRepetitions.getSelectedItemPosition();
            switch (selectedPosition) {
                case 0:
                    listTypeRepetition.set(selectedPosition, "tous les jours");
                    break;
                case 1:
                    listTypeRepetition.set(selectedPosition, "toutes les semaines");
                    break;
                case 2:
                    listTypeRepetition.set(selectedPosition, "tous les mois");
                    break;
            }
        } else {
            int selectedPosition = spinnerRepetitions.getSelectedItemPosition();
            switch (selectedPosition) {
                case 0:
                    listTypeRepetition.set(selectedPosition, "tous les " + compteurRepetition + " jours");
                    break;
                case 1:
                    listTypeRepetition.set(selectedPosition, "toutes les " + compteurRepetition + " semaines");
                    break;
                case 2:
                    listTypeRepetition.set(selectedPosition, "tous les " + compteurRepetition + " mois");
                    break;
            }
        }
        spinnerAdapter.notifyDataSetChanged();
    }


        public void setCoupleActionDate (CoupleActionDate coupleActionDate){
            this.coupleActionDate = coupleActionDate;
        }

        public void setPosition ( int position){
            this.position = position;
        }
    }