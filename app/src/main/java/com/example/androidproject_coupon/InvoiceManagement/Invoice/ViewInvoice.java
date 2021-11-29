package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

public class ViewInvoice extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private InvoiceAdapter invoiceBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_information_view);

        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        invoiceBookAdapter = new InvoiceAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvInvoiceitem.setLayoutManager(linearLayoutManager);

        rcvInvoiceitem.setAdapter(invoiceBookAdapter);
    }
}
