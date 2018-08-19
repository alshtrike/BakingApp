package com.projects.android.bakingapp;


import android.content.Context;
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
import com.projects.android.bakingapp.utils.HelperFunctions;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsFragment extends Fragment implements StepsAdapter.StepsAdapterOnClickHandler{

    private Step[] mSteps;
    private Ingredient[] mIngredients;
    OnStepClickListener mListener;

    public interface OnStepClickListener{
        void onStepClicked(Step detail);
        void onIngredientsClicked(Ingredient[] ingredients);
    }

    public RecipeStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mListener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
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
        mSteps = recipe.getSteps();
        mIngredients = recipe.getIngredients();

        String[] stepDescripitons = HelperFunctions.makeStepDescriptionsStringArray(mSteps, getString(R.string.tv_ingredients));
        StepsAdapter stepsAdapter = new StepsAdapter(this);
        stepsAdapter.setSteps(stepDescripitons);
        RecyclerView stepsRv = binding.rvSteps;
        stepsRv.setAdapter(stepsAdapter);
        stepsRv.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onClick(int adapterPosition) {

        if(adapterPosition == 0){
            mListener.onIngredientsClicked(mIngredients);
        }
        else{
            Step currentStep = mSteps[adapterPosition-1];
            mListener.onStepClicked(currentStep);
        }


    }
}
