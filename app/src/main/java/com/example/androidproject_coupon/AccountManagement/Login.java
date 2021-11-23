package com.example.androidproject_coupon.AccountManagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.R;
import com.example.androidproject_coupon.viewUser.MainActivity_User;

import java.io.IOException;

public class Login extends AppCompatActivity {

    Button Login;
    EditText username, password;
    TextView Error, Register;
    ImageView Back;
    GetIDandRole idAndRole = new GetIDandRole();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        matching();

        CheckAccount account = new CheckAccount(this);
        try {
            account.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }


        Login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Error.setText("");
                String tk = username.getText().toString();
                String mk = password.getText().toString();
                if (tk.length() < 1 || mk.length() < 1){
                    Error.setText("Không được bỏ trống username hoặc password");
                }else {
                    Boolean checkAccount = account.checkAccount(tk,mk);
                    //Kiểm tra thông tin tài khoản
                    if (checkAccount == false){
                        Error.setText("Thông tin tài khoản hoặc mật khẩu không chính xác. Vui lòng nhập lại!!!");
                    }else{
                        Cursor cursor = account.layidaccount(tk,mk);
                        cursor.moveToFirst();
                        Integer id_account = Integer.parseUnsignedInt(cursor.getString(0));
                        Integer id_role = Integer.parseUnsignedInt(cursor.getString(3));
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity_User.class);

                        idAndRole.id = id_account;
                        idAndRole.role = id_role;
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void matching() {
        Login = findViewById(R.id.dangNhap_btn_DangNhap);
        username = findViewById(R.id.dangNhap_et_TaiKhoan);
        password = findViewById(R.id.dangNhap_et_MatKhau);
        Error = findViewById(R.id.dangNhap_tv_BaoLoi);
        Register = findViewById(R.id.dangNhap_tv_DangKy);
        Back = findViewById(R.id.dangNhap_img_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}