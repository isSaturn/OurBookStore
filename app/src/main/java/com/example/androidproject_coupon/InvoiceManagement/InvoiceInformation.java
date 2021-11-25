package com.example.androidproject_coupon.InvoiceManagement;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.CouponFragment;
import com.example.androidproject_coupon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceInformation extends AppCompatActivity {
    private RecyclerView rcvInvoiceitem;
    private InvoiceBookAdapter invoiceBookAdapter;

    Button btnDathang;
    EditText etHoten, etSDT, etDiachi;
    TextView tvGiaohangnhanh, tvGiaohangtietkiem, tvTamtinh, tvPhivanchuyen, tvTongcong, tvTensach, tvSoluong, tvGia, tvMagiamgia;
    CheckBox cbShipCOD;
    RadioGroup rdGHinhthuc;
    RadioButton rdBtnNhanh, rdBtnTietkiem;
    RecyclerView rvListitem;
    AutoCompleteTextView autotvMagiamgia;
    ArrayAdapter<String> arrayAdapterMagiamgia;
    ArrayList<String> arrayMagiamgia = new ArrayList<>();
    ArrayList<Integer> idMagiagia = new ArrayList<>();
    String slcMagiamgia;
    String TAG="FIREBASE";


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_infomation);

        getSupportActionBar().setTitle("Đặt hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        matching();

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
        //auto tích checkbox thanh toán
        cbShipCOD.setChecked(true);

        invoiceBookAdapter = new InvoiceBookAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvInvoiceitem.setLayoutManager(linearLayoutManager);

        invoiceBookAdapter.setData(getListBook());
        rcvInvoiceitem.setAdapter(invoiceBookAdapter);
    }

    private void Check() {
        if(TextUtils.isEmpty(etHoten.getText().toString())){
            Toast.makeText(this, "Vui lòng điền đầy đủ họ tên.",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(etSDT.getText().toString())){
            Toast.makeText(this, "Vui lòng nhập số điện thoại.",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(etDiachi.getText().toString())){
            Toast.makeText(this, "Vui lòng điền thông tin địa chỉ.",Toast.LENGTH_LONG).show();
        }
        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
        DatabaseReference invRef = database.getReference().child("DonHang");



        invRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    i = (int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InvoiceInformation.this, "Error",Toast.LENGTH_LONG).show();
            }
        });
        //hoten,sdt,diachi nguoi dat don hang
        String nameTxt = etHoten.getText().toString();
        String numphonesTxt = etSDT.getText().toString();
        String addressTxt = etDiachi.getText().toString();
        invRef.child(String.valueOf(i+1)).child("Ho_Ten").setValue(nameTxt);
        invRef.child(String.valueOf(i+1)).child("SDT").setValue(numphonesTxt);
        invRef.child(String.valueOf(i+1)).child("Dia_Chi").setValue(addressTxt);
        //Hinh thuc giao hang
        if (rdBtnNhanh.isChecked()){
            invRef.child(String.valueOf(i+1)).child("ID_Hinh_Thuc_GH").setValue("0");
        }else {
            invRef.child(String.valueOf(i+1)).child("ID_Hinh_Thuc_GH").setValue("1");
        }
        //add ma giam gia
        arrayAdapterMagiamgia = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayMagiamgia);
        DatabaseReference cpnRef = database.getReference("KhuyenMai");
        cpnRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.getValue().toString();
                    arrayMagiamgia.add(value);
                    idMagiagia.add(Integer.parseInt(data.getKey()));
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
                slcMagiamgia = idMagiagia.get(position).toString();
            }
        });

        //ChiTietDonHang
        DatabaseReference invDetailRef = database.getReference().child("ChiTietDonHang");

        //Dialog complete
        Toast.makeText(InvoiceInformation.this,"Đặt hàng thành công",Toast.LENGTH_LONG).show();
    }

    private List<InvoiceBook> getListBook() {
        List<InvoiceBook> list = new ArrayList<>();
        list.add(new InvoiceBook(R.drawable.sach,"Book Name"));

        return list;
    }

    private void matching() {
        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
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
        tvTensach = (TextView) findViewById(R.id.inv_tv_chitietdonhang_tensach);
        tvSoluong = (TextView) findViewById(R.id.inv_tv_chitietdonhang_sl);
        tvGia = (TextView) findViewById(R.id.inv_tv_chitietdonhang_gia);
        rdGHinhthuc = (RadioGroup) findViewById(R.id.inv_rdG_hinhthucgiaohang);
        rdBtnNhanh = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangnhanh);
        rdBtnTietkiem = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangtietkiem);
        rvListitem = (RecyclerView) findViewById(R.id.inv_rv_item);
        autotvMagiamgia = (AutoCompleteTextView) findViewById(R.id.inv_tv_magiamgia_list);
    }
}
