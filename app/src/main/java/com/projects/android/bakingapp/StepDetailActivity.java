package com.projects.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.projects.android.bakingapp.data.Ingredient;
import com.projects.android.bakingapp.data.Recipe;
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setDetail(detail);
        fragmentManager.beginTransaction()
                .replace(R.id.steps_detail_fragment, detailFragment)
                .commit();
    }
}
