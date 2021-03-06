package com.example.androidproject_coupon;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;

import com.example.androidproject_coupon.CouponManagement.AddCoupon;
import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.CouponManagement.CouponAdapter;
import com.example.androidproject_coupon.CouponManagement.EditCoupon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CouponFragment extends Fragment {

    private View mView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static CouponAdapter couponAdapter;
    public static ArrayList<Coupon> couponList = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_view);
        couponAdapter = new CouponAdapter(getContext(), R.layout.adapter_view_layout_coupon, couponList);

        String TAG="FIREBASE";
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ourbookstore-e8241-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("KhuyenMai");
        myRef.addValueEventListener(new ValueEventListener() {
            // ?????y d??? li???u v??o list
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                couponAdapter.clear();
                couponList.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    String code = data.child("code").getValue().toString();
                    String name = data.child("name").getValue().toString();
                    String value = data.child("value").getValue().toString();
                    String valueCondition = data.child("valueCondition").getValue().toString();
                    String idType = data.child("idType").getValue().toString();
                    String idCondition = data.child("idCondition").getValue().toString();
                    String eStart = data.child("eStart").getValue().toString();
                    String eEnd = data.child("eEnd").getValue().toString();
                    couponList.add(new Coupon(code,name, eStart, eEnd, value,
                            valueCondition,idCondition, idType,R.drawable.coupon_icon));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
        Button add = view.findViewById(R.id.Cp_btn_Add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCoupon.class);
                int size = couponAdapter.getCount() + 1;
                intent.putExtra("size", size);
                startActivity(intent);
            }
        });
        listView.setAdapter(couponAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent ( getContext(), EditCoupon.class);
                intent.putExtra( "code", couponList.get(i).getCode());
                intent.putExtra( "name", couponList.get(i).getName());
                intent.putExtra( "value", couponList.get(i).getValue());
                intent.putExtra( "valueCondition", couponList.get(i).getValueCondition());
                intent.putExtra( "eStart", couponList.get(i).geteStart());
                intent.putExtra( "eEnd", couponList.get(i).geteEnd());
                intent.putExtra( "idType", couponList.get(i).getIdType());
                intent.putExtra( "idCondition", couponList.get(i).getIdCondition());
                startActivity(intent);
            }
        });
    }
}