package com.example.androidproject_coupon.InvoiceManagement.Invoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private Context mContext;
    private List<Oder> mUploads;
    public InvoiceAdapter(Context context, List<Oder> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_oder, parent, false);

        return new InvoiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Oder uploadCurrent = mUploads.get(position);
        holder.layoutInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToEdit(uploadCurrent);

            }
        });
        holder.time.setText("Thời gian đặt hàng: "+uploadCurrent.getTime());
        holder.code.setText("Mã đơn hàng: "+uploadCurrent.getMa_Don_Hang());
        DatabaseReference trangThaiDonHang = FirebaseDatabase.getInstance().getReference("TrangThaiDonHang");
        trangThaiDonHang.child(uploadCurrent.getID_Trang_Thai_DH()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                holder.status.setText(hashMap.get("Trang_Thai").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.price.setText("Tổng tiền: "+uploadCurrent.getTong_Tien());
        holder.address.setText("Địa chỉ: "+uploadCurrent.getDia_Chi());
    }

    private void onClickGoToEdit(Oder uploadCurrent) {
        Intent intent = new Intent(mContext, ViewInvoice.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_invoice", uploadCurrent);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layoutInvoice;
        public TextView time, code, status, price, address;
        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutInvoice = itemView.findViewById(R.id.rl_oder);
            time = itemView.findViewById(R.id.tv_oder_time);
            code = itemView.findViewById(R.id.tv_oder_code);
            status = itemView.findViewById(R.id.tv_oder_status);
            price = itemView.findViewById(R.id.tv_oder_price);
            address = itemView.findViewById(R.id.tv_oder_address);
        }
    }
}
