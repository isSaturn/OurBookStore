package com.example.androidproject_coupon.CouponManagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.BookCategory;
import com.example.androidproject_coupon.CouponManagement.CpCondition.DatabaseHelper_CpCondition;
import com.example.androidproject_coupon.CouponManagement.CpType.DatabaseHelper_CpType;
import com.example.androidproject_coupon.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddCoupon extends AppCompatActivity {
    AutoCompleteTextView autoType, autoCondition;
    ArrayAdapter<String> arrayAdapterType, arrayAdapterCondition;
    EditText dateStart, dateEnd, cpCode, cpName, cpValue, cpMinimum ;
    TextView cpType, cpCondition;
    Calendar calendar;
    ImageView arrowReturn;
    ArrayList<String> arrayListType = new ArrayList<>();
    ArrayList<String> arrayListCondition = new ArrayList<>();
    ArrayList<Integer> idType = new ArrayList<Integer>();
    ArrayList<Integer> idCondition = new ArrayList<Integer>();
    DatabaseHelper_CpType mDBHELPERTYPE;
    DatabaseHelper_CpCondition mDBHELPERCONDITION;
    File dbType, dbCondition;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);
        matching();

        mDBHELPERTYPE = new DatabaseHelper_CpType(this);
        try {
            mDBHELPERTYPE.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }
        Cursor contro = mDBHELPERTYPE.getCpTypes();
        contro.moveToFirst();
        do {
            idType.add(Integer.parseUnsignedInt(contro.getString(0)));
            arrayListType.add(contro.getString(1));
        }while (contro.moveToNext());

        arrayAdapterType = new ArrayAdapter(this, R.layout.list_type_coupon,arrayListType);
        autoType = findViewById(R.id.addCp_tv_Type);
        autoType.setAdapter(arrayAdapterType);
        autoType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: " + idType.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // AutoTextVIew type
//        mDBHELPERTYPE = new DatabaseHelper_CpType(this, "database");
//        dbType = getApplicationContext().getDatabasePath(DatabaseHelper_CpType.DBNAME);
//        if (dbType.exists()==false){
//            mDBHELPERTYPE.getReadableDatabase();
//            if(!copydatabase(this)){
//                return;
//            }
//        }
//        arrayListType = mDBHELPERTYPE.GetCouponTypes();
//        arrayAdapterType = new ArrayAdapter<>(this,R.layout.list_type_coupon,arrayListType);
//        arrayAdapterType.notifyDataSetChanged();
//        autoType = findViewById(R.id.addCp_tv_Type);
//        autoType.setAdapter(arrayAdapterType);
//
//
////        String []items = {"Giảm theo số tiền", "Giảm theo %", "Miễn phí vận chuyển"};
////        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_type_coupon,items);
//
//        autoType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),"Item: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // AutoTextVIew condition
//        mDBHELPERCONDITION = new DatabaseHelper_CpCondition(this, "database");
//        dbCondition = getApplicationContext().getDatabasePath(DatabaseHelper_CpCondition.DBNAME);
//        if (dbCondition.exists()==false){
//            mDBHELPERTYPE.getReadableDatabase();
//            if(!copydatabase(this)){
//                return;
//            }
//        }
//        arrayListCondition = mDBHELPERCONDITION.GetCouponConditions();
//        arrayAdapterCondition = new ArrayAdapter<>(this,R.layout.list_type_coupon,arrayListCondition);
//        arrayAdapterCondition.notifyDataSetChanged();
//        autoCondition = findViewById(R.id.addCp_tv_Condition);
//        autoCondition.setAdapter(arrayAdapterCondition);


//        String []items = {"Giảm theo số tiền", "Giảm theo %", "Miễn phí vận chuyển"};
//        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_type_coupon,items);

//        autoCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(),"Item: " + item, Toast.LENGTH_SHORT).show();
//            }
//        });

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
    }
    public Boolean copydatabase(Context context){
        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper_CpType.DBNAME);
            String OutFileName = DatabaseHelper_CpType.DBLOCATION + DatabaseHelper_CpType.DBNAME;
            File file = new File(OutFileName);
            file.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(OutFileName);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (IOException e){
            return false;
        }
    }



    private void matching(){
        dateEnd = findViewById(R.id.addCp_et_DateEnd);
        dateStart = findViewById(R.id.addCp_et_DateStart);
        cpCode = findViewById(R.id.addCp_et_Code);
        cpName = findViewById(R.id.addCp_et_Name);
        cpValue = findViewById(R.id.addCp_et_Value);
        cpCondition = findViewById(R.id.addCp_tv_Condition);
        cpMinimum = findViewById(R.id.addCp_et_Minimum);
        cpType = findViewById(R.id.addCp_tv_Type);
        arrowReturn = findViewById(R.id.addCp_img_Return);
    }
}