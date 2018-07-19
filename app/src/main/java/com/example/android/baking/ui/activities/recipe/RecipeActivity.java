package com.example.android.baking.ui.activities.recipe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;
import com.example.android.baking.ui.activities.step.StepActivity;
import com.example.android.baking.ui.fragments.recipe.RecipeFragment;
import com.example.android.baking.ui.fragments.step.StepFragment;

public class RecipeActivity extends AppCompatActivity
        implements RecipeFragment.onBakingStepClickListener
        , StepFragment.StepFragmentSaveInstanceListener {

    public static final String INTENT_EXTRA_RECIPE = "intent-extra-recipe";

    private long mPlayerLastPosition = -1;
    private Recipe mRecipe;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intentThatStartedThisActivity = getIntent();
        mRecipe = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_RECIPE);

        setTitle(mRecipe.getName());

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipe(mRecipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();

        if (findViewById(R.id.divider) != null) {
            mTwoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            StepFragment stepFragment = new StepFragment();
            //
            stepFragment.setBakingStep(mRecipe.getSteps().get(0));
            stepFragment.setListener(this);
            stepFragment.setTwoPane(true);
            stepFragment.setPlayerLastPosition(mPlayerLastPosition);
            fragmentManager.beginTransaction()
                    .add(R.id.baking_step_container, stepFragment)
                    .commit();

        } else {
            mTwoPane = false;
        }

    }

    @Override
    public void onBakingStepSelected(int position) {
        if (mTwoPane) {
            mPlayerLastPosition = -1;
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepFragment stepFragment = new StepFragment();
            stepFragment.setBakingStep(mRecipe.getSteps().get(position));
            stepFragment.setListener(this);
            stepFragment.setTwoPane(true);
            stepFragment.setPlayerLastPosition(mPlayerLastPosition);
            fragmentManager.beginTransaction()
                    .replace(R.id.baking_step_container, stepFragment)
                    .commit();

        } else {
            Intent intent = new Intent(RecipeActivity.this, StepActivity.class);
            intent.putExtra(StepActivity.INTENT_EXTRA_RECIPE_NAME, mRecipe.getName());
            intent.putExtra(StepActivity.INTENT_EXTRA_BAKING_STEPS, mRecipe.getSteps());
            intent.putExtra(StepActivity.INTENT_EXTRA_BAKING_STEP_POSITION, position);
            startActivity(intent);
        }
    }

    @Override
    public void onStepFragmentSaveInstance(long aPlayerPosition) {
        mPlayerLastPosition = aPlayerPosition;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putLong(INSTANCE_STATE_PLAYER_CURRENT_POSITION, mPlayerLastPosition);
//        outState.putInt(INSTANCE_STATE_LAST_STEP_POSITION, mLastStepPosition);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
