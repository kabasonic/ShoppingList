package com.kabasonic.shoppinglist.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kabasonic.shoppinglist.data.dao.ShoppingListWithItemsDao;
import com.kabasonic.shoppinglist.data.db.ShoppingDatabase;
import com.kabasonic.shoppinglist.data.db.ShoppingListWithItems;

import java.util.List;

public class ShoppingListWithItemsRepo {

    private ShoppingListWithItemsDao shoppingListWithItemsDao;

    private LiveData<ShoppingListWithItems> shoppingListWithItems;
    private LiveData<List<ShoppingListWithItems>> allShoppingListWithItems;

    public ShoppingListWithItemsRepo(Application application){
        ShoppingDatabase shoppingDatabase = ShoppingDatabase.getInstance(application);
        this.shoppingListWithItemsDao = shoppingDatabase.shoppingListWithItemsDao();

        this.allShoppingListWithItems = shoppingListWithItemsDao.getShoppingListWithItems();
    }

    //Insert
    public void insert(ShoppingListWithItems shoppingListWithItems){
        new InsertShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
    }

    public static class InsertShoppingListWithItems extends AsyncTask<ShoppingListWithItems,Void,Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private InsertShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao){
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            shoppingListWithItemsDao.insert(shoppingListWithItems[0]);
            return null;
        }
    }

    //Update
    public void update(ShoppingListWithItems shoppingListWithItems){
        new UpdateShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
    }

    public static class UpdateShoppingListWithItems extends AsyncTask<ShoppingListWithItems,Void,Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private UpdateShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao){
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            shoppingListWithItemsDao.update(shoppingListWithItems[0]);
            return null;
        }
    }

    //delete
    public void delete(ShoppingListWithItems shoppingListWithItems){
        new DeleteShoppingListWithItems(shoppingListWithItemsDao).execute(shoppingListWithItems);
    }

    public static class DeleteShoppingListWithItems extends AsyncTask<ShoppingListWithItems,Void,Void> {

        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private DeleteShoppingListWithItems(ShoppingListWithItemsDao shoppingListWithItemsDao){
            this.shoppingListWithItemsDao = shoppingListWithItemsDao;
        }

        @Override
        protected Void doInBackground(ShoppingListWithItems... shoppingListWithItems) {
            shoppingListWithItemsDao.delete(shoppingListWithItems[0]);
            return null;
        }
    }

    //get all ShoppingListWithItems
    public LiveData<List<ShoppingListWithItems>> getAllShoppingListWithItems(){
        return allShoppingListWithItems;
    }

}
