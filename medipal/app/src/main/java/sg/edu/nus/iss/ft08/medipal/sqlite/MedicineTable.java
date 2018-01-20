package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class MedicineTable {

    public static final String CreateSql =
            "CREATE TABLE MEDICINE ( " +
                    "ID                     INTEGER PRIMARY KEY, " +
                    "MEDICINE_NAME          TEXT, " +
                    "MEDICINE_DESCRIPTION   TEXT, " +
                    "MEDICINE_CATEGORY_ID   INTEGER, " +
                    "MEDICINE_REMINDER_ID   INTEGER, " +
                    "MEDICINE_REMINDER_FLAG TEXT, " +
                    "MEDICINE_QUANTITY      INTEGER, " +
                    "MEDICINE_FREQUENCY     INTEGER, " +
                    "MEDICINE_DOSAGE        INTEGER, " +
                    "MEDICINE_DATE_ISSUED   DATE, " +
                    "MEDICINE_THRESHOLD     INTEGER, " +
                    "MEDICINE_EXPIRE_FACTOR INTEGER " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS MEDICINE";


    public static final String CreateBackupSql =
            "CREATE TABLE MEDICINE_BKP AS SELECT * FROM MEDICINE WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO MEDICINE (" +
                    "ID, " +
                    "MEDICINE_NAME, " +
                    "MEDICINE_DESCRIPTION, " +
                    "MEDICINE_CATEGORY_ID, " +
                    "MEDICINE_REMINDER_ID, " +
                    "MEDICINE_REMINDER_FLAG, " +
                    "MEDICINE_QUANTITY, " +
                    "MEDICINE_DOSAGE, " +
                    "MEDICINE_DATE_ISSUED, " +
                    "MEDICINE_THRESHOLD, " +
                    "MEDICINE_EXPIRE_FACTOR " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "MEDICINE_NAME, " +
                    "MEDICINE_DESCRIPTION, " +
                    "MEDICINE_CATEGORY_ID, " +
                    "MEDICINE_REMINDER_ID, " +
                    "MEDICINE_REMINDER_FLAG, " +
                    "MEDICINE_QUANTITY, " +
                    "MEDICINE_DOSAGE, " +
                    "MEDICINE_DATE_ISSUED, " +
                    "MEDICINE_THRESHOLD, " +
                    "MEDICINE_EXPIRE_FACTOR " +
                    "FROM MEDICINE_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS MEDICINE_BKP";


    public static final String TABLE_NAME = "MEDICINE";

    public static final String ID = "ID";
    public static final String MEDICINE_NAME = "MEDICINE_NAME";
    public static final String MEDICINE_DESCRIPTION = "MEDICINE_DESCRIPTION";
    public static final String MEDICINE_CATEGORY_ID = "MEDICINE_CATEGORY_ID";
    public static final String MEDICINE_REMINDER_ID = "MEDICINE_REMINDER_ID";
    public static final String MEDICINE_REMINDER_FLAG = "MEDICINE_REMINDER_FLAG";
    public static final String MEDICINE_QUANTITY = "MEDICINE_QUANTITY";
    public static final String MEDICINE_FREQUENCY = "MEDICINE_FREQUENCY";
    public static final String MEDICINE_DOSAGE = "MEDICINE_DOSAGE";
    public static final String MEDICINE_DATE_ISSUED = "MEDICINE_DATE_ISSUED";
    public static final String MEDICINE_THRESHOLD = "MEDICINE_THRESHOLD";
    public static final String MEDICINE_EXPIRE_FACTOR = "MEDICINE_EXPIRE_FACTOR";

}
