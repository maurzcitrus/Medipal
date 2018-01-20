package sg.edu.nus.iss.ft08.medipal.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ReminderService.class);
        service.setAction("CREATE");
        context.startService(service);
    }
}

