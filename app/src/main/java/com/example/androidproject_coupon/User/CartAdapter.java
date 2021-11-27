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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDetail(uploadCurrent);
            }
        });
    }

    private void onClickDetail(Book uploadCurrent) {
        Intent intent = new Intent(mContext, CartFragment.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_sach", uploadCurrent);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private Button layout;
        public TextView textViewTenSach, textViewGiaTien, masach, tacgia, theloai, mota;
        public ImageView imageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.tv_itemdetail_name);
            textViewGiaTien = itemView.findViewById(R.id.tv_itemdetail_price);
            imageView = itemView.findViewById(R.id.img_itemdetail_book);
//            masach = itemView.findViewById(R.id.tv_itemdetail_masach);
//            tacgia = itemView.findViewById(R.id.tv_itemdetail_tacgia);
//            theloai = itemView.findViewById(R.id.tv_itemdetail_theloai);
//            mota = itemView.findViewById(R.id.tv_itemdetail_mota);
            layout = itemView.findViewById(R.id.btn_itemdetail_add);
        }
    }
}
