package com.example.androidproject_coupon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidproject_coupon.OrderManagement.Oder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class activity_oderdetails extends AppCompatActivity {

    private String dia_Chi;
    private String ho_Ten;
    private String id_Hinh_Thuc_GH;
    private String id_Khuyen_Mai;
    private String id_Tai_Khoan;
    private String id_Trang_Thai_DH;
    private String ma_Don_Hang;
    private String sdt;
    private String time;
    private String tong_Tien;


    TextView giaohang, name, address, phone, code, timeorder, status, iduser, tongtien, khuyenmai;
    ImageView imgback;
    Spinner sptrangthai;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oderdetails);

        matching();

        ArrayList<Integer> ID = new ArrayList<Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DatabaseReference trangThaiDonHang = database.getReference("TrangThaiDonHang");
        trangThaiDonHang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chilSnapshot:dataSnapshot.getChildren()){
                    ID.add(Integer.parseUnsignedInt(chilSnapshot.getKey()));
                    arrayList.add(chilSnapshot.child("Trang_Thai").getValue(String.class));
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Oder upload = (Oder) bundle.get("object_oder");

        dia_Chi = upload.getDia_Chi();
        ho_Ten = upload.getHo_Ten();
        id_Hinh_Thuc_GH = upload.getID_Hinh_Thuc_GH();
        id_Tai_Khoan = upload.getID_Tai_Khoan();
        id_Trang_Thai_DH = upload.getID_Trang_Thai_DH();
        ma_Don_Hang = upload.getMa_Don_Hang();
        sdt = upload.getMa_Don_Hang();
        time = upload.getTime();
        tong_Tien = upload.getTong_Tien();
        id_Khuyen_Mai = upload.getID_Khuyen_Mai();

        address.setText(dia_Chi);
        name.setText(ho_Ten);
        code.setText(ma_Don_Hang);
        phone.setText(sdt);
        timeorder.setText(time);
        tongtien.setText("Tong Tien:"+tong_Tien+"VND");
        iduser.setText(id_Tai_Khoan);

        DatabaseReference hinhThucGiaoHang = database.getReference("HinhThucGiaoHang");
        hinhThucGiaoHang.child(id_Hinh_Thuc_GH).addListenerForSingleValueEvent(new ValueEventListener() {
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
        khuyenMai.child(id_Khuyen_Mai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                khuyenmai.setText(hashMap.get("name").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//
//        DatabaseReference trangThaiDonHang = database.getReference("TrangThaiDonHang");
//        trangThaiDonHang.child(id_Trang_Thai_DH).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
//                status.setText(hashMap.get("Trang_Thai").toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        mStorageRef = FirebaseStorage.getInstance().getReference("DonHang");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("DonHang");

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void matching() {
        giaohang = findViewById(R.id.tv_details_giaohang);
        name = findViewById(R.id.tv_details_name);
        address = findViewById(R.id.tv_details_address);
        phone = findViewById(R.id.tv_details_phone);
        code = findViewById(R.id.tv_details_madonhang);
        timeorder = findViewById(R.id.tv_details_timeorder);
        iduser = findViewById(R.id.tv_details_iduser);
        tongtien = findViewById(R.id.tv_details_tongtien);
        khuyenmai = findViewById(R.id.tv_details_khuyenmai);
        imgback = findViewById(R.id.im_details_back);
        sptrangthai = findViewById(R.id.sp_details_status);
    }

//abc
    }
