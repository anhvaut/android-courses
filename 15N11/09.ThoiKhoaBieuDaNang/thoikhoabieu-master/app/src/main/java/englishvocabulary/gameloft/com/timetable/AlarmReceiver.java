package englishvocabulary.gameloft.com.timetable;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by PC on 28-Sep-17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent myIntent = new Intent(context, notification.class);
        //context.startActivity(myIntent);

        Intent myIntent = new Intent(context,Music.class);
        context.startService(myIntent);

    }
}
