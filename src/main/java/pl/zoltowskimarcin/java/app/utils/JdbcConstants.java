package pl.zoltowskimarcin.java.app.utils;

public class JdbcConstants {
    public static final String ANIMAL_SELECT_QUERY = "SELECT * FROM animal WHERE id = ?";
    public static final String ANIMAL_INSERT_QUERY = "INSERT INTO animal (id, name, birth_date) VALUES (NEXTVAL('seq'), ?, ?)";
    public static final String ANIMAL_UPDATE_QUERY = "UPDATE animal SET name = ?, birth_date = ? WHERE id = ?";
    public static final String ANIMAL_DELETE_QUERY = "DELETE FROM animal WHERE id = ?";
    public static final String ANIMAL_DROP_TABLE_QUERY = "DROP TABLE animal";
    public static final String ANIMAL_DROP_SEQ_QUERY = "DROP SEQUENCE seq";
    public static final String CREATE_ANIMAL_TABLE_QUERY =
            "CREATE TABLE Animal (\n" +
            "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    birth_date DATE NOT NULL\n" +
            ");";

    public static final String CUSTOM_SEQUENCER =
            "CREATE SEQUENCE seq\n" +
            "START WITH 1\n" +
            "INCREMENT BY 1\n";
}
