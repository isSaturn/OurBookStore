package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.CouponManagement.EditCoupon;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.R;
import com.example.androidproject_coupon.User.CartAdapter;
import com.example.androidproject_coupon.User.CartFragment;
import com.example.androidproject_coupon.User.MainActivity_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddInvoice extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private String slcMagiamgia;
    private String slcHinhthucgiaohang;

    private CartAdapter invAdapter;
    private List<Book> mInvoices;
    ImageView imgReturn;
    Button btnDathang;
    EditText etHoten, etSDT, etDiachi;
    TextView tvHinhthucgiaohang, tvTamtinh, tvPhivanchuyen, tvTongcong;
    CheckBox cbShipCOD;
    RecyclerView rvListitem;
    AutoCompleteTextView autotvMagiamgia;
    Spinner spHinhthucgiaohang;
    ArrayAdapter<String> arrayAdapterMagiamgia;
    ArrayAdapter<String> arrayAdapterHinhthucgiaohang;
    ArrayList<String> arrayMagiamgia = new ArrayList<>();
    ArrayList<String> arrayHinhthucgiaohang = new ArrayList<>();
    ArrayList<String> idMagiamgia = new ArrayList<>();
    ArrayList<Integer> idHinhthucgiaohang = new ArrayList<>();
    ArrayList<String> idTrangthaidonhang = new ArrayList<>();
    ArrayList<Integer> idSach = new ArrayList<>();


    String TAG="FIREBASE";
    GetIDandRole getIDandRole = new GetIDandRole();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
    long i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        rcvInvoiceitem.setHasFixedSize(true);
        rcvInvoiceitem.setLayoutManager(new LinearLayoutManager(AddInvoice.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(AddInvoice.this, DividerItemDecoration.VERTICAL);
        rcvInvoiceitem.addItemDecoration(itemDecoration);


        invAdapter = CartFragment.cartAdapter;
        mInvoices = CartFragment.cart;

        invAdapter = new CartAdapter(AddInvoice.this,mInvoices);
        rcvInvoiceitem.setAdapter(invAdapter);

        matching();
        idTrangthaidonhang.add("1");
        cbShipCOD.setChecked(true);
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //show list ma giam gia va gan id ma khuyen mai
        arrayAdapterMagiamgia = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayMagiamgia);
        DatabaseReference cpnRef = database.getReference("KhuyenMai");
        cpnRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.child("name").getValue().toString()+ " - " + data.child("code").getValue().toString();
                    arrayMagiamgia.add(value);
                    idMagiamgia.add(data.child("code").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        autotvMagiamgia.setAdapter(arrayAdapterMagiamgia);
        autotvMagiamgia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                slcMagiamgia = idMagiamgia.get(position);
            }
        });
        arrayAdapterHinhthucgiaohang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arrayHinhthucgiaohang);
        DatabaseReference htghRef = database.getReference("HinhThucGiaoHang");
        htghRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    idHinhthucgiaohang.add(Integer.parseUnsignedInt(data.getKey()));
                    arrayHinhthucgiaohang.add(data.child("Hinh_Thuc").getValue(String.class));
                }
                arrayAdapterHinhthucgiaohang.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        spHinhthucgiaohang.setAdapter(arrayAdapterHinhthucgiaohang);
        spHinhthucgiaohang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                slcHinhthucgiaohang = idHinhthucgiaohang.get(i).toString();
                if(idHinhthucgiaohang.get(i) == 1){
                    tvHinhthucgiaohang.setText("20.000VNĐ");
                    tvHinhthucgiaohang.setEnabled(false);
                }else if(idHinhthucgiaohang.get(i) == 0){
                    tvHinhthucgiaohang.setText("10.000VNĐ");
                    tvHinhthucgiaohang.setEnabled(false);
                } else{
                    tvHinhthucgiaohang.setText("");
                    tvHinhthucgiaohang.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        DatabaseReference ttdhRef = database.getReference("TrangThaiDonHang");
        ttdhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    idTrangthaidonhang.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference itemRef = database.getReference("Sach");
        itemRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    idSach.add(Integer.parseUnsignedInt(data.getKey()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference invRef = FirebaseDatabase.getInstance().getReference("DonHang");
        invRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    i = snapshot.getChildrenCount();
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etHoten.getText().toString().trim().length() == 0 || etSDT.getText().toString().trim().length() == 0
                        || etDiachi.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "All field must be not empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ConfirmOrder();
                    Intent intent = new Intent(AddInvoice.this, MainActivity_User.class);
                    startActivity(intent);
                }
            }

            private void ConfirmOrder() {

                //ma don hang
                NumberFormat nf = new DecimalFormat("0000");
                String num = nf.format(i);
                final String saveCurrentIDDate;
                Calendar calendarIDDate = Calendar.getInstance();
                SimpleDateFormat idDate = new SimpleDateFormat("yyMMdd");
                saveCurrentIDDate = idDate.format(calendarIDDate.getTime());
                String saveIDDate = saveCurrentIDDate.trim();
                String maDonhang = saveIDDate+"SACH"+num;

                String diachi = etDiachi.getText().toString().trim();
                String hoten = etHoten.getText().toString().trim();
                String sdt = etSDT.getText().toString().trim();

                //hinhthucgh
                String idHinhthucGH = slcHinhthucgiaohang.trim();


                //makhuyenmai
                String idKhuyenmai = slcMagiamgia.trim();

                //taikhoan
                String idTaiKhoan = getIDandRole.id;

                //id trang thai
                String idTrangthaiDH = "1";

                //time
                final String saveCurrentDate;
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
                saveCurrentDate = currentDate.format(calendar.getTime());
                String time = saveCurrentDate.trim();

                //tong tien chưa fix
                String tongtien = tvTongcong.getText().toString().trim();

                String item = tvTongcong.getText().toString().trim();

                Oder invoice = new Oder(diachi, hoten, idHinhthucGH, idKhuyenmai, idTaiKhoan, idTrangthaiDH, maDonhang, sdt, time, tongtien, item);
                invRef.child(String.valueOf(i)).setValue(invoice);

                Toast.makeText(getApplicationContext(),"Thêm đơn hàng thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void matching() {
        rcvInvoiceitem = (RecyclerView) findViewById(R.id.inv_rv_item);
        btnDathang = (Button)findViewById(R.id.inv_btn_dathang);
        cbShipCOD = (CheckBox) findViewById(R.id.inv_cb_thanhtoantienmat);
        etHoten = (EditText) findViewById(R.id.inv_et_hoten);
        etSDT = (EditText) findViewById(R.id.inv_et_sdt);
        etDiachi = (EditText) findViewById(R.id.inv_et_diachi);
        tvHinhthucgiaohang = (TextView) findViewById(R.id.inv_tv_giaohangnhanh_gia);
        spHinhthucgiaohang = (Spinner) findViewById(R.id.inv_sp_hinhthucgiaohang);
        tvTamtinh = (TextView) findViewById(R.id.inv_tv_tamtinh_gia);
        tvPhivanchuyen = (TextView) findViewById(R.id.inv_tv_phivanchuyen_gia);
        tvTongcong = (TextView) findViewById(R.id.inv_tv_tongcong_gia);
        rvListitem = (RecyclerView) findViewById(R.id.inv_rv_item);
        autotvMagiamgia = (AutoCompleteTextView) findViewById(R.id.inv_tv_magiamgia_list);
        imgReturn = (ImageView) findViewById(R.id.inv_img_return);
    }
}
