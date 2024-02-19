package pl.zoltowskimarcin.java.app.sql;

public class JdbcTestConstants {

    public static final String ANIMAL_DROP_TABLE_QUERY = "DROP TABLE IF EXISTS animal";
    public static final String ANIMAL_DROP_SEQ_QUERY = "DROP SEQUENCE IF EXISTS seq";

    public static final String CREATE_ANIMAL_TABLE_QUERY_WITH_PREVIOUS_DROP =
            "DROP TABLE IF EXISTS animal;" +
                    "CREATE TABLE Animal (\n" +
                    "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    birth_date DATE NOT NULL\n" +
                    ");";

    public static final String CUSTOM_SEQUENCER_WITH_PREVIOUS_DROP =
            "DROP SEQUENCE IF EXISTS seq;" +
                    "CREATE SEQUENCE seq\n" +
                    "START WITH 1\n" +
                    "INCREMENT BY 1\n";
}
