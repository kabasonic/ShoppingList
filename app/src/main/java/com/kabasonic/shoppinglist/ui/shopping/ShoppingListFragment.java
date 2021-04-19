package com.kabasonic.shoppinglist.ui.shopping;

import android.app.Activity;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.kabasonic.shoppinglist.R;
import com.kabasonic.shoppinglist.adapters.ShoppingListAdapter;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;
import com.kabasonic.shoppinglist.ui.dialogs.ArchivingDialogs;
import com.kabasonic.shoppinglist.ui.dialogs.DeleteDialogs;
import com.kabasonic.shoppinglist.ui.dialogs.IconDialog;
import com.kabasonic.shoppinglist.ui.dialogs.TitleShoppingListDialog;
import com.kabasonic.shoppinglist.ui.home.HomeViewModel;
import com.kabasonic.shoppinglist.util.Constants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ShoppingListAdapter mAdapter;
    private AutoCompleteTextView editText;
    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    private LinearLayout layoutPlug;
    private ShoppingViewModel viewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private int id = 0;
    private ImageView newItemIcon;
    private ShoppingList shoppingList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        Constants.TYPE_FRAGMENT_ADAPTER = Constants.CREATING_LIST;
        //homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        return inflater.inflate(R.layout.fragment_shopping_list_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingList = new ShoppingList();
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
        //incomingArgument
        incomingArguments();


    }

    private void incomingArguments() {
        //get id when typed on the row in HomeFragment
        if (getArguments() != null) {
            this.id = getArguments().getInt("id_shopping_list");
        }
        //if id do not equals 0, it is not new record
        if (id != 0) {
            getListById(id);
        } else {
            createNewList();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCountItemsInRecyclerView(0);
        viewModel.setArchiving(false);

        if (id == 0) {
            List<ItemList> itemLists = mAdapter.getSubList();
            if (!itemLists.isEmpty())
                viewModel.insert(new ShoppingListWithItems(new ShoppingList(shoppingList.getTitle(), mAdapter.getItemCount(), shoppingList.isArchiveStatus(), System.currentTimeMillis()), itemLists));
        }
        if (id != 0) {
            List<ItemList> itemLists = mAdapter.getSubList();
            ShoppingList shoppingListUpdate = new ShoppingList(shoppingList.getTitle(), mAdapter.getItemCount(), shoppingList.isArchiveStatus(), System.currentTimeMillis());
            shoppingListUpdate.setId(id);
            viewModel.update(new ShoppingListWithItems(shoppingListUpdate, itemLists));
        }
        viewModel.getStatusDelete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                List<ItemList> itemLists = mAdapter.getSubList();
                ShoppingList shoppingListUpdate = new ShoppingList(shoppingList.getTitle(), mAdapter.getItemCount(), shoppingList.isArchiveStatus(), System.currentTimeMillis());
                shoppingListUpdate.setId(id);
                viewModel.delete(new ShoppingListWithItems(shoppingListUpdate, itemLists));
                Navigation.findNavController(getView()).navigate(R.id.viewPagerFragment);
            }
        });

    }

    private void createNewList() {
        String title = getArguments().getString("title_from_home_to_shopping");
        shoppingList.setTitle(title);
        mCollapsingToolbarLayout.setTitle(title);
        List<ItemList> itemLists = new ArrayList<>();
        ShoppingListWithItems shoppingListWithItems = new ShoppingListWithItems(new ShoppingList(), itemLists);
        mAdapter.setRowListShopping(shoppingListWithItems);
        mAdapter.notifyDataSetChanged();
    }

    //show object with room
    private void getListById(int id) {
        viewModel.getShoppingListWithItems(id).observe(getViewLifecycleOwner(), new Observer<ShoppingListWithItems>() {
            @Override
            public void onChanged(ShoppingListWithItems shoppingListWithItems) {
                if (!shoppingListWithItems.itemListShoppingList.isEmpty()) {
                    shoppingList.setTitle(shoppingListWithItems.shoppingList.getTitle());
                    mCollapsingToolbarLayout.setTitle(shoppingListWithItems.shoppingList.getTitle());
                    mAdapter.setRowListShopping(shoppingListWithItems);
                    viewModel.setCountItemsInRecyclerView(mAdapter.getItemCount());
                    mAdapter.notifyDataSetChanged();
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
                ItemList itemList = mAdapter.getAtItemList(viewHolder.getPosition());
                viewModel.deleteItemList(itemList);
                mAdapter.removeItemFromItemsList(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                viewModel.setCountItemsInRecyclerView(mAdapter.getItemCount());
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
        MaterialButton mBtDelete = (MaterialButton) view.findViewById(R.id.bt_delete_list_shopping);
        newItemIcon = (ImageView) view.findViewById(R.id.choose_icon_item);
        ImageView addItemToList = (ImageView) view.findViewById(R.id.rv_add_item);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_shopping_list);
        editText = (AutoCompleteTextView) view.findViewById(R.id.actv);
        layoutPlug = (LinearLayout) view.findViewById(R.id.plug_layout_list_details);
        mBtEditTitle.setOnClickListener(this);
        mBtArchiving.setOnClickListener(this);
        mBtShare.setOnClickListener(this);
        mBtDelete.setOnClickListener(this);
        addItemToList.setOnClickListener(this);
        newItemIcon.setOnClickListener(this);
    }

    private void buildAdapter() {
        mAdapter = new ShoppingListAdapter(mContext);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
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
    public void onResume() {
        super.onResume();
        viewModel.getIcon().observe(getViewLifecycleOwner(), integer -> {
            if (integer != null) {
                newItemIcon.setImageResource(integer);
                Constants.TEMP_ICON = integer;
            }
        });

        viewModel.getArchiving().observe(getViewLifecycleOwner(), aBoolean -> {
            //action archiving
            shoppingList.setArchiveStatus(aBoolean);
        });

        viewModel.getCountItemsInRecyclerView().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer>0){
                    layoutPlug.setVisibility(View.GONE);
                }else{
                    layoutPlug.setVisibility(View.VISIBLE);
                }
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
                TitleShoppingListDialog titleShoppingListDialog = TitleShoppingListDialog.newInstance(shoppingList.getTitle());
                titleShoppingListDialog.setTargetFragment(this, Constants.REQUEST_CHANGE_TITLE);
                assert getFragmentManager() != null;
                titleShoppingListDialog.show(getFragmentManager(), titleShoppingListDialog.getTag());
                break;
            case R.id.bt_archiving:
                ArchivingDialogs archivingDialogs = ArchivingDialogs.newInstance(shoppingList.isArchiveStatus());
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
                if (!editText.getText().toString().isEmpty()) {
                    mAdapter.addItemToRowList(new ItemList(Constants.TEMP_ICON, editText.getText().toString(), 1, false));
                    recyclerView.smoothScrollToPosition(0);
                }
                newItemIcon.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
                viewModel.setIcon(R.drawable.ic_baseline_add_shopping_cart_24);
                viewModel.setCountItemsInRecyclerView(mAdapter.getItemCount());
                break;
            case R.id.bt_delete_list_shopping:
                DeleteDialogs deleteDialogs = DeleteDialogs.newInstance();
                deleteDialogs.setTargetFragment(this,Constants.REQUEST_DELETE);
                deleteDialogs.show(getFragmentManager(),deleteDialogs.getTag());
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
                    String newTitle = data.getStringExtra("title_from_shopping_dialog");
                    if (newTitle.length() != 0) {
                        shoppingList.setTitle(newTitle);
                        mCollapsingToolbarLayout.setTitle(newTitle);
                    }
                    break;
                case Constants.REQUEST_CHANGE_ICON:
                    //get new icon
                    int selectedIcon = data.getIntExtra(Constants.TAG_CHANGE_ICON, Constants.DEFAULT_ICON);
                    viewModel.setIcon(selectedIcon);
                    //action
                    break;
                case Constants.REQUEST_CHANGE_ARCHIVING:
                    //get archiving status list
                    boolean archiving = data.getBooleanExtra(Constants.TAG_CHANGE_ARCHIVING, false);
                    //action
                    viewModel.setArchiving(archiving);
                    break;
                case Constants.REQUEST_DELETE:
                    boolean statusDelete = data.getBooleanExtra("delete_dialog",false);
                    if(statusDelete){
                        viewModel.setStatusDelete(statusDelete);
                        onDestroyView();
                    }
                    break;
            }
        }
    }

}
