package com.example.androidproject_coupon.InvoiceManagement;

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

public class InvoiceBookAdapter extends RecyclerView.Adapter<InvoiceBookAdapter.BookViewHolder> {

    private Context mContext;
    private List<InvoiceBook> mInvoiceBooks;

    public InvoiceBookAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<InvoiceBook> list){
        this.mInvoiceBooks = list;
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
        InvoiceBook invoiceBook = mInvoiceBooks.get(position);
        if(invoiceBook == null){
            return;
        }
        holder.imgSach.setImageResource(invoiceBook.getResourceId());
        holder.tvTensach.setText(invoiceBook.getTitle());
        holder.tvSoluongsach.setText(invoiceBook.getTitle());
        holder.tvGiasach.setText(invoiceBook.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mInvoiceBooks !=null){
            return mInvoiceBooks.size();
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
            tvTensach = itemView.findViewById(R.id.inv_tv_chitietdonhang_tensach);
            tvSoluongsach = itemView.findViewById(R.id.inv_tv_chitietdonhang_sl);
            tvGiasach = itemView.findViewById(R.id.inv_tv_chitietdonhang_gia);
        }
    }
}
