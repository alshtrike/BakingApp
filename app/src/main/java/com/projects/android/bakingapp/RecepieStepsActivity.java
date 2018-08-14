package com.projects.android.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.projects.android.bakingapp.data.Recepie;
import com.projects.android.bakingapp.utils.HelperFunctions;

import timber.log.Timber;

public class RecepieStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_steps);



        /*Bundle b = getIntent().getExtras();
        if(b!=null){
            Recepie r = b.getParcelable(getString(R.string.recepie_parcel));
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
