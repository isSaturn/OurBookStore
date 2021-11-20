package com.example.androidproject_coupon.CouponManagement.Coupon;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper_Cp extends SQLiteOpenHelper {
    public static String DBNAME = "database";
    public static String TABLE = "KhuyenMai";
    public static String DBPATH;
    public static final String DBLOCATION = "/data/data/com.example.androidproject_coupon/databases/";
    private Context mContext;
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper_Cp(Context context){
        super(context,DBNAME, null, DATABASE_VERSION);
        this.mContext = context;
        this.DBPATH = mContext.getDatabasePath(DBNAME).getAbsolutePath();
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            copyDataBase();
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        super.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DBPATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null)
            checkDB.close();
        return checkDB != null ? true : false;
    }

    private void copyDataBase() {
        try{
            InputStream myInput = mContext.getAssets().open(DBNAME);
            String outFileName = DBPATH;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase(); //chep du lieu
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    public Cursor getCps() {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "select * from " + TABLE;
        Cursor contro = null;
        try {
            contro = database.rawQuery(sql, null);
        } catch (Exception e) {
            Log.d("Loi db", e.toString());
        }
        return contro;
    }
}
