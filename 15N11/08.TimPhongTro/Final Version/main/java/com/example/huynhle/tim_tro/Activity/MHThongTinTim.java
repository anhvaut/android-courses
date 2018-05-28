package com.example.huynhle.tim_tro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.huynhle.tim_tro.R;

public class MHThongTinTim extends AppCompatActivity {

    Button btnHuy, btnTimKiem;
    Spinner spnCity, spnDistrict, spnRoomType, spnDienTich, spnGiaPhong;
    String arrCity[];
    String arrDistrict[];
    String arrRoomType[];
    String arrDienTich[];
    String arrGiaPhong[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhthong_tin_tim);

        final String[] type = new String[1];

        addControls();
        arrCity = getResources().getStringArray(R.array.arrCity);
        arrDistrict = getResources().getStringArray(R.array.arrDistrict);
        arrRoomType = getResources().getStringArray(R.array.arrRoomType);
        arrDienTich = getResources().getStringArray(R.array.arrDienTich);
        arrGiaPhong = getResources().getStringArray(R.array.arrGiaPhong);
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCity.setAdapter(adapterCity);
        ArrayAdapter<String> adtDistrict = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrDistrict);
        adtDistrict.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnDistrict.setAdapter(adtDistrict);
        ArrayAdapter<String> adtRoomType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrRoomType);
        adtRoomType.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnRoomType.setAdapter(adtRoomType);
        spnRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type[0] = arrRoomType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adtDienTich = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrDienTich);
        adtDienTich.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnDienTich.setAdapter(adtDienTich);
        ArrayAdapter<String> adtGiaPhong = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrGiaPhong);
        adtGiaPhong.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnGiaPhong.setAdapter(adtGiaPhong);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MHThongTinTim.this, MainActivity.class));
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MHThongTinTim.this, laygiulieubase.class));
            }
        });
    }
    private void addControls() {
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        spnRoomType= (Spinner) findViewById(R.id.spnRoomType);
        spnDienTich= (Spinner) findViewById(R.id.spnDienTich);
        spnGiaPhong= (Spinner) findViewById(R.id.spnGiaPhong);
        spnCity= (Spinner) findViewById(R.id.spnCity);
        spnDistrict= (Spinner) findViewById(R.id.spnDistrict);
    }
}
