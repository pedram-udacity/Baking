package com.example.android.baking.ui.fragments.recipe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;

import java.util.ArrayList;

public class StepsAdapter extends ArrayAdapter<BakingStep> {

    private int mSelectedPosition;

    public StepsAdapter(@NonNull Context context, ArrayList<BakingStep> aBakingSteps, int aSelectedPosition) {
        super(context, 0, aBakingSteps);
        mSelectedPosition = aSelectedPosition;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        BakingStep bakingStep = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_baking_steps, parent, false);
        }

        TextView desc_tv = convertView.findViewById(R.id.baking_steps_desc_tv);
        desc_tv.setText(bakingStep.getShortDescription());

//        CardView cardView = convertView.findViewById(R.id.listItemBakingStepsCardView);
//        cardView.setBackgroundColor(getContext().getResources().getColor(R.color.md_blue_grey_100));
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                CardView cardView = v.findViewById(R.id.listItemBakingStepsCardView);
//                cardView.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
//
//            }
//        });

        return convertView;
    }



}
