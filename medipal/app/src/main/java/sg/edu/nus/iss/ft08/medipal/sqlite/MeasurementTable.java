package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class MeasurementTable {

    public static final String CreateSql =
            "CREATE TABLE MEASUREMENT ( " +
                    "ID                         INTEGER PRIMARY KEY, " +
                    "MEASUREMENT_SYSTOLIC       INTEGER, " +
                    "MEASUREMENT_DIASTOLIC      INTEGER, " +
                    "MEASUREMENT_PULSE          INTEGER, " +
                    "MEASUREMENT_TEMPERATURE    DECIMAL(5,2), " +
                    "MEASUREMENT_WEIGHT         INTEGER," +
                    "MEASUREMENT_MEASURED_ON    DATE," +
                    "MEASUREMENT_NOTES          TEXT " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS MEASUREMENT";

    public static final String CreateBackupSql =
            "CREATE TABLE MEASUREMENT_BKP AS SELECT * FROM MEASUREMENT WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO MEASUREMENT ( " +
                    "ID, " +
                    "MEASUREMENT_SYSTOLIC, " +
                    "MEASUREMENT_DIASTOLIC, " +
                    "MEASUREMENT_PULSE, " +
                    "MEASUREMENT_TEMPERATURE, " +
                    "MEASUREMENT_WEIGHT, " +
                    "MEASUREMENT_MEASURED_ON, " +
                    "MEASUREMENT_NOTES " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "MEASUREMENT_SYSTOLIC, " +
                    "MEASUREMENT_DIASTOLIC, " +
                    "MEASUREMENT_PULSE, " +
                    "MEASUREMENT_TEMPERATURE, " +
                    "MEASUREMENT_WEIGHT, " +
                    "MEASUREMENT_MEASURED_ON, " +
                    "MEASUREMENT_NOTES " +
                    "FROM MEASUREMENT_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS MEASUREMENT_BKP";

    public static final String TABLE_NAME = "MEASUREMENT";

    public static final String ID = "ID";
    public static final String MEASUREMENT_SYSTOLIC = "MEASUREMENT_SYSTOLIC";
    public static final String MEASUREMENT_DIASTOLIC = "MEASUREMENT_DIASTOLIC";
    public static final String MEASUREMENT_PULSE = "MEASUREMENT_PULSE";
    public static final String MEASUREMENT_TEMPERATURE = "MEASUREMENT_TEMPERATURE";
    public static final String MEASUREMENT_WEIGHT = "MEASUREMENT_WEIGHT";
    public static final String MEASUREMENT_MEASURED_ON = "MEASUREMENT_MEASURED_ON";
    public static final String MEASUREMENT_NOTES = "MEASUREMENT_NOTES";
}
