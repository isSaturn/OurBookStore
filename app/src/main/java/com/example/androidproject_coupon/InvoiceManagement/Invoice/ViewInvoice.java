package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.BookManagement.BookAdapter;
import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.R;
import com.example.androidproject_coupon.User.CartAdapter;
import com.example.androidproject_coupon.User.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewInvoice extends AppCompatActivity {

    private String Dia_Chi, Ho_Ten,ID_Hinh_Thuc_GH ,ID_Khuyen_Mai,ID_Tai_Khoan,ID_Trang_Thai_DH,Ma_Don_Hang,SDT,Time,Tong_Tien, Item;
    private String id_Sach, gia_Sach;
    private BookAdapter mAdapter;
    private List<Book> mItem;

    TextView tvThongtinvanchuyen, tvHoten, tvSDT, tvDiachi, tvMadonhang, tvNgaydathang, tvTongtien;
    ImageView imgReturn;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_view);

        mactching();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Oder oder = (Oder) bundle.get("object_invoice");
        //lay du lieu
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
        tvTongtien.setText("Tổng tiền: "+Tong_Tien+" VNĐ");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewInvoice.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(ViewInvoice.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mItem = new ArrayList<>();

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("DonHang").child("item");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mItem.clear();

                for (DataSnapshot posSnapshot : dataSnapshot.getChildren()) {
                    String sID = posSnapshot.child("id").getValue().toString().trim();
                    String sMaSach = posSnapshot.child("ma_Sach").getValue().toString().trim();
                    String sTenSach = posSnapshot.child("ten_Sach").getValue().toString().trim();
                    String sTacGia = posSnapshot.child("tac_Gia").getValue().toString().trim();
                    String sMoTa = posSnapshot.child("mo_Ta").getValue().toString().trim();
                    String sGia = posSnapshot.child("gia").getValue().toString().trim();
                    String sSoLuong = posSnapshot.child("so_Luong").getValue().toString().trim();
                    String anh = posSnapshot.child("anh").getValue().toString();
                    String id_Nhom_Sach = posSnapshot.child("id_Nhom_Sach").getValue().toString().trim();

                    mItem.add(new Book(sID, sMaSach, sTenSach, sTacGia, sMoTa, sGia, sSoLuong, anh, id_Nhom_Sach));
                }

                mAdapter = new BookAdapter(ViewInvoice.this, mItem);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewInvoice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        recyclerView = findViewById(R.id.inv_rv_item_view);
    }
}
