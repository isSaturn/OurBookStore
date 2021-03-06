package com.example.androidproject_coupon.User;

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
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.InvoiceAdapter;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.ViewInvoice;
import com.example.androidproject_coupon.OrderManagement.Oder;
import com.example.androidproject_coupon.OrderManagement.OderAdapter;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceFragment extends Fragment {


    private InvoiceAdapter mInvoiceInfoAdapter;
    private RecyclerView rcvInvoiceList;
    private GetIDandRole getIDandRole  = new GetIDandRole();

    private DatabaseReference invRef;
    private List<Oder> invList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public InvoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceFragment newInstance(String param1, String param2) {
        InvoiceFragment fragment = new InvoiceFragment();
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
        return inflater.inflate(R.layout.fragment_invoice, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvInvoiceList = view.findViewById(R.id.inv_rv_danhsach);
        rcvInvoiceList.setHasFixedSize(true);
        rcvInvoiceList.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvInvoiceList.addItemDecoration(itemDecoration);

        invList = new ArrayList<>();

        invRef = FirebaseDatabase.getInstance().getReference("DonHang");
        invRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                invList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String diachi = dataSnapshot.child("dia_Chi").getValue().toString().trim();
                    String hoten = dataSnapshot.child("ho_Ten").getValue().toString().trim();
                    String idHinhthucGH = dataSnapshot.child("id_Hinh_Thuc_GH").getValue().toString().trim();
                    String idKhuyenmai = dataSnapshot.child("id_Khuyen_Mai").getValue().toString().trim();
                    String idTaiKhoan = dataSnapshot.child("id_Tai_Khoan").getValue().toString().trim();
                    String idTrangthaiDH = dataSnapshot.child("id_Trang_Thai_DH").getValue().toString().trim();
                    String maDonhang = dataSnapshot.child("ma_Don_Hang").getValue().toString().trim();
                    String sdt = dataSnapshot.child("sdt").getValue().toString().trim();
                    String time = dataSnapshot.child("time").getValue().toString().trim();
                    String tongtien = dataSnapshot.child("tong_Tien").getValue().toString().trim();
                    invList.add(new Oder(diachi, hoten,  idHinhthucGH,  idKhuyenmai,  idTaiKhoan,  idTrangthaiDH,  maDonhang,  sdt,  time,  tongtien));
                }
                String s = getIDandRole.id;
                if (!s.equals("")){
                    List<Oder> mOder = new ArrayList<>();
                    for(Oder object: invList){
                        if (object.getID_Tai_Khoan().toLowerCase().contains(s.toLowerCase())){
                            mOder.add(object);
                        }
                    }
                    mInvoiceInfoAdapter = new InvoiceAdapter(getContext(), mOder);
                    rcvInvoiceList.setAdapter(mInvoiceInfoAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
