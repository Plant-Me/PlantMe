package com.plantme.plantme.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.plantme.plantme.R;
import com.plantme.plantme.adapter.UserActionDialogAdapter;
import com.plantme.plantme.fragment.HomeWeekFragment;
import com.plantme.plantme.model.CoupleActionDate;
import com.plantme.plantme.model.UserAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActionPlantDetailDialog extends DialogFragment {

    private List<CoupleActionDate> coupleActionDateList;
    private Map<CoupleActionDate, List<Object>> repetitionMap;


    private RecyclerView recyclerViewActions;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_action_plant_detail, null);

        repetitionMap = new HashMap<>();


        recyclerViewActions = view.findViewById(R.id.recyclerViewActionDialog);

        UserActionDialogAdapter userActionDialogAdapter = new UserActionDialogAdapter(coupleActionDateList);
        userActionDialogAdapter.setRepetitionMap(repetitionMap);
        recyclerViewActions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewActions.setAdapter(userActionDialogAdapter);






        builder
                .setTitle("Répétitions")
                .setView(view)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Iterator iterator = repetitionMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry entry = (Map.Entry) iterator.next();
                            ((CoupleActionDate)entry.getKey()).setRepetition((String)((List<Object>)entry.getValue()).get(0), (int)((List<Object>)entry.getValue()).get(1));
                        }
                    }
                })
                .setNegativeButton("Annnuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }





    public void setUserActionList(List<CoupleActionDate> coupleActionDateList) {
        this.coupleActionDateList = coupleActionDateList;
    }

    public Map<CoupleActionDate, List<Object>> getRepetitionMap() {
        return repetitionMap;
    }
}