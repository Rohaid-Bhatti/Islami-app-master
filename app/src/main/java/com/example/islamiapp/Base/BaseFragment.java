package com.example.islamiapp.Base;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

public  class BaseFragment extends Fragment {

    MaterialDialog dialog;
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= ((BaseActivity) context);
    }

    public MaterialDialog showMessage(int titleResId, int contentResId){
        return activity.showMessage(titleResId,contentResId);
    }
 public MaterialDialog showMessage(String titleResId, String contentResId){
        return activity.showMessage(titleResId,contentResId);
    }

    public MaterialDialog showConfirmationMessage(int titleResId,
                                                  int contentResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPos
    ){
        return
                activity.showConfirmationMessage(titleResId,contentResId,posTextResId,onPos);

    }

    public MaterialDialog showConfirmationMessage(String titleResId,
                                                  String contentResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPos
    ){
        return
                activity.showConfirmationMessage(titleResId,contentResId,posTextResId,onPos);

    }



    public MaterialDialog showProgressBar(int titleResId, int contentResId){
        return activity.showProgressBar(titleResId,contentResId);
    }

    public void hideProgressBar(){
        activity.hideProgressBar();
    }


}
