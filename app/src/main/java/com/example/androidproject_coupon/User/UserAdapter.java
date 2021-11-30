package com.example.androidproject_coupon.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.BookManagement.EditAndDeleteBook;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<Book> mUploads;

    public UserAdapter(Context context, List<Book> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_row, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
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
        Intent intent = new Intent(mContext, ItemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail_sach", uploadCurrent);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout layout;
        public TextView textViewTenSach, textViewGiaTien;
        public ImageView imageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.tv_shop_tensach);
            textViewGiaTien = itemView.findViewById(R.id.tv_shop_giasach);
            imageView = itemView.findViewById(R.id.img_shop_sach);
            layout = itemView.findViewById(R.id.layout_item_row);
        }
    }

}
