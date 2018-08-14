package com.projects.android.bakingapp.loaders;

import com.google.gson.Gson;
import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.utils.JSONGetter;

public class UrlLoadingStrategy implements LoadingStrategy {
    private String mUrl;
    private JSONGetter mJsonGetter;

    public UrlLoadingStrategy(String url, JSONGetter jsonGetter){
        mUrl = url;
        mJsonGetter = jsonGetter;
    }

    @Override
    public Recipe[] load() {
        String json = mJsonGetter.getJson(mUrl);
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(json, Recipe[].class);
        return recipes;
    }


}
