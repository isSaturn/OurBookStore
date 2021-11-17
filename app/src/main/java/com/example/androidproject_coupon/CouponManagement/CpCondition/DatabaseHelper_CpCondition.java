package com.example.androidproject_coupon.CouponManagement.CpCondition;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.androidproject_coupon.CouponManagement.CpType.CouponType;

import java.util.ArrayList;

public class DatabaseHelper_CpCondition extends SQLiteOpenHelper {
    public static String DBNAME;
    public static String TABLE;
    public static final String DBLOCATION = "/data/data/com.example.androidproject_coupon.CouponManagement/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper_CpCondition(Context context, String DBname){
        super(context,DBNAME, null, 1);
        DBNAME = DBname;
        TABLE = "LoaiApDung";
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

    public ArrayList<String> GetCouponConditions(){
        CouponCondition cpCondition = null;
        ArrayList<String> arrayListCpCondition = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from " + TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            cpCondition = new CouponCondition(cursor.getString(1));
            arrayListCpCondition.add(cpCondition.getCondition());
            cursor.moveToNext();
        }
        cursor.close();
        CloseDatabase();
        return arrayListCpCondition;

    }
}
