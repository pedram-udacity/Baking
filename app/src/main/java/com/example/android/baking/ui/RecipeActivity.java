package com.example.android.baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;

public class RecipeActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_RECIPE = "intent-extra-recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intentThatStartedThisActivity = getIntent();
        Recipe recipe = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_RECIPE);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipe(recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }
}
