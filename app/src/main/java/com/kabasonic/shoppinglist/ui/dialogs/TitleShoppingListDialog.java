package com.kabasonic.shoppinglist.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.util.Constants;

public class TitleShoppingListDialog extends DialogFragment implements View.OnClickListener {

    private Dialog mDialog;
    private EditText titleField;

    public TitleShoppingListDialog() {
    }

    public static TitleShoppingListDialog newInstance() {
        return new TitleShoppingListDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = super.onCreateDialog(savedInstanceState);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView image = (ImageView) view.findViewById(R.id.plug_image_dialog);
        final TextView txMainTitle = (TextView) view.findViewById(R.id.maint_dialog_title);
        final TextView txSubTitle = (TextView) view.findViewById(R.id.sub_dialog_title);
        final ExtendedFloatingActionButton fabOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_list_created);
        final ExtendedFloatingActionButton fabNOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_cancel_create_list);
        titleField = (EditText) view.findViewById(R.id.dialog_edit_text);

        switch (Constants.TYPE_TITLE_SHOPPING_DIALOG) {
            case Constants.TITLE_CHANGED_DIALOG:
                image.setVisibility(View.VISIBLE);
                txMainTitle.setText(getResources().getString(R.string.change_title_shopping_list));
                txSubTitle.setText(getResources().getString(R.string.new_title));
                fabOK.setText(getResources().getString(R.string.okay));
                break;
            case Constants.CREATING_LIST:
                image.setVisibility(View.VISIBLE);
                txMainTitle.setText(getResources().getString(R.string.shopping_list));
                txSubTitle.setText(getResources().getString(R.string.add_shopping_list_name));
                fabOK.setText(getResources().getString(R.string.create_list));
                break;
        }

        fabOK.setOnClickListener(this);
        fabNOK.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_list_created:
                Intent intent = new Intent();
                intent.putExtra(Constants.TAG_CHANGE_TITLE, titleField.getText().toString());
                if (getTargetFragment() != null)
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                mDialog.dismiss();
                break;
            case R.id.bt_cancel_create_list:
                mDialog.dismiss();
                break;
        }
    }
}
