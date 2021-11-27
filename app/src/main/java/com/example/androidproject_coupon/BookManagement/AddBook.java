package com.example.androidproject_coupon.BookManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.androidproject_coupon.MainActivity;
import com.example.androidproject_coupon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddBook extends AppCompatActivity {

    Spinner spinnerTheLoai;
    Button ChonAnh, ThemSach;
    EditText MaSach, TenSach, GiaTien, SoLuong, Mota, TacGia;
    ImageView AnhSach, Back;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    StorageTask mUploadTask;

    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String ID_Nhom_Sach;

    private Uri mImageUri;
    int size;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        matching();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        ArrayList<Integer> ID = new ArrayList<Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DatabaseReference nhomSach = database.getReference("NhomSach");
        nhomSach.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chilSnapshot:dataSnapshot.getChildren()){
                    ID.add(Integer.parseUnsignedInt(chilSnapshot.getKey()));
                    arrayList.add(chilSnapshot.child("Loai_Sach").getValue(String.class));
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnerTheLoai.setAdapter(arrayAdapter);

        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ID_Nhom_Sach = ID.get(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("Sach");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sach");

        ThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Check Data...");
                progressDialog.show();
                String sMaSach = MaSach.getText().toString().trim();
                String sTenSach = TenSach.getText().toString().trim();
                String sTacGia = TacGia.getText().toString().trim();
                String sMoTa = Mota.getText().toString().trim();
                String sGia = GiaTien.getText().toString().trim();
                String sSoLuong = SoLuong.getText().toString().trim();
                if (sMaSach.equals("") || sTenSach.equals("") ||
                    sTacGia.equals("") || sMoTa.equals("") ||
                    sGia.equals("") || sSoLuong.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(AddBook.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else if (mUploadTask != null && mUploadTask.isInProgress()){
                    progressDialog.dismiss();
                    Toast.makeText(AddBook.this, "Đang thêm mới sản phẩm", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.dismiss();
                    uploadFile(sMaSach,sTenSach,sTacGia,sMoTa,sGia,sSoLuong,ID_Nhom_Sach);
                    startActivity(new Intent(AddBook.this, MainActivity.class));
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(String sMaSach, String sTenSach, String sTacGia, String sMoTa,
                            String sGia, String sSoLuong, String ID_Nhom_Sach) {
        progressDialog.setMessage("Upload Book...");
        progressDialog.show();
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "."
                    + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss();
                                    Bundle extras = getIntent().getExtras();
                                    //thêm 1 đơn vị
                                    if (extras != null) {
                                        size = extras.getInt("size") + 1;
                                    }
                                    String BookId = String.valueOf(size);
                                    Toast.makeText(AddBook.this, "Thêm sách mới thành công", Toast.LENGTH_LONG).show();
                                    Book book = new Book(BookId,sMaSach,  sTenSach, sTacGia, sMoTa,
                                            sGia, sSoLuong,uri.toString(), ID_Nhom_Sach);
                                    mDatabaseRef.child(BookId).setValue(book);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Chưa chọn file ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.setMessage("Loading Image...");
        progressDialog.show();
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(AnhSach);
            progressDialog.dismiss();
        }
    }


    private void matching() {
        spinnerTheLoai = findViewById(R.id.addBook_spn_TheLoai);
        ChonAnh = findViewById(R.id.addBook_btn_ChonAnh);
        ThemSach = findViewById(R.id.addBook_btn_ThemSach);
        MaSach = findViewById(R.id.addBook_et_MaSach);
        TenSach = findViewById(R.id.addBook_et_TenSach);
        GiaTien = findViewById(R.id.addBook_et_GiaTien);
        SoLuong = findViewById(R.id.addBook_et_SoLuong);
        Mota = findViewById(R.id.addBook_et_MoTa);
        TacGia = findViewById(R.id.addBook_et_TacGia);
        AnhSach = findViewById(R.id.addBook_img_Sach);
        Back = findViewById(R.id.addBook_img_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}