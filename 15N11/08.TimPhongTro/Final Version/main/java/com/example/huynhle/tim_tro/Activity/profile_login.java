package com.example.huynhle.tim_tro.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhle.tim_tro.Firebase.FirebaseDatabaseHelper;
import com.example.huynhle.tim_tro.Firebase.FirebaseUserEntity;
import com.example.huynhle.tim_tro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Huynh Le on 11/20/2017.
 */

public class profile_login extends AppCompatActivity{
    private FirebaseAuth firebaseAuth;
    private TextView txtvuser;
    private Button btnLogout;

    private EditText editProfileName;
    private EditText editProfileCountry;
    private EditText editProfilePhoneNumber;
    private TextView txtvProfileBirthday;
    private EditText editProfileHobby;
    private Button btnbirthdate;
    private Button saveEditButton;
    private ImageView imvanh;


    private ProgressDialog progressDialog;
    private Calendar cal;
    private Date dateFinish;

    private String use;

    public String getUse() {
        return use;
    }

    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference mData;

    int request_code_image = 1;
    int request_code_folder = 456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_login);

        progressDialog = new ProgressDialog(this);

        //them anh dai dien
        final StorageReference storageRef = storage.getReference();


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        txtvuser = (TextView) findViewById(R.id.txtvemail);
        txtvuser.setText("Hi "+ user.getEmail());
        Log.d("UUU", use +"");
        btnLogout = (Button) findViewById(R.id.btnlogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(profile_login.this, Login.class));
            }
        });


        setTitle("Edit Profile Information");

        editProfileName = (EditText)findViewById(R.id.profile_name);
        editProfileCountry = (EditText)findViewById(R.id.profile_country);
        editProfilePhoneNumber = (EditText)findViewById(R.id.profile_phone);
        editProfileHobby = (EditText)findViewById(R.id.profile_hobby);
        txtvProfileBirthday = (TextView) findViewById(R.id.profile_birth);
        btnbirthdate = findViewById(R.id.profile_birth_button);
        imvanh = findViewById(R.id.imvanh);

        imvanh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = {"Thư viện", "Máy ảnh"};
                AlertDialog.Builder b = new AlertDialog.Builder(profile_login.this);
                b.setTitle("Chọn ảnh từ");
                b.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==1)
                        {
                            ActivityCompat.requestPermissions(profile_login.this,
                                    new String[]{android.Manifest.permission.CAMERA}, request_code_image);
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

        getDefaultInfor();

        btnbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        saveEditButton = (Button)findViewById(R.id.save_edit_button);

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String profileName = editProfileName.getText().toString();
                final String profileCountry = editProfileCountry.getText().toString();
                final String profileHobby = editProfileHobby.getText().toString();
                final String profilePhoneNumber = editProfilePhoneNumber.getText().toString();
                final String profileBirthday = txtvProfileBirthday.getText().toString();

                if(TextUtils.isEmpty(profileName) || TextUtils.isEmpty(profileCountry) || TextUtils.isEmpty(profilePhoneNumber) || TextUtils.isEmpty(profileBirthday)){
                    Toast.makeText(profile_login.this, "Điền vào tất cả các mục!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    //them anh dai dien
                    android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();

                    StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");


                    // Get the data from an ImageView as bytes
                    imvanh.setDrawingCacheEnabled(true);
                    imvanh.buildDrawingCache();
                    Bitmap bitmap = imvanh.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile_login.this, "loi", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            mData = FirebaseDatabase.getInstance().getReference();

                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(profile_login.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                            Log.d("AAAA1", downloadUrl + "");

                            String userId = user.getProviderId();
                            String id = user.getUid();
                            String profileEmail = user.getEmail();
                            FirebaseUserEntity userEntity = new FirebaseUserEntity(id, profileEmail, profileName, profileCountry, profilePhoneNumber, profileBirthday, profileHobby, String.valueOf(downloadUrl));
                            mData.child("users").push().setValue(userEntity, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null) {
                                        Toast.makeText(profile_login.this, "Đã lưu dữ liệu!!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), laygiulieubase.class));
                                    } else {
                                        Toast.makeText(profile_login.this, "Lỗi lưu dữ liệu!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
                            firebaseDatabaseHelper.createUserInFirebaseDatabase(id, userEntity);


                        }
                    });
                    //
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == request_code_image && grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, request_code_image);
        }else{
            Toast.makeText(this, "Bạn không cho phép mở Camera!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_code_folder && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imvanh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == request_code_image && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imvanh.setImageBitmap(bitmap);
        }
    }

    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        txtvProfileBirthday.setText(strDate);
        dateFinish=cal.getTime();
    }


    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtvProfileBirthday.setText((dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };
        String s=txtvProfileBirthday.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(profile_login.this, callback, nam, thang, ngay);
        pic.setTitle("Ngày sinh của bạn!!!");
        pic.show();
    }

}
