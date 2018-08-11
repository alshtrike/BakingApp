package com.projects.android.bakingapp;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.projects.android.bakingapp.adapters.RecepieAdapter;
import com.projects.android.bakingapp.data.Recepie;
import com.projects.android.bakingapp.loaders.LoadingStrategy;
import com.projects.android.bakingapp.loaders.RecepieAsyncLoader;
import com.projects.android.bakingapp.loaders.UrlLoadingStrategy;
import com.projects.android.bakingapp.utils.JSONGetter;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecepieAdapter.RecepieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Recepie[]>{

    private final int RECEPIE_LOADER_ID = 1;
    private ProgressBar mLoadingIndicator;
    private RecepieAdapter mAdapter;
    private RecyclerView mRecepieRV;
    private Parcelable mRvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        Timber.plant(new Timber.DebugTree());

        mAdapter = new RecepieAdapter(this);
        mRecepieRV = findViewById(R.id.rv_recepies);
        mRecepieRV.setAdapter(mAdapter);
        mRecepieRV.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState !=null){
            if(savedInstanceState.containsKey(getString(R.string.rv_recepie_layout_state))){
                mRvState = savedInstanceState.getParcelable(getString(R.string.rv_recepie_layout_state));
                mRecepieRV.getLayoutManager().onRestoreInstanceState(mRvState);
            }
        }

        Loader<Recepie[]> loader = getSupportLoaderManager().getLoader(RECEPIE_LOADER_ID);
        if(loader == null){
            getSupportLoaderManager().initLoader(RECEPIE_LOADER_ID, savedInstanceState, MainActivity.this);
        }
        else{
            getSupportLoaderManager().restartLoader(RECEPIE_LOADER_ID, savedInstanceState, MainActivity.this);
        }
    }

    @NonNull
    @Override
    public Loader<Recepie[]> onCreateLoader(int id, @Nullable Bundle args) {
        String url = getString(R.string.url_recepies);
        LoadingStrategy strategy = new UrlLoadingStrategy(url, new JSONGetter());
        return new RecepieAsyncLoader(args, this, mLoadingIndicator, strategy);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Recepie[]> loader, Recepie[] recepies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mAdapter.setRecepies(recepies);
        if(mRecepieRV!=null){
            mRecepieRV.getLayoutManager().onRestoreInstanceState(mRvState);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recepie[]> loader) {

    }

    @Override
    public void onClick(Recepie movie) {
        showToast("Clicked on "+movie.getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable layoutState = mRecepieRV.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(getString(R.string.rv_recepie_layout_state), layoutState);
    }

    private void showToast(String toastText){
        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
