package com.example.androidproject_coupon.OrderManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

class OrderInfomation extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private OrderBookAdapter orderBookAdapter;
    Button btnDathang;
    CheckBox cbThanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ord_infomation);

        getSupportActionBar().setTitle("Đặt hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matching();
        Intent intent = new Intent(this, ViewOrderInformation.class);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        cbThanhtoan.setChecked(true);

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

    private void matching() {
        btnDathang = (Button)findViewById(R.id.ord_btn_dathang);
        cbThanhtoan = (CheckBox) findViewById(R.id.ord_cb_thanhtoantienmat);
    }
}