package com.example.android.baking.ui.activities.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRequestQueue = Volley.newRequestQueue(this);

        mRecipesAdapter = new RecipesAdapter(this, this);
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mSpanCount = UiUtils.numberOfColumns(this);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, mSpanCount);
        mRecipeRecyclerView.setLayoutManager(layoutManager);
        mRecipeRecyclerView.setAdapter(mRecipesAdapter);
        LoadData();

    }

    private void LoadData() {
//        String url = "http://go.udacity.com/android-baking-app-json";
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Recipe[] recipes = gson.fromJson(response, Recipe[].class);
                ArrayList<Recipe> recipeArrayList = new ArrayList<>(Arrays.asList(recipes));
                mRecipesAdapter.swapData(recipeArrayList);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, stringListener, errorListener);

        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onClick(Recipe aRecipe) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.INTENT_EXTRA_RECIPE, aRecipe);
        startActivity(intent);
    }
}
