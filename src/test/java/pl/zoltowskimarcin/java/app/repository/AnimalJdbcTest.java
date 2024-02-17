package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.utils.JdbcConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

class AnimalJdbcTest {

    private static final String ANIMAL_NAME = "Dog";
    private static final LocalDate ANIMAL_BIRTH_DATE = LocalDate.of(2000, 1, 1);

    private static Connection connection;

    @BeforeEach
    public void setUpDatabase() throws SQLException {
        ConnectionManager.setPath("src/test/resources/database.properties");


        try (Connection connection = ConnectionManager.getInstance();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.CUSTOM_SEQUENCER);
            statement.execute(JdbcConstants.CREATE_ANIMAL_TABLE_QUERY);
        }

    }

    @AfterEach
    public void cleanTable() {
        try (Connection connection = ConnectionManager.getInstance();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcConstants.ANIMAL_DROP_SEQ_QUERY);
            ConnectionManager.getInstance().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create() {
        //given
        AnimalJdbc animalJDBC = new AnimalJdbc();
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJDBC.create(animal);
        String createdAnimalName = createdAnimal.getName();
        LocalDate createdAnimalBirthDate = createdAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(ANIMAL_NAME, createdAnimalName, "Animal objects names don't match"),
                () -> Assertions.assertEquals(ANIMAL_BIRTH_DATE, createdAnimalBirthDate, "Animal objects birthday dates don't match")
        );
    }

}