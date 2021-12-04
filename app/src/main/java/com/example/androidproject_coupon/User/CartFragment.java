package com.example.androidproject_coupon.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.GetIDandRole;
import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.InvoiceManagement.Invoice.AddInvoice;
import com.example.androidproject_coupon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    public static Integer tien = 0 ;

    public static TextView tongtien;
    public static TextView tvEmptyCart;
    public static ImageView imgEmptyCart;
    public static List<Book> cart = new ArrayList<>();
    GetIDandRole getIDandRole = new GetIDandRole();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private AppCompatButton appCompatButton;
    public static CartAdapter cartAdapter;
    public static CartAdapter invAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnInv;

        tongtien = view.findViewById(R.id.tv_cart_tongtien);
        tongtien.setText(String.valueOf(tien)+ " VNĐ");

        btnInv = view.findViewById(R.id.btn_cart_dathang);

        mRecyclerView = view.findViewById(R.id.rcv_cart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        cartAdapter = new CartAdapter(getContext(), cart);

        mRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        tvEmptyCart = view.findViewById(R.id.tv_emptyCart);
        imgEmptyCart = view.findViewById(R.id.img_emptyCart);
        if (cart.size() != 0){
            tvEmptyCart.setVisibility(View.GONE);
            imgEmptyCart.setVisibility(View.GONE);
        }
        Intent intent = new Intent(getContext(), AddInvoice.class);
        btnInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.size() == 0){
                    Toast.makeText(getContext(), "Giỏ hàng không được trống", Toast.LENGTH_SHORT).show();
                    return;
                }else if (getIDandRole.email.equals("")){
                    Toast.makeText(getContext(), "Vui lòng đăng nhập để đặt hàng", Toast.LENGTH_SHORT).show();
                    return;
                }
                getContext().startActivity(intent);
            }
        });
    }
}