package com.kabasonic.shoppinglist.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kabasonic.shoppinglist.data.model.ItemList;

import java.util.List;

public interface ItemListDao {

    @Insert
    void insert (ItemList itemList);

    @Update
    void update (ItemList itemList);

    @Delete
    void delete (ItemList itemList);

    @Query("SELECT * FROM items_shopping_list")
    LiveData<List<ItemList>> getAllItemList();
}
