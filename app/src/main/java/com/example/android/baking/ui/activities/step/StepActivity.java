package com.example.android.baking.ui.activities.step;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;
import com.example.android.baking.ui.fragments.step.StepFragment;

public class StepActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_STEP = "intent-extra-step";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intentThatStartedThisActivity = getIntent();
        BakingStep bakingStep = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_STEP);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setBakingStep(bakingStep);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.baking_step_container, stepFragment)
                .commit();
    }
}
