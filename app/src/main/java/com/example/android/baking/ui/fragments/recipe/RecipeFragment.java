package com.example.android.baking.ui.fragments.recipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;

import timber.log.Timber;

public class RecipeFragment extends Fragment {

    private Recipe mRecipe;
    private onBakingStepClickListener mCallback;

    public void setRecipe(Recipe aRecipe) {
        mRecipe = aRecipe;
    }

    public RecipeFragment() {
    }

    public interface onBakingStepClickListener {
        void onBakingStepSelected(int position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (onBakingStepClickListener) context;
        } catch (ClassCastException aE) {
            throw new ClassCastException(context.toString()
                    + " must implement onBakingStepClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        if (mRecipe != null) {

            Timber.d(mRecipe.getSteps().get(0).getShortDescription());

            IngredientsExpandableListAdapter ingredientsExpandableListAdapter = new IngredientsExpandableListAdapter(getActivity(), mRecipe.getIngredients());
            ExpandableListView expandableListView = rootView.findViewById(R.id.ingredients_elv);
            expandableListView.setAdapter(ingredientsExpandableListAdapter);

            StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), mRecipe.getSteps());
            ListView listView = rootView.findViewById(R.id.steps_lv);
            listView.setAdapter(stepsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCallback.onBakingStepSelected(position);
                }
            });


        }


        return rootView;
    }
}
