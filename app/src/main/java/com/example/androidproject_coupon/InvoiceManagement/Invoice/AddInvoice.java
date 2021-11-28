package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.OrderManagement.OderAdapter;
import com.example.androidproject_coupon.R;
import com.example.androidproject_coupon.User.CartAdapter;
import com.example.androidproject_coupon.User.CartFragment;
import com.example.androidproject_coupon.User.MainActivity_User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddInvoice extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    Button btnDathang;
    EditText etHoten, etSDT, etDiachi;
    TextView tvGiaohangnhanh, tvGiaohangtietkiem, tvTamtinh, tvPhivanchuyen, tvTongcong;
    CheckBox cbShipCOD;
    RadioGroup rdGHinhthuc;
    RadioButton rdBtnNhanh, rdBtnTietkiem;
    RecyclerView rvListitem;
    AutoCompleteTextView autotvMagiamgia;
    ArrayAdapter<String> arrayAdapterMagiamgia;
    ArrayList<String> arrayMagiamgia = new ArrayList<>();
    ArrayList<String> idMagiamgia = new ArrayList<>();
    String slcMagiamgia;
    String TAG="FIREBASE";

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_infomation);

        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        rcvInvoiceitem.setHasFixedSize(true);
        rcvInvoiceitem.setLayoutManager(new LinearLayoutManager(AddInvoice.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(AddInvoice.this, DividerItemDecoration.VERTICAL);
        rcvInvoiceitem.addItemDecoration(itemDecoration);

        CartFragment.cartAdapter = new CartAdapter(AddInvoice.this,CartFragment.cart);
        rcvInvoiceitem.setAdapter(CartFragment.cartAdapter);

        matching();
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

        autotvMagiamgia.setAdapter(arrayAdapterMagiamgia);
        autotvMagiamgia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                slcMagiamgia = idMagiamgia.get(position);
            }
        });

        cbShipCOD.setChecked(true);
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etHoten.getText().toString().trim().length() == 0 || etSDT.getText().toString().trim().length() == 0
                        || etDiachi.getText().toString().length() == 0 || autotvMagiamgia.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "All field must be not empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ConfirmOrder();
                    startActivity(new Intent(AddInvoice.this, MainActivity_User.class));
                }
            }

            private void ConfirmOrder() {
                DatabaseReference invRef = database.getReference().child("DonHang");
                DatabaseReference htghRef = database.getReference().child("HinhThucGiaoHang");
                //id
//                size = OrderFragment.mAdapter.getItemCount()+1;

                try {
                    //ma don hang
                    final String saveCurrentIDDate;
                    Calendar calendarIDDate = Calendar.getInstance();
                    SimpleDateFormat idDate = new SimpleDateFormat("yyMMdd");
                    saveCurrentIDDate = idDate.format(calendarIDDate.getTime());
                    String saveIDDate = saveCurrentIDDate.trim();
                    String maDonhang = saveIDDate+"SACH"+size;
                    invRef.child(maDonhang).setValue(maDonhang);

                    String diachi = etDiachi.getText().toString().trim();
                    String hoten = etHoten.getText().toString().trim();
                    String sdt = etSDT.getText().toString().trim();

                    //hinhthucgh chưa fix
                    if(rdBtnNhanh.isChecked()){
                        invRef.child(maDonhang).setValue("0");
                    }else {
                        invRef.child(maDonhang).setValue("1");
                    }
                    String idHinhthucGH = rdBtnNhanh.getText().toString().trim();

                    //makhuyenmai
                    String idKhuyenmai = slcMagiamgia.trim();
                    invRef.child(maDonhang).setValue(idKhuyenmai);

                    //taikhoan chưa fix
                    String idTaiKhoan = etSDT.getText().toString().trim();

                    //id trang thai
                    String idTrangthaiDH = "1";
                    invRef.child(maDonhang).setValue(idTrangthaiDH);

                    //time
                    final String saveCurrentDate;
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
                    saveCurrentDate = currentDate.format(calendar.getTime());
                    String time = saveCurrentDate.trim();
                    invRef.child(maDonhang).setValue(time);

                    //tong tien chưa fix
                    String tongtien = tvTongcong.getText().toString().trim();

                    Oder invoice = new Oder(diachi, hoten,  idHinhthucGH,  idKhuyenmai,  idTaiKhoan,  idTrangthaiDH,  maDonhang,  sdt,  time,  tongtien);
                    invRef.child(maDonhang).setValue(invoice);
                    finish();
                    Toast.makeText(getApplicationContext(),"Thêm đơn hàng thành công", Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
                }
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
        tvGiaohangnhanh = (TextView) findViewById(R.id.inv_tv_giaohangnhanh_gia);
        tvGiaohangtietkiem = (TextView) findViewById(R.id.inv_tv_giaohangtietkiem_gia);
        tvTamtinh = (TextView) findViewById(R.id.inv_tv_tamtinh_gia);
        tvPhivanchuyen = (TextView) findViewById(R.id.inv_tv_phivanchuyen_gia);
        tvTongcong = (TextView) findViewById(R.id.inv_tv_tongcong_gia);
        rdGHinhthuc = (RadioGroup) findViewById(R.id.inv_rdG_hinhthucgiaohang);
        rdBtnNhanh = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangnhanh);
        rdBtnTietkiem = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangtietkiem);
        rvListitem = (RecyclerView) findViewById(R.id.inv_rv_item);
        autotvMagiamgia = (AutoCompleteTextView) findViewById(R.id.inv_tv_magiamgia_list);
    }
}
