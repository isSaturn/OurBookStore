package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.BookViewHolder> {

    private Context mContext;
    private List<Oder> mInvoiceBooks;

    public InvoiceAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<Oder> list){
        this.mInvoiceBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        if(mInvoiceBooks !=null){
            return mInvoiceBooks.size();
        }
        return 0;
    }


    public class BookViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSach,tvXoa;
        private TextView tvTensach;
        private TextView tvGiasach;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSach = itemView.findViewById(R.id.img_cart_book);
            tvTensach = itemView.findViewById(R.id.tv_cart_tensach);
            tvGiasach = itemView.findViewById(R.id.tv_cart_giatien);
            tvXoa = itemView.findViewById(R.id.img_cart_delete);
        }
    }
}
