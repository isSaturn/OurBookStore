package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.text.TextUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.BookManagement.AddBook;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.CouponFragment;
import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.CouponManagement.CouponAdapter;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvoiceInformation extends AppCompatActivity {
    private RecyclerView rcvInvoiceitem;
    public static InvoiceBookAdapter invoiceBookAdapter;
    public static ArrayList<Invoice> invList = new ArrayList<>();
    Button btnDathang;
    EditText etHoten, etSDT, etDiachi;
    TextView tvGiaohangnhanh, tvGiaohangtietkiem, tvTamtinh, tvPhivanchuyen, tvTongcong, tvTensach, tvGia;
    CheckBox cbShipCOD;
    RadioGroup rdGHinhthuc;
    RadioButton rdBtnNhanh, rdBtnTietkiem;
    RecyclerView rvListitem;
    AutoCompleteTextView autotvMagiamgia;
    ArrayAdapter<String> arrayAdapterMagiamgia;
    ArrayList<String> arrayMagiamgia = new ArrayList<>();
    ArrayList<String> idMagiamgia = new ArrayList<>();
    ArrayList<Integer> idSach = new ArrayList<>();
    ArrayList<Integer> soLuong = new ArrayList<>();
    String slcMagiamgia;
    String TAG="FIREBASE";
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inv_infomation);

//        invoiceBookAdapter = new InvoiceBookAdapter(this);
//        rcvInvoiceitem.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        rcvInvoiceitem.setLayoutManager(linearLayoutManager);
//        invoiceBookAdapter.setData(getListBook());
//        rcvInvoiceitem.setAdapter(invoiceBookAdapter);

        matching();

        //show list ma giam gia va gan id ma khuyen mai
        arrayAdapterMagiamgia = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayMagiamgia);
        DatabaseReference cpnRef = database.getReference("KhuyenMai");
        cpnRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.child("name").getValue().toString()+ " - " + data.child("code").getValue().toString();;
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

        cbShipCOD.setChecked(true);
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = etDiachi.getText().toString().trim();
                String hoten = etHoten.getText().toString().trim();
                String sdt = etSDT.toString().trim();
                String tongtien = tvTongcong.getText().toString().trim();
                String maDonhang = tvTongcong.getText().toString().trim();
                String time = tvTongcong.getText().toString().trim();

                if (etHoten.getText().toString().trim().length() == 0 || etSDT.getText().toString().trim().length() == 0
                        || etDiachi.getText().toString().length() == 0 || autotvMagiamgia.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "All field must be not empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ConfirmOrder(diachi, hoten,sdt ,maDonhang, time, tongtien);//
                }
            }

            private void ConfirmOrder(String diachi, String hoten, String sdt, String maDonHang, String time, String tongtien) {
                DatabaseReference invRef = database.getReference().child("DonHang");
                //id
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    size = extras.getInt("size") + 1;
                }
                String InvoiceID = String.valueOf(size);
                Toast.makeText(InvoiceInformation.this, "Thêm sách mới thành công", Toast.LENGTH_LONG).show();
                Invoice invoice = new Invoice(InvoiceID, diachi, hoten, sdt, maDonHang, time, tongtien);
                invRef.child(InvoiceID).setValue(invoice);
                String cpnID = slcMagiamgia.trim();
                invRef.child(InvoiceID).child("ID_Khuyen_Mai").setValue(cpnID);


//                //add hoten,sdt,diachi
//                String nameTxt = etHoten.getText().toString();
//                String numphonesTxt = etSDT.getText().toString();
//                String addressTxt = etDiachi.getText().toString();
//                invRef.child(String.valueOf(i+1)).child("Ho_Ten").setValue(nameTxt);
//                invRef.child(String.valueOf(i+1)).child("SDT").setValue(numphonesTxt);
//                invRef.child(String.valueOf(i+1)).child("Dia_Chi").setValue(addressTxt);
//                //sanpham
//
//                //id role
//                String userTxt = arrRole.toString();
//                invRef.child(String.valueOf(i+1)).child("ID_Tai_Khoan").setValue(arrRole);
//                //id trang thai
//                invRef.child(String.valueOf(i+1)).child("ID_Trang_Thai_DH").setValue("1");
//
//                //tong tien
//                String totalMoney = tvTongcong.getText().toString();
//                invRef.child(String.valueOf(i+1)).child("Tong_Tien").setValue(totalMoney);
//
//                //id Ma khuyen mai
//                String cpnID = slcMagiamgia.trim();
//                invRef.child(String.valueOf(i+1)).child("ID_Khuyen_Mai").setValue(cpnID);
//
//                //id Hinh thuc giao hang
//                if (rdBtnNhanh.isChecked()){
//                    invRef.child(String.valueOf(i+1)).child("ID_Hinh_Thuc_GH").setValue("0");
//                }else {
//                    invRef.child(String.valueOf(i+1)).child("ID_Hinh_Thuc_GH").setValue("1");
//                }
//
//                //add time
//                final String saveCurrentDate;
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
//                saveCurrentDate = currentDate.format(calendar.getTime());
//                String saveDate = saveCurrentDate.trim();
//                invRef.child(String.valueOf(i+1)).child("Time").setValue(saveDate);
//
//                //ma don hang
//                final String saveCurrentIDDate;
//                Calendar calendarIDDate = Calendar.getInstance();
//                SimpleDateFormat idDate = new SimpleDateFormat("yyMMdd");
//                saveCurrentIDDate = idDate.format(calendarIDDate.getTime());
//                String saveIDDate = saveCurrentIDDate.trim();
//                invRef.child(String.valueOf(i+1)).child("Ma_Don_Hang").setValue(saveIDDate+"SACH");
//
//                //Chitietdonhang
//                DatabaseReference invDetailsRef = database.getReference("ChiTietDonHang");
//                String idSachtxt = idSach.toString().trim();
//                String soLuongtxt = soLuong.toString().trim();
//                invDetailsRef.child(String.valueOf(i+1)).child("ID_Don_Hang").setValue(String.valueOf(i+1));
//                invDetailsRef.child(String.valueOf(i+1)).child("ID_Sach").setValue(idSachtxt);
//                invDetailsRef.child(String.valueOf(i+1)).child("So_Luong").setValue(soLuongtxt);
//
//                Toast.makeText(InvoiceInformation.this, "Đặt hàng thành công",Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference invCartDetailsRef = database.getReference("DonHang");
        invCartDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                invoiceBookAdapter.clear();
                invList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String id = data.child("ID").getValue().toString();
                    String diachi = data.child("Dia_Chi").getValue().toString();
                    String hoten = data.child("Ho_Ten").getValue().toString();
                    String idHinhthucGH = data.child("ID_Hinh_Thuc_GH").getValue().toString();
                    String idKhuyenmai = data.child("ID_Khuyen_Mai").getValue().toString();
                    String idTaiKhoan = data.child("ID_Tai_Khoan").getValue().toString();
                    String idTrangthaiDH = data.child("ID_Trang_Thai_DH").getValue().toString();
                    String maDonhang = data.child("Ma_Don_Hang").getValue().toString();
                    String sdt = data.child("SDT").getValue().toString();
                    String time = data.child("Time").getValue().toString();
                    String tongtien = data.child("Tong_Tien").getValue().toString();
                    invList.add(new Invoice(id, diachi, hoten, idHinhthucGH, idKhuyenmai, idTaiKhoan, idTrangthaiDH, maDonhang, sdt, time, tongtien));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


//        //auto tích checkbox thanh toán
//
//        invoiceBookAdapter = new InvoiceBookAdapter(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        rcvInvoiceitem.setLayoutManager(linearLayoutManager);
//        invoiceBookAdapter.setData(getListBook());
//        rcvInvoiceitem.setAdapter(invoiceBookAdapter);
//

//        //gan id chi tiet don hang
//
//        //show tong tien
//
//        //gan role
//
//    }
//
//

    private List<InvoiceBook> getListBook() {
        List<InvoiceBook> list = new ArrayList<>();

        return list;
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
        tvTensach = (TextView) findViewById(R.id.inv_tv_chitietdonhang_tensach);
        tvGia = (TextView) findViewById(R.id.inv_tv_chitietdonhang_gia);
        rdGHinhthuc = (RadioGroup) findViewById(R.id.inv_rdG_hinhthucgiaohang);
        rdBtnNhanh = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangnhanh);
        rdBtnTietkiem = (RadioButton) findViewById(R.id.inv_rdBtn_giaohangtietkiem);
        rvListitem = (RecyclerView) findViewById(R.id.inv_rv_item);
        autotvMagiamgia = (AutoCompleteTextView) findViewById(R.id.inv_tv_magiamgia_list);

    }
}
