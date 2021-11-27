package com.example.androidproject_coupon.User;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.InvoiceInformation;
import com.example.androidproject_coupon.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private List<Book> mUploads;

    public CartAdapter(Context context, List<Book> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    public CartAdapter() {

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Intent intent = new Intent(mContext, InvoiceInformation.class);

        holder.btnInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private Button btnInv;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            btnInv = itemView.findViewById(R.id.btn_cart_dathang);

        }
    }
}
