package com.example.androidproject_coupon.BookManagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManagementProduct extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String KEY_CAUHOI = "cauhoi";
    private static final String KEY_A = "cau_a";
    private static final String KEY_B = "cau_b";
    private static final String KEY_C = "cau_c";
    private static final String KEY_D = "cau_d";
    private static final String KEY_DA = "dapan";
    private static String DB_PATH = "/data/data/com.example.androidproject_coupon.BookManagement/databases/";
    private static String DB_NAME = "database";
//    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public ManagementProduct(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
