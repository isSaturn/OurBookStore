package com.example.androidproject_coupon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.CouponManagement.CpCondition.DatabaseHelper_CpCondition;
import com.example.androidproject_coupon.CouponManagement.CpType.DatabaseHelper_CpType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditCoupon extends AppCompatActivity {
    AutoCompleteTextView autoType, autoCondition;
    ArrayAdapter<String> arrayAdapterType, arrayAdapterCondition;
    EditText dateStart, dateEnd, cpCode, cpName, cpValue, cpMinimum ;
    TextView cpType, cpCondition;
    Calendar calendar;
    ImageView arrowReturn;
    ArrayList<String> arrayListType = new ArrayList<>();
    ArrayList<String> arrayListCondition = new ArrayList<>();
    ArrayList<Integer> idType = new ArrayList<>();
    ArrayList<Integer> idCondition = new ArrayList<>();
    DatabaseHelper_CpType mDBHELPERTYPE;
    DatabaseHelper_CpCondition mDBHELPERCONDITION;
    Cursor cursorType, cursorCondition;
    Button edit, delete;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_coupon);

        matching();
        //Loai khuyen mai
        mDBHELPERTYPE = new DatabaseHelper_CpType(this);
        try {
            mDBHELPERTYPE.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }
        cursorType = mDBHELPERTYPE.getCpTypes();
        cursorType.moveToFirst();
        do {
            idType.add(Integer.parseUnsignedInt(cursorType.getString(0)));
            arrayListType.add(cursorType.getString(1));
        }while (cursorType.moveToNext());

        arrayAdapterType = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListType);
        autoType = findViewById(R.id.editCp_tv_Type);
        autoType.setAdapter(arrayAdapterType);
        autoType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: " + idType.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Loai ap dung
        mDBHELPERCONDITION = new DatabaseHelper_CpCondition(this);
        try {
            mDBHELPERTYPE.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }
        cursorCondition = mDBHELPERCONDITION.getCpConditions();
        cursorCondition.moveToFirst();
        do {
            idCondition.add(Integer.parseUnsignedInt(cursorCondition.getString(0)));
            arrayListCondition.add(cursorCondition.getString(1));
        }while (cursorCondition.moveToNext());

        arrayAdapterCondition = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListCondition);
        autoCondition = findViewById(R.id.editCp_tv_Condition);
        autoCondition.setAdapter(arrayAdapterCondition);
        autoCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: " + idCondition.get(position).toString(), Toast.LENGTH_SHORT).show();
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
                String Format = "MM/dd/yy";
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
                String Format = "MM/dd/yy";
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
                showDialog();
            }
        });
    }
    private void showDialog(){
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
        cpMinimum = findViewById(R.id.editCp_et_Minimum);
        cpType = findViewById(R.id.editCp_tv_Type);
        arrowReturn = findViewById(R.id.editCp_img_Return);
        edit = findViewById(R.id.editCp_btn_Edit);
        delete = findViewById(R.id.editCp_btn_Delete);

    }
}