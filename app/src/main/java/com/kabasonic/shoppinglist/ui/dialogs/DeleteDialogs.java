package com.kabasonic.shoppinglist.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.Objects;

public class DeleteDialogs extends DialogFragment {

    public DeleteDialogs() {}

    public static DeleteDialogs newInstance() {
        return new DeleteDialogs();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(getResources().getString(R.string.delete_shopping_list));
        alertDialogBuilder.setMessage(getResources().getString(R.string.messege_delete));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
            Intent intent = new Intent();
            intent.putExtra("delete_dialog", true);
            Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            dismiss();
        });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        return alertDialogBuilder.create();
    }
}
