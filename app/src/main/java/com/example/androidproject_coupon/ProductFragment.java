package com.example.androidproject_coupon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.AddBook;
import com.example.androidproject_coupon.BookManagement.BookAdapter;
import com.example.androidproject_coupon.BookManagement.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;

    private DatabaseReference mDatabaseReference;
    private List<Upload> mUploads;

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
                Intent intent = new Intent(getContext(), AddBook.class);
                int size = mAdapter.getItemCount();
                intent.putExtra("size", size);
                startActivity(intent);
            }
        });
        //hiển thị danh sách sản phẩm
        mRecyclerView = view.findViewById(R.id.sach_rv_Sach);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mUploads = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Sach");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot posSnapshot : dataSnapshot.getChildren()) {
                    String sID = posSnapshot.child("id").getValue().toString().trim();
                    String sMaSach = posSnapshot.child("ma_Sach").getValue().toString().trim();
                    String sTenSach = posSnapshot.child("ten_Sach").getValue().toString().trim();
                    String sTacGia = posSnapshot.child("tac_Gia").getValue().toString().trim();
                    String sMoTa = posSnapshot.child("mo_Ta").getValue().toString().trim();
                    String sGia = posSnapshot.child("gia").getValue().toString().trim();
                    String sSoLuong = posSnapshot.child("so_Luong").getValue().toString().trim();
                    String anh = posSnapshot.child("anh").getValue().toString();
                    String id_Nhom_Sach = posSnapshot.child("id_Nhom_Sach").getValue().toString().trim();
                    mUploads.add(new Upload(sID, sMaSach, sTenSach, sTacGia, sMoTa, sGia, sSoLuong, anh, id_Nhom_Sach));
                }

                mAdapter = new BookAdapter(getContext(), mUploads);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}