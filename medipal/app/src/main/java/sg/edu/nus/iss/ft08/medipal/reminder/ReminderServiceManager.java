package sg.edu.nus.iss.ft08.medipal.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;

public abstract class ReminderServiceManager {

    public static void reminderIntentService(String action, int id, String message, long time,
                                                  Context context){
        Intent service = new Intent(context, ReminderService.class);
        service.putExtra("ID", id);
        service.putExtra("MESSAGE", message);
        service.putExtra("DATETIME", time);
        service.putExtra("ACTION", action);
        context.startService(service);
    }

    public static void initConsumptionReminder(String action, int id, String message,
                                           ArrayList<Long> dateTimeArray, Context context) {
        for (int i = 0; i < dateTimeArray.size(); i++) {
            reminderIntentService(action, id, message, dateTimeArray.get(i), context);
        }
    }

    public static ArrayList<Long> getArrayOfTimeInMilliSec(int frequency, Date startDateTime, int interval){
        ArrayList<Long> dateArrayMilliSeconds = new ArrayList<>();
        for (int i=0; i<frequency; i++){
            long timeMilliSec = startDateTime.getTime() + (interval * 60 * 60 * 1000 * i);
            dateArrayMilliSeconds.add(timeMilliSec);
        }
        return dateArrayMilliSeconds;
    }

    public static void triggerNotification(int id, Context context, String message,
                                           PendingIntent pendingIntent){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new NotificationCompat.Builder(context)
                .setContentTitle("REMINDER")
                .setContentText(message)
                .setSound(sound)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .build();
        notify.defaults = Notification.DEFAULT_VIBRATE;
        notify.defaults = Notification.DEFAULT_SOUND;
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        notifyManager.notify(id, notify);
    }

    public static void startConsumptionReminder(Context context, Medicine medicine) {
        Reminder reminder = medicine.getReminder();
        String message = "Time to take your medicine: ".concat(medicine.getName());
        if (reminder.getStartTime() != null) {
            ArrayList<Long> datesMilliSeconds =
                    new ArrayList<>(getArrayOfTimeInMilliSec(reminder.getFrequency(),
                            MediPalUtils.setCurrentDate(reminder.getStartTime()),
                            reminder.getInterval()));
            initConsumptionReminder("CREATE", medicine.getId(), message,
                    datesMilliSeconds, context);
        }
    }

    public static void cancelConsumptionReminder(Context context, Medicine medicine) {
        Reminder reminder = medicine.getReminder();
        if (reminder.getStartTime() != null) {
            ArrayList<Long> datesMilliSeconds =
                    new ArrayList<>(getArrayOfTimeInMilliSec(reminder.getFrequency(),
                            MediPalUtils.setCurrentDate(reminder.getStartTime()),
                            reminder.getInterval()));
            initConsumptionReminder("CANCEL", medicine.getId(), "CANCEL NOTIFICATION",
                    datesMilliSeconds, context);
        }
    }

    public static void startAppointmentReminder(Context context, Appointment appointment){
        String message = "You have an appointment at: ".concat(appointment.getLocation());
        if (appointment.getAppointmentDateTime() != null){
            reminderIntentService("CREATE", 10000+appointment.getId(), message,
                    appointment.getAppointmentDateTime().getTime()-60*60*1000, context);
        }
    }

    public static void cancelAppointmentReminder(Context context, Appointment appointment){
        if (appointment.getAppointmentDateTime() != null){
            reminderIntentService("CANCEL", 10000+appointment.getId(), "CANCEL REMINDER",
                    appointment.getAppointmentDateTime().getTime()-60*60*1000, context);
        }
    }

    public static void startThresholdReminder(Context context, Medicine medicine){
        String message = "Out of Stock!!!: ".concat(medicine.getName());
        reminderIntentService("CREATE", 20000+medicine.getId(), message,
                Calendar.getInstance().getTimeInMillis(), context);
    }
}
