package com.example.androidproject_coupon.InvoiceManagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

public class ViewInvoiceInformation extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private InvoiceBookAdapter invoiceBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_information_view);

        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        invoiceBookAdapter = new InvoiceBookAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvInvoiceitem.setLayoutManager(linearLayoutManager);

        invoiceBookAdapter.setData(getListBook());
        rcvInvoiceitem.setAdapter(invoiceBookAdapter);
    }

    private List<InvoiceBook> getListBook() {
        List<InvoiceBook> list = new ArrayList<>();
        list.add(new InvoiceBook(R.drawable.sach,"Book Name"));

        return list;
    }
}
