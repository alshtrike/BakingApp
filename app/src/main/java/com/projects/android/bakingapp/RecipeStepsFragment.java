package com.projects.android.bakingapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.bakingapp.databinding.FragmentRecipeStepsBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment {


    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRecipeStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);
        View view = binding.getRoot();
        binding.testMasterListTv.setText("Test deez nutz");
        return view;
    }

}
