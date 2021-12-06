package com.example.androidproject_coupon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.BookManagement.BookAdapter;
import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.ViewInvoice;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.User.ItemDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class activity_oderdetails extends AppCompatActivity {

    private String dia_Chi;
    private String ho_Ten;
    private String id_Hinh_Thuc_GH;
    private String id_Khuyen_Mai;
    private String id_Tai_Khoan;
    private String id_Trang_Thai_DH;
    private Integer id_TrangThaiCu, id_TrangThaiMoi;
    private String ma_Don_Hang;
    private String sdt;
    private String time;
    private String tong_Tien;

    private BookAdapter mAdapter;

    TextView giaohang, name, address, phone, code, timeorder, status, iduser, tongtien, khuyenmai;
    ImageView imgback;
    Spinner sptrangthai;
    RecyclerView recyclerView;
    Button confirm;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

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
                for (DataSnapshot chilSnapshot : dataSnapshot.getChildren()) {
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
        Oder upload = (Oder) bundle.get("object_order");

        dia_Chi = upload.getDia_Chi();
        ho_Ten = upload.getHo_Ten();
        id_Hinh_Thuc_GH = upload.getID_Hinh_Thuc_GH();
        id_Tai_Khoan = upload.getID_Tai_Khoan();
        id_Trang_Thai_DH = upload.getID_Trang_Thai_DH();
        id_TrangThaiCu = Integer.parseUnsignedInt(upload.getID_Trang_Thai_DH());
        ma_Don_Hang = upload.getMa_Don_Hang();
        sdt = upload.getSDT();
        time = upload.getTime();
        tong_Tien = upload.getTong_Tien();
        id_Khuyen_Mai = upload.getID_Khuyen_Mai();


        if (id_TrangThaiCu == 3){
            sptrangthai.setEnabled(false);
            confirm.setText("Quay trở về");
        }else {
            sptrangthai.setAdapter(arrayAdapter);
            sptrangthai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    id_TrangThaiMoi = ID.get(position);
                    if (id_TrangThaiMoi > id_TrangThaiCu)
                        id_Trang_Thai_DH = ID.get(position).toString();
                    else
                        Toast.makeText(activity_oderdetails.this, "Chọn lại tình trạng đơn hàng"
                                , Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            confirm.setText("Cập nhật đơn hàng");
        }

        address.setText("Địa chỉ: " + dia_Chi);
        name.setText("Họ và tên: " + ho_Ten);
        code.setText("Mã đơn hàng: " + ma_Don_Hang);
        phone.setText("Số điện thoại: " + sdt);
        timeorder.setText("Thời gian đặt hàng: " + time);
        tongtien.setText("Tong Tien: " + tong_Tien + "VND");
        DatabaseReference user = database.getReference("Users");
        user.child(id_Tai_Khoan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                iduser.setText("Email đặt hàng: " + hashMap.get("email").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        if (id_Khuyen_Mai.equals("")) {
            khuyenmai.setText("Không áp dụng khuyến mãi");
        } else {
            DatabaseReference khuyenMai = database.getReference("KhuyenMai");
            khuyenMai.child(id_Khuyen_Mai).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                    khuyenmai.setText("Mã khuyến mãi: " + hashMap.get("code").toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity_oderdetails.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(activity_oderdetails.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        List<Book> list = new ArrayList<>();
        DatabaseReference listdonhang = database.getReference("DonHang");
        listdonhang.child(ma_Don_Hang).child("item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot posSnapshot : snapshot.getChildren()) {
                    String sID = posSnapshot.child("id").getValue().toString().trim();
                    String sMaSach = posSnapshot.child("ma_Sach").getValue().toString().trim();
                    String sTenSach = posSnapshot.child("ten_Sach").getValue().toString().trim();
                    String sTacGia = posSnapshot.child("tac_Gia").getValue().toString().trim();
                    String sMoTa = posSnapshot.child("mo_Ta").getValue().toString().trim();
                    String sGia = posSnapshot.child("gia").getValue().toString().trim();
                    String sSoLuong = posSnapshot.child("so_Luong").getValue().toString().trim();
                    String anh = posSnapshot.child("anh").getValue().toString();
                    String id_Nhom_Sach = posSnapshot.child("id_Nhom_Sach").getValue().toString().trim();

                    list.add(new Book(sID, sMaSach, sTenSach, sTacGia, sMoTa, sGia, sSoLuong, anh, id_Nhom_Sach));
                }
                mAdapter = new BookAdapter(activity_oderdetails.this, list);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity_oderdetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(ma_Don_Hang);
                finish();
            }
        });
    }

    private void updateStatus(String key) {
        DatabaseReference donHang = database.getReference("DonHang");
        donHang.child(key).child("id_Trang_Thai_DH").setValue(id_Trang_Thai_DH);
        if (id_TrangThaiCu != 3)
        Toast.makeText(activity_oderdetails.this, "Đã cập nhật trạng thái đơn hàng"
                , Toast.LENGTH_SHORT).show();
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
        recyclerView = findViewById(R.id.oderdetail_rv_listBook);
        confirm = findViewById(R.id.oderdetail_btn_confirm);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
