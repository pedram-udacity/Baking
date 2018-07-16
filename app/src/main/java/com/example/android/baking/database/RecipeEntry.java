package com.example.android.baking.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipe")
public class RecipeEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipe_name;
    private String ingredients;
    private Double quantity;
    private String measure;


    @Ignore
    public RecipeEntry(String recipe_name, String ingredients, Double quantity, String measure) {
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.measure = measure;
    }

    public RecipeEntry(int id, String recipe_name, String ingredients, Double quantity, String measure) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.measure = measure;
    }


    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String aRecipe_name) {
        recipe_name = aRecipe_name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String aIngredients) {
        ingredients = aIngredients;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double aQuantity) {
        quantity = aQuantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String aMeasure) {
        measure = aMeasure;
    }
}
