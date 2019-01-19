package com.plantme.plantme.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.plantme.plantme.R;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.UserAction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserActionDialogViewHolder extends RecyclerView.ViewHolder {

    private String currentTypeRepetition;

    private CoupleActionDate coupleActionDate;

    Map<CoupleActionDate, List<Object>> repetitionMap;

    private TextView typeAction;
    private Spinner spinnerRepetitions;
    private ArrayAdapter<String> spinnerAdapter;

    private Button buttonMinus;
    private Button buttonPlus;

    private int compteurRepetition;

    private List<String> listTypeRepetition;

    private List<Object> listRepetition;

    private boolean isFirstRun = true;
    private boolean doesValueExist = false;


    public UserActionDialogViewHolder(View viewItem) {
        super(viewItem);

        typeAction = viewItem.findViewById(R.id.rvUserActionType);
        spinnerRepetitions = viewItem.findViewById(R.id.rvSpinnerRepetition);
        buttonMinus = viewItem.findViewById(R.id.rvActionDialogMinus);
        buttonPlus = viewItem.findViewById(R.id.rvActionDialogPlus);

    }

    public void bind(final CoupleActionDate coupleActionDate) {
        this.coupleActionDate = coupleActionDate;
        //ivActionImage.setactionPlant.getActionImage());
        typeAction.setText(coupleActionDate.getUserAction().getActionName());

        listRepetition = new ArrayList<>();
        listRepetition.add(coupleActionDate.getTypeRepetition());
        listRepetition.add(coupleActionDate.getValeurRepetition());
        repetitionMap.put(coupleActionDate, listRepetition);

        buttonMinus.setOnClickListener(onClick());
        buttonPlus.setOnClickListener(onClick());


        listTypeRepetition = new ArrayList<>();
        listTypeRepetition.add("pas de répétition");
        listTypeRepetition.add("tous les jours");
        listTypeRepetition.add("toutes les semaines");
        listTypeRepetition.add("tous les mois");

        spinnerRepetitions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirstRun) {
                    compteurRepetition = coupleActionDate.getValeurRepetition();
                    isFirstRun = false;
                } else {
                    if(!doesValueExist) {
                        compteurRepetition = 1;
                    } else {
                        doesValueExist = false;
                    }
                    listTypeRepetition.set(1, "tous les jours");
                    listTypeRepetition.set(2, "toutes les semaines");
                    listTypeRepetition.set(3, "tous les mois");
                    switch (position) {
                        case 0:
                            repetitionMap.get(coupleActionDate).set(0, "");
                            repetitionMap.get(coupleActionDate).set(1, 0);
                            break;
                        case 1:
                            repetitionMap.get(coupleActionDate).set(0, "Jours");
                            repetitionMap.get(coupleActionDate).set(1, 1);
                            break;
                        case 2:
                            repetitionMap.get(coupleActionDate).set(0, "Semaines");
                            repetitionMap.get(coupleActionDate).set(1, 1);
                            break;
                        case 3:
                            repetitionMap.get(coupleActionDate).set(0, "Mois");
                            repetitionMap.get(coupleActionDate).set(1, 1);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerAdapter = new ArrayAdapter<>(itemView.getContext(),
                android.R.layout.simple_spinner_item, listTypeRepetition);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRepetitions.setAdapter(spinnerAdapter);


        if (coupleActionDate.getValeurRepetition() == 1) {
            switch (coupleActionDate.getTypeRepetition()) {
                case "Jours":
                    listTypeRepetition.set(1, "tous les jours");
                    spinnerRepetitions.setSelection(1);
                    break;
                case "Semaines":
                    listTypeRepetition.set(2, "toutes les semaines");
                    spinnerRepetitions.setSelection(2);
                    break;
                case "Mois":
                    listTypeRepetition.set(3, "tous les mois");
                    spinnerRepetitions.setSelection(3);
                    break;
            }
            doesValueExist = true;
        } else if (coupleActionDate.getValeurRepetition() > 1) {
            switch (coupleActionDate.getTypeRepetition()) {
                case "Jours":
                    listTypeRepetition.set(1, "tous les "+ coupleActionDate.getValeurRepetition() + " jours");
                    spinnerRepetitions.setSelection(1);
                    break;
                case "Semaines":
                    listTypeRepetition.set(2, "toutes les "+ coupleActionDate.getValeurRepetition() + " semaines");
                    spinnerRepetitions.setSelection(2);
                    break;
                case "Mois":
                    listTypeRepetition.set(3, "tous les "+ coupleActionDate.getValeurRepetition() + " mois");
                    spinnerRepetitions.setSelection(3);
                    break;
            }
            doesValueExist = true;

        } else {
            spinnerRepetitions.setSelection(0);
        }

        spinnerAdapter.notifyDataSetChanged();


    }

    public View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rvActionDialogMinus:
                        if (compteurRepetition > 1) {
                            compteurRepetition--;
                            setSpinnerValue();
                        }
                        break;
                    case R.id.rvActionDialogPlus:
                        if (spinnerRepetitions.getSelectedItemPosition() == 1) {
                            if (compteurRepetition < 10) {
                                compteurRepetition++;
                            }
                        } else if (spinnerRepetitions.getSelectedItemPosition() == 2) {
                            if (compteurRepetition < 6) {
                                compteurRepetition++;
                            }
                        } else if (spinnerRepetitions.getSelectedItemPosition() == 3) {
                            if (compteurRepetition < 6) {
                                compteurRepetition++;
                            }
                        }
                        setSpinnerValue();
                        break;
                }
                repetitionMap.get(coupleActionDate).set(1, compteurRepetition);
            }
        };
    }

    public void setSpinnerValue() {
        if (compteurRepetition == 1) {
            int selectedPosition = spinnerRepetitions.getSelectedItemPosition();
            switch (selectedPosition) {
                case 1:
                    listTypeRepetition.set(selectedPosition, "tous les jours");
                    break;
                case 2:
                    listTypeRepetition.set(selectedPosition, "toutes les semaines");
                    break;
                case 3:
                    listTypeRepetition.set(selectedPosition, "tous les mois");
                    break;
            }
        } else {
            int selectedPosition = spinnerRepetitions.getSelectedItemPosition();
            switch (selectedPosition) {
                case 1:
                    listTypeRepetition.set(selectedPosition, "tous les " + compteurRepetition + " jours");
                    break;
                case 2:
                    listTypeRepetition.set(selectedPosition, "toutes les " + compteurRepetition + " semaines");
                    break;
                case 3:
                    listTypeRepetition.set(selectedPosition, "tous les " + compteurRepetition + " mois");
                    break;
            }
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void setRepetitionMap(Map<CoupleActionDate, List<Object>> repetitionMap) {
        this.repetitionMap = repetitionMap;
    }
}