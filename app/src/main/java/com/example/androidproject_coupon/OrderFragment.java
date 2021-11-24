package com.example.androidproject_coupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.CouponManagement.CouponAdapter;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.OrderManagement.Oder_adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    ListView listView;
    ArrayList<Oder> dataList = new ArrayList<>();
    Oder_adapter oder_adapter;
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

      listView = view.findViewById(R.id.lv_status_oder);
//        dataList.add(new Oder("04/11/2021 14:00", "211104SACH0001", "Cho xac nhan", "85.000d", "69/68 Dang Thuy Tram"));
//        oder_adapter = new Oder_adapter(getContext(),R.layout.item_oder, dataList);
//        listView.setAdapter(oder_adapter);



        ListView listView = view.findViewById(R.id.list_view);
        String TAG="FIREBASE";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DonHang");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data: snapshot.getChildren()){
                    String time = data.child("Time").getValue().toString();
                    String code = data.child("Ma_Don_Hang").getValue().toString();
                    String status = data.child("ID_Trang_Thai_DH").getValue().toString();
                    String price = data.child("Tong_Tien").getValue().toString();
                    String address = data.child("Dia_Chi").getValue().toString();
                    String name = data.child("Ho_Ten").getValue().toString();
                    String phone = data.child("SDT").getValue().toString();
                    String hinhthuc = data.child("ID_Hinh_Thuc_GH").getValue().toString();
                    String khuyenmai = data.child("ID_Khuyen_Mai").getValue().toString();
                    String taikhoan = data.child("ID_Tai_Khoan").getValue().toString();

                    dataList.add(new Oder(address,time, code, status, price));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Oder_adapter oder_adapter  = new Oder_adapter(getContext(), R.layout.item_oder, dataList);
        listView.setAdapter(oder_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent ( getContext(), EditCoupon.class);
                intent.putExtra( "code", dataList.get(i).getCode());
                intent.putExtra( "time", dataList.get(i).getTime());
                intent.putExtra( "status", dataList.get(i).getStatus());
                intent.putExtra( "price", dataList.get(i).getPrice().toString());
                intent.putExtra( "address", dataList.get(i).getAddress());
                startActivity(intent);
            }
        });
        }
    }
