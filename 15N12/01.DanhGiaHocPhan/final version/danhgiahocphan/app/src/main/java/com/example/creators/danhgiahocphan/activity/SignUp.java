package com.example.creators.danhgiahocphan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.encryption.MD5Encryption;
import com.example.creators.danhgiahocphan.models.Major;
import com.example.creators.danhgiahocphan.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;


public class SignUp extends AppCompatActivity {
    private ImageView imgUpLoadImage;
    private EditText edtUser, edtPass, edtReEnterPass, edtMail, edtStartYear;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private int REQUEST_CODE_IMAGE = 123;
    static int gendle = -1;
    private String idMajor;
    private DatabaseReference mListMajor;
    private Spinner spinnerMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Mapping();
        getIdMajor();
        blurEdtStartYear();
    }

    private void blurEdtStartYear() {
        edtStartYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == false) {
                    String startYear = edtStartYear.getText().toString();
                    if (startYear == "")
                        return;
                    else {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        if (Integer.parseInt(startYear) > year) {
                            edtStartYear.setText(year + "");
                        } else if (Integer.parseInt(startYear) < (year - 10)) {
                            edtStartYear.setText((year - 10) + "");
                        } else return;


                    }
                }
            }
        });
    }

    public void SignUp(View view) {
        String username = edtUser.getText().toString();
        String mail = edtMail.getText().toString();
        String pass = edtPass.getText().toString();
        String reEnterPass = edtReEnterPass.getText().toString();

        String startYear = edtStartYear.getText().toString();
        if (username.isEmpty() || mail.isEmpty() || pass.isEmpty() || startYear.isEmpty()) {
            Toast.makeText(this, "Bạn phải điền đẫy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if (pass.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có 6 ký tự trở lên", Toast.LENGTH_SHORT).show();
            } else {
                if (pass.trim().compareTo(reEnterPass) != 0) {
                    Toast.makeText(SignUp.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    if (Integer.parseInt(startYear) > year) {
                        Toast.makeText(this, "Bạn phải nhập khóa học sai", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Integer.parseInt(startYear) < (year - 10)) {
                            Toast.makeText(this, "Bạn phải nhập khóa học sai", Toast.LENGTH_SHORT).show();
                        } else {
                            SettleSignUp();
                        }
                    }
                }
            }
        }
    }


    private void SettleSignUp() {
        String email = edtMail.getText().toString();
        String password = MD5Encryption.md5(edtPass.getText().toString());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, Login.class);
                            startActivity(intent);
                            pushDatabase();
                        } else
                            Toast.makeText(SignUp.this, "Lỗi đăng ký", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    /**
     *
     */
    private void pushDatabase() {
        final String username = edtUser.getText().toString();
        final String mail = edtMail.getText().toString();
        final String pass = MD5Encryption.md5(edtPass.getText().toString());
        final String id = mDatabase.child("Users").push().getKey();
        final int startYear;
        try {
            int startYear1 = Integer.parseInt(edtStartYear.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Bạn phải nhập đúng thông tin", Toast.LENGTH_SHORT).show();
        }
        startYear = Integer.parseInt(edtStartYear.getText().toString());
        //up image
        Calendar calendar = Calendar.getInstance();
        final StorageReference storageRef =
                storage.getReferenceFromUrl("gs://databaseappdanhgiahocpha-d0d59.appspot.com/user");
        final StorageReference mountainsRef = storageRef.child("img" + calendar.getTimeInMillis() + ".png");
        imgUpLoadImage.setDrawingCacheEnabled(true);
        imgUpLoadImage.buildDrawingCache();
        Bitmap bitmap = imgUpLoadImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //push database
                User user = new User(id, String.valueOf(downloadUrl), username, mail, idMajor, gendle, startYear, pass);
                mDatabase.child("Users").child(id).setValue(user);
            }
        });
    }

    public void uploadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUpLoadImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void radioCheck(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rdMale:
                if (checked) {
                    gendle = 1;
                }
                break;
            case R.id.rdFemale:
                if (checked) {
                    gendle = 0;
                }
                break;
        }

    }

    private void Mapping() {
        imgUpLoadImage = (ImageView) findViewById(R.id.imgUpLoadImage);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        edtReEnterPass = (EditText) findViewById(R.id.edtReEnterpassword);
        edtMail = (EditText) findViewById(R.id.edtMail);
        edtStartYear = (EditText) findViewById(R.id.edtStartYear);
        edtUser = (EditText) findViewById(R.id.edtUser);
        spinnerMajor = (Spinner) findViewById(R.id.spinerMajorMajor);
    }

    /**
     * hàm sử lý lấy id của ngành học lưu vào thông tin user
     */
    private void getIdMajor() {
        final ArrayList<Major> arrayListMajor = new ArrayList<Major>();
        final ArrayAdapter adapterMajor = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListMajor);


        mListMajor = FirebaseDatabase.getInstance().getReference();
        mListMajor.child("major").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Major major = (Major) child.getValue(Major.class);

                    arrayListMajor.add(major);
                    adapterMajor.notifyDataSetChanged();

                }
                adapterMajor.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinnerMajor.setAdapter(adapterMajor);
                spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Major major = arrayListMajor.get(i);
                        idMajor = major.getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }


        });


    }

}






