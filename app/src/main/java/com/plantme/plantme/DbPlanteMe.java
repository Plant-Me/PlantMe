package com.plantme.plantme;

@Database(entities = {Task.class}, version = 1)
public class DbPlanteMe {
}

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
