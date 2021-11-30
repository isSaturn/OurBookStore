package com.example.androidproject_coupon.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private List<Book> mUploads;
    public CartAdapter(Context context, List<Book> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Book uploadCurrent = mUploads.get(position);
        holder.textViewTenSach.setText(uploadCurrent.getTen_Sach());
        holder.textViewGiaTien.setText("Giá tiền: " + uploadCurrent.getGia() + " vnđ");
        Picasso.with(mContext)
                .load(uploadCurrent.getAnh())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment.cart.remove(uploadCurrent);
                CartFragment.cartAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTenSach, textViewGiaTien;
        public ImageView imageView, delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.tv_cart_tensach);
            textViewGiaTien = itemView.findViewById(R.id.tv_cart_giatien);
            imageView = itemView.findViewById(R.id.img_cart_book);
            delete = itemView.findViewById(R.id.img_cart_delete);
        }
    }
}
