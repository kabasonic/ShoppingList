package com.kabasonic.shoppinglist.ui.archiving;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.repository.ShoppingListWithItemsRepo;

import java.util.List;

public class ArchivingViewModel extends AndroidViewModel {

    private ShoppingListWithItemsRepo repo;
    private LiveData<List<ShoppingListWithItems>> allArchivingShoppingListWithItems;

    public ArchivingViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ShoppingListWithItemsRepo(application);
        this.allArchivingShoppingListWithItems = repo.getAllArchivingShoppingListWithItems();
    }

    public LiveData<List<ShoppingListWithItems>> getAllArchivingShoppingListWithItems() {
        return allArchivingShoppingListWithItems;
    }
}
