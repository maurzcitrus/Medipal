package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class HealthBioTable {
    public static final String CreateSql =
            "CREATE TABLE HEALTH_BIO (" +
                    "ID                         INTEGER PRIMARY KEY, " +
                    "HEALTH_BIO_CONDITION       TEXT, " +
                    "HEALTH_BIO_START_DATE      DATE, " +
                    "HEALTH_BIO_CONDITION_TYPE  TEXT " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS HEALTH_BIO";

    public static final String CreateBackupSql =
            "CREATE TABLE HEALTH_BIO_BKP AS SELECT * FROM HEALTH_BIO WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO HEALTH_BIO (" +
                    "ID, " +
                    "HEALTH_BIO_CONDITION, " +
                    "HEALTH_BIO_START_DATE, " +
                    "HEALTH_BIO_CONDITION_TYPE " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "HEALTH_BIO_CONDITION, " +
                    "HEALTH_BIO_START_DATE, " +
                    "HEALTH_BIO_CONDITION_TYPE " +
                    "FROM HEALTH_BIO_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS HEALTH_BIO_BKP";

    public static final String TABLE_NAME = "HEALTH_BIO";

    public static final String ID = "ID";
    public static final String HEALTH_BIO_CONDITION = "HEALTH_BIO_CONDITION";
    public static final String HEALTH_BIO_START_DATE = "HEALTH_BIO_START_DATE";
    public static final String HEALTH_BIO_CONDITION_TYPE = "HEALTH_BIO_CONDITION_TYPE";
}
