package com.example.androidproject_coupon.CouponManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.R;

import java.util.ArrayList;

public class CouponAdapter extends ArrayAdapter<Coupon> {
    private Context mContext;
    private int mResource;
    public CouponAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Coupon> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource,parent,false);
        ImageView imageView = convertView.findViewById(R.id.img_image);
        TextView txtName = convertView.findViewById(R.id.Cp_tv_Name);
        TextView txtValue = convertView.findViewById(R.id.Cp_tv_Value);
        imageView.setImageResource(getItem(position).getImgUrl());
        txtName.setText(getItem(position).getName());
        txtValue.setText("Giá trị: "+getItem(position).getValue().toString());
        return convertView;
    }
}
