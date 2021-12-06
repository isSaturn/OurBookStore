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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddInvoice extends AppCompatActivity {

    private RecyclerView rcvInvoiceitem;
    private String slcMagiamgia = "";
    private String slcHinhthucgiaohang;
    public static CartAdapter invAdapter;
    private List<Book> mInvoices;
//    private ArrayList<Item> itemList;
    private ArrayList<Book> itemList;
    private String idItem;
    ImageView imgReturn;
    Button btnDathang;
    EditText etHoten, etSDT, etDiachi;
    TextView tvHinhthucgiaohang, tvTamtinh, tvTongcong, tvDieukien,tvMakhuyenmai;
    CheckBox cbShipCOD;
    RecyclerView rvListitem;
    AutoCompleteTextView autotvMagiamgia;
    Spinner spHinhthucgiaohang;
    ArrayAdapter<String> arrayAdapterMagiamgia;
    ArrayAdapter<String> arrayAdapterHinhthucgiaohang;
    ArrayList<String> arrayMagiamgia = new ArrayList<>();
    ArrayList<String> arrayHinhthucgiaohang = new ArrayList<>();
    ArrayList<String> idMagiamgia = new ArrayList<>();
    ArrayList<String> valueCpn = new ArrayList<>();
    ArrayList<String> idType = new ArrayList<>();
    ArrayList<String> valueConditionCpn = new ArrayList<>();
    ArrayList<Integer> idHinhthucgiaohang = new ArrayList<>();
    ArrayList<String> idTrangthaidonhang = new ArrayList<>();

    String TAG="FIREBASE";
    GetIDandRole getIDandRole = new GetIDandRole();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
    long i = 0;
    int giaohang;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        matching();

        rcvInvoiceitem = findViewById(R.id.inv_rv_item);
        rcvInvoiceitem.setHasFixedSize(true);
        rcvInvoiceitem.setLayoutManager(new LinearLayoutManager(AddInvoice.this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(AddInvoice.this, DividerItemDecoration.VERTICAL);
        rcvInvoiceitem.addItemDecoration(itemDecoration);

        //invAdapter = CartFragment.cartAdapter;
        mInvoices = CartFragment.cart;

        invAdapter = new CartAdapter(AddInvoice.this,mInvoices);
        rcvInvoiceitem.setAdapter(invAdapter);
        invAdapter.notifyDataSetChanged();

        itemList = new ArrayList<>();


        idTrangthaidonhang.add("1");
        cbShipCOD.setChecked(true);
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //time
        final String saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy/MM/dd");
        saveCurrentDate = currentDate.format(calendar.getTime());

        //show list ma giam gia va gan id ma khuyen mai
        DatabaseReference cpnRef = database.getReference("KhuyenMai");
//        String currentDate = String.valueOf(android.text.format.DateFormat.format("yyyy/MM/dd", new java.util.Date()));
        cpnRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.child("name").getValue().toString()+ " - " + data.child("code").getValue().toString();
                    String dateEnd = data.child("eEnd").getValue().toString();
                    String dateStart = data.child("eStart").getValue().toString();

                    // Xet ngày hiển thị mã khuyến mãi nếu trong tg KM
                    long compare1 = compareDate(dateStart, saveCurrentDate);
                    long compare2 = compareDate(saveCurrentDate, dateEnd);
                    if(compare1 >=0 && compare2 >= 0){
                        arrayMagiamgia.add(value);
                        idMagiamgia.add(data.child("code").getValue().toString());
                        idType.add(data.child("idType").getValue().toString());
                        valueCpn.add(data.child("value").getValue().toString());
                        valueConditionCpn.add(data.child("valueCondition").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //
        arrayAdapterMagiamgia = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayMagiamgia);
        autotvMagiamgia.setAdapter(arrayAdapterMagiamgia);
        autotvMagiamgia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                slcMagiamgia = idMagiamgia.get(position);
                if(idType.get(position).equals("1")){
                    tvDieukien.setText("Giảm "+valueCpn.get(position)+" VNĐ cho đơn hàng từ "+valueConditionCpn.get(position)+" VNĐ");
                    tvDieukien.setEnabled(false);
                    tvSelectCpn(idType.get(position),valueCpn.get(position),valueConditionCpn.get(position));

                }else if(idType.get(position).equals("2")){
                    tvDieukien.setText("Giảm "+valueCpn.get(position)+" % cho đơn hàng từ "+valueConditionCpn.get(position)+" VNĐ");
                    tvDieukien.setEnabled(false);
                    tvSelectCpn(idType.get(position),valueCpn.get(position),valueConditionCpn.get(position));

                }
                else if(idType.get(position).equals("3")){
                    tvDieukien.setText("Miễn phí vận chuyển cho đơn hàng từ "+valueConditionCpn.get(position)+" VNĐ");
                    tvDieukien.setEnabled(false);
                    tvSelectCpn(idType.get(position),valueCpn.get(position),valueConditionCpn.get(position));
                }
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
                    tvHinhthucgiaohang.setText("25.000VNĐ");
                }else if(idHinhthucgiaohang.get(i) == 0){
                    tvHinhthucgiaohang.setText("25.000VNĐ");
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

        //madonhang
        DatabaseReference invRef = FirebaseDatabase.getInstance().getReference("DonHang");
        invRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    i = (snapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        NumberFormat nf = new DecimalFormat("0000");
        String num = nf.format(i);
        final String saveCurrentIDDate;
        Calendar calendarIDDate = Calendar.getInstance();
        SimpleDateFormat idDate = new SimpleDateFormat("yyMMdd");
        saveCurrentIDDate = idDate.format(calendarIDDate.getTime());
        String saveIDDate = saveCurrentIDDate.trim();

        tvTamtinh.setText(String.valueOf(CartFragment.tien+25000));
        tvTongcong.setText(tvTamtinh.getText());
        tvMakhuyenmai.setText("0");

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
                String maDonhang = saveIDDate+"SACH"+String.valueOf(i+1);
                String diachi = etDiachi.getText().toString().trim();
                String hoten = etHoten.getText().toString().trim();
                String sdt = etSDT.getText().toString().trim();
                String idHinhthucGH = slcHinhthucgiaohang.trim();
                String idKhuyenmai = slcMagiamgia.trim();
                String idTaiKhoan = getIDandRole.id;
                String idTrangthaiDH = "1";
                String time = saveCurrentDate.trim();
                //tong tien chưa fix
                String tongtien = tvTongcong.getText().toString().trim();

                Oder invoice = new Oder(diachi, hoten, idHinhthucGH, idKhuyenmai, idTaiKhoan, idTrangthaiDH, maDonhang, sdt, time, tongtien);
                invRef.child(maDonhang).setValue(invoice);

                invRef.child(maDonhang).child("item").setValue(CartFragment.cart);
                Toast.makeText(AddInvoice.this, "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void tvSelectCpn(String idType, String valueCpn, String valueConditionCpn) {
        Integer valueCondition = Integer.parseUnsignedInt(valueConditionCpn);
        Integer value = Integer.parseUnsignedInt(valueCpn);
        Integer total = Integer.parseUnsignedInt(tvTamtinh.getText().toString());
        Integer result = 0;
        Integer cpn = 0;
        if(idType.equals("1")||idType.equals("3")){
            if(total<valueCondition){
                Toast.makeText(AddInvoice.this, "Đơn hàng không đủ điều kiện áp dụng mã khuyến mãi", Toast.LENGTH_SHORT).show();
                result = total;
                tvMakhuyenmai.setText("0");
                tvTongcong.setText(result.toString());
                return;
            }
            result = total-value;
            tvTongcong.setText(result.toString());
            tvMakhuyenmai.setText(value.toString());

        }
        else{
            if (total<valueCondition){
                Toast.makeText(AddInvoice.this, "Đơn hàng không đủ điều kiện áp dụng mã khuyến mãi", Toast.LENGTH_SHORT).show();
                result = total;
                tvMakhuyenmai.setText("0");
                tvTongcong.setText(result.toString());
                return;
        }
            else{
                cpn = (total*value)/100;
                result = total - cpn;
                tvTongcong.setText(result.toString());
                tvMakhuyenmai.setText(cpn.toString());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long compareDate(String dateStart, String dateEnd){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date dStart = null;
        Date dEnd = null;
        try {
            dStart = formatter.parse(dateStart);
            dEnd = formatter.parse(dateEnd);
            formatter.applyPattern("yyyy-MM-dd");
            dateStart = formatter.format(dStart);
            dateEnd = formatter.format(dEnd);
            LocalDate d1 = LocalDate.parse(dateStart, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate d2 = LocalDate.parse(dateEnd, DateTimeFormatter.ISO_LOCAL_DATE);
            Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
            long diffDays = diff.toDays();
            return diffDays;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void matching() {
        rcvInvoiceitem = (RecyclerView) findViewById(R.id.inv_rv_item_view);
        btnDathang = (Button)findViewById(R.id.inv_btn_dathang);
        cbShipCOD = (CheckBox) findViewById(R.id.inv_cb_thanhtoantienmat);
        etHoten = (EditText) findViewById(R.id.inv_et_hoten);
        etSDT = (EditText) findViewById(R.id.inv_et_sdt);
        etDiachi = (EditText) findViewById(R.id.inv_et_diachi);
        tvHinhthucgiaohang = (TextView) findViewById(R.id.inv_tv_giaohangnhanh_gia);
        spHinhthucgiaohang = (Spinner) findViewById(R.id.inv_sp_hinhthucgiaohang);
        tvTamtinh = (TextView) findViewById(R.id.inv_tv_tamtinh_gia);
        tvTongcong = (TextView) findViewById(R.id.inv_tv_tongcong_gia);
        rvListitem = (RecyclerView) findViewById(R.id.inv_rv_item_view);
        autotvMagiamgia = (AutoCompleteTextView) findViewById(R.id.inv_tv_magiamgia_list);
        imgReturn = (ImageView) findViewById(R.id.inv_img_return);
        tvDieukien = (TextView) findViewById(R.id.inv_tv_dieukiengiamgia);
        tvMakhuyenmai = findViewById(R.id.inv_tv_magiamgia_gia);
    }
}
