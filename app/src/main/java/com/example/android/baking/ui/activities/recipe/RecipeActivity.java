package com.example.android.baking.ui.activities.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;
import com.example.android.baking.ui.activities.step.StepActivity;
import com.example.android.baking.ui.fragments.recipe.RecipeFragment;

public class RecipeActivity extends AppCompatActivity
        implements RecipeFragment.onBakingStepClickListener {

    public static final String INTENT_EXTRA_RECIPE = "intent-extra-recipe";
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intentThatStartedThisActivity = getIntent();
        mRecipe = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_RECIPE);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipe(mRecipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }

    @Override
    public void onBakingStepSelected(int position) {
        Intent intent = new Intent(RecipeActivity.this, StepActivity.class);
        intent.putExtra(StepActivity.INTENT_EXTRA_STEP, mRecipe.getSteps().get(position));
        startActivity(intent);
    }
}
