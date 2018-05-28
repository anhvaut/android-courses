package com.example.admin.my_nha_tro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Show_Activity extends AppCompatActivity {
    private  int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_);


        Bundle recive = this.getIntent().getExtras();
        i=recive.getInt("i");


    }
}
