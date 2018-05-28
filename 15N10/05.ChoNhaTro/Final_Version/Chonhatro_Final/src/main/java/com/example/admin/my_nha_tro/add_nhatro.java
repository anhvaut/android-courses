package com.example.admin.my_nha_tro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class add_nhatro extends AppCompatActivity {
    TextView tvthem;
    EditText edttieude, edtgia, edtdiachi, edtmota;
    Button btn;

    ImageButton ibtnCamera, ibtnFolder;
    ImageView imgHinh;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhatro);
        tvthem =(TextView) findViewById(R.id.textView);
        edttieude=(EditText) findViewById(R.id.editTextTD);
        edtgia =(EditText) findViewById(R.id.editTextGia);
        edtdiachi=(EditText) findViewById(R.id.editTextDC);
        edtmota=(EditText) findViewById(R.id.editTextMT);
        btn=(Button) findViewById(R.id.btn);

        imgHinh = (ImageView) findViewById(R.id.imgHinh);
        ibtnCamera = (ImageButton) findViewById(R.id.ibtnCamera);
        ibtnFolder = (ImageButton) findViewById(R.id.ibtnFolder);


        ibtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent , REQUEST_CODE_CAMERA);
            }
        });
        ibtnFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edttieude.getText().toString().equals("")||edtgia.getText().toString().equals("")||
                        edtdiachi.getText().toString().equals("")||edtmota.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(add_nhatro.this);
                    builder.setTitle("INFO ERROR!!!");
                    builder.setMessage("Please enter all information of the form!");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }
                else
                InsertData("http://chonhatro.xyz/insert.php");

            }
        });

    }







    private void InsertData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener <String>() {
            @Override
            public void onResponse(String response) {
                            Toast.makeText(add_nhatro.this,"upload thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(add_nhatro.this,MainActivity.class));}

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap <>();
                params.put("TieudeNhaTro",edttieude.getText().toString());
                params.put("GiaNhaTro",edtgia.getText().toString());
                params.put("DiachiNhaTro",edtdiachi.getText().toString());
                params.put("MotaNhaTro",edtmota.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
