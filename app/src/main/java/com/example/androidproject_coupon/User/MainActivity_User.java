package com.example.androidproject_coupon.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.Login;
import com.example.androidproject_coupon.MainActivity;
import com.example.androidproject_coupon.ProductFragment;
import com.example.androidproject_coupon.R;

public class MainActivity_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.mnuAdmin){
            finish();
        }
        else if(item.getItemId() == R.id.mnuLogout){
            Toast.makeText(getApplicationContext(), "Chao mung ban den voi mon \n Chuyen de lap trinh di dong", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==R.id.mnuLogin){
            startActivity(new Intent(MainActivity_User.this, Login.class));
        }
        else if(item.getItemId()==R.id.mnuUser){
            startActivity(new Intent(getApplicationContext(), MainActivity_User.class));
        }
        return super.onOptionsItemSelected(item);
    }
}