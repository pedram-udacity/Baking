package com.example.android.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.database.RecipeDatabase;
import com.example.android.baking.database.RecipeEntry;

import java.util.List;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<RecipeEntry> mRecipeEntries;
    private final RecipeDatabase mRecipeDatabase;
    private final String mRecipeName;

    public BakingWidgetRemoteViewsFactory(Context aContext, Intent intent) {
        mContext = aContext;
        mRecipeName = intent.getStringExtra(BakingWidgetProvider.WIDGET_INTENT_EXTRA_RECIPE_NAME);
        mRecipeDatabase = RecipeDatabase.getInstance(mContext);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipeEntries = mRecipeDatabase.recipeDao().loadRecipeIngredients(mRecipeName);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipeEntries == null ? 0 : mRecipeEntries.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || mRecipeEntries == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_baking_widget_ingredients);
        final String listText = String.format(mContext.getString(R.string.format_ingredients),
                mRecipeEntries.get(position).getIngredients(),
                String.valueOf(mRecipeEntries.get(position).getQuantity()),
                mRecipeEntries.get(position).getMeasure());
        rv.setTextViewText(R.id.ap_baking_li_tv, listText);

        return rv;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
