package com.plantme.plantme.Dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.plantme.plantme.model.databaseEntity.PlantType;


import java.util.List;

public interface PlantTypeDao {

    @Query("SELECT * FROM plant_type")
    List<PlantType> getAllPlantType();

    @Query("SELECT * FROM plant_type where plantTypeId = :planteTypeId")
    PlantType getPlantTypeById(int planteTypeId);

    @Insert
    void insert(PlantType plantType);

    @Delete
    void delete(PlantType plantType);

    @Update
    void update(PlantType plantType);
}
