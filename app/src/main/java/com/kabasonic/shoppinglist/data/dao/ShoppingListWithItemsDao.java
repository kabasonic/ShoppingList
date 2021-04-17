package com.kabasonic.shoppinglist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;

import java.util.List;

@Dao
public interface ShoppingListWithItemsDao {

    @Insert
    void insert(ShoppingListWithItems shoppingListWithItems);

    @Update
    void update(ShoppingListWithItems shoppingListWithItems);

    @Delete
    void delete(ShoppingListWithItems shoppingListWithItems);

    // get all
    @Transaction
    @Query("SELECT * FROM shopping_list")
    LiveData<List<ShoppingListWithItems>> getShoppingListWithItems();

    // get by id
    @Transaction
    @Query("SELECT * FROM shopping_list WHERE id = :id")
    LiveData<ShoppingListWithItems> getShoppingListWithItems(int id);

}
