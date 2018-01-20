package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class ReminderTable {

    public static final String CreateSql =
            "CREATE TABLE REMINDER ( " +
                    "ID                     INTEGER PRIMARY KEY, " +
                    "REMINDER_FREQUENCY     INTEGER, " +
                    "REMINDER_START_TIME    DATE, " +
                    "REMINDER_INTERVAL      INTEGER " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS REMINDER";

    public static final String CreateBackupSql =
            "CREATE TABLE REMINDER_BKP AS SELECT * FROM REMINDER WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO REMINDER ( " +
                    "ID, " +
                    "REMINDER_FREQUENCY, " +
                    "REMINDER_START_TIME, " +
                    "REMINDER_INTERVAL " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "REMINDER_FREQUENCY, " +
                    "REMINDER_START_TIME, " +
                    "REMINDER_INTERVAL " +
                    "FROM REMINDER_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS REMINDER_BKP";

    public static final String TABLE_NAME = "REMINDER";

    public static final String ID = "ID";
    public static final String REMINDER_FREQUENCY = "REMINDER_FREQUENCY";
    public static final String REMINDER_START_TIME = "REMINDER_START_TIME";
    public static final String REMINDER_INTERVAL = "REMINDER_INTERVAL";

}
