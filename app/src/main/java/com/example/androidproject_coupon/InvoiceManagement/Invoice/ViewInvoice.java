package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

public class ViewInvoice extends AppCompatActivity {

    TextView tvThongtinvanchuyen, tvHoten, tvSDT, tvDiachi;
    RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_view);

        mactching();

    }

    private void mactching() {

    }
}
