package com.kabasonic.shoppinglist.util;

import com.kabasonic.shoppinglist.R;

import java.io.Serializable;

public class Constants implements Serializable {
    public static int REQUEST_TITLE = 1;

    public static int TYPE_FRAGMENT_ADAPTER = 0;
    public static final int HOME = 0;
    public static final int ARCHIVING = 1;
    public static final int CREATING_LIST = 2;

    public static int TYPE_TITLE_SHOPPING_DIALOG = -1;
    public static final int TITLE_CREATING_DIALOG = 0;
    public static final int TITLE_CHANGED_DIALOG = 1;

    public static final String TAG_CHANGE_TITLE = "title";
    public static final String TAG_CHANGE_ICON = "icon";
    public static final int DEFAULT_ICON = R.drawable.shopping_bag;
    public static final String TAG_CHANGE_ARCHIVING = "archiving";

    public static final int REQUEST_CHANGE_TITLE = 1;
    public static final int REQUEST_CHANGE_ICON = 2;
    public static final int REQUEST_CHANGE_ARCHIVING = 3;
    public static final int REQUEST_DELETE = 4;

    public static final String KEY_STATE_SHOPPING_LIST = "KEY_STATE_SHOPPING_LIST";

    public static int TEMP_ICON = R.drawable.ic_baseline_add_shopping_cart_24;
}
