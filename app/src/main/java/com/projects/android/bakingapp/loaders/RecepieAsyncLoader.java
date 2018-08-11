package com.projects.android.bakingapp.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.projects.android.bakingapp.data.Recepie;

public class RecepieAsyncLoader extends AsyncTaskLoader<Recepie[]> {

    private final Bundle mArgs;
    private Recepie[] mRecepies;
    private final ProgressBar mLoadingIndicator;
    private LoadingStrategy mLoadingStrategy;

    public RecepieAsyncLoader(Bundle args, Context context, ProgressBar loadingIndicator, LoadingStrategy loadingStrategy) {
        super(context);
        this.mArgs = args;
        mRecepies = null;
        mLoadingIndicator = loadingIndicator;
        mLoadingStrategy = loadingStrategy;
    }

    @Override
    public void deliverResult(Recepie[] recepies) {
        mRecepies = recepies;
        super.deliverResult(recepies);
    }

    @Override
    protected void onStartLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        if (mRecepies != null) {
            deliverResult(mRecepies);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public Recepie[] loadInBackground() {
        return mLoadingStrategy.load();
    }
}
