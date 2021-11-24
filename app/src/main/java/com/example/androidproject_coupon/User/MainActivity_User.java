package com.example.androidproject_coupon.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity_User extends AppCompatActivity {

    Integer check, role;
    GetIDandRole idAndRole = new GetIDandRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        check = idAndRole.id;
        role = idAndRole.role;
        Log.d("Kiem tra:", String.valueOf(role));
        if (check == 0){
            menuInflater.inflate(R.menu.main_menu,menu);
        }else{
            if (role == 1){
                menuInflater.inflate(R.menu.menu_logout_admin_inuser,menu);
            }else{
                menuInflater.inflate(R.menu.menu_logout,menu);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuAdmin){
            startActivity(new Intent(MainActivity_User.this, MainActivity.class));
        }
        else if(item.getItemId() == R.id.mnuLogout || item.getItemId() == R.id.mnuLogout_admin_inuser){
            Toast.makeText(getApplicationContext(), "Bạn đã đăng xuất", Toast.LENGTH_LONG).show();
            idAndRole.id = 0;
            startActivity(new Intent(MainActivity_User.this,MainActivity.class));
        }
        else if(item.getItemId()==R.id.mnuLogin){
            startActivity(new Intent(MainActivity_User.this,Login.class));
            //check = 1;
        }
        return super.onOptionsItemSelected(item);
    }
}