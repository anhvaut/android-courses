package btob.finalbilingualstories;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //Cai dat 2,5s man hinh tu chuyen
        Thread countdowntime = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                }
                catch (Exception e) {
                    //
                }
                finally {
                    Intent nextactivity = new Intent(LaunchActivity.this,MainActivity.class);
                    startActivity(nextactivity);
                }
            }
        };
        countdowntime.start();
    }

    protected  void onPause() {
        super.onPause();
        finish();
    }

}

