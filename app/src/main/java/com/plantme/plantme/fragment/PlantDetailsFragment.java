package com.plantme.plantme.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plantme.plantme.MainActivity;
import com.plantme.plantme.R;
import com.plantme.plantme.model.UserPlant;

import org.w3c.dom.Text;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlantDetailsFragment extends Fragment {

    UserPlant userPlant;
    ImageView detailImagePlant;
    TextView detailFrName;
    TextView detailLtnName;
    TextView detailFlowerColor;
    TextView detailType;
    TextView detailExposition;
    TextView detailGround;
    TextView detailUsage;
    TextView detailActionType;

    View view;

    public PlantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ActionBar ab = ((MainActivity)getContext()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        detailImagePlant = view.findViewById(R.id.detailImagePlant);
        detailFrName = view.findViewById(R.id.detailFrName);
        //detailLtnName = view.findViewById(R.id.detailImagePlant);
        //detailFlowerColor = view.findViewById(R.id.detailImagePlant);
        detailType = view.findViewById(R.id.detailType);
        //detailExposition = view.findViewById(R.id.detailImagePlant);
        //detailGround = view.findViewById(R.id.detailImagePlant);
        detailUsage = view.findViewById(R.id.detailUsage);
        //detailActionType = view.findViewById(R.id.detailImagePlant);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_details, container, false);

        this.view = view;

//        Bundle bundle = getArguments();
//        plant = (Plant) bundle.getSerializable("selectedPlant");
//
        detailImagePlant = view.findViewById(R.id.detailImagePlant);
        detailFrName = view.findViewById(R.id.detailFrName);
        //detailLtnName = view.findViewById(R.id.detailImagePlant);
        //detailFlowerColor = view.findViewById(R.id.detailImagePlant);
        detailType = view.findViewById(R.id.detailType);
        //detailExposition = view.findViewById(R.id.detailImagePlant);
        //detailGround = view.findViewById(R.id.detailImagePlant);
        detailUsage = view.findViewById(R.id.detailUsage);
        //detailActionType = view.findViewById(R.id.detailImagePlant);

        bind(userPlant);

        // Inflate the layout for this fragment
        return view;
    }

    public void bind(UserPlant userPlant) {
        detailFrName.setText(userPlant.getUserPlant().getFrName());
        detailType.setText(userPlant.getUserPlant().getType());
        detailUsage.setText(userPlant.getUserPlant().getUsage());
    }

    public void setPlant(UserPlant userPlant) {
        if(this.userPlant == null || !this.userPlant.equals(userPlant)) {
            this.userPlant = userPlant;

        }
    }



}
