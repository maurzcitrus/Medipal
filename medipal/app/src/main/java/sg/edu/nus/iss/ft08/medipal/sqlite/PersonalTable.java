package sg.edu.nus.iss.ft08.medipal.sqlite;


public abstract class PersonalTable {

    public static final String CreateSql =
            "CREATE TABLE PERSON ( " +
                    "ID                 INTEGER PRIMARY KEY, " +
                    "PERSON_NAME        TEXT, " +
                    "PERSON_DOB         DATE, " +
                    "PERSON_ID_NO       TEXT, " +
                    "PERSON_ADDRESS     TEXT, " +
                    "PERSON_POSTAL_CODE TEXT, " +
                    "PERSON_HEIGHT      INTEGER, " +
                    "PERSON_BLOOD_TYPE  TEXT " +
                    ") ";

    public static final String DropSql =
            "DROP TABLE IF EXISTS PERSON";

    public static final String CreateBackupSql =
            "CREATE TABLE PERSON_BKP AS SELECT * FROM PERSON WHERE ID IS NOT NULL";

    public static final String InsertFromBackupSql =
            "INSERT INTO PERSON ( " +
                    "ID, " +
                    "PERSON_NAME, " +
                    "PERSON_DOB, " +
                    "PERSON_ID_NO, " +
                    "PERSON_ADDRESS, " +
                    "PERSON_POSTAL_CODE, " +
                    "PERSON_HEIGHT, " +
                    "PERSON_BLOOD_TYPE " +
                    ") " +
                    "SELECT " +
                    "ID, " +
                    "PERSON_NAME, " +
                    "PERSON_DOB, " +
                    "PERSON_ID_NO, " +
                    "PERSON_ADDRESS, " +
                    "PERSON_POSTAL_CODE, " +
                    "PERSON_HEIGHT, " +
                    "PERSON_BLOOD_TYPE " +
                    "FROM PERSON_BKP";

    public static final String DropBackupSql =
            "DROP TABLE IF EXISTS PERSON_BKP";

    public static final String TABLE_NAME = "PERSON";

    public static final String ID = "ID";
    public static final String PERSON_NAME = "PERSON_NAME";
    public static final String PERSON_DOB = "PERSON_DOB";
    public static final String PERSON_ID_NO = "PERSON_ID_NO";
    public static final String PERSON_ADDRESS = "PERSON_ADDRESS";
    public static final String PERSON_POSTAL_CODE = "PERSON_POSTAL_CODE";
    public static final String PERSON_HEIGHT = "PERSON_HEIGHT";
    public static final String PERSON_BLOOD_TYPE = "PERSON_BLOOD_TYPE";

}
