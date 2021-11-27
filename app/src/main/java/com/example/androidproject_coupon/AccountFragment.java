package com.example.androidproject_coupon;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidproject_coupon.AccountManagement.ManagentRole;
import com.example.androidproject_coupon.BookManagement.AddBook;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    Spinner spinnerRole, spinnerEmail;
    private String key, role, email = "", name = "", profileImage="", timetamp="", uid="";
    Button Luu;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerRole = view.findViewById(R.id.account_spn_Role);
        spinnerEmail = view.findViewById(R.id.account_spn_Email);
        Luu = view.findViewById(R.id.account_btn_Luu);

        ArrayList<String> keyaccount = new ArrayList<>();
        ArrayList<String> ListEmail = new ArrayList<>();

        ArrayAdapter arrayAdapterEmail = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, ListEmail);
        arrayAdapterEmail.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DatabaseReference Users = database.getReference("Users");
        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chilSnapshot : snapshot.getChildren()) {
                    keyaccount.add(chilSnapshot.getKey());
                    ListEmail.add(chilSnapshot.child("email").getValue(String.class));
                }
                arrayAdapterEmail.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerEmail.setAdapter(arrayAdapterEmail);

        spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getContext(), keyaccount.get(i), Toast.LENGTH_SHORT).show();
                key = keyaccount.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<Integer> ID = new ArrayList<Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();

        ArrayAdapter arrayAdapterRole = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DatabaseReference Role = database.getReference("Role");
        Role.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chilSnapshot : snapshot.getChildren()) {
                    ID.add(Integer.parseUnsignedInt(chilSnapshot.getKey()));
                    arrayList.add(chilSnapshot.child("role").getValue(String.class));
                }
                arrayAdapterRole.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerRole.setAdapter(arrayAdapterRole);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                role = arrayList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRole();
            }
        });
    }

    private void UpdateRole() {
        DatabaseReference Users = database.getReference("Users");

        Users.child(key).child("userType").setValue(role);
        Toast.makeText(getContext(),"Đã cập nhật role mới", Toast.LENGTH_SHORT ).show();
    }
}