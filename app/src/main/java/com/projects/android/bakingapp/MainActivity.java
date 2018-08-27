package com.projects.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;

import com.projects.android.bakingapp.adapters.RecipeAdapter;
import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.loaders.LoadingStrategy;
import com.projects.android.bakingapp.loaders.RecipeAsyncLoader;
import com.projects.android.bakingapp.loaders.UrlLoadingStrategy;
import com.projects.android.bakingapp.utils.JSONGetter;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Recipe[]>{

    private final int RECEPIE_LOADER_ID = 1;
    private ProgressBar mLoadingIndicator;
    private RecipeAdapter mAdapter;
    private RecyclerView mRecipeRV;
    private Parcelable mRvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        Timber.plant(new Timber.DebugTree());

        mAdapter = new RecipeAdapter(this);
        int numOfColumns = calculateNoOfColumns();
        GridLayoutManager layoutManager = new GridLayoutManager(this, numOfColumns);
        mRecipeRV = findViewById(R.id.rv_recipes);
        mRecipeRV.setAdapter(mAdapter);
        mRecipeRV.setLayoutManager(layoutManager);

        if(savedInstanceState !=null){
            if(savedInstanceState.containsKey(getString(R.string.rv_recipe_layout_state))){
                mRvState = savedInstanceState.getParcelable(getString(R.string.rv_recipe_layout_state));
                mRecipeRV.getLayoutManager().onRestoreInstanceState(mRvState);
            }
        }

        Loader<Recipe[]> loader = getSupportLoaderManager().getLoader(RECEPIE_LOADER_ID);
        if(loader == null){
            getSupportLoaderManager().initLoader(RECEPIE_LOADER_ID, savedInstanceState, MainActivity.this);
        }
        else{
            getSupportLoaderManager().restartLoader(RECEPIE_LOADER_ID, savedInstanceState, MainActivity.this);
        }
    }

    @NonNull
    @Override
    public Loader<Recipe[]> onCreateLoader(int id, @Nullable Bundle args) {
        String url = getString(R.string.url_recipes);
        LoadingStrategy strategy = new UrlLoadingStrategy(url, new JSONGetter());
        return new RecipeAsyncLoader(this, mLoadingIndicator, strategy);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Recipe[]> loader, Recipe[] recipes) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setRecipes(recipes);
        if(mRecipeRV!=null){
            mRecipeRV.getLayoutManager().onRestoreInstanceState(mRvState);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recipe[]> loader) {

    }

    @Override
    public void onClick(Recipe recipe) {
        Intent startStepsActivity = new Intent(this, RecipeStepsActivity.class);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(getString(R.string.recipe_parcel),recipe);
        startStepsActivity.putExtras(recipeBundle);
        startActivity(startStepsActivity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable layoutState = mRecipeRV.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(getString(R.string.rv_recipe_layout_state), layoutState);
    }

    private int calculateNoOfColumns() {
        //check if tablet (smallest width > 600dp)
        //2 columns for tablet 1 otherwise
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        float smallestWidth = Math.min(widthDp, heightDp);

        return smallestWidth > 600 ? 2 : 1;
    }
}
