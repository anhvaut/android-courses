package ttn.cuongnguyen.tomato;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ClockAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myInTent = new Intent(context, ClockMusic.class);
        context.startService(myInTent);

    }
}
