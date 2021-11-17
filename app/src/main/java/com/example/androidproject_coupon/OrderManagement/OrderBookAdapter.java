package com.example.androidproject_coupon.OrderManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

import java.util.List;

public class OrderBookAdapter extends RecyclerView.Adapter<OrderBookAdapter.BookViewHolder> {

    private Context mContext;
    private List<OrderBook> mOrderBooks;

    public OrderBookAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<com.example.androidproject_coupon.OrderManagement.OrderBook> list){
        this.mOrderBooks = list;
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
        OrderBook orderBook = mOrderBooks.get(position);
        if(orderBook == null){
            return;
        }
        holder.imgSach.setImageResource(orderBook.getResourceId());
        holder.tvTensach.setText(orderBook.getTitle());
        holder.tvSoluongsach.setText(orderBook.getTitle());
        holder.tvGiasach.setText(orderBook.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mOrderBooks !=null){
            return mOrderBooks.size();
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
