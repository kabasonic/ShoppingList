package com.kabasonic.shoppinglist.ui.shopping;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.repository.ShoppingListWithItemsRepo;

public class ShoppingViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> icon = new MutableLiveData<>();
    private MutableLiveData<Boolean> archiving = new MutableLiveData<>();
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<Integer> countItemsInRecyclerView = new MutableLiveData<>();
    private MutableLiveData<Boolean> statusDelete = new MutableLiveData<>();


    private ShoppingListWithItemsRepo repo;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ShoppingListWithItemsRepo(application);

    }

    public LiveData<ShoppingListWithItems> getShoppingListWithItems(int id){
        return repo.getShoppingListWithItems(id);
    }

    public void insert(ShoppingListWithItems shoppingListWithItems){
        repo.insert(shoppingListWithItems);
    }

    public void update(ShoppingListWithItems shoppingListWithItems){
        repo.update(shoppingListWithItems);
    }

    public void delete(ShoppingListWithItems shoppingListWithItems){
        repo.delete(shoppingListWithItems);
    }

    public void deleteItemList(ItemList itemList){
        repo.deleteListItem(itemList);
    }

    LiveData<ShoppingListWithItems> getById(int id){
        return repo.getShoppingListWithItems(id);
    }

    public void setIcon(int icon) {
        this.icon.setValue(icon);
    }

    public LiveData<Integer> getIcon() {
        return this.icon;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public LiveData<String> getTitle() {
        return this.title;
    }

    public void setArchiving(boolean archiving) {
        this.archiving.setValue(archiving);
    }

    public LiveData<Boolean> getArchiving() {
        return this.archiving;
    }

    public void setCountItemsInRecyclerView(int count){
        this.countItemsInRecyclerView.setValue(count);
    }

    public LiveData<Integer> getCountItemsInRecyclerView(){
        return this.countItemsInRecyclerView;
    }
    public void setStatusDelete(boolean status){
        this.statusDelete.setValue(status);
    }

    public LiveData<Boolean> getStatusDelete(){
        return statusDelete;
    }
}
