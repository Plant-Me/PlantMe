package com.plantme.plantme.Dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.plantme.plantme.model.databaseEntity.UserPlant;

import java.util.List;

public interface UserPlantDao {

    @Query("SELECT * FROM user_plant")
    List<UserPlant> getAllPlant();

    @Query("SELECT * FROM user_plant where userPlantId = :userPlantId")
    UserPlant getPlantById(int userPlantId);

    @Insert
    void insert(UserPlant userPlant);

    @Delete
    void delete(UserPlant userPlant);

    @Update
    void update(UserPlant userPlant);
}
