package com.example.mathapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhepTruActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_check;
    private Button btn_result;
    private TextView tv_result;
    private EditText edt_sohang1;
    private EditText edt_sohang2;
    private EditText edt_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_tru);
        anhxa();
        batsukien();
    }
    public void anhxa(){
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_result = (Button) findViewById(R.id.btn_result);
        tv_result = (TextView) findViewById(R.id.tv_result);
        edt_sohang1 = (EditText) findViewById(R.id.edt_sohang1);
        edt_sohang2 = (EditText) findViewById(R.id.edt_sohang2);
        edt_answer = (EditText) findViewById(R.id.edt_answer);
    }
    public void batsukien(){
        btn_check.setOnClickListener(this);
        btn_result.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int a = Integer.parseInt((edt_sohang1.getText()+""));
        int b = Integer.parseInt((edt_sohang2.getText()+""));
        int c = Integer.parseInt((edt_answer.getText()+""));
        switch (view.getId()){
            case R.id.btn_check:
                // TODO: 11/23/2017

                if(a-b == c){
                    tv_result.setText("Chúc mừng em đã trả lời đúng");
                    edt_sohang1.setText("");
                    edt_sohang2.setText("");
                    edt_answer.setText("");
                    edt_sohang1.findFocus();
                }
                else { tv_result.setText("Em đã tính sai \nThử lại nào!");
                    edt_answer.setText("");
                    edt_answer.findFocus();
                }
                break;
            case R.id.btn_result:
                // TODO: 11/23/2017

                tv_result.setText(""+a+" - "+b+" = "+ (a-b)+"\n Mình cùng làm tiếp nào");

                break;
        }
    }
}
