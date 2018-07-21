package com.example.android.baking.ui.activities.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {


    private final Context mContext;
    private ArrayList<Recipe> mRecipeArrayList;

    private final RecipesAdapterOnClickHandler mRecipesAdapterOnClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe aRecipe);
    }


    public RecipesAdapter(Context aContext, RecipesAdapterOnClickHandler aRecipesAdapterOnClickHandler) {
        mContext = aContext;
        mRecipesAdapterOnClickHandler = aRecipesAdapterOnClickHandler;
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_item_recipes;

        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        view.setFocusable(true);

        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {

        String image_url = mRecipeArrayList.get(position).getImage();
        if (image_url.equals("")) {
            image_url = null;
        }
        Picasso.with(mContext)
                .load(image_url)
                .placeholder(R.drawable.ic_launcher)
                .into(holder.recipeImageView);
        holder.recipeTextView.setText(mRecipeArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipeArrayList == null)
            return 0;
        return mRecipeArrayList.size();
    }

    public void swapData(ArrayList<Recipe> aRecipeArrayList) {
        mRecipeArrayList = aRecipeArrayList;
        notifyDataSetChanged();
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final ImageView recipeImageView;
        final TextView recipeTextView;

        public RecipesAdapterViewHolder(View itemView) {
            super(itemView);

            recipeImageView = itemView.findViewById(R.id.recipe_iv);
            recipeTextView = itemView.findViewById(R.id.recipe_tv);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeArrayList.get(adapterPosition);
            mRecipesAdapterOnClickHandler.onClick(recipe);
        }

    }
}
