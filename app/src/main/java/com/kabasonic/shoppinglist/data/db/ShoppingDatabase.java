package com.kabasonic.shoppinglist.data.db;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.room.Database;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kabasonic.shoppinglist.data.dao.ItemListDao;
import com.kabasonic.shoppinglist.data.dao.ShoppingListDao;
import com.kabasonic.shoppinglist.data.dao.ShoppingListWithItemsDao;
import com.kabasonic.shoppinglist.data.model.ItemList;
import com.kabasonic.shoppinglist.data.model.ShoppingList;
import com.kabasonic.shoppinglist.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Database(entities = {ShoppingList.class, ItemList.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoppingDatabase extends RoomDatabase {

    private static ShoppingDatabase instance;

    public abstract ShoppingListDao shoppingListDao();
    public abstract ItemListDao itemListDao();
    public abstract ShoppingListWithItemsDao shoppingListWithItemsDao();


    public static synchronized ShoppingDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext()
                    , ShoppingDatabase.class
                    , "shopping_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {

        private ShoppingListDao shoppingListDao;
        private ItemListDao itemListDao;
        private ShoppingListWithItemsDao shoppingListWithItemsDao;

        private PopulateDbAsyncTask(ShoppingDatabase shoppingListDb){
            this.shoppingListDao = shoppingListDb.shoppingListDao();
            this.itemListDao = shoppingListDb.itemListDao();
            this.shoppingListWithItemsDao = shoppingListDb.shoppingListWithItemsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //init elements
            return null;
        }
    }
}
