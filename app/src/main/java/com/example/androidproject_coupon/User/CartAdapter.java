package com.example.androidproject_coupon.User;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.AddInvoice;
import com.example.androidproject_coupon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private List<Book> mUploads;
    int total = 0;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                CartFragment.tien -= Integer.parseUnsignedInt(uploadCurrent.getGia());
                CartFragment.tongtien.setText(String.valueOf(CartFragment.tien) + " VNĐ");
                CartFragment.cart.remove(uploadCurrent);
                CartFragment.cartAdapter.notifyDataSetChanged();
//                AddInvoice.invAdapter.notifyDataSetChanged();
                if (CartFragment.cart.size() == 0){
                    CartFragment.tvEmptyCart.setVisibility(View.VISIBLE);
                    CartFragment.imgEmptyCart.setVisibility(View.VISIBLE);
                }
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

        public TextView textViewTenSach, textViewGiaTien, tongtien;
        public ImageView imageView, delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.tv_cart_tensach);
            textViewGiaTien = itemView.findViewById(R.id.tv_cart_giatien);
            imageView = itemView.findViewById(R.id.img_cart_book);
            delete = itemView.findViewById(R.id.img_cart_delete);
            tongtien = itemView.findViewById(R.id.tv_cart_tongtien);

        }
    }
}
