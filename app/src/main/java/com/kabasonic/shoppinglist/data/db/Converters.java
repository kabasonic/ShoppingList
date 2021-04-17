package com.kabasonic.shoppinglist.data.db;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.sql.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Bitmap fromByteToBitmap(String base64Value) {
        if (!TextUtils.isEmpty(base64Value)) {
            byte[] decodedBytes = Base64.decode(base64Value, 0);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String fromBitmapToByte(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        } else {
            return null;
        }
    }


}
