package com.example.androidproject_coupon.BookManagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidproject_coupon.R;

import java.io.IOException;
import java.util.ArrayList;

public class AddBook extends AppCompatActivity {

    Spinner spinnerTheLoai;
    Button ChonAnh, ThemSach;
    EditText MaSach, TenSach, GiaTien, Mota, TacGia;
    ImageView AnhSach, Back;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        matching();

        BookCategory category = new BookCategory(this);
        try {
            category.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }

        Cursor contro = category.laytheloai();
        contro.moveToFirst();

        ArrayList<Integer> ID = new ArrayList<Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();

        do {
            ID.add(Integer.parseUnsignedInt(contro.getString(0)));
            arrayList.add(contro.getString(1));
        }while (contro.moveToNext());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheLoai.setAdapter(arrayAdapter);

        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(AddBook.this, ID.get(i).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void matching() {
        spinnerTheLoai = findViewById(R.id.addBook_spn_TheLoai);
        ChonAnh = findViewById(R.id.addBook_btn_ChonAnh);
        ThemSach = findViewById(R.id.addBook_btn_ThemSach);
        MaSach = findViewById(R.id.addBook_et_MaSach);
        TenSach = findViewById(R.id.addBook_et_TenSach);
        GiaTien = findViewById(R.id.addBook_et_GiaTien);
        Mota = findViewById(R.id.addBook_et_MoTa);
        TacGia = findViewById(R.id.addBook_et_TacGia);
        AnhSach = findViewById(R.id.addBook_img_Sach);
        Back = findViewById(R.id.addBook_img_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}