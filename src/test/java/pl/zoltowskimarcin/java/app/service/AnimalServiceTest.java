package pl.zoltowskimarcin.java.app.service;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.utils.JdbcConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@Tag("plain")
class AnimalServiceTest {

    private static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.CUSTOM_SEQUENCER_WITH_PREVIOUS_DROP);
            statement.execute(JdbcTestConstants.CREATE_ANIMAL_TABLE_QUERY_WITH_PREVIOUS_DROP);
        }

    }

    @AfterAll
    public static void tearDown() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcTestConstants.ANIMAL_DROP_SEQ_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create() throws AnimalCreateFaultException {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        AnimalService animalService = new AnimalService(animalJdbc);
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalService.create(animal);
        animal.setId(FIRST_ANIMAL_ID_1);

        Long actualId = createdAnimal.getId();
        String actualName = createdAnimal.getName();
        LocalDate actualBirtDate = createdAnimal.getBirthDate();


        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, actualId, "Id doesn't match"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, actualName, "Animal names are different"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, actualBirtDate, "Animal birth dates are different")
        );
    }

}