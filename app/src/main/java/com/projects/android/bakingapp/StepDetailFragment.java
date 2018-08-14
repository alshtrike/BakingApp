package com.projects.android.bakingapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.bakingapp.databinding.FragmentStepDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {


    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStepDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        View root = binding.getRoot();
        binding.testDetailTv.setText("bagioehaiogebagh;");
        return root;
    }

}
