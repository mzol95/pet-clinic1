package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@Tag("plain")
class AnimalJdbcTest {

    private static final String ANIMAL_NAME = "Dog";
    private static final LocalDate ANIMAL_BIRTH_DATE = LocalDate.of(2000, 1, 1);


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
    void create() {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJdbc.create(animal);
        String createdAnimalName = createdAnimal.getName();
        LocalDate createdAnimalBirthDate = createdAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(ANIMAL_NAME, createdAnimalName, "Animal objects names don't match"),
                () -> Assertions.assertEquals(ANIMAL_BIRTH_DATE, createdAnimalBirthDate, "Animal objects birthday dates don't match")
        );
    }


}