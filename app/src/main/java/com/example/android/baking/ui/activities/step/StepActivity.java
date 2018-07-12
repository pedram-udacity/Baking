package com.example.android.baking.ui.activities.step;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;
import com.example.android.baking.ui.fragments.step.StepFragment;

public class StepActivity extends AppCompatActivity
        implements StepFragment.StepFragmentSaveInstanceListener {

    public static final String INTENT_EXTRA_STEP = "intent-extra-step";
    public static final String PLAYER_CURRENT_POSITION = "player-current-position";


    private long mPlayerLastPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            mPlayerLastPosition = savedInstanceState.getLong(PLAYER_CURRENT_POSITION, -1);
        }

        LinearLayout bakingStepControlsLinearLayout = findViewById(R.id.baking_step_controls_ll);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            bakingStepControlsLinearLayout.setVisibility(View.GONE);
        } else {
            getSupportActionBar().show();
            bakingStepControlsLinearLayout.setVisibility(View.VISIBLE);
        }

        Intent intentThatStartedThisActivity = getIntent();
        BakingStep bakingStep = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_STEP);


        StepFragment stepFragment = new StepFragment();
        stepFragment.setBakingStep(bakingStep);
        stepFragment.setTwoPane(false);
        stepFragment.setListener(this);
        stepFragment.setPlayerLastPosition(mPlayerLastPosition);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.baking_step_container, stepFragment)
                .commit();
    }

    @Override
    public void onStepFragmentSaveInstance(long aPlayerPosition) {
        mPlayerLastPosition = aPlayerPosition;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_CURRENT_POSITION, mPlayerLastPosition);
    }
}
