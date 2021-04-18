package com.kabasonic.shoppinglist.ui.shopping;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kabasonic.shoppinglist.data.repository.ShoppingListWithItemsRepo;

public class ShoppingViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> icon = new MutableLiveData<>();
    private MutableLiveData<Boolean> archiving = new MutableLiveData<>();
    private MutableLiveData<String> title = new MutableLiveData<>();

    private MutableLiveData<Boolean> listIsEmpty = new MutableLiveData<>();


    private ShoppingListWithItemsRepo repo;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        this.repo = new ShoppingListWithItemsRepo(application);

    }

//    public LiveData<Boolean> getListIsEmpty() {
//        return this.listIsEmpty;
//    }
//
//
//    public void setListIsEmpty(boolean isEmpty) {
//        this.listIsEmpty.setValue(isEmpty);
//    }

    public void setIcon(int icon) {
        this.icon.setValue(icon);
    }

    public LiveData<Integer> getIcon() {
        return this.icon;
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public LiveData<String> getTitle() {
        return this.title;
    }

    public void setArchiving(boolean archiving) {
        this.archiving.setValue(archiving);
    }

    public LiveData<Boolean> getArchiving() {
        return this.archiving;
    }
}
