package com.projects.android.bakingapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.bakingapp.adapters.StepsAdapter;
import com.projects.android.bakingapp.data.Ingredient;
import com.projects.android.bakingapp.data.Recipe;
import com.projects.android.bakingapp.data.Step;
import com.projects.android.bakingapp.databinding.FragmentRecipeStepsBinding;

import java.util.List;


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

        Bundle b = getActivity().getIntent().getExtras();
        Recipe recipe = b.getParcelable(getString(R.string.recipe_parcel));
        String name = recipe.getName();
        FragmentRecipeStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false);
        View view = binding.getRoot();
        List<Ingredient> ingredients = recipe.getIngredients();
        List<Step> steps = recipe.getSteps();

        StepsAdapter stepsAdapter = new StepsAdapter();
        stepsAdapter.setSteps(steps);
        RecyclerView stepsRv = binding.rvSteps;
        stepsRv.setAdapter(stepsAdapter);
        stepsRv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

}
