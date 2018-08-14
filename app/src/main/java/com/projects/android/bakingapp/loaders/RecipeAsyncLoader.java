package com.projects.android.bakingapp.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.projects.android.bakingapp.data.Recipe;

public class RecipeAsyncLoader extends AsyncTaskLoader<Recipe[]> {

    private final Bundle mArgs;
    private Recipe[] mRecipes;
    private final ProgressBar mLoadingIndicator;
    private LoadingStrategy mLoadingStrategy;

    public RecipeAsyncLoader(Bundle args, Context context, ProgressBar loadingIndicator, LoadingStrategy loadingStrategy) {
        super(context);
        this.mArgs = args;
        mRecipes = null;
        mLoadingIndicator = loadingIndicator;
        mLoadingStrategy = loadingStrategy;
    }

    @Override
    public void deliverResult(Recipe[] recipes) {
        mRecipes = recipes;
        super.deliverResult(recipes);
    }

    @Override
    protected void onStartLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        if (mRecipes != null) {
            deliverResult(mRecipes);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public Recipe[] loadInBackground() {
        return mLoadingStrategy.load();
    }
}
