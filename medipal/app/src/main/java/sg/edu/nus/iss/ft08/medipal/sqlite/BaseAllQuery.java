package sg.edu.nus.iss.ft08.medipal.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

public abstract class BaseAllQuery<T> extends AsyncTask<Void, Void, ArrayList<T>> {

    protected SQLiteDatabase db;
    protected DatabaseHelper helper;
    protected Context context;

    public BaseAllQuery(Context context) {
        this.context = context;
        helper = DatabaseHelper.getInstance(this.context);
        db = helper.getReadableDatabase();
    }

    @Override
    protected ArrayList<T> doInBackground(Void... params) {
        return executeQuery();
    }

    @Override
    protected void onPostExecute(ArrayList<T> records) {
        if (helper != null)
            helper.close();

        if (db != null)
            db = null;
    }

    // sub classes must implement this method
    // to retrieve records from database and return
    protected abstract ArrayList<T> executeQuery();
}
