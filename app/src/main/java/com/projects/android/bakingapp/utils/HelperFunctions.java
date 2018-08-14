package com.projects.android.bakingapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alshtray on 8/14/18.
 */

public class HelperFunctions {

    public static void showToast(String toastText, Context context){
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
