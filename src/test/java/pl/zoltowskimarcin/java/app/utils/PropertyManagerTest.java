package pl.zoltowskimarcin.java.app.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PropertyManagerTest {

    public static final String JDBC_H_2_MEM_TEST_DB_CLOSE_DELAY_1 = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    public static final String JDBC_URL_FROM_JDBC_PROPERTIES = "jdbcUrl";

    @Test
    void getProperty() {
        //given

        //when
        String jdbUrlFromFile = PropertyManager.getInstance().getProperty(JDBC_URL_FROM_JDBC_PROPERTIES);

        //then
        Assertions.assertEquals(
                JDBC_H_2_MEM_TEST_DB_CLOSE_DELAY_1,
                jdbUrlFromFile,
                "Properties are different"
        );

    }

}