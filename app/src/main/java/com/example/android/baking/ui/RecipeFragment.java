package com.example.android.baking.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;

import timber.log.Timber;

public class RecipeFragment extends Fragment {

    private Recipe mRecipe;

    public void setRecipe(Recipe aRecipe) {
        mRecipe = aRecipe;
    }

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        Timber.d(mRecipe.getBakingSteps().get(0).getShortDescription());
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), mRecipe.getBakingSteps());
        ListView listView = rootView.findViewById(R.id.steps_lv);
        listView.setAdapter(stepsAdapter);

        return rootView;
    }
}
