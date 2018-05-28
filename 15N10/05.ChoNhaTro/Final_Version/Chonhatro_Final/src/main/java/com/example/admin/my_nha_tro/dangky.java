package com.example.admin.my_nha_tro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class dangky extends MainActivity  {
    TextView tvdangky;
    EditText edtusername2, edtpassword2,edtpassword3;
    Button btndangky, btnhuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        tvdangky =(TextView) findViewById(R.id.tvdangky);
        edtusername2 =(EditText) findViewById(R.id.edtusername2);
        edtpassword2=(EditText) findViewById(R.id.edtpassword2);
        edtpassword3=(EditText) findViewById(R.id.edtpassword3);
        btndangky=(Button) findViewById(R.id.btndangky);
        btnhuy=(Button) findViewById(R.id.btnhuy);


        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String struser2 = edtusername2.getText().toString().trim();
                String strpass2 = edtpassword2.getText().toString().trim();
                String strpass3 = edtpassword3.getText().toString().trim();

                if(struser2.isEmpty()||strpass2.isEmpty()||strpass3.isEmpty()||!strpass2.equals(strpass3)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(dangky.this);
                    builder.setTitle("INFO ERROR!!!");
                    builder.setMessage("Please enter all information of the form or erro confirm password!");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else{

                    thongtinkhachhang("http://chonhatro.xyz/thongtinkhachhang.php");
            }
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dangky.this, MainActivity.class));
                finish();
            }
        });
    }


    private void thongtinkhachhang(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {
                kt=1;
                Toast.makeText(dangky.this,"đăng ký thành công", Toast.LENGTH_LONG).show();
                startActivity(new Intent(dangky.this,MainActivity.class));
                finish();}

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("User",edtusername2.getText().toString());
                params.put("Pass",edtpassword2.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}
