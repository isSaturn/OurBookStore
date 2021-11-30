package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewInvoice extends AppCompatActivity {

    private String Dia_Chi, Ho_Ten,ID_Hinh_Thuc_GH ,ID_Khuyen_Mai,ID_Tai_Khoan,ID_Trang_Thai_DH,Ma_Don_Hang,SDT,Time,Tong_Tien, Item;


    TextView tvThongtinvanchuyen, tvHoten, tvSDT, tvDiachi, tvMadonhang, tvNgaydathang, tvTongtien;
    ImageView imgReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_view);



        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Oder oder = (Oder) bundle.get("object_invoice");

        Dia_Chi = oder.getDia_Chi();
        Ho_Ten = oder.getHo_Ten();
        ID_Hinh_Thuc_GH = oder.getID_Hinh_Thuc_GH();
        ID_Khuyen_Mai = oder.getID_Khuyen_Mai();
        ID_Tai_Khoan = oder.getID_Tai_Khoan();
        ID_Trang_Thai_DH = oder.getID_Trang_Thai_DH();
        Ma_Don_Hang = oder.getMa_Don_Hang();
        SDT = oder.getSDT();
        Time = oder.getTime();
        Tong_Tien = oder.getTong_Tien();
        Item = oder.getItem();
        //

        mactching();

        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvHoten.setText(Ho_Ten);
        tvSDT.setText(SDT);
        tvDiachi.setText(Dia_Chi);
        DatabaseReference trangThaiDonHang = FirebaseDatabase.getInstance().getReference("TrangThaiDonHang");
        trangThaiDonHang.child(oder.getID_Trang_Thai_DH()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                tvThongtinvanchuyen.setText(hashMap.get("Trang_Thai").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tvMadonhang.setText(Ma_Don_Hang);
        tvNgaydathang.setText(Time);
        tvTongtien.setText("Tổng tiền: "+Tong_Tien+"VNĐ");
    }

    private void mactching() {
        tvTongtien = findViewById(R.id.inv_tv_thongtinthanhtoan);
        tvNgaydathang = findViewById(R.id.inv_tv_thoigiandathang_date);
        tvMadonhang = findViewById(R.id.inv_tv_madonhang_id);
        tvThongtinvanchuyen = findViewById(R.id.inv_tv_hinhthucgiaohang);
        tvHoten = findViewById(R.id.inv_tv_hoten);
        tvSDT = findViewById(R.id.inv_tv_sdt);
        tvDiachi = findViewById(R.id.inv_tv_diachi);
        imgReturn = findViewById(R.id.inv_img_back);
    }
}
