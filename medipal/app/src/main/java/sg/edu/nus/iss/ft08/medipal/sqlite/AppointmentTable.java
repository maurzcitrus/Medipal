package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class AppointmentTable {

    public static final String CreateSql =
            "CREATE TABLE APPOINTMENT ( " +
                    "ID                         INTEGER PRIMARY KEY, " +
                    "APPOINTMENT_LOCATION       TEXT, " +
                    "APPOINTMENT_DATE_TIME      DATE, " +
                    "APPOINTMENT_DESCRIPTION    TEXT, " +
                    "APPOINTMENT_REMINDER_FLAG  TEXT " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS APPOINTMENT";

    public static final String CreateBackupSql =
            "CREATE TABLE APPOINTMENT_BKP AS SELECT * FROM APPOINTMENT WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO APPOINTMENT ( " +
                    "ID, " +
                    "APPOINTMENT_LOCATION, " +
                    "APPOINTMENT_DATE_TIME, " +
                    "APPOINTMENT_DESCRIPTION, " +
                    "APPOINTMENT_REMINDER_FLAG " +
                    ") " +
                    "SELECT " +
                    "ID," +
                    "APPOINTMENT_LOCATION, " +
                    "APPOINTMENT_DATE_TIME, " +
                    "APPOINTMENT_DESCRIPTION, " +
                    "APPOINTMENT_REMINDER_FLAG " +
                    "FROM APPOINTMENT_BKP ";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS APPOINTMENT_BKP";

    public static final String TABLE_NAME = "APPOINTMENT";

    public static final String ID = "ID";
    public static final String APPOINTMENT_LOCATION = "APPOINTMENT_LOCATION";
    public static final String APPOINTMENT_DATE_TIME = "APPOINTMENT_DATE_TIME";
    public static final String APPOINTMENT_DESCRIPTION = "APPOINTMENT_DESCRIPTION";
    public static final String APPOINTMENT_REMINDER_FLAG = "APPOINTMENT_REMINDER_FLAG";

}
