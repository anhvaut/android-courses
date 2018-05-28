package ttn.cuongnguyen.tomato;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tomato extends AppCompatActivity {
    Button btnStarttmt, btnPausetmt, btnStoptmt;
    TextView txtTimer;
    int xuLySuKien= 1;
    boolean aBoolean;
    long lStartTime, lPauseTime, lSystemTime = 0L;
    ProgressBar prTomato;
    DonutProgress prTomato2;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            prTomato.setProgress(msg.what+1);
        }
    };
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
            handler.postDelayed(this,0);


        }
    };
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tomato);
        btnStarttmt= (Button)  findViewById(R.id.btn_Starttmt);
        btnPausetmt= (Button) findViewById(R.id.btn_Pausetmt);
        btnStoptmt= (Button) findViewById(R.id.btn_Stoptmt);
        txtTimer = (TextView) findViewById(R.id.txtHienThitmt);
        prTomato = (ProgressBar) findViewById(R.id.prTomato);
        prTomato2 = (DonutProgress) findViewById(R.id.prTomato2);
        prTomato.setVisibility(View.INVISIBLE);
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
                xuLyLy();
                xuLyLy2();
                if(isRun) return;
                isRun= true;
                lStartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });
    }

    void clickStop() {
        btnStoptmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prTomato.setProgress(0);
                prTomato2.setProgress(0);

                aBoolean = true;
                if(!isRun)
                    return;
                isRun = false;
                txtTimer.setText("00:00");
                lPauseTime = 0;
                handler.removeCallbacks(runnable);

            }
        });

    }
    void xuLyLy (){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (xuLySuKien<100){
                    xuLySuKien=prTomato.getProgress();
                    handler.sendEmptyMessage(xuLySuKien);
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
                handler.removeCallbacks(runnable);

            }
        });
    }

}