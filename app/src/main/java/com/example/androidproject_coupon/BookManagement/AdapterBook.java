package com.example.androidproject_coupon.BookManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject_coupon.R;

import java.util.List;

public class AdapterBook extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Book> arraylist;

    public AdapterBook(Context context, int layout, List<Book> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        Book book = arraylist.get(position);

        TextView ten = convertView.findViewById(R.id.ten_sach);
        ImageView imageView = convertView.findViewById(R.id.img_Sach);

        ten.setText(book.getTensach());
        imageView.setImageResource(book.getHinh());

        return convertView;
    }
}
