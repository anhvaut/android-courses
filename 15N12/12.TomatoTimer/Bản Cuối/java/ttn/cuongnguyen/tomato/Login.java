package ttn.cuongnguyen.tomato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button Facebook, Twitter, Register;
    EditText TenDangNhap, Matkhau;
    TextView Canhbao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Facebook= (Button) findViewById(R.id.btnFb);
        Twitter= (Button) findViewById(R.id.btnTt);
        Canhbao= (TextView) findViewById(R.id.txtCanhbao);
        Register= (Button) findViewById(R.id.btnDn);
        Facebook.setOnClickListener(this);
        Twitter.setOnClickListener(this);
        Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i =view.getId();
        switch (i){
            case R.id.btnDn:
            {

                String tenDangNhap = TenDangNhap.getText().toString();
                String matkhau= Matkhau.getText().toString();
                String admin = "Cuongnguyen";
                String mk="9801376248";
                if(tenDangNhap.equalsIgnoreCase(admin)&& matkhau.equalsIgnoreCase(mk)){
                    Canhbao.setText("Mật Khẩu Chính Xác");
                    break;
                }
            }

            case R.id.btnFb:
                Intent intent = new Intent(Login.this, Facebook.class);
                startActivity(intent);
                break;
            case R.id.btnTt:
                intent = new Intent(Login.this, Twitter.class);
                startActivity(intent);
                break;

        }

    }
}
