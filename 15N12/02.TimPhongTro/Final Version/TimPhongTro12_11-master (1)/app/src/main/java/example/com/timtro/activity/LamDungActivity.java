package example.com.timtro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import example.com.timtro.R;
import example.com.timtro.models.LamDung;

public class LamDungActivity extends AppCompatActivity
        implements  View.OnClickListener {
    private EditText edtmail, edtreview;
    private Button btngui;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamdung);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference("BÁO CÁO");
        databaseReference = mDatabase.child("Lạm dụng");

        connectView();

    }
    private void connectView() {
        //Ket noi voi edit text
        edtmail = (EditText) findViewById(R.id.edtmail7);
        edtreview = (EditText) findViewById(R.id.edtReview7);
        btngui = (Button) findViewById(R.id.btnGui7);

        // set on click
        btngui.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onClickGui();
    }

    public void onClickGui() {

        String str1 = edtmail.getText().toString();
        String str2 = edtreview.getText().toString();

        if (str1.length() == 0) {
            Toast.makeText(LamDungActivity.this, "Hãy nhập Email.", Toast.LENGTH_LONG).show();

        } else {
            if (str2.length() == 0) {
                Toast.makeText(LamDungActivity.this, "Hãy nhập phản hồi.", Toast.LENGTH_LONG).show();
            } else {
                addDataToFirebase();
                Toast.makeText(LamDungActivity.this, "Đã gửi phản hồi.", Toast.LENGTH_LONG).show();
                Thread bamgio = new Thread() {
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (Exception e) {

                        } finally {
                            Intent activitymoi = new Intent("example.com.timtro.ActivityMap");
                            startActivity(activitymoi);

                        }
                    }
                };
                bamgio.start();
            }
        }
    }

    private void addDataToFirebase() {
        LamDung lamDung = new LamDung();
        lamDung.setEmail(edtmail.getText().toString());
        lamDung.setReview(edtreview.getText().toString());
        String key = databaseReference.push().getKey();
        lamDung.setId(key);
        databaseReference.push().setValue(lamDung);
    }




}

