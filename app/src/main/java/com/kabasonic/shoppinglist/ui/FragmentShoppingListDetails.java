package com.kabasonic.shoppinglist.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.util.Constants;

public class FragmentShoppingListDetails extends Fragment implements View.OnClickListener {

    public static final String TAG = "FragmentShopListDetails";
    public static final int REQUEST_TITLE = 1;

    private Context mContext;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_list_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar_shopping_details);
        if(mToolbar != null){
            setHasOptionsMenu(true);
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        }

        //AppBarLayout mAppBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_shopping_details);
        mCollapsingToolbarLayout = getView().findViewById(R.id.collapsing_toolbar_sopping_list);
        if(mCollapsingToolbarLayout != null){
            String title = getArguments().getString("title",getResources().getString(R.string.shopping_list));
            mCollapsingToolbarLayout.setTitle(title);
        }

        MaterialButton mBtEditTitle = (MaterialButton) view.findViewById(R.id.bt_edit_title);
        MaterialButton mBtArchiving = (MaterialButton) view.findViewById(R.id.bt_archiving);
        MaterialButton mBtShare = (MaterialButton) view.findViewById(R.id.bt_share);
        mBtEditTitle.setOnClickListener(this);
        mBtArchiving.setOnClickListener(this);
        mBtShare.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Log.d(TAG,"Home back pressed");
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTitle(){
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.fragment_dialog_shopping_list, null,false);
//        view.findViewById(R.id.plug_image_dialog).setVisibility(View.GONE);
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
//                .setView(view);
//        builder.create();
//        builder.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_edit_title:
                Bundle bundle = new Bundle();
                bundle.putInt("type",0);
                HomeDialogFragment homeDialogFragment = HomeDialogFragment.newInstance(bundle);
                homeDialogFragment.setTargetFragment(this,REQUEST_TITLE);
                homeDialogFragment.show(getFragmentManager(),homeDialogFragment.getTag());
                break;
            case R.id.bt_archiving:
                break;
            case R.id.bt_share:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TITLE:
                    CharSequence title = (String) data.getStringExtra(HomeDialogFragment.TAG_TITLE);
                    if(title.length()!=0){
                        mCollapsingToolbarLayout.setTitle(title);
                    }
                    break;
            }
        }
    }

}
