package com.example.creators.danhgiahocphan.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.creators.danhgiahocphan.R;
import com.example.creators.danhgiahocphan.adapter.SubjectAdapter;
import com.example.creators.danhgiahocphan.models.SingletonUser;
import com.example.creators.danhgiahocphan.models.Subject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Subject> arraySubject;
    private SubjectAdapter adapterSubject;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private DatabaseReference subjectGeneral;
    private DatabaseReference subjectMajor;
    private ImageView imageUser;
    private TextView txtName, txtEmail;
    private SearchView searchView;
    public static String KEY_NAME_SUBJECT = "user";
    public static String KEY_TEACHER = "TeacherProfile";
    public static String KEY_DATA = "data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        subjectGeneral = mDatabase.child("Subjects").child("general");
        subjectMajor = mDatabase.child("Subjects").child("major").child(SingletonUser.Instance().getMajor());
        arraySubject = new ArrayList<Subject>();
//        adapterSubject = new SubjectAdapter(arraySubject, getApplicationContext());
        getWidget();
        getSubjectMajor();
        getSubjectGeneral();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        txtName = (TextView) hView.findViewById(R.id.textName);
        txtEmail = (TextView) hView.findViewById(R.id.textEmail);
        imageUser = (ImageView) hView.findViewById(R.id.imageView);
        try {
            txtName.setText(SingletonUser.Instance().getUserName());
            txtEmail.setText(SingletonUser.Instance().getMail());
            Picasso.with(getApplicationContext()).load(SingletonUser.Instance().getAvartar()).into(imageUser);


        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapterSubject.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterSubject.filter(s.trim().toLowerCase(Locale.getDefault()));
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        arraySubject.clear();
        getSubjectMajor();
        getSubjectGeneral();
    }

    private void getWidget() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerHome);
        searchView = (SearchView) findViewById(R.id.searchView);
    }

    private void getSubjectMajor() {
        subjectMajor.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Subject subject1 = (Subject) child.getValue(Subject.class);
                    String key = child.getKey().toString();
                    subject1.setId(key);
                    arraySubject.add(subject1);

                //   adapterSubject.notifyDataSetChanged();
                }
                initRecyclerView();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getSubjectGeneral() {
        subjectGeneral.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Subject subject1 = (Subject) child.getValue(Subject.class);
                    String key = child.getKey().toString();
                    subject1.setId(key);
                    arraySubject.add(subject1);
                    adapterSubject = new SubjectAdapter(arraySubject, getApplicationContext());
                    recyclerView.setAdapter(adapterSubject);
                  //  adapterSubject.notifyDataSetChanged();

                }
                initRecyclerView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void informationUser() {
        Intent intent = new Intent(Home.this, UserProfile.class);
        startActivity(intent);
    }

    private void feedback() {
        Intent intent = new Intent(Home.this, Feedback.class);
        startActivity(intent);
    }

    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn đăng xuất không?");
        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = getSharedPreferences(Login.LOGIN, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Login.CHECK_KEY);
                editor.remove(Login.USER_KEY);
                editor.remove(Login.PASS_KEY);
                editor.commit();
                ProgressDialog dialog;
                dialog = new ProgressDialog(Home.this);
                dialog.setMessage("Đang tải, xin chờ...");
                dialog.setCancelable(false);
                dialog.show();
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Home.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSubject);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            arraySubject.clear();
            getSubjectGeneral();
            getSubjectMajor();
        }
        if (id == R.id.infor) {
            informationUser();
        }
        if (id == R.id.general) {
            arraySubject.clear();
            getSubjectGeneral();
        }
        if (id == R.id.major) {
            arraySubject.clear();
            getSubjectMajor();
        }
        if (id == R.id.feedBack) {
            feedback();
        }
        if (id == R.id.out) {
            logOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}