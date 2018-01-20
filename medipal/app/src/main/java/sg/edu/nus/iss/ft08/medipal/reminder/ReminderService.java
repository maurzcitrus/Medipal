package sg.edu.nus.iss.ft08.medipal.reminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class ReminderService extends IntentService{

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int alarmID = intent.getIntExtra("ID", 0);
        String alarmMessage = intent.getStringExtra("MESSAGE");
        long alarmDateTime = intent.getLongExtra("DATETIME", new Date().getTime());
        String action = intent.getStringExtra("ACTION");
        executeService(action, alarmID, alarmMessage, alarmDateTime);
    }

    private void executeService(String action, int alarmID, String alarmMessage,
                                long alarmDateTime) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("ID", alarmID);
        intent.putExtra("MESSAGE", alarmMessage);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmID,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if ("CREATE".equals(action)) {
            if (alarmID > 10000){
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmDateTime, pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmDateTime,
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        } else if ("CANCEL".equals(action)) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}