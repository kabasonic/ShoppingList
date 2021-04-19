package com.kabasonic.shoppinglist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;

import java.util.List;

@Dao
public interface ShoppingListWithItemsDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ShoppingList shoppingList);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ItemList> itemLists);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ShoppingList shoppingList);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<ItemList> itemLists);

    @Transaction
    @Delete
    void delete(ShoppingList shoppingList);
    @Delete
    void delete(List<ItemList> itemLists);

    // get all
    @Transaction
    @Query("SELECT * FROM shopping_list WHERE backup_states = 0 ORDER BY time_saving DESC")
    LiveData<List<ShoppingListWithItems>> getShoppingListWithItems();

    // get by id
    @Transaction
    @Query("SELECT * FROM shopping_list WHERE id = :id")
    LiveData<ShoppingListWithItems> getShoppingListWithItems(int id);

    @Transaction
    @Query("SELECT * FROM shopping_list WHERE backup_states = 1")
    LiveData<List<ShoppingListWithItems>> getArchivingShoppingListWithItems();

    @Query("SELECT MAX(id) FROM shopping_list")
    int getLastId();



}
