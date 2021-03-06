package com.kabasonic.shoppinglist.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "items_shopping_list")
public class ItemList implements Parcelable {

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
    private int amount;

    @ColumnInfo(name = "completed")
    private boolean completed;

//    public ItemList(byte[] image, String title, String amount, boolean completed) {
//        this.image = image;
//        this.title = title;
//        this.amount = amount;
//        this.completed = completed;
//    }
    @Ignore
    public ItemList(){}

    public ItemList(int image, String title, int amount, boolean completed) {
        this.image = image;
        this.title = title;
        this.amount = amount;
        this.completed = completed;
    }

    protected ItemList(Parcel in) {
        id = in.readInt();
        idFkListItem = in.readLong();
        image = in.readInt();
        title = in.readString();
        amount = in.readInt();
        completed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(idFkListItem);
        dest.writeInt(image);
        dest.writeString(title);
        dest.writeInt(amount);
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel in) {
            return new ItemList(in);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };

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

    public int getAmount() {
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
