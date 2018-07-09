package com.example.android.baking.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;

import java.util.List;

public class StepsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<BakingStep> mBakingSteps;

    public StepsExpandableListAdapter(Context aContext, List<BakingStep> aBakingSteps) {
        mContext = aContext;
        mBakingSteps = aBakingSteps;
    }

    @Override
    public int getGroupCount() {
        return mBakingSteps.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // For BakingStep the Description is the child and it is always one.
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mBakingSteps.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mBakingSteps.get(groupPosition).getDescription();
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
        BakingStep bakingStep = (BakingStep) getGroup(groupPosition);
        String headerTitle = bakingStep.getShortDescription();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_baking_step_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.baking_step_group);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_baking_step_child, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.baking_step_child);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
