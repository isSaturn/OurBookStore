package com.example.dathangbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private List<Book> mBooks;

    public BookAdapter (Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<Book> list){
        this.mBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_recycler_item,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mBooks.get(position);
        if(book == null){
            return;
        }
        holder.imgSach.setImageResource(book.getResourceId());
        holder.tvTensach.setText(book.getTitle());
        holder.tvSoluongsach.setText(book.getTitle());
        holder.tvGiasach.setText(book.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mBooks !=null){
            return mBooks.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSach;
        private TextView tvTensach;
        private TextView tvSoluongsach;
        private TextView tvGiasach;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSach = itemView.findViewById(R.id.ord_iv_sach);
            tvTensach = itemView.findViewById(R.id.ord_tv_chitietdonhang_tensach);
            tvSoluongsach = itemView.findViewById(R.id.ord_tv_chitietdonhang_sl);
            tvGiasach = itemView.findViewById(R.id.ord_tv_chitietdonhang_gia);
        }
    }
}

