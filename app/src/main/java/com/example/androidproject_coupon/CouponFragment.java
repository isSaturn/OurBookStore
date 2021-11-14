package com.example.androidproject_coupon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
        ListView listView = view.findViewById(R.id.list_view);
        ArrayList<Coupon> couponList = new ArrayList<>();
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        couponList.add(new Coupon("NGVN", 120000, R.drawable.tag_2));
        CouponAdapter couponAdapter = new CouponAdapter(getContext(), R.layout.adapter_view_layout_coupon, couponList);
        listView.setAdapter(couponAdapter);
//        recyclerView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent ( getContext(), EditCoupon.class);
//                intent.putExtra( "KEY", key);
                startActivity(intent);
            }
        });
    }
}