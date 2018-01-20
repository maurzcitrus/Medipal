package sg.edu.nus.iss.ft08.medipal.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public abstract class BaseCommand<T> extends AsyncTask<T, Void, Long> {

    protected SQLiteDatabase db;
    protected DatabaseHelper helper;
    protected Context context;

    public BaseCommand(Context context) {
        this.context = context;
        helper = DatabaseHelper.getInstance(this.context);
        db = helper.getWritableDatabase();
    }

    @Override
    protected Long doInBackground(T... params) {
        return executeCommand(params);
    }

    @Override
    protected void onPostExecute(Long result) {
        if (helper != null)
            helper.close();

        if (db != null)
            db = null;
    }

    // sub classes must implement this method
    // to insert/update/delete records into database
    protected abstract Long executeCommand(T... params);
}
