package com.kabasonic.shoppinglist.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.ui.HomeDialogFragment;

public class HomeFragment extends Fragment {
    private static final int REQUEST_TITLE = 1;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_create_shopping_list);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDialog();
            }
        });
    }

    private void callDialog(){
        HomeDialogFragment homeDialogFragment = new HomeDialogFragment();
        homeDialogFragment.setTargetFragment(this,REQUEST_TITLE);
        homeDialogFragment.show(getFragmentManager(),homeDialogFragment.getTag());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TITLE:
                    String title = data.getStringExtra(HomeDialogFragment.TAG_TITLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",title);
                    Navigation.findNavController(view).navigate(R.id.fragmentShoppingListDetails,bundle);
                    break;
            }
        }
    }
}
