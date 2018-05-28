package com.example.admin.my_nha_tro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView LvNhatro;
    ArrayList<Nhatro> arrayNhatro;
    NhatroAdapter adapter;
    public static int kt=0;
    static String struser;
    static String strpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LvNhatro =(ListView) findViewById(R.id.lv);
        arrayNhatro=new ArrayList <>();
        adapter = new NhatroAdapter(this, R.layout.dong_nha_tro, arrayNhatro);
        LvNhatro.setAdapter(adapter);
        GetData("http://chonhatro.xyz/getdata.php");

    }


    private void GetData( String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener <JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject object= response.getJSONObject(i);
                                arrayNhatro.add(new Nhatro(
                                        object.getInt("ID"),
                                        object.getString("TieuDe"),
                                        object.getInt("Gia"),
                                        object.getString("DiaChi"),
                                        object.getString("Mota")

                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        LvNhatro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                               Toast.makeText(MainActivity.this, "Hiển thị trên bản đồ ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

    requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_nhatro,menu);
        getMenuInflater().inflate(R.menu.account,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_nhatro) {
            if(kt==1) startActivity(new Intent(MainActivity.this, add_nhatro.class));
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Please login before posting!!!");
                builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dangnhap();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        }
        if (item.getItemId() == R.id.add_acount) {
            if (kt==1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("You Logged!!!");
                builder.setPositiveButton("Log Out!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        kt = 0;
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            } else {
                dangnhap();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dangnhap(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_login);
        final TextView tvdangnhap = (TextView) dialog.findViewById(R.id.tvdangnhap);
        final EditText edtusername = (EditText) dialog.findViewById(R.id.edtusername);
        final EditText edtpassword = (EditText) dialog.findViewById(R.id.edtpass);
        final Button btnsub = (Button) dialog.findViewById(R.id.btnsub1);
        final Button btnadd = (Button) dialog.findViewById(R.id.btnadd1);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, dangky.class));

            }
        });

        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                struser = edtusername.getText().toString().trim();
                 strpass = edtpassword.getText().toString().trim();
                if (struser.isEmpty() || strpass.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("INFO ERROR!!!");
                    builder.setMessage("Please enter all information of the form!");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                } else {

                    checkpass("http://chonhatro.xyz/ktpass.php");
                }
            }

        });
        dialog.show();
    }

    private void checkpass(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener <String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {
                            kt = 1;
                            final Dialog dialog = new Dialog(MainActivity.this);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Very Good!!!");
                            builder.setMessage("Want to post now!!");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(MainActivity.this, add_nhatro.class));
                                    dialog.cancel();

                                }
                            });
                            builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap <>();
                params.put("user",struser);
                params.put("pass", strpass);
                return params;
            }
        };
    requestQueue.add(stringRequest);
    }
}
