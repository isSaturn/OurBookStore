package com.example.androidproject_coupon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.AccountManagement.Login;
import com.example.androidproject_coupon.AccountManagement.RegisterAccount;
import com.example.androidproject_coupon.User.MainActivity_User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapterFragment;
    String role, email;
    GetIDandRole idAndRole = new GetIDandRole();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapterFragment = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapterFragment);
        // Add tên và icon vào các tab
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.product_icon).setText("Product"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.invoice_icon).setText("Order"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.coupon_icon).setText("Coupon"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.account_icon).setText("Account"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // di chuyển tab được chọn
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        email = idAndRole.email;
        role = idAndRole.role;
        if (email.equals("")){
            menuInflater.inflate(R.menu.main_menu,menu);
        }else if (role.equals("admin")){
                menuInflater.inflate(R.menu.menu_logout_admin_inadmin,menu);
        }
        else{
                menuInflater.inflate(R.menu.menu_logout,menu);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuAdmin){
            startActivity(new Intent(MainActivity.this, RegisterAccount.class));
        }
        else if(item.getItemId() == R.id.mnuLogout || item.getItemId() == R.id.mnuLogout_admin_inadmin){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(), "Bạn đã đăng xuất", Toast.LENGTH_LONG).show();
            idAndRole.email = "";
            startActivity(new Intent(MainActivity.this,MainActivity_User.class));
            finish();
        }
        else if(item.getItemId()==R.id.mnuLogin){
            startActivity(new Intent(MainActivity.this,Login.class));
        }
        else if(item.getItemId() == R.id.mnuUser){
            startActivity(new Intent(MainActivity.this, MainActivity_User.class));
        }
        return super.onOptionsItemSelected(item);
    }
}