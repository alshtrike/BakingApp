package com.projects.android.bakingapp.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.bakingapp.R;
import com.projects.android.bakingapp.data.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder>{

    private Recipe[] mRecipes;
    private final RecipeAdapterOnClickHandler mClickHandler;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public void setRecipes(Recipe[] recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipes[holder.getAdapterPosition()];
        TextView mRecipeTitle = holder.mRecipeTitle;
        String recipeName = recipe.getName();
        mRecipeTitle.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.length;
    }

    protected class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView mRecipeTitle;
        public RecipeAdapterViewHolder(View view) {
            super(view);
            mRecipeTitle = view.findViewById(R.id.tv_recipe_card_text);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Recipe currentRecipe = mRecipes[getAdapterPosition()];
            mClickHandler.onClick(currentRecipe);
        }
    }

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe movie);
    }
}
