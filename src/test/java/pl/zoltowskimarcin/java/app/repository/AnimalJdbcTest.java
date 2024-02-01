package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.utils.JdbcUtilities;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.DriverManager;
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
        ConnectionManager.getInstance();
        connection = ConnectionManager.getInstance();
        System.out.println("create connection");
        try (Statement statement = connection.createStatement()) {
            statement.execute(JdbcUtilities.CUSTOM_SEQUENCER);
            statement.execute(JdbcUtilities.CREATE_ANIMAL_TABLE_QUERY);
        }

    }

    @AfterEach
    public void cleanTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(JdbcUtilities.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcUtilities.ANIMAL_DROP_SEQ_QUERY);
            ConnectionManager.getInstance().close();
            System.out.println("drop connection");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void create() {
        //given
        AnimalJdbc animalJDBC = new AnimalJdbc(connection);
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