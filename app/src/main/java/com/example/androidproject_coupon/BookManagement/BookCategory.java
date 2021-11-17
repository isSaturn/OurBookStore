package com.example.androidproject_coupon.BookManagement;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BookCategory extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NHOMSACH = "NhomSach";
    private static final String KEY_ID_NHOMSACH = "ID_Nhom_Sach";
    private static final String KEY_ID_LOAISACH = "Loai_Sach";
    private static String DB_PATH = "/data/data/com.example.androidproject_coupon.BookManagement/databases/";
    private static String DB_NAME = "database";
    private final Context myContext;
    private SQLiteDatabase myDataBase;


    public BookCategory(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);

        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    /*private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database chua ton tai
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        //mo db trong thu muc assets nhu mot input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        //duong dan den db se tao
        String outFileName = DB_PATH + DB_NAME;

        //mo db giong nhu mot output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //truyen du lieu tu inputfile sang outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Dong
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase(); //kiem tra db

        if (dbExist) {
            //khong lam gi ca, database da co roi
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase(); //chep du lieu
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }*/

    public Cursor laytheloai() {
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "select * from NhomSach";
        Cursor contro = null;
        try {
            contro = database.rawQuery(sql, null);
        } catch (Exception e) {
            Log.d("Loi db", e.toString());
        }

        return contro;
    }
}
