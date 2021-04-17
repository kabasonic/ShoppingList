package com.kabasonic.shoppinglist.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.repository.ShoppingListWithItemsRepo;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private ShoppingListWithItemsRepo repo;
    private LiveData<List<ShoppingListWithItems>> allShoppingListWithItems;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ShoppingListWithItemsRepo(application);
        this.allShoppingListWithItems = repo.getAllShoppingListWithItems();
    }

    public LiveData<List<ShoppingListWithItems>> getAllShoppingListWithItems() {
        return allShoppingListWithItems;
    }
}
