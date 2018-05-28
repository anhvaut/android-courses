package ttn.cuongnguyen.tomato;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
public class Clock extends AppCompatActivity {
    private Button btnStar, btnStop;
    private TextView txtHienThi;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baothuc);
        btnStar= (Button) findViewById(R.id.btn_star);
        btnStop= (Button) findViewById(R.id.btn_stop);
        txtHienThi=(TextView) findViewById(R.id.txtHienThi);
        timePicker= (TimePicker) findViewById(R.id.timePicker);
        calendar=Calendar.getInstance();
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(Clock.this, ClockAlarm.class);

        btnStar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                int gio=timePicker.getCurrentHour();
                int phut=timePicker.getCurrentMinute();
                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);
                if(gio>12){
                    string_gio=String.valueOf(gio-12);
                }
                if(phut<10){
                    string_phut="0"+String.valueOf(phut);
                }


                pendingIntent = PendingIntent.getBroadcast(Clock.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                txtHienThi.setText("Giờ Bạn Đặt Là "+string_gio+":"+string_phut);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHienThi.setText("Bạn Đã Dừng Lại");
                pendingIntent.cancel();
            }
        });
    }
}
