package com.example.android.baking.ui.fragments.step;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;

import timber.log.Timber;

public class StepFragment extends Fragment {

    private BakingStep mBakingStep;

    public void setBakingStep(BakingStep aBakingStep) {
        mBakingStep = aBakingStep;
    }

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_baking_step, container, false);

        if (mBakingStep != null) {

            Timber.d(mBakingStep.getShortDescription());

            TextView descTextView = rootView.findViewById(R.id.baking_step_tv);
            descTextView.setText(mBakingStep.getDescription());

        }
        return rootView;

    }
}
