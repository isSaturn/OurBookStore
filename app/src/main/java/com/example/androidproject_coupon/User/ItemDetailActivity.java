package com.example.androidproject_coupon.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {

    private String ID_Sach ,Ma_Sach, Ten_Sach, Tac_Gia, Mo_Ta, Gia, So_Luong, Anh, ID_Nhom_Sach;

    ImageView img;
    TextView name, price, masach, tacgia, theloai, mota;
    Button addtocart;
    ImageView back;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        matching();

        //Lấy dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Book upload = (Book) bundle.get("detail_sach");

        ID_Sach = upload.getID();
        Ma_Sach = upload.getMa_Sach();
        Ten_Sach = upload.getTen_Sach();
        Tac_Gia = upload.getTac_Gia();
        Mo_Ta = upload.getMo_Ta();
        Gia = upload.getGia();
        So_Luong = upload.getSo_Luong();
        Anh = upload.getAnh();
        ID_Nhom_Sach = upload.getID_Nhom_Sach();

        //Hiển thị thông tin sản phẩm lên app.

        masach.setText("Mã sách: " + upload.getMa_Sach());
        name.setText(Ten_Sach);
        tacgia.setText("Tác giả: " + upload.getTac_Gia());
        mota.setText(upload.getMo_Ta());
        price.setText("Giá tiền: " + upload.getGia() + " VNĐ");

        //Lấy Id nhóm sách thành chữ
        DatabaseReference nhomSach = database.getReference("NhomSach");
        nhomSach.child(ID_Nhom_Sach).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                theloai.setText(" Thể loại: " + hashMap.get("Loai_Sach").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Picasso.with(ItemDetailActivity.this)
                .load(Anh)
                .fit()
                .centerCrop()
                .into(img);
        //lấy data
        mStorageRef = FirebaseStorage.getInstance().getReference("Sach");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sach");

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sid_sach = ID_Sach.toString().trim();
                String sma_sach = Ma_Sach.toString().trim();
                String sten_sach = Ten_Sach.toString().trim();
                String stac_gia = Tac_Gia.toString().trim();
                String smo_ta = Mo_Ta.toString().trim();
                String sgia = Gia.toString().trim();
                String sso_luong = So_Luong.toString().trim();
                String sanh = Anh.toString().trim();
                String sid_nhom_sach = ID_Nhom_Sach.toString().trim();

                CartFragment.cart.add(new Book(sid_sach, sma_sach, sten_sach, stac_gia, smo_ta, sgia, sso_luong, sanh, sid_nhom_sach));
                CartFragment.cartAdapter.notifyDataSetChanged();
                Toast.makeText(ItemDetailActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void matching() {
        img = findViewById(R.id.img_itemdetail_book);
        name = findViewById(R.id.tv_itemdetail_name);
        price = findViewById(R.id.tv_itemdetail_price);
        masach = findViewById(R.id.tv_itemdetail_masach);
        tacgia = findViewById(R.id.tv_itemdetail_tacgia);
        theloai = findViewById(R.id.tv_itemdetail_theloai);
        mota = findViewById(R.id.tv_itemdetail_mota);
        addtocart = findViewById(R.id.btn_itemdetail_add);
        back = findViewById(R.id.itemDetail_btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}