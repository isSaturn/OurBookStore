package com.example.androidproject_coupon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class activity_oderdetails extends AppCompatActivity {

    TextView tvgiaohang, tvname, tvaddress, tvphone, tvcode, tvtime, tvstatus;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oderdetails);

        matching();
        Intent intent = getIntent();
        Integer tvgiaohang = intent.getIntExtra("hinhthuc",1);
        String tvname = intent.getStringExtra("name");
        String tvaddress= intent.getStringExtra( "address");
        String tvphone = intent.getStringExtra( "phone");
        String tvcode = intent.getStringExtra( "code");
        String tvtime = intent.getStringExtra( "time");
        String tvstatus = intent.getStringExtra( "status, ");
    }

    private void matching() {
        tvgiaohang = findViewById(R.id.tv_details_giaohang);
        tvname = findViewById(R.id.tv_details_name);
        tvaddress = findViewById(R.id.tv_details_address);
        tvphone = findViewById(R.id.tv_details_phone);
        tvcode = findViewById(R.id.tv_details_madonhang);
        tvtime = findViewById(R.id.tv_details_timeorder);
        tvstatus = findViewById(R.id.tv_details_choxn);
    }
}