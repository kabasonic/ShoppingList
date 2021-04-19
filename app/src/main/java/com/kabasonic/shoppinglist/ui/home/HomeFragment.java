package com.kabasonic.shoppinglist.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.adapters.ShoppingListAdapter;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;
import com.kabasonic.shoppinglist.ui.dialogs.TitleShoppingListDialog;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_TITLE = 1;

    private Context mContext;
    private View view;
    private HomeViewModel homeViewModel;
    private ShoppingListAdapter mAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Constants.TYPE_FRAGMENT_ADAPTER = Constants.HOME;
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_create_shopping_list);
        floatingActionButton.setOnClickListener(this);

        //homeViewModel.insert();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_home_fragment);

         mAdapter = new ShoppingListAdapter(mContext);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        homeViewModel.getAllShoppingListWithItems().observe(getViewLifecycleOwner(), new Observer<List<ShoppingListWithItems>>() {
            @Override
            public void onChanged(List<ShoppingListWithItems> shoppingList) {
                mAdapter.setRowListHome(shoppingList);
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter.setOnItemClickListener(new ShoppingListAdapter.OnItemClickListener() {
            @Override
            public void onClickItemView(int position, int id) {
                Constants.TYPE_FRAGMENT_ADAPTER = Constants.CREATING_LIST;
                Bundle bundle = new Bundle();
                bundle.putInt("id_shopping_list",id);
                Navigation.findNavController(view).navigate(R.id.fragmentShoppingListDetails,bundle);
            }
        });

    }

    private void callDialog(){
        TitleShoppingListDialog titleShoppingListDialog = new TitleShoppingListDialog();
        titleShoppingListDialog.setTargetFragment(this,REQUEST_TITLE);
        titleShoppingListDialog.show(getFragmentManager(), titleShoppingListDialog.getTag());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TITLE:
                    String title = data.getStringExtra("title_home_shopping");
                    Bundle bundle = new Bundle();
                    bundle.putString("title_from_home_to_shopping",title);
                    Navigation.findNavController(view).navigate(R.id.fragmentShoppingListDetails,bundle);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_create_shopping_list:
                callDialog();
                break;
        }
    }
}
