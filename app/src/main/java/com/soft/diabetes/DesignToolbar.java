package com.soft.diabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DesignToolbar {
    public static void ShowToolbar(AppCompatActivity activity , String titulo , boolean upBottom){

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(titulo);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upBottom);

    }
}
