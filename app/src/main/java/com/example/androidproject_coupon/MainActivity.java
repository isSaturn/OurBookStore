package com.example.androidproject_coupon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button add;
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapterFragment;
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        matching();
//
//        Log.d(TAG, "onCreate: Started.");
//        add.setBackgroundResource(R.drawable.buttonshape);
//        add.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddCoupon.class);
//                startActivity(intent);
//            }
//        });
//
//
//        listView = (ListView) findViewById(R.id.list_view);
//        ArrayList<Coupon> couponList = new ArrayList<>();
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
//        CouponAdapter couponAdapter = new CouponAdapter(this, R.layout.adapter_view_layout_coupon, couponList);
//        listView.setAdapter(couponAdapter);
////        recyclerView = findViewById(R.id.list_view);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent ( MainActivity.this, EditCoupon.class);
////                intent.putExtra( "KEY", key);
//                startActivity(intent);
//            }
//        });
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapterFragment = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapterFragment);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.gift_1).setText("Product"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.clipboard_1).setText("Order"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.tag_2).setText("Coupon"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.settings_1).setText("Account"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
    private void matching(){
        add = findViewById(R.id.Cp_btn_Add);
//        listAdapter = findViewById(R.layout.adapter_view_layout);
    }
}