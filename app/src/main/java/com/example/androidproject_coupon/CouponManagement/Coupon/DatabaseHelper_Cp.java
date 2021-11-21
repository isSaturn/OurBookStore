package com.example.androidproject_coupon.CouponManagement.Coupon;

import android.content.ContentValues;
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

    public void onCreate(SQLiteDatabase DB){
        DB.execSQL("create Table KhuyenMai(IDKhuyenMai INTEGER primary key, MaKhuyenMai TEXT, TenKhuyenMai TEXT,TimeStart DATE, TimeEnd DATE, GiaGiam INTEGER, GiaApDung INTEGER, IDLoaiApDung INTEGER, IDLoaiKhuyenMai INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(newVersion>oldVersion)
//            copyDataBase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
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
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "select * from " + TABLE;
        Cursor contro = null;
        try {
            contro = database.rawQuery(sql, null);
        } catch (Exception e) {
            Log.d("Loi db", e.toString());
        }
        return contro;
    }
    public Boolean insertCpData(Integer id, String code, String name, String eStart, String eEnd, Integer value, Integer valueCondition, Integer idCondition, Integer idType)
    {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDKhuyenMai", id);
        contentValues.put("MaKhuyenMai", code);
        contentValues.put("TenKhuyenMai", name);
        contentValues.put("TimeStart", eStart);
        contentValues.put("TimeEnd", eEnd);
        contentValues.put("GiaGiam", value);
        contentValues.put("GiaApDung", valueCondition);
        contentValues.put("IDLoaiApDung", idCondition);
        contentValues.put("IDLoaiKhuyenMai", idType);
        long result=DB.insert(TABLE, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateCpData(Integer id, String code, String name, String eStart, String eEnd, Integer value, Integer valueCondition, Integer idCondition, Integer idType) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_Khuyen_Mai", id);
        contentValues.put("Ma_Khuyen_Mai", code);
        contentValues.put("Ten_Khuyen_Mai", name);
        contentValues.put("Time_Start", eStart);
        contentValues.put("Time_End", eEnd);
        contentValues.put("Gia_Giam", value);
        contentValues.put("Gia_Ap_Dung", valueCondition);
        contentValues.put("ID_Loai_Ap_Dung", idCondition);
        contentValues.put("ID_Loai_Khuyen_Mai", idType);
        Cursor cursor = DB.rawQuery("select * from KhuyenMai where ID_Khuyen_Mai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = DB.update(TABLE, contentValues, "ID_Khuyen_Mai=?", new String[]{String.valueOf(id)});
            DB.close();
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else {
        return false;
        }

    }
}
