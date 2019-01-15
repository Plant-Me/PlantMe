package com.plantme.plantme.Dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.plantme.plantme.model.databaseEntity.CoupleActionDate;

import java.util.List;

public interface CoupleActionDateDao {

    @Query("SELECT * FROM couple_action_date")
    List<CoupleActionDate> getAllCoupleActionDate();

    @Query("SELECT * FROM action_calendrier where id = :coupleActionId")
    CoupleActionDate getAllCoupleActionDateById(int coupleActionId);

    @Insert
    void insert(CoupleActionDate coupleActionDate);

    @Delete
    void delete(CoupleActionDate coupleActionDate);

    @Update
    void update(CoupleActionDate coupleActionDate);
}
