package com.zet.learnsqlite.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Zet on 2017/7/4.
 * 发射火箭 对话框 碎片
 */

public class ChoosePeopleOperationDialogFragment extends DialogFragment {

    private static final String TAG = "ChoosePeopleOperationDi";
    private static ChoosePeopleOperationDialogFragment fragment = null;

    /**
     * 获取新的实例
     *
     * @return
     */
    public static ChoosePeopleOperationDialogFragment getInstance() {
        if (fragment == null) {
            fragment = new ChoosePeopleOperationDialogFragment();
        }
        return fragment;
    }

    /**
     * 显示对话框
     *
     * @param activity 继承appcompatactivity的子类activity
     * @param tag      标签
     */
    public void show(AppCompatActivity activity, String tag, IDealItemsClick iDealItemsClick) {
        ChoosePeopleOperationDialogFragment instance = ChoosePeopleOperationDialogFragment.getInstance();
        instance.mIDealItemsClick = iDealItemsClick;
        Log.e(TAG, "show: " + instance + " tag=" + tag);
        instance.show(activity.getSupportFragmentManager(), tag);
    }
    private IDealItemsClick mIDealItemsClick;

    public interface IDealItemsClick{
        void dealItemsClick(DialogInterface dialog, int which);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] items = new String[]{"删除", "修改"};
        builder.setTitle("选择操作")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mIDealItemsClick != null) {
                            mIDealItemsClick.dealItemsClick(dialog, which);
                        }
                    }
                });

        return builder.create();
    }

}
