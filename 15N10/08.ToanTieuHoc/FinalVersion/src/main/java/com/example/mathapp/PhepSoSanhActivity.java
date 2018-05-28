package com.example.mathapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

public class PhepSoSanhActivity extends AppCompatActivity {
    private TextView tv_sohang1;
    private TextView tv_sohang2;
    private TextView tv_result;
    private RadioGroup radioGroup;
    private Button btn_check;
    private Button btn_result;
    private int a;
    private int b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_so_sanh);
        anhxa();
        setWidgets();
        batsukien();
    }
    public void anhxa(){
        tv_sohang1 = (TextView) findViewById(R.id.tv_sohang1);
        tv_sohang2 = (TextView) findViewById(R.id.tv_sohang2);
        tv_result = (TextView) findViewById(R.id.tv_result);
        radioGroup = (RadioGroup) findViewById(R.id.rd_group);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_result = (Button) findViewById(R.id.btn_result);
    }
    public void setWidgets(){
        Random random = new Random();
         a = random.nextInt() % 5 + 5;
         b = random.nextInt() % 5 + 5;
        tv_sohang1.setText(a+"");
        tv_sohang2.setText(b+"");
    }
    public void batsukien(){

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idCheck = radioGroup.getCheckedRadioButtonId();
                switch (idCheck){
                    case R.id.radio0:
                        if(a==b) { tv_result.setText("Chúc mừng em đã trả lời đúng");
                            setWidgets();}
                        else { tv_result.setText("Sai rồi! Thử lại nha!");}
                        break;
                    case R.id.radio1:
                        if(a<b) { tv_result.setText("Chúc mừng em đã trả lời đúng");
                            setWidgets();}
                        else { tv_result.setText("Sai rồi! Thử lại nha!");}
                        break;
                    case R.id.radio2:
                        if(a>b) { tv_result.setText("Chúc mừng em đã trả lời đúng");
                            setWidgets();}
                        else { tv_result.setText("Sai rồi! Thử lại nha!");}
                        break;

                }
            }
        });
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kq = "...";
                if(a==b) kq = "bằng";
                if(a>b) kq = "lớn hơn";
                if(a<b) kq = "bén hơn";
                tv_result.setText("Câu trả lời là: "+a+" "+kq+" "+b+"\nLàm tiếp bài khác nha!");
            }
        });
    }
}
