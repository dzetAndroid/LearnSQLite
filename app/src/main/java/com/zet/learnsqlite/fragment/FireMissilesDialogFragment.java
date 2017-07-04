package com.zet.learnsqlite.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zet.learnsqlite.R;

/**
 * Created by Zet on 2017/7/4.
 * 发射火箭 对话框 碎片
 */

public class FireMissilesDialogFragment extends DialogFragment {

    private static final String TAG = "FireMissilesDialogFragm";
    private static FireMissilesDialogFragment fireMissilesDialogFragment = null;

    /**
     * 获取新的实例
     *
     * @return
     */
    public static FireMissilesDialogFragment getInstance() {
        if (fireMissilesDialogFragment == null) {
            fireMissilesDialogFragment = new FireMissilesDialogFragment();
        }
        return fireMissilesDialogFragment;
    }

    /**
     * 显示对话框
     *
     * @param activity 继承appcompatactivity的子类activity
     * @param tag      标签
     */
    public static void show(AppCompatActivity activity, String tag) {
        FireMissilesDialogFragment instance = FireMissilesDialogFragment.getInstance();
        Log.e(TAG, "show: " + instance + " tag=" + tag);
        instance.show(activity.getSupportFragmentManager(), tag);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User cancelled the dialog
                    }
                });

        return builder.create();
    }
}
