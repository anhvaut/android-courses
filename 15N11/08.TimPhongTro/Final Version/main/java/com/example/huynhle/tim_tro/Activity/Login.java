package com.example.huynhle.tim_tro.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhle.tim_tro.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    CallbackManager callbackManager;

    private FirebaseAuth mAuth;
    LoginButton btnconnectFB;
    Button btnregister;
    Button btnDangky, btnHuy, btnDangNhap;
    EditText edtEmail, edtTaiKhoan, edtMK;
    EditText edtPass;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private TextView loginError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        dialog = new Dialog(Login.this);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){

        }

        AnhXa();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_register);
                edtEmail = dialog.findViewById(R.id.edtEmail);
                edtPass = dialog.findViewById(R.id.edtPass);
                btnDangky = dialog.findViewById(R.id.btnDangky);
                btnHuy = dialog.findViewById(R.id.btnHuyregister);
                btnDangky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DangKy();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangNhap();
            }
        });
    }
    private void AnhXa() {
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        edtTaiKhoan = (EditText) findViewById(R.id.edtTaiKhoan);
        edtMK = (EditText) findViewById(R.id.edtMK);
        btnregister = (Button) findViewById(R.id.btnregister);
    }

    private void DangKy(){
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui lòng nhập vào tài khoản!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Vui lòng nhập vào mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.cancel();
                            Toast.makeText(Login.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }else{
                            Toast.makeText(Login.this, "Email không hợp lệ hoặc đã được dùng!!", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }
                });

    }
    private void DangNhap(){
        String taikhoan = edtTaiKhoan.getText().toString();
        String matkhau = edtMK.getText().toString();

        if(TextUtils.isEmpty(taikhoan)){
            Toast.makeText(this, "Vui lòng nhập vào tài khoản!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(matkhau)){
            Toast.makeText(this, "Vui lòng nhập vào mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(taikhoan, matkhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), profile_login.class));
                        }else {
                            progressDialog.cancel();
                            Toast.makeText(Login.this, "Email hoặc pass không đúng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        ((FirebaseApplication)getApplication()).loginAUser(Login.this, taikhoan, matkhau, loginError);
    }
}
