package sg.edu.nus.iss.ft08.medipal.reminder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import sg.edu.nus.iss.ft08.medipal.MainActivity;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;

public class ReminderReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("ID", 0);
        String message = intent.getStringExtra("MESSAGE");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        ReminderServiceManager.triggerNotification(id, context, message, pendingIntent);
    }
}