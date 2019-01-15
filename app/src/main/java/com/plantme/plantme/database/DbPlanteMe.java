package com.plantme.plantme.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.plantme.plantme.Dao.UserPlantDao;
import com.plantme.plantme.model.databaseEntity.ActionCalendrier;
import com.plantme.plantme.model.databaseEntity.CoupleActionDate;
import com.plantme.plantme.model.databaseEntity.PlantType;
import com.plantme.plantme.model.databaseEntity.UserPlant;

@Database(entities = {ActionCalendrier.class,CoupleActionDate.class,PlantType.class,UserPlant.class}, version = 1)
public abstract class DbPlanteMe extends RoomDatabase {

    public abstract UserPlantDao userPlantDao();
}

