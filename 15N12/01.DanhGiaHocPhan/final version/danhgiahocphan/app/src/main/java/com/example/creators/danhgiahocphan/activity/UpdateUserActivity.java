package com.example.creators.danhgiahocphan.activity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.encryption.MD5Encryption;
import com.example.creators.danhgiahocphan.models.Major;
import com.example.creators.danhgiahocphan.models.SingletonUser;
import com.example.creators.danhgiahocphan.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UpdateUserActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private EditText edtName, edtPassword, edtYear, edtRePassWord;
    private TextView txtPass;
    private SingletonUser user;
    private Spinner spinnerMajor;
    private Button btnSave, btnBack;
    private ArrayList<Major> arrayListMajor = new ArrayList<Major>();
    private ArrayAdapter<Major> majorArrayAdapter;
    private DatabaseReference mDatabaseRef;
    private RadioGroup rdGrGendle;
    private String newPassword;
    private RadioButton rdMale, rdFemale;
    private boolean check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Mapping();
        user=SingletonUser.Instance();
        showInforUser();
        getListMajor();
        updateUser();
        if (check) {
            Intent intent = new Intent(UpdateUserActivity.this, UserProfile.class);
            startActivity(intent);
        }
    }



    private void showInforUser() {

        edtName.setText(user.getUserName());
        edtYear.setText(String.valueOf(user.getStartYear()));
        txtPass.setText("");
        if (user.getGendle() == 1) {
            rdGrGendle.check(rdMale.getId());
        } else {
            rdGrGendle.check(rdFemale.getId());
        }
        Picasso.with(getApplicationContext()).load(user.getAvartar()).into(imgAvatar);

    }

    private void getListMajor() {
        majorArrayAdapter = new ArrayAdapter<Major>(this, android.R.layout.simple_list_item_single_choice, arrayListMajor);
        majorArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("major");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Major major = child.getValue(Major.class);
                    arrayListMajor.add(major);
                }
                majorArrayAdapter.notifyDataSetChanged();
                spinnerMajor.setAdapter(majorArrayAdapter);
                int position = 0;
                for (int i = 0; i < arrayListMajor.size(); i++) {
                    if (user.getMajor().equals(arrayListMajor.get(i).getId())) {
                        position = i;
                    }
                }
                spinnerMajor.setSelection(position);
                spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Major major = arrayListMajor.get(i);
                        user.setMajor(major.getId());
                        Toast.makeText(UpdateUserActivity.this, user.getMajor(), Toast.LENGTH_SHORT).show();
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

    public void radioClick(View view) {
        if (view.getId() == R.id.rdMale) {
            user.setGendle(1);
        } else {
            user.setGendle(0);
        }

    }


    private void Mapping() {
        imgAvatar = (ImageView) findViewById(R.id.imgUpLoadImage);
        edtName = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRePassWord = (EditText) findViewById(R.id.edtReEnterpassword);
        edtYear = (EditText) findViewById(R.id.edtStartYear);
        spinnerMajor = (Spinner) findViewById(R.id.spinerMajor);
        rdGrGendle = (RadioGroup) findViewById(R.id.rdgrGendle);
        rdMale = (RadioButton) findViewById(R.id.rdMale);
        rdFemale = (RadioButton) findViewById(R.id.rdFemale);
        btnSave = (Button) findViewById(R.id.btnSave);
        //btnBack = (Button) findViewById(R.id.btnBack);
        txtPass = (TextView) findViewById(R.id.txtPass);

    }

    private void updateUser() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                newPassword = user.getPassword();
                if (currentUser != null) {
                    final String email = user.getMail();
                    final String name = edtName.getText().toString();
                    final int startYear = Integer.parseInt(edtYear.getText().toString());
                    final String id = user.getId();
                    final String major = user.getMajor();
                    final String avartar = user.getAvartar();
                    final int gender = user.getGendle();
                    if (!edtPassword.getText().toString().isEmpty()) {
                        if (!edtPassword.getText().toString().equals(edtRePassWord.getText().toString())) {
                            Toast.makeText(UpdateUserActivity.this, "Mat khau khong trung khop", Toast.LENGTH_SHORT).show();
                        } else {
                            newPassword = MD5Encryption.md5(edtPassword.getText().toString());
                            currentUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                check = true;
                                                Toast.makeText(UpdateUserActivity.this, "change Password sucess", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        }

                    }

                    User newUser = new User(id, avartar, name, email, major, gender, startYear, newPassword);
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                    mDatabaseRef.child("Users").child(id).setValue(newUser);

                }
            }
        });


    }
}