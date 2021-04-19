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
    private String title;
    public TitleShoppingListDialog() {
    }

    public static TitleShoppingListDialog newInstance(String title) {
        TitleShoppingListDialog titleShoppingListDialog = new TitleShoppingListDialog();
        Bundle args = new Bundle();
        args.putString("title_from_shopping_dialog", title);
        titleShoppingListDialog.setArguments(args);
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
        if(getArguments() != null){
            title = getArguments().getString("title_from_shopping_dialog");
        }
        final ImageView image = (ImageView) view.findViewById(R.id.plug_image_dialog);
        final TextView txMainTitle = (TextView) view.findViewById(R.id.maint_dialog_title);
        final TextView txSubTitle = (TextView) view.findViewById(R.id.sub_dialog_title);
        final ExtendedFloatingActionButton fabOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_list_created);
        final ExtendedFloatingActionButton fabNOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_cancel_create_list);
        titleField = (EditText) view.findViewById(R.id.dialog_edit_text);
        titleField.setHint(title);
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
                if(!titleField.getText().toString().isEmpty()){
                    Intent intent = new Intent();
                    if(Constants.TYPE_TITLE_SHOPPING_DIALOG == Constants.TITLE_CHANGED_DIALOG){
                        intent.putExtra("title_from_shopping_dialog", titleField.getText().toString());
                    }else {
                        intent.putExtra("title_home_shopping", titleField.getText().toString());
                    }
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }
                 break;
            case R.id.bt_cancel_create_list:
                dismiss();
                break;
        }
    }
}
