package com.example.androidproject_coupon.CouponManagement.CpType;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper_CpType extends SQLiteOpenHelper {
    public static String DBNAME;
    public static String TABLE;
    public static final String DBLOCATION = "/data/data/com.example.androidproject_coupon.CouponManagement/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper_CpType(Context context, String DBname){
        super(context,DBNAME, null, 1);
        DBNAME = DBname;
        TABLE = "LoaiKhuyenMai";
        mContext = context;
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase(){
        String DBPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(DBPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void CloseDatabase(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    public ArrayList<String> GetCouponTypes(){
        CouponType cpType = null;
        ArrayList<String> arrayListCpType = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from " + TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            cpType = new CouponType(cursor.getString(1));
            arrayListCpType.add(cpType.getType());
            cursor.moveToNext();
        }
        cursor.close();
        CloseDatabase();
        return arrayListCpType;

    }
}
