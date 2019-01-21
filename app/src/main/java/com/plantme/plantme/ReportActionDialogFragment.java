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

import com.plantme.plantme.fragment.HomeWeekFragment;
import com.plantme.plantme.model.CoupleActionDate;

import java.util.ArrayList;
import java.util.List;

public class ReportActionDialogFragment extends DialogFragment {

    private CoupleActionDate coupleActionDate;
    private HomeWeekFragment homeWeekFragment;
    private int position;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_fragment_report_actions, null);

        final Spinner spin;
        spin = (Spinner)view.findViewById(R.id.spinner);

        List<String> listDays = new ArrayList<>();
        listDays.add("1 day");
        listDays.add("2 days");
        listDays.add("3 days");
        listDays.add("4 days");
        listDays.add("5 days");
        listDays.add("6 days");
        listDays.add("7 days");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, listDays);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(dataAdapter);

        builder
                .setTitle("Title")
                .setView(view)
                .setPositiveButton("Report", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        homeWeekFragment.updateUserActions(coupleActionDate, spin.getSelectedItem().toString(), position);
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

    public void setCoupleActionDate(CoupleActionDate coupleActionDate) {
        this.coupleActionDate = coupleActionDate;
    }

    public void setHomeWeekFragment(HomeWeekFragment homeWeekFragment) {
        this.homeWeekFragment = homeWeekFragment;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
