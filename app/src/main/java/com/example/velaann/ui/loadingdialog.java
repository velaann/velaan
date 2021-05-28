package com.example.velaann.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.velaann.R;

public class loadingdialog {
    Activity activity;
    AlertDialog dialog;

    public loadingdialog(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        ViewGroup root;
        builder.setView(inflater.inflate(R.layout.custom1_dialog, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
    void dismissDialog()
    {
        dialog.dismiss();
    }
}

