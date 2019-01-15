package com.plantme.plantme.Dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.plantme.plantme.model.databaseEntity.ActionCalendrier;

import java.util.List;

public interface ActionCalendrierDao {

    @Query("SELECT * FROM action_calendrier")
    List<ActionCalendrier> getAllActionCalendrier();

    @Query("SELECT * FROM action_calendrier where id = :actionCalendrierId")
    ActionCalendrier getActionCalendrierById(int actionCalendrierId);

    @Insert
    void insert(ActionCalendrier actionCalendrier);

    @Delete
    void delete(ActionCalendrier actionCalendrier);

    @Update
    void update(ActionCalendrier actionCalendrier);
}
