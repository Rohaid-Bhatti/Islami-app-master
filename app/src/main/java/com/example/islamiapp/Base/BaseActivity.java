package com.example.islamiapp.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;


public class BaseActivity extends AppCompatActivity {

    MaterialDialog dialog;
    protected BaseActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
    }

    public MaterialDialog showMessage(int titleResId, int contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .show();
        return dialog;
    } public MaterialDialog showMessage(String titleResId, String contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .show();
        return dialog;
    }

    public MaterialDialog showConfirmationMessage(int titleResId,
                                                  int contentResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPos
                                                  ){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .positiveText(posTextResId)
                .onPositive(onPos)
                .show();
        return dialog;
    }

    public MaterialDialog showConfirmationMessage(String titleResId,
                                                  String contentResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPos
    ){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .positiveText(posTextResId)
                .onPositive(onPos)
                .show();
        return dialog;
    }

    public MaterialDialog showProgressBar(int titleResId, int contentResId){
        dialog= new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(contentResId)
                .progress(true,0)
                .cancelable(false)
                .show();
        return dialog;
    }

    public void hideProgressBar(){
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }

}
