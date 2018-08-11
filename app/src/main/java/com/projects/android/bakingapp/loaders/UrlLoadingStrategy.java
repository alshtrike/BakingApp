package com.projects.android.bakingapp.loaders;

import com.google.gson.Gson;
import com.projects.android.bakingapp.data.Recepie;
import com.projects.android.bakingapp.utils.JSONGetter;

public class UrlLoadingStrategy implements LoadingStrategy {
    private String mUrl;
    private JSONGetter mJsonGetter;

    public UrlLoadingStrategy(String url, JSONGetter jsonGetter){
        mUrl = url;
        mJsonGetter = jsonGetter;
    }

    @Override
    public Recepie[] load() {
        String json = mJsonGetter.getJson(mUrl);
        Gson gson = new Gson();
        Recepie[] recepies = gson.fromJson(json, Recepie[].class);
        return recepies;
    }


}
