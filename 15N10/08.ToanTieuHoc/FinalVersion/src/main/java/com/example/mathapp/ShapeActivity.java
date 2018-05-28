package com.example.mathapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ShapeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_hinhtamgiac = null;
    private Button btn_hinhvuong = null;
    private Button btn_hinhtron = null;
    private Button btn_doanthang = null;
    private Button btn_demhinh = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        batsukien();

    }
    public void anhxa(){
        btn_hinhtamgiac = (Button) findViewById(R.id.btn_hinhtamgiac);
        btn_hinhvuong = (Button) findViewById(R.id.btn_hinhvuong);
        btn_hinhtron = (Button) findViewById(R.id.btn_hinhtron);
        btn_doanthang = (Button) findViewById(R.id.btn_doanthang);
        btn_demhinh = (Button) findViewById(R.id.btn_demhinh);
    }
    public void batsukien(){
        btn_hinhtamgiac.setOnClickListener(this);
        btn_hinhvuong.setOnClickListener(this);
        btn_hinhtron.setOnClickListener(this);
        btn_doanthang.setOnClickListener(this);
        btn_demhinh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_hinhtamgiac:
                // TODO: 11/23/2017
                intent = new Intent(this,HinhTamGiacActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_hinhvuong:
                intent = new Intent(this,HinhVuongActivity.class);
                startActivity(intent);
                // TODO: 11/23/2017
                break;
            case R.id.btn_hinhtron:
                intent = new Intent(this,HinhTronActivity.class);
                startActivity(intent);
                // TODO: 11/23/2017
                break;
            case R.id.btn_doanthang:
                // TODO: 11/23/2017
                intent = new Intent(this,DoanThangActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_demhinh:
                // TODO: 11/23/2017
                intent = new Intent(this,DemHinhActivity.class);
                startActivity(intent);
                break;
        }
    }
}
