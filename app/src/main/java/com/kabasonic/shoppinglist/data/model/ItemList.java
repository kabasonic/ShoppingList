package com.kabasonic.shoppinglist.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_shopping_list")
public class ItemList {

    @ColumnInfo(name = "item_list_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo (name = "item_list_id_fk_note")
    public long idFkListItem;

//    @ColumnInfo (name = "icon", typeAffinity = ColumnInfo.BLOB)
//    private byte[] image;

    @ColumnInfo (name = "icon")
    private int image;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "completed")
    private boolean completed;

//    public ItemList(byte[] image, String title, String amount, boolean completed) {
//        this.image = image;
//        this.title = title;
//        this.amount = amount;
//        this.completed = completed;
//    }


    public ItemList(int image, String title, String amount, boolean completed) {
        this.image = image;
        this.title = title;
        this.amount = amount;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public long getIdFkListItem() {
        return idFkListItem;
    }

    public int getImage() {
        return image;
    }

    //    public byte[] getImage() {
//        return image;
//    }

    public String getTitle() {
        return title;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdFkListItem(long idFkListItem) {
        this.idFkListItem = idFkListItem;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
