package com.example.mathapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NumberActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_phepcong;
    private Button btn_pheptru;
    private Button btn_sosanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        anhxa();
        batsukien();
    }
    public void anhxa(){
        btn_phepcong = (Button) findViewById(R.id.btn_phepcong);
        btn_pheptru = (Button) findViewById(R.id.btn_pheptru);
        btn_sosanh = (Button) findViewById(R.id.btn_sosanh);
    }
    public void batsukien(){
        btn_phepcong.setOnClickListener(this);
        btn_pheptru.setOnClickListener(this);
        btn_sosanh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_phepcong:
                intent = new Intent(this,PhepCongActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pheptru:
                intent = new Intent(this,PhepTruActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sosanh:
                intent = new Intent(this,PhepSoSanhActivity.class);
                startActivity(intent);
                break;
        }
    }
}
