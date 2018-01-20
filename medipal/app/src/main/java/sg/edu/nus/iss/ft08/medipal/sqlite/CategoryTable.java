package sg.edu.nus.iss.ft08.medipal.sqlite;

public abstract class CategoryTable {
    public static final String CreateSql =
            "CREATE TABLE CATEGORY (" +
                    "ID                     INTEGER PRIMARY KEY, " +
                    "CATEGORY_NAME          TEXT, " +
                    "CATEGORY_CODE          TEXT, " +
                    "CATEGORY_DESCRIPTION   TEXT, " +
                    "CATEGORY_REMINDER_FLAG TEXT " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS CATEGORY";

    public static final String CreateBackupSql =
            "CREATE TABLE CATEGORY_BKP AS SELECT * FROM CATEGORY WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO CATEGORY ( " +
                    "ID, " +
                    "CATEGORY_NAME, " +
                    "CATEGORY_CODE, " +
                    "CATEGORY_DESCRIPTION, " +
                    "CATEGORY_REMINDER_FLAG " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "CATEGORY_NAME, " +
                    "CATEGORY_CODE, " +
                    "CATEGORY_DESCRIPTION, " +
                    "CATEGORY_REMINDER_FLAG " +
                    "FROM CATEGORY_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS CATEGORY_BKP";

    public static final String TABLE_NAME = "CATEGORY";

    public static final String ID = "ID";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    public static final String CATEGORY_CODE = "CATEGORY_CODE";
    public static final String CATEGORY_DESCRIPTION = "CATEGORY_DESCRIPTION";
    public static final String CATEGORY_REMINDER_FLAG = "CATEGORY_REMINDER_FLAG";
}
