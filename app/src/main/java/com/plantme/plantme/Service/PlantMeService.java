package com.plantme.plantme.Service;

import com.plantme.plantme.model.PlanteUtilisateur;
import com.plantme.plantme.model.UserPlant;
import com.plantme.plantme.model.retrofitEntity.ResponseLastId;
import com.plantme.plantme.model.retrofitEntity.ResultAllPlant;
import com.plantme.plantme.model.retrofitEntity.ResultOnePlant;
import com.plantme.plantme.model.retrofitEntity.UtilisateurAllPlant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlantMeService {

    public static final String ENDPOINT = "https://planteme.herokuapp.com";

    @GET("/plante")
    Call<List<ResultAllPlant>> listPlant();

    @GET("/plante/{id}")
    Call<List<ResultOnePlant>> plantDetail(@Path("id") int id);


    @POST("/plantesUtilisateur")
    Call<ResponseLastId> addAllPlantUserInDatabase(@Body List<PlanteUtilisateur> planteUtilisateur);

    @GET("/utilisateur/{id}/plante")
    Call<List<UtilisateurAllPlant>> listUtilisateurAllPlant(@Path("id") int id);


}
