package com.plantme.plantme.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plantme.plantme.ActionPlantsViewHolder;
import com.plantme.plantme.R;
import com.plantme.plantme.UserActionDialogViewHolder;
import com.plantme.plantme.model.CoupleActionDate;

import java.util.List;
import java.util.Map;

public class UserActionDialogAdapter extends RecyclerView.Adapter<UserActionDialogViewHolder> {
    private final List<CoupleActionDate> coupleActionDateList;

    private Map<CoupleActionDate, List<Object>> repetitionMap;

    public UserActionDialogAdapter(List<CoupleActionDate> coupleActionDateList) {
        this.coupleActionDateList = coupleActionDateList;
    }

    @NonNull
    @Override
    public UserActionDialogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_user_action_dialog, viewGroup, false);
        UserActionDialogViewHolder userActionDialogViewHolder = new UserActionDialogViewHolder(view);
        userActionDialogViewHolder.setRepetitionMap(repetitionMap);
        return userActionDialogViewHolder;
    }


    @Override
    public void onBindViewHolder(UserActionDialogViewHolder userActionDialogViewHolder, int i) {

        CoupleActionDate coupleActionDate = coupleActionDateList.get(i);
        userActionDialogViewHolder.bind(coupleActionDate);
    }

    @Override
    public int getItemCount() {
        return coupleActionDateList.size();
    }

    public void setRepetitionMap(Map<CoupleActionDate, List<Object>> repetitionMap) {
        this.repetitionMap = repetitionMap;
    }
}
