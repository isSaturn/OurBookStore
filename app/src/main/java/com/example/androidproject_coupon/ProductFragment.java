package com.example.androidproject_coupon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidproject_coupon.BookManagement.AdapterBook;
import com.example.androidproject_coupon.BookManagement.AddBook;
import com.example.androidproject_coupon.BookManagement.Book;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //bát sự kiện nút Thêm Sách
        Button themsach = view.findViewById(R.id.sach_btn_Them);
        themsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddBook.class));
            }
        });
        //hiển thị danh sách sản phẩm
        ListView recyclerViewBook = view.findViewById(R.id.sach_rv_Sach);
        ArrayList<Book> arrayList;
        AdapterBook adapterBook;

        arrayList = new ArrayList<>();
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));
        arrayList.add(new Book("Nguyen Minh Phu",R.drawable.anhsach));

        adapterBook = new AdapterBook(getContext(),R.layout.sach,arrayList);
        recyclerViewBook.setAdapter(adapterBook);
    }
}