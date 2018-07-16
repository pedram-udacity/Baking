package com.example.android.baking.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe ORDER BY id")
    List<RecipeEntry> loadAllRecipes();

    @Query("SELECT * FROM recipe WHERE recipe_name = :recipe_name")
    List<RecipeEntry> loadRecipeIngredients(String recipe_name);

    @Query("SELECT id FROM recipe WHERE recipe_name= :recipe_name")
    List<Integer> checkIfRecipeAlreadySaved(String recipe_name);

    @Query("SELECT DISTINCT recipe_name FROM recipe")
    List<String> loadExistingRecipeNames();

    @Insert
    void insertRecipe(RecipeEntry aRecipeEntry);

    @Query("DELETE FROM recipe WHERE recipe_name= :recipe_name")
    void deleteRecipe(String recipe_name);
}
