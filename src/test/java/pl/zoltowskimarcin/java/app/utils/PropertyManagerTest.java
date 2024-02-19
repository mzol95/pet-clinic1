package pl.zoltowskimarcin.java.app.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("plain")
class PropertyManagerTest {

    public static final String JDBC_URL_FROM_JDBC_PROPERTIES = "dataSource.jdbcUrl";
    public static final String JDBC_H_2_MEM_TEST_DB_CLOSE_DELAY_1 = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    @Test
    void getProperty() {
        //given

        //when
        String jdbUrlFromFile = PropertyManager.getProperty(JDBC_URL_FROM_JDBC_PROPERTIES);

        //then
        Assertions.assertEquals(
                JDBC_H_2_MEM_TEST_DB_CLOSE_DELAY_1,
                jdbUrlFromFile,
                "Properties are different"
        );

    }

}