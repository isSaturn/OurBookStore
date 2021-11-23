package com.example.androidproject_coupon.BookManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<Book> mUploads;

    public BookAdapter(Context context, List<Book> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sach, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book uploadCurrent = mUploads.get(position);
        holder.textViewTenSach.setText(uploadCurrent.getTen_Sach());
        holder.textViewGiaTien.setText(uploadCurrent.getGia());
        Picasso.with(mContext)
                .load(uploadCurrent.getAnh())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTenSach, textViewGiaTien;
        public ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTenSach = itemView.findViewById(R.id.ten_sach);
            textViewGiaTien = itemView.findViewById(R.id.gia_tien);
            imageView = itemView.findViewById(R.id.img_Sach);
        }
    }

}
