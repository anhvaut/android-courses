package com.example.creators.danhgiahocphan.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.encryption.MD5Encryption;
import com.example.creators.danhgiahocphan.models.SingletonUser;
import com.example.creators.danhgiahocphan.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private EditText editUserName, editPassWord;
    private Button btnLogIn;
    private FirebaseAuth mAuth;
    private String mKeyUser;
    public final static String LOGIN = "login";
    public final static String CHECK_KEY = "checked";
    public final static String USER_KEY = "user";
    public final static String PASS_KEY = "pass";
    private String email;
    private String password;
//    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(CHECK_KEY, false);
        if (check) {
            email = sharedPreferences.getString(USER_KEY, "");
            password = sharedPreferences.getString(PASS_KEY, "");
            LogIn(email, password);
        } else {
            setContentView(R.layout.activity_login);
            editUserName = (EditText) findViewById(R.id.editUserName);
            editPassWord = (EditText) findViewById(R.id.editPassword);
            btnLogIn = (Button) findViewById(R.id.btnLogIn);
        }


//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//        .enableAutoManage(this, this)
//        .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
  }

    public void LogIn(View view) {
        email = editUserName.getText().toString();
        password = MD5Encryption.md5(editPassWord.getText().toString());
        LogIn(email, password);
    }

    private void LogIn(String mail, String pass) {

        if (mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Bạn phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                getCurrentUser();


                            } else {
                                Toast.makeText(Login.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }
    }

    public void signUp(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);


    }

    public void forgotPass(View view) {

        Toast.makeText(this, "Ứng dụng sẽ cập nhật trong bản sau", Toast.LENGTH_SHORT).show();
    }

    /**
     * get current singletonUser
     */
    public void getCurrentUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHECK_KEY, true);
        editor.putString(USER_KEY, email);
        editor.putString(PASS_KEY, password);
        editor.commit();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference mDataReference = FirebaseDatabase.getInstance().getReference();
        String email;
        if (user != null) {
            email = user.getEmail();
            mDataReference.child("Users")
                    .orderByChild("mail").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        mKeyUser = child.getKey().toString();

                    }
                    mDataReference.child("Users").child(mKeyUser).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user1 = (User) dataSnapshot.getValue(User.class);
                            SingletonUser.Instance().setAvartar(user1.getAvartar());
                            SingletonUser.Instance().setUserName(user1.getUserName());
                            SingletonUser.Instance().setId(user1.getId());
                            SingletonUser.Instance().setGendle(user1.getGendle());
                            SingletonUser.Instance().setMajor(user1.getMajor());
                            SingletonUser.Instance().setMail(user1.getMail());
                            SingletonUser.Instance().setPassword(user1.getPassword());
                            SingletonUser.Instance().setStartYear(user1.getStartYear());
                            ProgressDialog dialog;
                            dialog = new ProgressDialog(Login.this);
                            dialog.setMessage("Đang tải, xin chờ...");
                            dialog.setCancelable(false);
                            dialog.show();
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
