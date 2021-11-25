package com.example.androidproject_coupon.CouponManagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidproject_coupon.CouponFragment;
import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCoupon extends AppCompatActivity {
    AutoCompleteTextView autoType, autoCondition;
    ArrayAdapter<String> arrayAdapterType, arrayAdapterCondition;
    EditText dateStart, dateEnd, cpCode, cpName, cpValue, cpValueCondition ;
    Calendar calendar;
    ImageView arrowReturn;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
    DatabaseReference addCpRef = database.getReference("KhuyenMai");
    ArrayList<String> arrayListType = new ArrayList<>();
    ArrayList<String> arrayListCondition = new ArrayList<>();
    ArrayList<Integer> idType = new ArrayList<>();
    ArrayList<Integer> idCondition = new ArrayList<>();
    Button addBtn;
    String selectedType, selectedCondition;
    String TAG="FIREBASE";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        matching();


        //Loai khuyen mai
        arrayAdapterType = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayListType);
        DatabaseReference myRef = database.getReference("LoaiKhuyenMai");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.child("Loai_Khuyen_Mai").getValue().toString();
                    arrayListType.add(value);
                    idType.add(Integer.parseInt(data.getKey()));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        autoType.setAdapter(arrayAdapterType);

        autoType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Item: " + arrayListType.get(position), Toast.LENGTH_SHORT).show();
                selectedType = idType.get(position).toString();
                // Block field khi user chon Mien phi giao hang
                if(idType.get(position) == 3){
                    cpValue.setText("25000");
                    cpValue.setEnabled(false);
                }
                else{
                    cpValue.setText("");
                    cpValue.setEnabled(true);
                }
            }
        });

        // Loai ap dung
        arrayAdapterCondition = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListCondition);
        DatabaseReference myRefCondition = database.getReference("LoaiApDung");
        myRefCondition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    String value = data.child("Loai_Ap_Dung").getValue().toString();
                    arrayListCondition.add(value);
                    idCondition.add(Integer.parseInt(data.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        autoCondition.setAdapter(arrayAdapterCondition);
        autoCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Item: " + arrayListCondition.get(position), Toast.LENGTH_SHORT).show();
                selectedCondition = idCondition.get(position).toString();
            }
        });

        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateS = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }
            private void updateCalendar() {
                String Format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
                dateStart.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener dateE = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }
            private void updateCalendar() {
                String Format = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);
                dateEnd.setText(sdf.format(calendar.getTime()));
            }

        };
        dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCoupon.this, dateS, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCoupon.this, dateE, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        arrowReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xét đk cho từng field
                if(cpName.getText().toString().trim().length() == 0  || cpValue.getText().toString().trim().length() == 0
                        || dateStart.getText().toString().length() == 0 || cpCode.getText().toString().length() == 0
                        || autoType.getText().toString().length() == 0 || dateEnd.getText().toString().length() == 0
                        || autoCondition.getText().toString().length() == 0 || cpValueCondition.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "All field must be not empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(autoType.getText().toString().indexOf("%") != -1){
                    if(Integer.parseInt(cpValue.getText().toString())>100 || Integer.parseInt(cpValue.getText().toString()) < 0){
                        Toast.makeText(getApplicationContext(), "Chỉ giảm từ 1 - 100% giá trị", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if(autoType.getText().toString().indexOf("tiền") != -1 || autoType.getText().toString().indexOf("vận") !=-1){
                    if(Integer.parseInt(cpValue.getText().toString()) > Integer.parseInt(cpValueCondition.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Giá trị giảm phải < giá trị tối thiểu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if(Integer.parseInt(cpValueCondition.getText().toString()) < 1000){
                    Toast.makeText(getApplicationContext(), "Đơn hàng tối thiểu từ 1000đ", Toast.LENGTH_SHORT).show();
                    return;
                }
                long compareDate = compareDate(dateStart.getText().toString(),dateEnd.getText().toString());
                if(compareDate <= 0){
                    Toast.makeText(getApplicationContext(), "Date start < Date end", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Coupon> cpList = CouponFragment.couponList;
                String str1 = cpCode.getText().toString();
                for (int i = 0; i < cpList.size(); i++) {
                    String str = cpList.get(i).getCode();
                    if(str.equals(str1)){
                        Toast.makeText(getApplicationContext(),  str1 + ": is already exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //Add dữ liệu vào fbase
                try {
                    Bundle extras = getIntent().getExtras();
                    int size = 0;
                    if(extras!=null){
                        size = extras.getInt("size");
                    }
                    String id = String.valueOf(size);
                    String codeTxt = cpCode.getText().toString().trim();
                    String nameTxt = cpName.getText().toString().trim();
                    String eStartTxt = dateStart.getText().toString().trim();
                    String eEndTxt = dateEnd.getText().toString().trim();
                    String valueTxt = cpValue.getText().toString().trim();
                    String valueConditionTxt = cpValueCondition.getText().toString().trim();
                    String idConditionTxt = selectedCondition.trim();
                    String idTypeTxt = selectedType.trim();
                    addCouponInfo(id, codeTxt,nameTxt,eStartTxt,eEndTxt,valueConditionTxt,valueTxt,idTypeTxt,idConditionTxt);
                    Toast.makeText(getApplicationContext(),"Thêm mã khuyến mãi thành công", Toast.LENGTH_LONG).show();


                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"Error:"+ex.toString(),Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(getApplicationContext(), CouponFragment.class);
                startService(intent);
            }
        });
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
    private void addCouponInfo(String id, String code, String name, String eStart, String eEnd, String valueCondition, String value, String idType, String idCondition){
        addCpRef.child(id).child("Ma_Khuyen_Mai").setValue(code).isSuccessful();
        addCpRef.child(id).child("Ten_Khuyen_Mai").setValue(name);
        addCpRef.child(id).child("Time_Start").setValue(eStart);
        addCpRef.child(id).child("Time_End").setValue(eEnd);
        addCpRef.child(id).child("Gia_Ap_Dung").setValue(valueCondition);
        addCpRef.child(id).child("Gia_Giam").setValue(value);
        addCpRef.child(id).child("ID_Loai_Ap_Dung").setValue(idCondition);
        addCpRef.child(id).child("ID_Loai_Khuyen_Mai").setValue(idType);
    }
    private void matching(){
        dateEnd = findViewById(R.id.addCp_et_DateEnd);
        dateStart = findViewById(R.id.addCp_et_DateStart);
        cpCode = findViewById(R.id.addCp_et_Code);
        cpName = findViewById(R.id.addCp_et_Name);
        cpValue = findViewById(R.id.addCp_et_Value);
        cpValueCondition = findViewById(R.id.addCp_et_ValueCondition);
        arrowReturn = findViewById(R.id.addCp_img_Return);
        addBtn = findViewById(R.id.addCp_btn_Add);
        autoCondition = findViewById(R.id.addCp_tv_Condition);
        autoType = findViewById(R.id.inv_tv_magiamgia_list);
    }
}