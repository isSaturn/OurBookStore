package com.example.androidproject_coupon.CouponManagement.CpType;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidproject_coupon.CouponManagement.CouponApplication;
import com.example.androidproject_coupon.R;

import java.util.ArrayList;

public class CouponTypeAdapter extends ArrayAdapter<CouponType> {
    public CouponTypeAdapter(ArrayList<CouponType> array){
        super(CouponApplication.context, R.layout.list_type_coupon,array);
    }
    public static class ViewHolder{
        public TextView txtCpType;

        public ViewHolder(View view){
            txtCpType = view.findViewById(R.id.addCp_tv_TypeTxt);
        }

        public void fill(final ArrayAdapter<CouponType> adapter , final CouponType item, final int position){
            txtCpType.setText(item.getType());
            txtCpType.setTextSize(20);
        }

    }
    public View getView(int position, View convertview, ViewGroup parent){
        ViewHolder holder;
        CouponType item = getItem(position);
        if(convertview == null){
            convertview = CouponApplication.inflater.inflate(R.layout.list_type_coupon,parent,false);
            holder = new ViewHolder(convertview);
            convertview.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertview.getTag();
        }
        holder.fill(this, item,position);
        return convertview;
    }
}
