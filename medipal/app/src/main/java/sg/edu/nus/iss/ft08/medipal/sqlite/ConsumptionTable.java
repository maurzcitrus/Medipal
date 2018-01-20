package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class ConsumptionTable {

    public static final String CreateSql =
            "CREATE TABLE CONSUMPTION ( " +
                    "ID                             INTEGER PRIMARY KEY, " +
                    "CONSUMPTION_MEDICINE_ID        INTEGER, " +
                    "CONSUMPTION_MEDICINE_QUANTITY  INTEGER, " +
                    "CONSUMPTION_CONSUMED_ON        DATE " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS CONSUMPTION";

    public static final String CreateBackupSql =
            "CREATE TABLE CONSUMPTION_BKP AS SELECT * FROM CONSUMPTION WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO CONSUMPTION ( " +
                    "ID, " +
                    "CONSUMPTION_MEDICINE_ID, " +
                    "CONSUMPTION_MEDICINE_QUANTITY, " +
                    "CONSUMPTION_CONSUMED_ON " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "CONSUMPTION_MEDICINE_ID, " +
                    "CONSUMPTION_MEDICINE_QUANTITY, " +
                    "CONSUMPTION_CONSUMED_ON " +
                    "FROM CONSUMPTION_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS CONSUMPTION_BKP";


    public static final String TABLE_NAME = "CONSUMPTION";

    public static final String ID = "ID";
    public static final String CONSUMPTION_MEDICINE_ID = "CONSUMPTION_MEDICINE_ID";
    public static final String CONSUMPTION_MEDICINE_QUANTITY = "CONSUMPTION_MEDICINE_QUANTITY";
    public static final String CONSUMPTION_CONSUMED_ON = "CONSUMPTION_CONSUMED_ON";
}
