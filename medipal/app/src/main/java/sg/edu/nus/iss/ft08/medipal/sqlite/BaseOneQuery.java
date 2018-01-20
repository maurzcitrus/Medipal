package sg.edu.nus.iss.ft08.medipal.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public abstract class BaseOneQuery<T> extends AsyncTask<Void, Void, T> {

    protected SQLiteDatabase db;
    protected DatabaseHelper helper;
    protected Context context;

    public BaseOneQuery(Context context) {
        this.context = context;
        helper = DatabaseHelper.getInstance(this.context);
        db = helper.getReadableDatabase();
    }

    @Override
    protected T doInBackground(Void... params) {
        return executeQuery();
    }

    @Override
    protected void onPostExecute(T record) {
        if (helper != null)
            helper.close();

        if (db != null)
            db = null;
    }

    // sub classes must implement this method
    // to retrieve records from database and return
    protected abstract T executeQuery();
}
