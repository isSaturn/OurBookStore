package com.example.dathangbook;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Information extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information_ord);

        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
