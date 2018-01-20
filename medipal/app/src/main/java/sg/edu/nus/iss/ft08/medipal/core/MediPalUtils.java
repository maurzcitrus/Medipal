package sg.edu.nus.iss.ft08.medipal.core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.ft08.medipal.R;

public abstract class MediPalUtils {

    public static String toString_dd_MMM_yyyy(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public static Date toDate_from_dd_MMM_yyyy(String value) {
        if (value == null || value.isEmpty())
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getToday() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public static String toString_dd_MMM_yyyy(int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return toString_dd_MMM_yyyy(c.getTime());
    }

    public static String toString_hh_mm_a(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                hourOfDay,
                minute);

        return toString_hh_mm_a(c.getTime());
    }

    public static String toString_d_MMM_yyyy_H_mm(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public static Date toDate_from_d_MMM_yyyy_H_mm(String value) {
        if (value == null || value.isEmpty())
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.getDefault());
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int toInt(String value) {
        if (value == null || value.isEmpty())
            return 0;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Float toFloat(String value) {
        if (value == null || value.isEmpty())
            return 0.0f;

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    public static Date toTime_from_hh_mm_a(String value) {
        if (value == null || value.isEmpty())
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toString_hh_mm_a(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(date);
    }

    public static Date setCurrentDate(Date time){
        Calendar setCurrentTime = Calendar.getInstance();
        setCurrentTime.setTime(time);
        setCurrentTime.set(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE));
        return setCurrentTime.getTime();
    }
}
