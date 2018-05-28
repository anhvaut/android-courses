package com.example.mathapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btn_number;
    private Button btn_hinh;
    private Button btn_time;
    private Button btn_practice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        batsukien();

    }
    public void anhxa(){

        btn_number = (Button) findViewById(R.id.btn_number);
        btn_hinh = (Button) findViewById(R.id.btn_hinh);
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_practice = (Button) findViewById(R.id.btn_practice);
    }
    public void batsukien(){

        btn_number.setOnClickListener(this);
        btn_hinh.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_practice.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){

            case R.id.btn_number:
                // TODO: 11/23/2017
                intent = new Intent(this,NumberActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_hinh:
                // TODO: 11/23/2017
                intent = new Intent(this,HinhActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_time:
                // TODO: 11/23/2017
                intent = new Intent(this,TimeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_practice:
                // TODO: 11/23/2017
                intent = new Intent(this,PracticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
