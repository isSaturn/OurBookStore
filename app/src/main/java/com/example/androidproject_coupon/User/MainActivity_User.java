package com.example.androidproject_coupon.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.AccountManagement.Login;
import com.example.androidproject_coupon.AccountManagement.RegisterAccount;
import com.example.androidproject_coupon.MainActivity;
import com.example.androidproject_coupon.ProductFragment;
import com.example.androidproject_coupon.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity_User extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    com.example.androidproject_coupon.User.FragmentAdapter adapterFragment;
    String role,email;
    GetIDandRole idAndRole = new GetIDandRole();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        tabLayout = findViewById(R.id.tab_layout_user);
        viewPager2 = findViewById(R.id.view_pager2_user);

        FragmentManager fm = getSupportFragmentManager();
        adapterFragment = new FragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapterFragment);
        // Add tên và icon vào các tab
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home).setText("Shop"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.giohanguser).setText("Cart"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.invoice_icon).setText("Coupon"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // di chuyển tab được chọn
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        email = idAndRole.email;
        role = idAndRole.role;
        Log.d("Kiem tra:", String.valueOf(role));
        if (email.equals("")){
            menuInflater.inflate(R.menu.main_menu,menu);
        }else if (role.equals("admin")){
            menuInflater.inflate(R.menu.menu_logout_admin_inuser,menu);
        }else{
            menuInflater.inflate(R.menu.menu_logout,menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuAdmin){
            startActivity(new Intent(MainActivity_User.this, MainActivity.class));
        }
        else if(item.getItemId() == R.id.mnuLogout || item.getItemId() == R.id.mnuLogout_admin_inuser){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(), "Bạn đã đăng xuất", Toast.LENGTH_LONG).show();
            idAndRole.email = "";
            startActivity(new Intent(MainActivity_User.this,MainActivity_User.class));
            finish();
        }
        else if(item.getItemId()==R.id.mnuLogin){
            startActivity(new Intent(MainActivity_User.this,Login.class));
        }
        return super.onOptionsItemSelected(item);
    }
}