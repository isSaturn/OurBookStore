package com.example.androidproject_coupon.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject_coupon.R;

public class ItemDetailActivity extends AppCompatActivity {

    ImageView img;
    TextView name, price, theloai, mota;
    ImageButton addtocart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        matching();


    }

    private void matching() {
        img = (ImageView) findViewById(R.id.img_itemdetail_book);
        name = (TextView) findViewById(R.id.tv_itemdetail_name);
        price = (TextView) findViewById(R.id.tv_itemdetail_price);
        theloai = (TextView) findViewById(R.id.tv_itemdetail_theloai);
        mota = (TextView) findViewById(R.id.tv_itemdetail_mota);
        addtocart = (ImageButton) findViewById(R.id.btn_itemdetail_add);
    }
}