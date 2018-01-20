package sg.edu.nus.iss.ft08.medipal.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medipalDatabase";
    private static final int DATABASE_VERSION = 2;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppointmentTable.CreateSql);
        db.execSQL(CategoryTable.CreateSql);
        db.execSQL(ConsumptionTable.CreateSql);
        db.execSQL(EmergencyContactTable.CreateSql);
        db.execSQL(HealthBioTable.CreateSql);
        db.execSQL(MeasurementTable.CreateSql);
        db.execSQL(MedicineTable.CreateSql);
        db.execSQL(PersonalTable.CreateSql);
        db.execSQL(ReminderTable.CreateSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(AppointmentTable.CreateBackupSql);
        db.execSQL(AppointmentTable.DropSql);
        db.execSQL(AppointmentTable.CreateSql);
        db.execSQL(AppointmentTable.InsertFromBackupSql);
        db.execSQL(AppointmentTable.DropBackupSql);

        db.execSQL(CategoryTable.CreateBackupSql);
        db.execSQL(CategoryTable.DropSql);
        db.execSQL(CategoryTable.CreateSql);
        db.execSQL(CategoryTable.InsertFromBackupSql);
        db.execSQL(CategoryTable.DropBackupSql);

        db.execSQL(ConsumptionTable.CreateBackupSql);
        db.execSQL(ConsumptionTable.DropSql);
        db.execSQL(ConsumptionTable.CreateSql);
        db.execSQL(ConsumptionTable.InsertFromBackupSql);
        db.execSQL(ConsumptionTable.DropBackupSql);

        db.execSQL(EmergencyContactTable.CreateBackupSql);
        db.execSQL(EmergencyContactTable.DropSql);
        db.execSQL(EmergencyContactTable.CreateSql);
        db.execSQL(EmergencyContactTable.InsertFromBackupSql);
        db.execSQL(EmergencyContactTable.DropBackupSql);

        db.execSQL(HealthBioTable.CreateBackupSql);
        db.execSQL(HealthBioTable.DropSql);
        db.execSQL(HealthBioTable.CreateSql);
        db.execSQL(HealthBioTable.InsertFromBackupSql);
        db.execSQL(HealthBioTable.DropBackupSql);

        db.execSQL(MeasurementTable.CreateBackupSql);
        db.execSQL(MeasurementTable.DropSql);
        db.execSQL(MeasurementTable.CreateSql);
        db.execSQL(MeasurementTable.InsertFromBackupSql);
        db.execSQL(MeasurementTable.DropBackupSql);

        db.execSQL(MedicineTable.CreateBackupSql);
        db.execSQL(MedicineTable.DropSql);
        db.execSQL(MedicineTable.CreateSql);
        db.execSQL(MedicineTable.InsertFromBackupSql);
        db.execSQL(MedicineTable.DropBackupSql);

        db.execSQL(PersonalTable.CreateBackupSql);
        db.execSQL(PersonalTable.DropSql);
        db.execSQL(PersonalTable.CreateSql);
        db.execSQL(PersonalTable.InsertFromBackupSql);
        db.execSQL(PersonalTable.DropBackupSql);

        db.execSQL(ReminderTable.CreateBackupSql);
        db.execSQL(ReminderTable.DropSql);
        db.execSQL(ReminderTable.CreateSql);
        db.execSQL(ReminderTable.InsertFromBackupSql);
        db.execSQL(ReminderTable.DropBackupSql);
    }
}
