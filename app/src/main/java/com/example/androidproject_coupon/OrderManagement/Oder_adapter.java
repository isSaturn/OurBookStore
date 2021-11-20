package com.example.androidproject_coupon.OrderManagement;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidproject_coupon.CouponManagement.Coupon.Coupon;
import com.example.androidproject_coupon.OrderFragment;
import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

public class Oder_adapter extends ArrayAdapter<Oder> {
    private Context mContext;
    private int mResource;
    public Oder_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Oder> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource,parent,false);
        TextView time = convertView.findViewById(R.id.tv_oder_time);
        TextView code = convertView.findViewById(R.id.tv_oder_code);
        TextView status = convertView.findViewById(R.id.tv_oder_status);
        TextView price = convertView.findViewById(R.id.tv_oder_price);
        TextView address = convertView.findViewById(R.id.tv_oder_address);


        time.setText(getItem(position).getTime());
        code.setText(getItem(position).getCode());
        status.setText(getItem(position).getStatus());
        price.setText(getItem(position).getPrice());
        address.setText(getItem(position).getAddress());
        return convertView;
    }
}
