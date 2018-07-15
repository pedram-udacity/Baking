package com.example.android.baking.ui.activities.step;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;
import com.example.android.baking.ui.fragments.step.StepFragment;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity
        implements StepFragment.StepFragmentSaveInstanceListener {

    public static final String INTENT_EXTRA_BAKING_STEPS = "intent-extra-steps";
    public static final String INTENT_EXTRA_BAKING_STEP_POSITION = "intent-extra-step-position";

    public static final String INSTANCE_STATE_PLAYER_CURRENT_POSITION = "instance-state-player-current-position";
    public static final String INSTANCE_STATE_BAKING_STEPS = "instance-state-baking-steps";
    public static final String INSTANCE_STATE_BAKING_STEP_POSITION = "instance-state-baking-step-position";


    private ArrayList<BakingStep> mBakingStepArrayList;
    private int mBakingStepPosition;

    private long mPlayerLastPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            mPlayerLastPosition = savedInstanceState.getLong(INSTANCE_STATE_PLAYER_CURRENT_POSITION, -1);
            mBakingStepArrayList = savedInstanceState.getParcelableArrayList(INSTANCE_STATE_BAKING_STEPS);
            mBakingStepPosition = savedInstanceState.getInt(INSTANCE_STATE_BAKING_STEP_POSITION);
        } else {
            mPlayerLastPosition = -1;
            Intent intentThatStartedThisActivity = getIntent();
            mBakingStepArrayList = intentThatStartedThisActivity.getParcelableArrayListExtra(INTENT_EXTRA_BAKING_STEPS);
            mBakingStepPosition = intentThatStartedThisActivity.getIntExtra(INTENT_EXTRA_BAKING_STEP_POSITION, 0);
        }

        LinearLayout bakingStepControlsLinearLayout = findViewById(R.id.baking_step_controls_ll);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            bakingStepControlsLinearLayout.setVisibility(View.GONE);
        } else {
            getSupportActionBar().show();
            bakingStepControlsLinearLayout.setVisibility(View.VISIBLE);
        }


        StepFragment stepFragment = new StepFragment();
        stepFragment.setBakingStep(mBakingStepArrayList.get(mBakingStepPosition));
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
        outState.putLong(INSTANCE_STATE_PLAYER_CURRENT_POSITION, mPlayerLastPosition);
        outState.putParcelableArrayList(INSTANCE_STATE_BAKING_STEPS, mBakingStepArrayList);
        outState.putInt(INSTANCE_STATE_BAKING_STEP_POSITION, mBakingStepPosition);
    }

    public void replaceStepsFragment() {
        mPlayerLastPosition = -1;

        StepFragment stepFragment = new StepFragment();
        stepFragment.setBakingStep(mBakingStepArrayList.get(mBakingStepPosition));
        stepFragment.setTwoPane(false);
        stepFragment.setListener(this);
        stepFragment.setPlayerLastPosition(mPlayerLastPosition);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.baking_step_container, stepFragment)
                .commit();

    }

    public void stepsControlPrevOnClick(View view) {
        if (mBakingStepPosition == 0) {
            Toast.makeText(this, "You are at the first step", Toast.LENGTH_SHORT).show();
        } else {
            mBakingStepPosition--;
            replaceStepsFragment();
        }
    }

    public void stepsControlNextOnClick(View view) {
        if (mBakingStepPosition == mBakingStepArrayList.size() - 1) {
            Toast.makeText(this, "You are at the last step", Toast.LENGTH_SHORT).show();
        } else {
            mBakingStepPosition++;
            replaceStepsFragment();
        }
    }
}
