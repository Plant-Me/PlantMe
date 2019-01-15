package com.plantme.plantme.Service;

import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;
import com.plantme.plantme.model.retrofitEntity.ResultOnePlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantMeService {

    public static final String ENDPOINT = "http://10.0.2.2:3000";

    @GET("/plante")
    Call<List<ResultAllPlant>> listPlant();

    @GET("/plante/{id}")
    Call<List<ResultOnePlant>> plantDetail(@Path("id") int id);
}
