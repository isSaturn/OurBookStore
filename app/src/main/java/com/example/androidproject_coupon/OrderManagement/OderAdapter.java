package com.example.androidproject_coupon.OrderManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.InvoiceManagement.Invoice.ViewInvoice;
import com.example.androidproject_coupon.R;
import com.example.androidproject_coupon.activity_oderdetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.OderViewHolder> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Context mContext;
    private List<Oder> mUploads;

    public OderAdapter(Context context, int item_oder, List<Oder> uploads) {
        mContext = context;
        mUploads = uploads;
    }


    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_oder, parent, false);

        return new OderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OderViewHolder holder, int position) {
        Oder oder = mUploads.get(position);
        holder.layoutOder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickGoToEdit(oder);

            }
        });


        holder.time.setText(oder.getTime());
        holder.code.setText(oder.getMa_Don_Hang());
        DatabaseReference trangThaiDonHang = database.getReference("TrangThaiDonHang");
        trangThaiDonHang.child(oder.getID_Trang_Thai_DH()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                holder.status.setText(hashMap.get("Trang_Thai").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.price.setText(oder.getTong_Tien());
        holder.address.setText(oder.getDia_Chi());

    }

    private void onClickGoToEdit(Oder oder) {
        Intent intent = new Intent(mContext, activity_oderdetails.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_oder", oder);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {

        return mUploads.size();
    }

    public class OderViewHolder extends RecyclerView.ViewHolder {
        public TextView time, code, status, price, address;
        public RelativeLayout layoutOder;

        public OderViewHolder (@NonNull View itemView) {
            super(itemView);
            layoutOder = itemView.findViewById(R.id.rl_oder);
            time = itemView.findViewById(R.id.tv_oder_time);
            code = itemView.findViewById(R.id.tv_oder_code);
            status = itemView.findViewById(R.id.tv_oder_status);
            price = itemView.findViewById(R.id.tv_oder_price);
            address = itemView.findViewById(R.id.tv_oder_address);
        }
    }
}
//gg