package com.kabasonic.shoppinglist.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kabasonic.shoppinglist.data.dao.ShoppingListDao;
import com.kabasonic.shoppinglist.data.dao.ShoppingListWithItemsDao;
import com.kabasonic.shoppinglist.data.db.ShoppingDatabase;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;
import com.kabasonic.shoppinglist.data.model.ItemList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListWithItemsRepo {

    private ShoppingListWithItemsDao shoppingListWithItemsDao;


    private LiveData<ShoppingListWithItems> shoppingListWithItems;
    private LiveData<List<ShoppingListWithItems>> allShoppingListWithItems;

    public ShoppingListWithItemsRepo(Application application) {
        ShoppingDatabase shoppingDatabase = ShoppingDatabase.getInstance(application);
        this.shoppingListWithItemsDao = shoppingDatabase.shoppingListWithItemsDao();

        this.allShoppingListWithItems = shoppingListWithItemsDao.getShoppingListWithItems();
    }

    //Insert
    public void insert(ShoppingListWithItems shoppingListWithItems) {
        new InsertShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
    }

    public static class InsertShoppingListWithItems extends AsyncTask<ShoppingListWithItems, Void, Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private InsertShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao) {
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            shoppingListWithItemsDao.insert(shoppingListWithItems[0].shoppingList);
            int lastIdShoppingList = shoppingListWithItemsDao.getLastId();
            for (ItemList item : shoppingListWithItems[0].itemListShoppingList) {
                item.setIdFkListItem(lastIdShoppingList);
            }
            shoppingListWithItemsDao.insert(shoppingListWithItems[0].itemListShoppingList);

            return null;
        }
    }

    //Update
//    public void update(ShoppingListWithItems shoppingListWithItems) {
//        new UpdateShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
//    }

    public static class UpdateShoppingListWithItems extends AsyncTask<ShoppingListWithItems, Void, Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private UpdateShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao) {
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            shoppingListWithItemsDao.insert(shoppingListWithItems[0].shoppingList);
            int idShoppingList = shoppingListWithItems[0].shoppingList.getId();

            return null;
        }
    }

    //delete
//    public void delete(ShoppingListWithItems shoppingListWithItems) {
//        new DeleteShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
//    }

    public static class DeleteShoppingListWithItems extends AsyncTask<ShoppingListWithItems, Void, Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private DeleteShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao) {
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            //shoppingListWithItemsDao.delete(shoppingListWithItems[0]);
            return null;
        }
    }

    //get ID ShoppingListWithItems
    public LiveData<ShoppingListWithItems> getShoppingListWithItems(int id) {
        return shoppingListWithItemsDao.getShoppingListWithItems(id);
    }

    //get all ShoppingListWithItems
    public LiveData<List<ShoppingListWithItems>> getAllShoppingListWithItems() {
        return allShoppingListWithItems;
    }

}
