package com.example.dathangbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class CartInfomation extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private BookAdapter bookAdapter;
    Button btnDathang;
    CheckBox cbThanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_infomation);

        getSupportActionBar().setTitle("Đặt hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matching();
        Intent intent = new Intent(this,Information.class);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        cbThanhtoan.setChecked(true);

        rcvInvoiceitem = findViewById(R.id.ord_invoice_recycler_item);
        bookAdapter = new BookAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvInvoiceitem.setLayoutManager(linearLayoutManager);

        bookAdapter.setData(getListBook());
        rcvInvoiceitem.setAdapter(bookAdapter);
    }

    private List<Book> getListBook() {
        List<Book> list = new ArrayList<>();
        list.add(new Book(R.drawable.sach,"Book Name"));

        return list;
    }

    private void matching() {
        btnDathang = (Button)findViewById(R.id.ord_btn_dathang);
        cbThanhtoan = (CheckBox) findViewById(R.id.ord_cb_thanhtoantienmat);
    }


}