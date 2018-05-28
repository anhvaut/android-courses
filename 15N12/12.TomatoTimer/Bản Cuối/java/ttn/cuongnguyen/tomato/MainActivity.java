package ttn.cuongnguyen.tomato;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    Button button;
    NavigationView navigationView;

    ActionBar actionBar;
    Button btnStarttmt, btnPausetmt, btnStoptmt;
    TextView txtTimer;
    int xuLySuKien= 1;
    boolean aBoolean;
    long lStartTime, lPauseTime, lSystemTime = 0L;

    DonutProgress prTomato2;

    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            prTomato2.setProgress(msg.what+1);
        }
    };

    boolean isRun;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = (long)(lUpdateTime/1000);
            long mins=secs/60;
            secs=secs%60;
            long milliseconds = (long)(lUpdateTime%1000);
            txtTimer.setText(""+String.format("%02d",mins)+":"+String.format("%02d", secs));
            handler2.postDelayed(this,0);


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tbTubar);
        drawerLayout = (DrawerLayout) findViewById(R.id.dwLayout);
        navigationView= (NavigationView) findViewById(R.id.navigation);
        btnStarttmt= (Button)  findViewById(R.id.btn_Starttmt);
        btnPausetmt= (Button) findViewById(R.id.btn_Pausetmt);
        btnStoptmt= (Button) findViewById(R.id.btn_Stoptmt);
        txtTimer = (TextView) findViewById(R.id.txtHienThitmt);
        prTomato2 = (DonutProgress) findViewById(R.id.prTomato2);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();


        actionBar.setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerToggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

    }
    protected  void onStart() {
        super.onStart();
        clickStart();
        clickStop();
        clickPause();
    }
    void clickStart(){
        btnStarttmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBoolean = false;
                xuLyLy2();
                if(isRun) return;
                isRun= true;
                lStartTime = SystemClock.uptimeMillis();
                handler2.postDelayed(runnable, 0);
            }
        });
    }

    void clickStop() {
        btnStoptmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prTomato2.setProgress(0);

                aBoolean = true;
                if(!isRun)
                    return;
                isRun = false;
                txtTimer.setText("00:00");
                lPauseTime = 0;
                handler2.removeCallbacks(runnable);

            }
        });

    }


    void xuLyLy2 (){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (xuLySuKien<100){
                    xuLySuKien=prTomato2.getProgress();
                    handler2.sendEmptyMessage(xuLySuKien);
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(aBoolean){
                        break;
                    }
                }
            }
        });
        thread.start();
    }



    void clickPause() {
        btnPausetmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isRun)
                    return;
                isRun = false;
                lPauseTime+=lSystemTime;
                handler2.removeCallbacks(runnable);

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemLich) {
            Intent intent= new Intent(MainActivity.this, LichBieu.class);
            startActivity(intent);
        } else if (id == R.id.itemBaoThuc) {
            Intent intent= new Intent(MainActivity.this, Clock.class);
            startActivity(intent);
        } else if (id == R.id.itemCaiDat) {
            Intent intent= new Intent(MainActivity.this, Setting.class);
            startActivity(intent);
        } else if (id == R.id.itemDangNhap) {
            Intent intent= new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        return true;
    }


}
