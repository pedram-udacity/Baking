package com.example.android.baking.utilities;

import com.example.android.baking.data.BakingApiJsonContract;
import com.example.android.baking.model.BakingStep;
import com.example.android.baking.model.Ingredient;
import com.example.android.baking.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {


    public static ArrayList<Recipe> getRecipesFromJsonString(String jsonString) {

        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                recipes.add(getRecipe((JSONObject) jsonArray.get(i)));
            }

        } catch (JSONException aE) {
            aE.printStackTrace();
        }

        return recipes;
    }


    private static Recipe getRecipe(JSONObject jsonObject) {

        Recipe recipe = new Recipe();

        try {
            recipe.setId(jsonObject.getInt(BakingApiJsonContract.RECIPE_ID));
            recipe.setName(jsonObject.getString(BakingApiJsonContract.NAME));
            recipe.setIngredients(getIngredients(jsonObject.getJSONArray(BakingApiJsonContract.INGREDIENTS)));
            recipe.setBakingSteps(getBakingSteps(jsonObject.getJSONArray(BakingApiJsonContract.STEPS)));
            recipe.setServings(jsonObject.getInt(BakingApiJsonContract.SERVINGS));
            recipe.setImage(jsonObject.getString(BakingApiJsonContract.IMAGE));
        } catch (JSONException aE) {
            aE.printStackTrace();
        }

        return recipe;
    }

    private static ArrayList<Ingredient> getIngredients(JSONArray aJSONArray) {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            for (int i = 0; i < aJSONArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) aJSONArray.get(i);
                Ingredient ingredient = new Ingredient();
                ingredient.setQuantity(jsonObject.getInt(BakingApiJsonContract.Ingredients.QUANTITY));
                ingredient.setMeasure(jsonObject.getString(BakingApiJsonContract.Ingredients.MEASURE));
                ingredient.setIngredient(jsonObject.getString(BakingApiJsonContract.Ingredients.INGREDIENT));
                ingredients.add(ingredient);
            }
        } catch (JSONException aE) {
            aE.printStackTrace();
        }

        return ingredients;
    }

    private static ArrayList<BakingStep> getBakingSteps(JSONArray aJSONArray) {

        ArrayList<BakingStep> bakingSteps = new ArrayList<>();

        try {
            for (int i = 0; i < aJSONArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) aJSONArray.get(i);
                BakingStep bakingStep = new BakingStep();
                bakingStep.setId(jsonObject.getInt(BakingApiJsonContract.Step.STEP_ID));
                bakingStep.setShortDescription(jsonObject.getString(BakingApiJsonContract.Step.SHORT_DESCRIPTION));
                bakingStep.setDescription(jsonObject.getString(BakingApiJsonContract.Step.DESCRIPTION));
                bakingStep.setVideoUrl(jsonObject.getString(BakingApiJsonContract.Step.VIDEO_URL));
                bakingStep.setThumbnailUrl(jsonObject.getString(BakingApiJsonContract.Step.THUMBNAIL_URL));
                bakingSteps.add(bakingStep);
            }
        } catch (JSONException aE) {
            aE.printStackTrace();
        }

        return bakingSteps;

    }
}
