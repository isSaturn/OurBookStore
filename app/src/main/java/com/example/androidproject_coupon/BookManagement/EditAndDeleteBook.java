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

public class EditAndDeleteBook extends AppCompatActivity {

    private String ID_Sach ,Ma_Sach, Ten_Sach, Tac_Gia, Mo_Ta, Gia, So_Luong, Anh, ID_Nhom_Sach;

    Spinner spinnerTheLoai;
    Button ChonAnh, Sua, Xoa;
    EditText MaSach, TenSach, GiaTien, SoLuong, Mota, TacGia;
    ImageView AnhSach, Back;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    StorageTask mUploadTask;

    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_book);

        matching();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //Spiner loại sách
        ArrayList<Integer> ID = new ArrayList<Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DatabaseReference nhomSach = database.getReference("NhomSach");
        nhomSach.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

        //Lấy dữ liệu
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Book book = (Book) bundle.get("object_sach");

        ID_Sach = book.getID();
        Ma_Sach = book.getMa_Sach();
        Ten_Sach = book.getTen_Sach();
        Tac_Gia = book.getTac_Gia();
        Mo_Ta = book.getMo_Ta();
        Gia = book.getGia();
        So_Luong = book.getSo_Luong();
        Anh = book.getAnh();
        ID_Nhom_Sach = book.getID_Nhom_Sach();
        //Hiển thị thông tin sản phẩm lên app
        MaSach.setText(Ma_Sach);
        TenSach.setText(Ten_Sach);
        TacGia.setText(Tac_Gia);
        Mota.setText(Mo_Ta);
        GiaTien.setText(Gia);
        SoLuong.setText(So_Luong);
        Picasso.with(EditAndDeleteBook.this)
                .load(Anh)
                .fit()
                .centerCrop()
                .into(AnhSach);
        //lấy data
        mStorageRef = FirebaseStorage.getInstance().getReference("Sach");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Sach");


        //Chọn ảnh
        ChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        Sua.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(EditAndDeleteBook.this, "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_LONG).show();
                }else if (mUploadTask != null && mUploadTask.isInProgress()){
                    progressDialog.dismiss();
                    Toast.makeText(EditAndDeleteBook.this, "Đang sửa đổi thông tin sản phẩm", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.dismiss();
                    uploadFile(sMaSach,sTenSach,sTacGia,sMoTa,sGia,sSoLuong,ID_Nhom_Sach);
                    startActivity(new Intent(EditAndDeleteBook.this, MainActivity.class));
                }
            }
        });

        Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseRef.child(ID_Sach).removeValue();
                Toast.makeText(EditAndDeleteBook.this, "Đã xóa thành công", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditAndDeleteBook.this, MainActivity.class));
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(String sMaSach, String sTenSach, String sTacGia, String sMoTa,
                            String sGia, String sSoLuong, String id_nhom_sach) {
        if (mImageUri != null) {
            progressDialog.setMessage("Upload Book...");
            progressDialog.show();
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
                                    String BookId = ID_Sach;
                                    Toast.makeText(EditAndDeleteBook.this, "Chỉnh Sửa thành công", Toast.LENGTH_LONG).show();
                                    Book book = new Book(BookId,sMaSach,  sTenSach, sTacGia, sMoTa,
                                            sGia, sSoLuong,uri.toString(), id_nhom_sach);
                                    mDatabaseRef.child(BookId).setValue(book);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditAndDeleteBook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            progressDialog.dismiss();
            String BookId = ID_Sach;
            Toast.makeText(EditAndDeleteBook.this, "Chỉnh Sửa thành công", Toast.LENGTH_LONG).show();
            Book book = new Book(BookId,sMaSach,  sTenSach, sTacGia, sMoTa,
                    sGia, sSoLuong,Anh, id_nhom_sach);
            mDatabaseRef.child(BookId).setValue(book);
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
            progressDialog.dismiss();
            Picasso.with(this).load(mImageUri).into(AnhSach);
            //mImageView.setImageURI(mImageUri);
        }
    }

    private void matching() {
        spinnerTheLoai = findViewById(R.id.editBook_spn_TheLoai);
        ChonAnh = findViewById(R.id.editBook_btn_ChonAnh);
        Sua = findViewById(R.id.editBook_btn_SuaSach);
        Xoa = findViewById(R.id.editBook_btn_XoaSach);
        MaSach = findViewById(R.id.editBook_et_MaSach);
        TenSach = findViewById(R.id.editBook_et_TenSach);
        GiaTien = findViewById(R.id.editBook_et_GiaTien);
        SoLuong = findViewById(R.id.editBook_et_SoLuong);
        Mota = findViewById(R.id.editBook_et_MoTa);
        TacGia = findViewById(R.id.editBook_et_TacGia);
        AnhSach = findViewById(R.id.editBook_img_Sach);
        Back = findViewById(R.id.editBook_img_Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}