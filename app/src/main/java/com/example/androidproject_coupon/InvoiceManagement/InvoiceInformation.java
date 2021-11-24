package com.example.androidproject_coupon.InvoiceManagement;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvoiceInformation extends AppCompatActivity {
    private RecyclerView rcvInvoiceitem;
    private InvoiceBookAdapter invoiceBookAdapter;

    Button btnDathang, btnMagiamgia;
    EditText etHoten, etSDT, etDiachi, etMagiamgia;
    TextView tvGiaohangnhanh, tvGiaohangtietkiem, tvTamtinh, tvPhivanchuyen, tvTongcong, tvTensach, tvSoluong, tvGia;
    CheckBox cbShipCOD;
    RadioGroup rdGHinhthuc;
    RadioButton rdBtnNhanh, rdBtnTietkiem;
    RecyclerView rvListitem;

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
        try {
            Bundle extras = getIntent().getExtras();
            int size = 0;
            if(extras!=null){
                size = extras.getInt("size");
            }
            String id = String.valueOf(size);
            String nameTxt = etHoten.getText().toString();
            String numphonesTxt = etSDT.getText().toString();
            String addressTxt = etDiachi.getText().toString();
            String rdGiaohangnhanh = rdBtnNhanh.getText().toString();
            String rdGiaohangtietkiem = rdBtnTietkiem.getText().toString();
            invRef.child(id).child("Ho_ten").setValue(nameTxt);
            invRef.child(id).child("SDT").setValue(numphonesTxt);
            invRef.child(id).child("Dia_chi").setValue(addressTxt);
            if (rdBtnNhanh.isChecked()){
                invRef.child(id).child("ID_Hinh_Thuc_GH").setValue(rdGiaohangnhanh);
            }else{
                invRef.child(id).child("ID_Hinh_Thuc_GH").setValue(rdGiaohangtietkiem);
            }
            Toast.makeText(InvoiceInformation.this,"Đặt hàng thành công",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private List<InvoiceBook> getListBook() {
        List<InvoiceBook> list = new ArrayList<>();
        list.add(new InvoiceBook(R.drawable.sach,"Book Name"));

        return list;
    }

    private void matching() {
        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        btnDathang = (Button)findViewById(R.id.inv_btn_dathang);
        btnMagiamgia = (Button) findViewById(R.id.inv_btn_magiamgia);
        cbShipCOD = (CheckBox) findViewById(R.id.inv_cb_thanhtoantienmat);
        etHoten = (EditText) findViewById(R.id.inv_et_hoten);
        etSDT = (EditText) findViewById(R.id.inv_et_sdt);
        etDiachi = (EditText) findViewById(R.id.inv_et_diachi);
        etMagiamgia = (EditText) findViewById(R.id.inv_et_magiamgia);
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
    }
}
