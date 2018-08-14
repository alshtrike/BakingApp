package com.projects.android.bakingapp.loaders;

import com.projects.android.bakingapp.data.Recipe;

public interface LoadingStrategy {
    Recipe[] load();
}
