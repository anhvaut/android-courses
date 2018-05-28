package com.example.creators.danhgiahocphan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.models.Major;
import com.example.creators.danhgiahocphan.models.SingletonUser;
import com.example.creators.danhgiahocphan.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class UserProfile extends AppCompatActivity {
    private TextView txtMail, txtMajor, txtStartYear, txtGendle, txtName;
    private Button btnChangeProfile;
    private ImageView imgAvatar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Mapping();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang tải, xin chờ...");
        dialog.setCancelable(false);
        dialog.show();

        getInforCurrentUser();
        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(UserProfile.this, UpdateUserActivity.class);
                startActivity(intent);
            }
        });

    }
    private void getInforCurrentUser() {

        txtName.setText(txtName.getText().toString() + ":" + SingletonUser.Instance().getUserName());
                            txtMail.setText(txtMail.getText().toString() + ":   " + SingletonUser.Instance().getMail());
                            txtStartYear.setText(txtStartYear.getText().toString() + ":   " + SingletonUser.Instance().getStartYear());
                            if (SingletonUser.Instance().getGendle() == 1) {
                                txtGendle.setText(txtGendle.getText().toString() + ":   Nam");
                            }
                            if (SingletonUser.Instance().getGendle() == 0) {
                                txtGendle.setText(txtGendle.getText().toString() + ":   Nữ");
                            }
                            if (SingletonUser.Instance().getGendle() == -1) {
                                txtGendle.setText(" ");
                            }
                            Picasso.with(getApplicationContext()).load(SingletonUser.Instance().getAvartar()).into(imgAvatar);
                                    txtMajor.setText(txtMajor.getText().toString() + ":   " + SingletonUser.Instance().getMajor());
                            dialog.dismiss();

    }
    private void Mapping() {
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        txtName = (TextView) findViewById(R.id.txtName);
        txtMail = (TextView) findViewById(R.id.txtMail);
        txtMajor = (TextView) findViewById(R.id.txtMajor);
        txtGendle = (TextView) findViewById(R.id.txtGendle);
        txtStartYear = (TextView) findViewById(R.id.txtStartYear);
        btnChangeProfile = (Button) findViewById(R.id.btnChangeProfile);
    }
}
