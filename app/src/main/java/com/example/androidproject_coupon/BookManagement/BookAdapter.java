package com.example.androidproject_coupon.BookManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<Book> mBooks;

    public BookAdapter(Context context, List<Book> books) {
        mContext = context;
        mBooks = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sach, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book bookCurrent = mBooks.get(position);
        holder.textViewTenSach.setText(bookCurrent.getTen_Sach());
        holder.textViewGiaTien.setText("Giá tiền: " + bookCurrent.getGia() + " Vnđ");
        Picasso.with(mContext)
                .load(bookCurrent.getAnh())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.layoutSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToEdit(bookCurrent);
            }
        });
    }

    private void onClickGoToEdit(Book bookCurrent) {
        Intent intent = new Intent(mContext, EditAndDeleteBook.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_sach", bookCurrent);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutSach;
        public TextView textViewTenSach, textViewGiaTien;
        public ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.ten_sach);
            textViewGiaTien = itemView.findViewById(R.id.gia_tien);
            imageView = itemView.findViewById(R.id.img_Sach);
            layoutSach = itemView.findViewById(R.id.layout_Sach);
        }
    }

}
