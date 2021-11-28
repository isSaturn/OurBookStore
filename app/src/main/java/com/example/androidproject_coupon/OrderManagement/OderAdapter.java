package com.example.androidproject_coupon.OrderManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject_coupon.R;

import java.util.List;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.OderViewHolder> {

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
        holder.time.setText(oder.getTime());
        holder.code.setText(oder.getMa_Don_Hang());
        holder.status.setText(oder.getID_Trang_Thai_DH());
        holder.price.setText(oder.getTong_Tien());
        holder.address.setText(oder.getDia_Chi());

    }

    @Override
    public int getItemCount() {

        return mUploads.size();
    }

    public class OderViewHolder extends RecyclerView.ViewHolder {
        public TextView time, code, status, price, address;
        public OderViewHolder (@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_oder_time);
            code = itemView.findViewById(R.id.tv_oder_code);
            status = itemView.findViewById(R.id.tv_oder_status);
            price = itemView.findViewById(R.id.tv_oder_price);
            address = itemView.findViewById(R.id.tv_oder_address);
        }
    }
}
