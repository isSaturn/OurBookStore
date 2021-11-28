package com.example.androidproject_coupon.User;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject_coupon.BookManagement.Book;
import com.example.androidproject_coupon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    private DatabaseReference mDatabaseReference;
    private List<Book> mCart;

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

        mRecyclerView = view.findViewById(R.id.rcv_cart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mCart = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Sach");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCart.clear();

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

                    mCart.add(new Book(sID, sMaSach, sTenSach, sTacGia, sMoTa, sGia, sSoLuong, anh, id_Nhom_Sach));
                }

                mAdapter = new UserAdapter(getContext(), mCart);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void initItem() {
//        productCartAdapter = new CartAdapter();
//        rlCartEmpty = mView.findViewById(R.id.rl_cart_empty);
//        rlCart = mView.findViewById(R.id.rl_cart);
//        recyclerView = mView.findViewById(R.id.rcv_cart);
//        tvCartTotalPrice = mView.findViewById(R.id.tv_cart_tongtien);
//    }
//
//    private void setVisibilityView() {
//        if (listCartProduct.size() == 0){
//
//            // Hiển thị giỏ hàng rỗng
//            setVisibilityEmptyCart();
//        }else {
//
//            // Hiển thị giỏ hàng
//            setVisibilityCart();
//            setDataProductCartAdapter();
//        }
//    }
//
//    private void setDataProductCartAdapter() {
//    }
//
//    private void setVisibilityCart() {
//        rlCartEmpty.setVisibility(View.GONE);
//        rlCart.setVisibility(View.VISIBLE);
//        String total = format.format(getTotalPrice());
//        tvCartTotalPrice.setText( total +" vnđ" );
//    }
//
//    private void setVisibilityEmptyCart() {
//    }
//
//    private int getTotalPrice(){
//        for (Book product : listCartProduct){
//            int priceProduct = product.getProductPrice() ;
//            totalPrice = totalPrice +  priceProduct * product.getNumProduct();
//        }
//        return totalPrice;
//    }
//
//    private List<Book> makeDetailOrder( String odrNo){
//        List<Book> listDetailOrder = new ArrayList<>();
//        for (Product product : home.getListCartProduct()){
//            DetailOrder detailOrder = new DetailOrder();
//            detailOrder.setOrderNo(odrNo);
//            detailOrder.setProductName(product.getProductName());
//            detailOrder.setProductPrice(product.getProductPrice());
//            detailOrder.setUrlImg(product.getUrlImg());
//            detailOrder.setNumProduct(product.getNumProduct());
//            detailOrder.setStatus("Đang chờ xác nhận");
//            listDetailOrder.add(detailOrder);
//        }
//        return listDetailOrder;
//    }
//
//    public void setTotalPrice(int mode,int count, int priceProduct ){
//        if( mode == 0){
//            totalPrice = totalPrice - priceProduct * count;
//        }else if (mode == 1){
//            totalPrice = totalPrice + priceProduct * count;
//        }
//
//        tvCartTotalPrice.setText( format.format(totalPrice) + " VNĐ");
//    }
}