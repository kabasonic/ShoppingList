package com.kabasonic.shoppinglist.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.kabasonic.shoppinglist.MainActivity;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.util.Constants;

import org.w3c.dom.Text;

import java.util.Objects;

public class HomeDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "HomeDialogFragment";
    public static final String TAG_TITLE = "title";

    private Dialog mDialog;
    private Context mContext;
    private View view;
    private EditText titleField;

    public HomeDialogFragment(){
    }

    public static HomeDialogFragment newInstance(Bundle bundle){
        HomeDialogFragment homeDialogFragment = new HomeDialogFragment();
        homeDialogFragment.setArguments(bundle);
        return new HomeDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mDialog = super.onCreateDialog(savedInstanceState);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        return mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_dialog_shopping_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView image = (ImageView) view.findViewById(R.id.plug_image_dialog);
        final TextView txMainTitle = (TextView) view.findViewById(R.id.maint_dialog_title);
        final TextView txSubTitle = (TextView) view.findViewById(R.id.sub_dialog_title);
        final ExtendedFloatingActionButton fabOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_list_created);
        final ExtendedFloatingActionButton fabNOK = (ExtendedFloatingActionButton) view.findViewById(R.id.bt_cancel_create_list);
        this.titleField = (EditText) view.findViewById(R.id.dialog_edit_text);
        if(getArguments()!= null && getArguments().getInt("type") == 0){
            image.setVisibility(View.GONE);
            txMainTitle.setText("Change title shopping list");
            txSubTitle.setText("Your new title");
            fabOK.setText("Okay");
        }
        fabOK.setOnClickListener(this);
        fabNOK.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_list_created:
                Intent intent = new Intent();
                intent.putExtra(TAG_TITLE, titleField.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
                break;
            case R.id.bt_cancel_create_list:
                mDialog.dismiss();
                break;
        }
    }
}
