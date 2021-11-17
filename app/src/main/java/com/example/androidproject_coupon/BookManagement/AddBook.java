package com.example.androidproject_coupon.BookManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidproject_coupon.R;

import java.io.IOException;
import java.util.ArrayList;

public class AddBook extends AppCompatActivity {

    Spinner spinnerTheLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        spinnerTheLoai = findViewById(R.id.addBook_spn_TheLoai);

        BookCategory category = new BookCategory(this);
        try {
            category.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }

        Cursor contro = category.laytheloai();
        contro.moveToFirst();

        ArrayList<String> arrayList = new ArrayList<String>();

        do {
            arrayList.add(contro.getString(1));
        }while (contro.moveToNext());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheLoai.setAdapter(arrayAdapter);

        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddBook.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}