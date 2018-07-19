package com.example.android.baking.ui.fragments.recipe;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.Ingredient;

import java.util.List;

public class IngredientsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Ingredient> mIngredients;
    private String mRecipeName;

    public IngredientsExpandableListAdapter(Context aContext, List<Ingredient> aIngredients, String aRecipeName) {
        mContext = aContext;
        mIngredients = aIngredients;
        mRecipeName = aRecipeName;
    }

    @Override
    public int getGroupCount() {
        return 1; // This list view is used for Ingredients and there is always one group for Ingredients.
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mIngredients.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mIngredients;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mIngredients.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        Ingredient ingredient = (Ingredient) getGroup(groupPosition);
        String headerTitle = mContext.getString(R.string.ingredients_card);
        if (mRecipeName.endsWith("s")) {
            headerTitle = mRecipeName + "' " + headerTitle;
        } else {
            headerTitle = mRecipeName + "'s " + headerTitle;
        }

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_ingredients_group, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.ingredients_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Ingredient ingredient = (Ingredient) getChild(groupPosition, childPosition);
        final String expandedListText = String.format(mContext.getString(R.string.format_ingredients), ingredient.getIngredient(), String.valueOf(ingredient.getQuantity()), ingredient.getMeasure());
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_baking_step_child, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.baking_step_child);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
