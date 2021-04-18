package com.kabasonic.shoppinglist.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.adapters.ShoppingListAdapter;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.ui.dialogs.ArchivingDialogs;
import com.kabasonic.shoppinglist.ui.dialogs.IconDialog;
import com.kabasonic.shoppinglist.ui.dialogs.TitleShoppingListDialog;
import com.kabasonic.shoppinglist.ui.home.HomeViewModel;
import com.kabasonic.shoppinglist.util.Constants;

public class ShoppingListFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private HomeViewModel homeViewModel;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ShoppingListAdapter mAdapter;
    private AutoCompleteTextView editText;
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private LinearLayout layoutPlug;
    private ShoppingListWithItems shoppingListWithItems;
    private ItemList itemList = new ItemList();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Constants.TYPE_FRAGMENT_ADAPTER = Constants.CREATING_LIST;
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_shopping_list_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //attaching view elements
        attachView(view);
        //build toolbar configuration
        buildToolbar();
        //build adapter
        buildAdapter();
        //build autoCompleteTextAdapter
        buildAutoCompleteTextAdapter(getResources().getStringArray(R.array.product_array));
        //set ItemTouchHelper for current recyclerView
        setItemTouchHelper();


        int id = getArguments().getInt("id_shopping_list");

        homeViewModel.getShoppingListWithItems(id).observe(getViewLifecycleOwner(), new Observer<ShoppingListWithItems>() {
            @Override
            public void onChanged(ShoppingListWithItems shoppingListWithItems) {
                if (!shoppingListWithItems.itemListShoppingList.isEmpty()) {
                    mCollapsingToolbarLayout.setTitle(shoppingListWithItems.shoppingList.getTitle());
                    layoutPlug.setVisibility(View.GONE);
                    mAdapter.setRowList(shoppingListWithItems);
                    mAdapter.notifyDataSetChanged();
                } else {
                    layoutPlug.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void setItemTouchHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.removeItemFromItemsList(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void buildAutoCompleteTextAdapter(String[] stringArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.text_list_item, R.id.text_view_list_item, stringArray);
        editText.setAdapter(adapter);
    }

    private void buildToolbar() {
        if (mToolbar != null) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void attachView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_shopping_details);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_sopping_list);
        MaterialButton mBtEditTitle = (MaterialButton) view.findViewById(R.id.bt_edit_title);
        MaterialButton mBtArchiving = (MaterialButton) view.findViewById(R.id.bt_archiving);
        MaterialButton mBtShare = (MaterialButton) view.findViewById(R.id.bt_share);
        ImageView newItemIcon = (ImageView) view.findViewById(R.id.choose_icon_item);
        ImageView addItemToList = (ImageView) view.findViewById(R.id.rv_add_item);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_shopping_list);
        editText = (AutoCompleteTextView) view.findViewById(R.id.actv);
        layoutPlug = (LinearLayout) view.findViewById(R.id.plug_layout_list_details);
        mBtEditTitle.setOnClickListener(this);
        mBtArchiving.setOnClickListener(this);
        mBtShare.setOnClickListener(this);
        addItemToList.setOnClickListener(this);
        newItemIcon.setOnClickListener(this);
    }

    private void buildAdapter() {
        mAdapter = new ShoppingListAdapter(mContext);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //override onBackPressed
                if (getActivity() != null)
                    getActivity().onBackPressed();
                break;
            case R.id.action_search:
                //search view
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //implementation Query to Room
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //implementation Query to Room
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            //pressed button edit title, open dialog fragment
            case R.id.bt_edit_title:
                Constants.TYPE_TITLE_SHOPPING_DIALOG = Constants.TITLE_CHANGED_DIALOG;
                TitleShoppingListDialog titleShoppingListDialog = TitleShoppingListDialog.newInstance();
                titleShoppingListDialog.setTargetFragment(this, Constants.REQUEST_CHANGE_TITLE);
                assert getFragmentManager() != null;
                titleShoppingListDialog.show(getFragmentManager(), titleShoppingListDialog.getTag());
                break;
            case R.id.bt_archiving:
                ArchivingDialogs archivingDialogs = ArchivingDialogs.newInstance();
                archivingDialogs.setTargetFragment(this, Constants.REQUEST_CHANGE_ARCHIVING);
                assert getFragmentManager() != null;
                archivingDialogs.show(getFragmentManager(), archivingDialogs.getTag());
                break;
            case R.id.bt_share:
                //function share
                break;
            case R.id.choose_icon_item:
                IconDialog iconDialog = new IconDialog();
                iconDialog.setTargetFragment(this, Constants.REQUEST_CHANGE_ICON);
                assert getFragmentManager() != null;
                iconDialog.show(getFragmentManager(), iconDialog.getTag());
                break;
            case R.id.rv_add_item:
                int icon = R.drawable.shopping_bag;
                String title = editText.getText().toString();
                if (!title.isEmpty()) {
                    ItemList itemList = new ItemList(icon, title, 1, false);
                    mAdapter.addItemToRowList(itemList);
                    recyclerView.smoothScrollToPosition(0);
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Constants.REQUEST_CHANGE_TITLE:
                    // get new title
                    CharSequence newTitle = (String) data.getStringExtra(Constants.TAG_CHANGE_TITLE);
                    if (newTitle.length() != 0) {
                        //action
                    }
                    break;
                case Constants.REQUEST_CHANGE_ICON:
                    //get new icon
                    int selectedIcon = data.getIntExtra(Constants.TAG_CHANGE_ICON, Constants.DEFAULT_ICON);

                    //action
                    break;
                case Constants.REQUEST_CHANGE_ARCHIVING:
                    //get archiving status list
                    boolean archiving = data.getBooleanExtra(Constants.TAG_CHANGE_ARCHIVING, false);
                    //action
                    break;
            }
        }
    }

}
