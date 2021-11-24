package com.example.androidproject_coupon.CouponManagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditCoupon extends AppCompatActivity {
    AutoCompleteTextView autoType, autoCondition;
    ArrayAdapter<String> arrayAdapterType, arrayAdapterCondition;
    EditText dateStart, dateEnd, cpCode,cpName, cpValue, cpValueCondition ;
    TextView cpType, cpCondition;
    Calendar calendar;
    ImageView arrowReturn;
    ArrayList<String> arrayListType = new ArrayList<>();
    ArrayList<String> arrayListCondition = new ArrayList<>();
    ArrayList<Integer> idType = new ArrayList<>();
    ArrayList<Integer> idCondition = new ArrayList<>();
    Button edit, delete;
    Integer selectedType, selectedCondition;
    String TAG="FIREBASE";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coupon);

        matching();
        Intent intent = getIntent();
        String cpId = intent.getStringExtra("id");
        String cpCodeInput = intent.getStringExtra("code");
        String cpNameInput= intent.getStringExtra( "name");
        String cpValueInput = intent.getStringExtra( "value");
        String cpValueConditionInput = intent.getStringExtra( "valueCondition");
        String cpEStartInput = intent.getStringExtra( "eStart");
        String cpEEndInput = intent.getStringExtra( "eEnd");
        Integer cpIdTypeInput = intent.getIntExtra( "idType",123);
        Integer cpIdConditionInput = intent.getIntExtra("idCondition", 2);
        cpCode.setText(cpCodeInput);
        cpValue.setText(cpValueInput);
        cpValueCondition.setText(cpValueConditionInput);
        cpName.setText(cpNameInput);
        dateStart.setText(cpEStartInput);
        dateEnd.setText(cpEEndInput);
        selectedType = cpIdTypeInput;
        selectedCondition = cpIdConditionInput;
        //Loai khuyen mai
        arrayListType.add("Giảm theo số tiền");
        arrayListType.add("Giảm theo số %");
        arrayListType.add("Miễn phí vận chuyển");
        idType.add(1);
        idType.add(2);
        idType.add(3);
        arrayAdapterType = new ArrayAdapter<>(this, R.layout.list_type_coupon,arrayListType);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
//        DatabaseReference myRefType = database.getReference("LoaiKhuyenMai");
//
//        myRefType.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data: snapshot.getChildren()){
//                    String value = data.child("Loai_Khuyen_Mai").getValue().toString();
//                    arrayListType.add(value);
//                    idType.add(Integer.parseInt(data.getKey()));
//
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        arrayAdapterType = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListType);
        autoType = findViewById(R.id.editCp_tv_Type);
        switch (cpIdTypeInput){
            case 1: autoType.setText(arrayListType.get(0));break;
            case 2: autoType.setText(arrayListType.get(1));break;
            case 3: {
                autoType.setText(arrayListType.get(2));
                cpValue.setEnabled(false);
            }break;
        }
        autoType.setAdapter(arrayAdapterType);
        autoType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedType = idType.get(position);
                Toast.makeText(getApplicationContext(),"Item: " + selectedType, Toast.LENGTH_SHORT).show();

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
        arrayListCondition.add("Giá trị đơn hàng từ");
        idCondition.add(1);
        arrayAdapterCondition = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListCondition);
//        DatabaseReference myRefCondition = database.getReference("LoaiApDung");
//        myRefCondition.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data: snapshot.getChildren()){
//                    String value = data.child("Loai_Ap_Dung").getValue().toString();
//                    arrayListCondition.add(value);
//                    idCondition.add(Integer.parseInt(data.getKey()));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        arrayAdapterCondition = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListCondition);
        autoCondition = findViewById(R.id.editCp_tv_Condition);
        if(cpIdConditionInput == 1){
            autoCondition.setText(arrayListCondition.get(0));
        }
        autoCondition.setAdapter(arrayAdapterCondition);

        autoCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCondition = idCondition.get(position);
                Toast.makeText(getApplicationContext(),"Item: " + selectedCondition, Toast.LENGTH_SHORT).show();
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
                new DatePickerDialog(EditCoupon.this, dateS, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditCoupon.this, dateE, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        arrowReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(cpId);
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent2 = getIntent();
                DatabaseReference editCpRef = database.getReference("KhuyenMai");
                String id = intent2.getStringExtra("id");
                String codeTxt = cpCode.getText().toString();
                String nameTxt = cpName.getText().toString();
                String eStart = dateStart.getText().toString();
                String eEnd = dateEnd.getText().toString();
                Integer value = Integer.parseUnsignedInt(cpValue.getText().toString());
                Integer valueCondition = Integer.parseUnsignedInt(cpValueCondition.getText().toString());
                Integer idCondition = selectedCondition;
                Integer idType = selectedType;
                editCpRef.child(id).child("Ma_Khuyen_Mai").setValue(codeTxt);
                editCpRef.child(id).child("Ten_Khuyen_Mai").setValue(nameTxt);
                editCpRef.child(id).child("Time_Start").setValue(eStart);
                editCpRef.child(id).child("Time_End").setValue(eEnd);
                editCpRef.child(id).child("Gia_Ap_Dung").setValue(valueCondition);
                editCpRef.child(id).child("Gia_Giam").setValue(value);
                editCpRef.child(id).child("ID_Loai_Ap_Dung").setValue(idCondition);
                editCpRef.child(id).child("ID_Loai_Khuyen_Mai").setValue(idType);
                finish();
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
    private void showDialog(String cpId){
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.dialog_delete_coupon);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.ic_corner);
        Button btnYes, btnNo;
        btnYes = dialog.findViewById(R.id.Cp_btn_Yes);
        btnNo = dialog.findViewById(R.id.Cp_btn_No);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
                DatabaseReference myRefCp = database.getReference("KhuyenMai");
                myRefCp.child(cpId).removeValue();
                finish();
            }
        });
        dialog.show();
    }
    private void matching(){
        dateEnd = findViewById(R.id.editCp_et_DateEnd);
        dateStart = findViewById(R.id.editCp_et_DateStart);
        cpCode = findViewById(R.id.editCp_et_Code);
        cpCode.setEnabled(false);
        cpName = findViewById(R.id.editCp_et_Name);
        cpValue = findViewById(R.id.editCp_et_Value);
        cpCondition = findViewById(R.id.editCp_tv_Condition);
        cpValueCondition = findViewById(R.id.editCp_et_ValueCondition);
        cpType = findViewById(R.id.editCp_tv_Type);
        arrowReturn = findViewById(R.id.editCp_img_Return);
        edit = findViewById(R.id.editCp_btn_Edit);
        delete = findViewById(R.id.editCp_btn_Delete);

    }
}