package com.kabasonic.shoppinglist.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "shopping_list")
public class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "completed_tasks")
    private int completedTasks;

    @ColumnInfo(name = "backup_states")
    private boolean archiveStatus;

    @ColumnInfo(name = "time_saving")
    private Date timeSaving;

    public ShoppingList(String title, int completedTasks, boolean archiveStatus, Date timeSaving) {
        this.title = title;
        this.completedTasks = completedTasks;
        this.archiveStatus = archiveStatus;
        this.timeSaving = timeSaving;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public boolean isArchiveStatus() {
        return archiveStatus;
    }

    public Date getTimeSaving() {
        return timeSaving;
    }
}
