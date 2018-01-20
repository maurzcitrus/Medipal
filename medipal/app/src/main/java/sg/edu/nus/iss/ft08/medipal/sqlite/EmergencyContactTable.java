package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class EmergencyContactTable {

    public static final String CreateSql =
            "CREATE TABLE EMERGENCY_CONTACT ( " +
                    "ID                                 INTEGER PRIMARY KEY, " +
                    "EMERGENCY_CONTACT_NAME             TEXT, " +
                    "EMERGENCY_CONTACT_NO               TEXT, " +
                    "EMERGENCY_CONTACT_TYPE             INTEGER, " +
                    "EMERGENCY_CONTACT_DESCRIPTION      TEXT, " +
                    "EMERGENCY_CONTACT_PRIORITY         INTEGER " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS EMERGENCY_CONTACT";

    public static final String CreateBackupSql =
            "CREATE TABLE EMERGENCY_CONTACT_BKP AS SELECT * FROM EMERGENCY_CONTACT WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO EMERGENCY_CONTACT ( " +
                    "ID, " +
                    "EMERGENCY_CONTACT_NAME, " +
                    "EMERGENCY_CONTACT_NO, " +
                    "EMERGENCY_CONTACT_TYPE, " +
                    "EMERGENCY_CONTACT_DESCRIPTION, " +
                    "EMERGENCY_CONTACT_PRIORITY " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "EMERGENCY_CONTACT_NAME, " +
                    "EMERGENCY_CONTACT_NO, " +
                    "EMERGENCY_CONTACT_TYPE, " +
                    "EMERGENCY_CONTACT_DESCRIPTION, " +
                    "EMERGENCY_CONTACT_PRIORITY " +
                    "FROM EMERGENCY_CONTACT_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS EMERGENCY_CONTACT_BKP";


    public static final String TABLE_NAME = "EMERGENCY_CONTACT";

    public static final String ID = "ID";
    public static final String EMERGENCY_CONTACT_NAME = "EMERGENCY_CONTACT_NAME";
    public static final String EMERGENCY_CONTACT_NO = "EMERGENCY_CONTACT_NO";
    public static final String EMERGENCY_CONTACT_TYPE = "EMERGENCY_CONTACT_TYPE";
    public static final String EMERGENCY_CONTACT_DESCRIPTION = "EMERGENCY_CONTACT_DESCRIPTION";
    public static final String EMERGENCY_CONTACT_PRIORITY = "EMERGENCY_CONTACT_PRIORITY";
}
