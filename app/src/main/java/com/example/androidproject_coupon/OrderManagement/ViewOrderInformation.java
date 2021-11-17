package com.example.androidproject_coupon.OrderManagement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderInformation extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private OrderBookAdapter orderBookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ord_information_view);

        getSupportActionBar().setTitle("Thông tin đơn hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvInvoiceitem = findViewById(R.id.ord_invoice_recycler_item);
        orderBookAdapter = new OrderBookAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvInvoiceitem.setLayoutManager(linearLayoutManager);

        orderBookAdapter.setData(getListBook());
        rcvInvoiceitem.setAdapter(orderBookAdapter);
    }

    private List<OrderBook> getListBook() {
        List<OrderBook> list = new ArrayList<>();
        list.add(new OrderBook(R.drawable.sach,"Book Name"));

        return list;
    }
}
