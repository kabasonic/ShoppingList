package com.kabasonic.shoppinglist.data.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;

import java.util.List;

public class ShoppingListWithItems {
    @Embedded
    public ShoppingList shoppingList;
    @Relation(
            parentColumn = "id",
            entityColumn = "item_list_id_fk_note"
    )
    public List<ItemList> itemListShoppingList;

    public ShoppingListWithItems(ShoppingList shoppingList, List<ItemList> itemListShoppingList) {
        this.shoppingList = shoppingList;
        this.itemListShoppingList = itemListShoppingList;
    }
}
