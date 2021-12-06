package com.example.androidproject_coupon.User;

import android.graphics.Canvas;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.AddInvoice;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    private SearchView searchView;

    private DatabaseReference mDatabaseReference;
    private List<Book> mBooks;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        return inflater.inflate(R.layout.fragment_shop, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        CartFragment.tvEmptyCart = view.findViewById(R.id.tv_emptyCart);
//        CartFragment.imgEmptyCart = view.findViewById(R.id.img_emptyCart);
        searchView = view.findViewById(R.id.searchview_user);

        AddInvoice.invAdapter = new CartAdapter(getContext(),CartFragment.cart);
        CartFragment.cartAdapter = new CartAdapter(getContext(),CartFragment.cart);

        mRecyclerView = view.findViewById(R.id.rcv_shop_user);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        mRecyclerView.addItemDecoration(itemDecoration);

        mBooks = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Sach");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBooks.clear();

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

                    mBooks.add(new Book(sID, sMaSach, sTenSach, sTacGia, sMoTa, sGia, sSoLuong, anh, id_Nhom_Sach));
                }

                mAdapter = new UserAdapter(getContext(), mBooks);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
    }
    private void search(String str) {
        List<Book> mList = new ArrayList<>();
        for (Book object: mBooks){
            if (object.getTen_Sach().toLowerCase().contains(str.toLowerCase())){
                mList.add(object);
            }
        }
        UserAdapter newApter = new UserAdapter(getContext(), mList);


        mRecyclerView.setAdapter(newApter);
    }
}