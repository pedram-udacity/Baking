package com.example.android.baking.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import timber.log.Timber;

@Database(entities = {RecipeEntry.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipe";
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d("Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, RecipeDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Timber.d( "Getting the database instance");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();

}
