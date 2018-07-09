package com.example.android.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<BakingStep> steps;
    private int servings;
    private String image;

    public Recipe() {
        super();
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(BakingStep.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> aIngredients) {
        ingredients = aIngredients;
    }

    public ArrayList<BakingStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<BakingStep> aSteps) {
        steps = aSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int aServings) {
        servings = aServings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String aImage) {
        image = aImage;
    }

}
