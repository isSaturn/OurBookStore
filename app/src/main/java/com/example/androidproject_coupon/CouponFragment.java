package com.example.androidproject_coupon;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidproject_coupon.CouponManagement.AddCoupon;
import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.CouponManagement.Coupon.DatabaseHelper_Cp;
import com.example.androidproject_coupon.CouponManagement.CouponAdapter;
import com.example.androidproject_coupon.CouponManagement.CpCondition.DatabaseHelper_CpCondition;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponFragment extends Fragment {

    private View mView;
    public Integer countItem = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CouponFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponFragment newInstance(String param1, String param2) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_coupon, container, false);
        // Inflate the layout for this fragment

        return mView;
    }
    private void matching(){
//        add = find;
//        listAdapter = findViewById(R.layout.adapter_view_layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button add = view.findViewById(R.id.Cp_btn_Add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCoupon.class);
                startActivity(intent);
            }
        });

        DatabaseHelper_Cp mDBHELPERCOUPON = new DatabaseHelper_Cp(getContext());
        try {
            mDBHELPERCOUPON.createDataBase();
            Log.d("Thanh cong", "Da tao duoc db");
        }catch (IOException e){
            Log.d("Bi loi roi", "khong tao duoc db");
        }
        Cursor cursor = mDBHELPERCOUPON.getCps();
        cursor.moveToFirst();
//
        ListView listView = view.findViewById(R.id.list_view);
        ArrayList<Coupon> couponList = new ArrayList<>();
        do {
            couponList.add(new Coupon(Integer.parseUnsignedInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2)
                    , cursor.getString(3),cursor.getString(4), Integer.parseUnsignedInt(cursor.getString(5))
                    , Integer.parseUnsignedInt(cursor.getString(6)), Integer.parseUnsignedInt(cursor.getString(7))
                    , Integer.parseUnsignedInt(cursor.getString(8)), R.drawable.coupon_icon));
            countItem += 1;
        }while (cursor.moveToNext());
        CouponAdapter couponAdapter = new CouponAdapter(getContext(), R.layout.adapter_view_layout_coupon, couponList);
        listView.setAdapter(couponAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent ( getContext(), EditCoupon.class);
                intent.putExtra("id", couponList.get(i).getId());
                intent.putExtra( "code", couponList.get(i).getCode());
                intent.putExtra( "name", couponList.get(i).getName());
                intent.putExtra( "value", couponList.get(i).getValue().toString());
                intent.putExtra( "valueCondition", couponList.get(i).getValueCondition().toString());
                intent.putExtra( "eStart", couponList.get(i).geteStart());
                intent.putExtra( "eEnd", couponList.get(i).geteEnd());
                intent.putExtra( "idType", couponList.get(i).getIdType());
                intent.putExtra( "idCondition", couponList.get(i).getIdCondition());
                startActivity(intent);
            }
        });
    }
}