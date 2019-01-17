package com.plantme.plantme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.plantme.plantme.fragment.SelectActionPlantsFragment;
import com.plantme.plantme.model.UserAction;

import java.util.ArrayList;
import java.util.List;

public class SelectActionDialogFragment extends DialogFragment {

    private List<UserAction> listUserActions;
    private UserAction userAction;
    private int position;
    private MainActivity mainActivity;
    private List<String> listUserActionsNames;
    private SelectActionPlantsFragment selectActionPlantsFragment;

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

        builder
                .setTitle("Title")
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        selectActionPlantsFragment.updateUserActions(spin.getSelectedItem().toString());
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
