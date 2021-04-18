package com.kabasonic.shoppinglist.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.Objects;

public class ArchivingDialogs extends DialogFragment {

    public ArchivingDialogs() {}

    public static ArchivingDialogs newInstance() {
        return new ArchivingDialogs();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.checkbox,null,false);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getResources().getString(R.string.archiving));
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setMessage(getResources().getString(R.string.messege_archiving));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.TAG_CHANGE_ARCHIVING, checkBox.isChecked());
            Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            dismiss();
        });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        return alertDialogBuilder.create();
    }
}
