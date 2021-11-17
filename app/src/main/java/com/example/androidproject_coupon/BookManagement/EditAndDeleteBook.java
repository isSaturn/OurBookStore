package com.example.androidproject_coupon.BookManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidproject_coupon.R;

import java.util.ArrayList;

public class EditAndDeleteBook extends AppCompatActivity {

    Spinner spinnerTheLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_book);

        spinnerTheLoai = findViewById(R.id.editBook_spn_TheLoai);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Python");
        arrayList.add("Java");
        arrayList.add("C");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheLoai.setAdapter(arrayAdapter);

        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(EditAndDeleteBook.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}