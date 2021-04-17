package com.kabasonic.shoppinglist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;

import java.util.List;
@Dao
public interface ShoppingListDao {

    @Insert
    void insert (ShoppingList shoppingList);

    @Update
    void update (ShoppingList shoppingList);

    @Delete
    void delete (ShoppingList shoppingList);

    @Query("SELECT * FROM items_shopping_list")
    LiveData<List<ItemList>> getAllShoppingList();
}
