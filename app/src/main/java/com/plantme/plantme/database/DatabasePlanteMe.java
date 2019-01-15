package com.plantme.plantme.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabasePlanteMe {

    private Context mCtx;
    private static DatabasePlanteMe mInstance;

    //our app database object
    private DbPlanteMe dbPlanteMe;

    private DatabasePlanteMe(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        dbPlanteMe = Room.databaseBuilder(mCtx, DbPlanteMe.class, "PlantMe").build();
    }

    public static synchronized DatabasePlanteMe getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabasePlanteMe(mCtx);
        }
        return mInstance;
    }

    public DbPlanteMe getAppDatabase() {
        return dbPlanteMe;
    }
}
