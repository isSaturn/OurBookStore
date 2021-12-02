package com.example.androidproject_coupon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject_coupon.OrderManagement.Oder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class activity_oderdetails extends AppCompatActivity {

    private String Dia_Chi;
    private String Ho_Ten;
    private String ID_Hinh_Thuc_GH;
    private String ID_Khuyen_Mai;
    private String ID_Sach;
    private String ID_Tai_Khoan;
    private String ID_Trang_Thai_DH;
    private String Ma_Don_Hang;
    private String SDT;
    private String Time;
    private String Tong_Tien;
    private String Anh;

    TextView giaohang, name, address, phone, tensach, giasach, code, time, status, iduser, tongtien, khuyenmai;
    ImageView anhsach;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oderdetails);

        matching();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Oder upload = (Oder) bundle.get("activity_oderdetails");

        Dia_Chi = upload.getDia_Chi();
        Ho_Ten = upload.getHo_Ten();
        ID_Hinh_Thuc_GH = upload.getID_Hinh_Thuc_GH();
        ID_Tai_Khoan = upload.getID_Tai_Khoan();
        ID_Trang_Thai_DH = upload.getID_Trang_Thai_DH();
        Ma_Don_Hang = upload.getMa_Don_Hang();
        SDT = upload.getMa_Don_Hang();
        Time = upload.getTime();
        Tong_Tien = upload.getTong_Tien();
        ID_Khuyen_Mai = upload.getID_Khuyen_Mai();

        address.setText(Dia_Chi);
        name.setText(Ho_Ten);
        code.setText(Ma_Don_Hang);
        phone.setText(SDT);
        time.setText(Time);
        tongtien.setText(Tong_Tien);

        DatabaseReference hinhThucGiaoHang = database.getReference("HinhThucGiaoHang");
        hinhThucGiaoHang.child(ID_Hinh_Thuc_GH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                giaohang.setText(hashMap.get("Hinh_Thuc").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference khuyenMai = database.getReference("KhuyenMai");
        khuyenMai.child(ID_Khuyen_Mai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                khuyenmai.setText(hashMap.get("name").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        DatabaseReference sach = database.getReference("Sach");
//        sach.child(ID_Sach).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
//                tensach.setText(hashMap.get("ten_Sach").toString());
//                giasach.setText(hashMap.get("gia").toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        DatabaseReference user = database.getReference("User");
        user.child(ID_Tai_Khoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                iduser.setText(hashMap.get("uid").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        DatabaseReference trangThaiDonHang = database.getReference("TrangThaiDonHang");
        trangThaiDonHang.child(ID_Trang_Thai_DH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                status.setText(hashMap.get("Trang_Thai").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("DonHang");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("DonHang");
    }

    private void matching() {
        giaohang = findViewById(R.id.tv_details_giaohang);
        name = findViewById(R.id.tv_details_name);
        address = findViewById(R.id.tv_details_address);
        phone = findViewById(R.id.tv_details_phone);
        code = findViewById(R.id.tv_details_madonhang);
        time = findViewById(R.id.tv_details_timeorder);
        status = findViewById(R.id.tv_details_choxn);
        iduser = findViewById(R.id.tv_details_iduser);
//        tensach = findViewById(R.id.tv_details_dacnhantam);
//        giasach = findViewById(R.id.tv_details_giatien);
        tongtien = findViewById(R.id.tv_details_tongtien);
        khuyenmai = findViewById(R.id.tv_details_khuyenmai);
//        anhsach = findViewById(R.id.im_details_dacnhantam);
    }


    }
