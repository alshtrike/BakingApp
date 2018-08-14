package com.projects.android.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.utils.HelperFunctions;

import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);



        /*Bundle b = getIntent().getExtras();
        if(b!=null){
            Recipe r = b.getParcelable(getString(R.string.recipe_parcel));
            tv.setText(r.getName());
        }*/

        if(findViewById(R.id.detail_linear_layout)!=null){
            HelperFunctions.showToast("Tablet Layout", this);
        }
        else{
            HelperFunctions.showToast("Phone Layout", this);
        }


    }
}
