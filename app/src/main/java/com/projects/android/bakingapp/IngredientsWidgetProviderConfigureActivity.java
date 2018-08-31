package com.projects.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.projects.android.bakingapp.data.Ingredient;
import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.loaders.LoadingStrategy;
import com.projects.android.bakingapp.loaders.RecipeAsyncLoader;
import com.projects.android.bakingapp.loaders.UrlLoadingStrategy;
import com.projects.android.bakingapp.utils.HelperFunctions;
import com.projects.android.bakingapp.utils.JSONGetter;

/**
 * The configuration screen for the {@link IngredientsWidgetProvider IngredientsWidgetProvider} AppWidget.
 */
public class IngredientsWidgetProviderConfigureActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe[]>{

    private static final String PREFS_NAME = "com.projects.android.bakingapp.IngredientsWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_PREFIX_INGREDIENTS_KEY = "appwidgetIngredients_";
    private final int RECEPIE_LOADER_ID = 1;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Spinner mSelectRecipe;
    private ProgressBar mLoadingIndicator;
    private Recipe[] mRecipes;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = IngredientsWidgetProviderConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mSelectRecipe.getSelectedItem().toString();
            String ingredients = findRecepieIngredients(widgetText);
            saveTitlePref(context, widgetText);
            saveIngredientPref(context, ingredients);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            IngredientsWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    private String findRecepieIngredients(String recipeName) {
        Ingredient[] ingredients = null;
        for(Recipe r : mRecipes){
            if(r.getName().equals(recipeName)){
                ingredients = r.getIngredients();

            }
        }

        return HelperFunctions.ingredientArrayToString(ingredients);
    }

    public IngredientsWidgetProviderConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY, text);
        prefs.apply();
    }

    static void saveIngredientPref(Context context, String ingredients) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_INGREDIENTS_KEY, ingredients );
        prefs.apply();
    }
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static String loadIngredientPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String ingredients = "";
        ingredients = prefs.getString(PREF_PREFIX_INGREDIENTS_KEY, ingredients);
        return ingredients;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredients_widget_provider_configure);
        mSelectRecipe = findViewById(R.id.select_recipe);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        mLoadingIndicator = findViewById(R.id.pb_widget_loading_indicator);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        Loader<Recipe[]> loader = getSupportLoaderManager().getLoader(RECEPIE_LOADER_ID);
        if(loader == null){
            getSupportLoaderManager().initLoader(RECEPIE_LOADER_ID, icicle, IngredientsWidgetProviderConfigureActivity.this);
        }
        else{
            getSupportLoaderManager().restartLoader(RECEPIE_LOADER_ID, icicle, IngredientsWidgetProviderConfigureActivity.this);
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
    public void onLoadFinished(@NonNull Loader<Recipe[]> loader, Recipe[] data) {
        String[] recipeNames = new String[data.length];
        mRecipes = data;
        for(int i=0; i<data.length; i++){
            recipeNames[i]= data[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, recipeNames);
        mSelectRecipe.setAdapter(adapter);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recipe[]> loader) {

    }
}

