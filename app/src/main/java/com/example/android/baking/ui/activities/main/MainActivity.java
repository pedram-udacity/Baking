package com.example.android.baking.ui.activities.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;
import com.example.android.baking.ui.activities.recipe.RecipeActivity;
import com.example.android.baking.utilities.NetworkUtils;
import com.example.android.baking.utilities.UiUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {


    @BindView(R.id.recipe_rv)
    RecyclerView mRecipeRecyclerView;

    private int mSpanCount = 1;

    private RecipesAdapter mRecipesAdapter;
    private ArrayList<Recipe> mRecipeArrayList;

    private RequestQueue mRequestQueue;
    private String INSTANCE_STATE_RECIPE_ARRAY_LIST = "instance-state-recipe-array-list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // If the device has a large screen(ie. a tablet) then calculate the Span Count otherwise Span Count is one.
        mRecipesAdapter = new RecipesAdapter(this, this);
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mSpanCount = UiUtils.numberOfColumns(this);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, mSpanCount);
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.setAdapter(mRecipesAdapter);

        if (savedInstanceState != null) {
            mRecipeArrayList = savedInstanceState.getParcelableArrayList(INSTANCE_STATE_RECIPE_ARRAY_LIST);
        }
        if (mRecipeArrayList == null) {
            loadData();
        } else {
            reloadRecyclerView();
        }

    }

    private void loadData() {

        if (NetworkUtils.isOnline(this)) {

            mRequestQueue = Volley.newRequestQueue(this);

            String url = NetworkUtils.BAKING_APP_URL;

            Response.Listener<String> stringListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Recipe[] recipes = gson.fromJson(response, Recipe[].class);
                    mRecipeArrayList = new ArrayList<>(Arrays.asList(recipes));
                    reloadRecyclerView();
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            };

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, stringListener, errorListener);

            mRequestQueue.add(stringRequest);

        } else {
            NetworkUtils.showNoNetworkToast(this);
            if (mRecipeArrayList != null) {
                reloadRecyclerView();
            }
        }
    }

    private void reloadRecyclerView() {
        mRecipesAdapter.swapData(mRecipeArrayList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(INSTANCE_STATE_RECIPE_ARRAY_LIST, mRecipeArrayList);
    }

    @Override
    public void onClick(Recipe aRecipe) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_EXTRA_RECIPE, aRecipe);
        startActivity(intent);
    }
}
