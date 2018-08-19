package com.projects.android.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.projects.android.bakingapp.adapters.StepsAdapter;
import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.utils.HelperFunctions;

import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_recipe_steps);

    }

    @Override
    public void onStepClicked(String detail) {
        if(findViewById(R.id.detail_linear_layout)!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepDetailFragment detailFragment = new StepDetailFragment();
            detailFragment.setDetail(detail);
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_detail_fragment, detailFragment)
                    .commit();
        }
        else{
            Intent startDetailActivity = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.step_details),detail);
            startDetailActivity.putExtras(bundle);
            startActivity(startDetailActivity);
        }
    }
}
