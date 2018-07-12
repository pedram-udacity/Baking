package com.example.android.baking.utilities;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class UiUtils {

    public static int numberOfColumns(Context aContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Activity activity = (Activity) aContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }
}
