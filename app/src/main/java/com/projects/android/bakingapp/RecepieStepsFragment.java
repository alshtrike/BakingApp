package com.projects.android.bakingapp;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.android.bakingapp.databinding.FragmentRecepieStepsBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecepieStepsFragment extends Fragment {


    public RecepieStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentRecepieStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recepie_steps, container, false);
        View view = binding.getRoot();
        binding.testMasterListTv.setText("Test deez nutz");
        return view;
    }

}
