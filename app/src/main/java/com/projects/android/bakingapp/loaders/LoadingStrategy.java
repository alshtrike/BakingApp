package com.projects.android.bakingapp.loaders;

import com.projects.android.bakingapp.data.Recepie;

public interface LoadingStrategy {
    Recepie[] load();
}
