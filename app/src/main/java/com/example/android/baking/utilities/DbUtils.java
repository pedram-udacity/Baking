package com.example.android.baking.utilities;

import com.example.android.baking.database.RecipeDatabase;
import com.example.android.baking.database.RecipeEntry;
import com.example.android.baking.model.Recipe;

import java.util.List;

public class DbUtils {

    public static void saveRecipeToDataBase(RecipeDatabase aRecipeDb, Recipe aRecipe) {
        if (!isRecipeAlreadySaved(aRecipeDb, aRecipe)) {
            for (int i = 0; i < aRecipe.getIngredients().size(); i++) {
                RecipeEntry recipeEntry = new RecipeEntry(aRecipe.getName(),
                        aRecipe.getIngredients().get(i).getIngredient(),
                        aRecipe.getIngredients().get(i).getQuantity(),
                        aRecipe.getIngredients().get(i).getMeasure());
                aRecipeDb.recipeDao().insertRecipe(recipeEntry);
            }
        }
    }


    private static boolean isRecipeAlreadySaved(RecipeDatabase aRecipeDb, Recipe aRecipe) {
        List<Integer> ids = aRecipeDb.recipeDao().checkIfRecipeAlreadySaved(aRecipe.getName());
        if (ids == null || ids.size() == 0) {
            return false;
        }
        return true;
    }

    public static void removeRecipeFromDatabase(RecipeDatabase aRecipeDb, Recipe aRecipe) {
        aRecipeDb.recipeDao().deleteRecipe(aRecipe.getName());
    }
}
