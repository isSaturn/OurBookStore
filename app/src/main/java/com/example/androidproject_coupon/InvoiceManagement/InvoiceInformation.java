package com.example.androidproject_coupon.InvoiceManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

public class InvoiceInformation extends AppCompatActivity {
    private RecyclerView rcvInvoiceitem;
    private InvoiceBookAdapter invoiceBookAdapter;
    Button btnDathang;
    CheckBox cbThanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_infomation);

        getSupportActionBar().setTitle("Đặt hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matching();
        Intent intent = new Intent(this, ViewInvoiceInformation.class);

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        cbThanhtoan.setChecked(true);

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

    private void matching() {
        btnDathang = (Button)findViewById(R.id.inv_btn_dathang);
        cbThanhtoan = (CheckBox) findViewById(R.id.inv_cb_thanhtoantienmat);
    }
}
