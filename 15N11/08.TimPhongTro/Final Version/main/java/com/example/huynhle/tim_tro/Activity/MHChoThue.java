package com.example.huynhle.tim_tro.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.huynhle.tim_tro.Firebase.FirebaseUserEntity;
import com.example.huynhle.tim_tro.R;
import com.example.huynhle.tim_tro.up_load;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MHChoThue extends AppCompatActivity {

    private static final int request_code_image = 1;
    private static final int  request_code_folder = 2;
    ImageView imgbtnanh1, imgbtnanh2, imgbtnanh3;
    Button btnHuy, btndangtin;
    EditText edtAddress, edtTitle, edtPrice, edtArceage, edtPhoneNumber, edtDetail;
    Spinner spnRoomType, spnCity, spnDistrict;
    String arrRoomType[];
    String arrCity[];
    String arrDistrict[];
    String name;
    String anhnguoidung;
    String time;
    private ProgressDialog progressDialog;

    DatabaseReference mData;
    java.util.Calendar cal;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhcho_thue);
        addControls();

        mData = FirebaseDatabase.getInstance().getReference();
        final StorageReference storageRef = storage.getReference();

        progressDialog = new ProgressDialog(this);


        time = getDefaultInfor();
        Log.d("CCCC", time + "");
        final String[] type = new String[1];
        final String[] city = new String[1];
        final String[] district = new String[1];

        arrRoomType = getResources().getStringArray(R.array.arrRoomType1);
        arrCity = getResources().getStringArray(R.array.arrCity1);
        arrDistrict = getResources().getStringArray(R.array.arrDistrict1);

        ArrayAdapter<String> adtRoomType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrRoomType);
        adtRoomType.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRoomType.setAdapter(adtRoomType);
        spnRoomType.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type[0] = arrRoomType[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCity.setAdapter(adapterCity);
        spnCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city[0] = arrCity[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        } );

        ArrayAdapter<String> adtDistrict = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrDistrict);
        adtDistrict.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnDistrict.setAdapter(adtDistrict);
        spnDistrict.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district[0] = arrDistrict[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        LoadName();
        btndangtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edtAddress.getText().toString()) || TextUtils.isEmpty(edtPrice.getText().toString())
                        || TextUtils.isEmpty(edtTitle.getText().toString()) || TextUtils.isEmpty(edtDetail.getText().toString())
                        || TextUtils.isEmpty(edtPhoneNumber.getText().toString())|| TextUtils.isEmpty(edtArceage.getText().toString())){
                    Toast.makeText(MHChoThue.this, "Vui lòng điền vào thông tin!!", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();

                Calendar calendar = Calendar.getInstance();

                StorageReference mountainsRef = storageRef.child("image"+ calendar.getTimeInMillis() +".png");


                // Get the data from an ImageView as bytes
                imgbtnanh1.setDrawingCacheEnabled(true);
                imgbtnanh1.buildDrawingCache();
                Bitmap bitmap = imgbtnanh1.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(MHChoThue.this, "loi", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(MHChoThue.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        Log.d("AAAA", downloadUrl + "");
                        Log.d("AAAA1", anhnguoidung +"");
                        //tao node tren phan database
                        up_load hinh = new up_load(city[0], district[0],  edtAddress.getText().toString(), type[0],
                                edtTitle.getText().toString(), edtPrice.getText().toString(), edtArceage.getText().toString(),
                                edtPhoneNumber.getText().toString(), edtDetail.getText().toString(), String.valueOf(downloadUrl),
                                name, String.valueOf(anhnguoidung), time);
                        mData.child("ThongTinTro").push().setValue(hinh, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError == null)
                                {
                                    Toast.makeText(MHChoThue.this, "Đăng tin thành công!!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(MHChoThue.this, laygiulieubase.class));
                                }else{
                                    Toast.makeText(MHChoThue.this, "Loi luu du lieu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }}
        });
        Log.d("AAAAs", anhnguoidung +"");
        imgbtnanh1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"Thư viện", "Máy ảnh"};
                AlertDialog.Builder b = new AlertDialog.Builder(MHChoThue.this);
                b.setTitle("Chọn ảnh từ");
                b.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==1)
                        {
                            ActivityCompat.requestPermissions(MHChoThue.this,
                                    new String[]{Manifest.permission.CAMERA}, request_code_image);
                        }
                        if(i==0)
                        {
                            Intent intent = new Intent( Intent.ACTION_PICK);
                            intent.setType( "image/*");
                            startActivityForResult( intent, request_code_folder );
                        }
                    }
                });
                b.show();
            }
        } );

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MHChoThue.this, laygiulieubase.class));
            }
        });


    }


    private void LoadName(){
        mData.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUserEntity uploadname = dataSnapshot.getValue(FirebaseUserEntity.class);
                anhnguoidung = uploadname.getanhnguoidung();
                name = uploadname.getName();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == request_code_image && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, request_code_image);
        }else{
            Toast.makeText(this, "Ban khong cho phep mo camera!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_code_image && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgbtnanh1.setImageBitmap(bitmap);
        }

        if (requestCode == request_code_folder && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgbtnanh1.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    private void addControls() {
        imgbtnanh1 = (ImageView) findViewById( R.id.imgbtnanh1 );
        imgbtnanh2 = (ImageView) findViewById( R.id.imgbtnanh2 );
        imgbtnanh3 = (ImageView) findViewById( R.id.imgbtnanh3 );
        btndangtin = (Button) findViewById( R.id.btndangtin );
        btnHuy = (Button) findViewById(R.id.btnHuy);

        edtAddress = (EditText) findViewById( R.id.edtAddress );
        edtArceage = (EditText) findViewById( R.id.edtArceage );
        edtDetail = (EditText) findViewById( R.id.edtDetail );
        edtPhoneNumber = (EditText) findViewById( R.id.edtPhoneNumber );
        edtPrice = (EditText) findViewById( R.id.edtPrice );
        edtTitle = (EditText) findViewById( R.id.edtTitle );

        spnRoomType= (Spinner) findViewById(R.id.spnRoomType);
        spnCity= (Spinner) findViewById(R.id.spnCity);
        spnDistrict= (Spinner) findViewById(R.id.spnDistrict);
    }

    public String getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal= java.util.Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        return strDate;
    }
}
