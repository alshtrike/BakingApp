package com.projects.android.bakingapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.projects.android.bakingapp.data.Step;

public class HelperFunctions {

    public static void showToast(String toastText, Context context){
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    public static String[] makeStepDescriptionsStringArray(Step[] steps, String firstItem){
        String[] stepDescriptions = new String[steps.length+1];
        stepDescriptions[0]= firstItem;
        for(int i = 0; i< steps.length; i++){
            stepDescriptions[i+1] = steps[i].getShortDescription();
        }
        return stepDescriptions;
    }
}
