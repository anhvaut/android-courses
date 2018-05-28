package ttn.cuongnguyen.tomato;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import android.util.Log;
public class ClockMusic extends Service {
    MediaPlayer mediaPlayer;
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this,R.raw.nhacchuong);
        mediaPlayer.start();
        return START_NOT_STICKY;
    }
}
