package com.projects.android.bakingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.projects.android.bakingapp.data.Ingredient;
import com.projects.android.bakingapp.data.Step;

import timber.log.Timber;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_step_detail);

        Bundle b = getIntent().getExtras();
        Step detail = b.getParcelable(getString(R.string.step_details));
        Parcelable[] parcel = b.getParcelableArray(getString(R.string.ingredients_list));
        Ingredient[] ingredients=null;
        if(parcel!=null){
            ingredients = new Ingredient[parcel.length];
            System.arraycopy(parcel, 0, ingredients, 0, parcel.length);
        }

        if(savedInstanceState ==null){
            //no need to replace again when we rotate
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepDetailFragment detailFragment = new StepDetailFragment();
            detailFragment.setDetail(detail);
            detailFragment.setIsPhone(true);
            detailFragment.setIngredients(ingredients);
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_detail_fragment, detailFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(getString(R.string.is_rotation), true);
        super.onSaveInstanceState(outState);
    }
}
