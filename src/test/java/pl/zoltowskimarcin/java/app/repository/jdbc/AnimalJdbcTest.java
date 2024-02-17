package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.CUSTOM_SEQUENCER);
            statement.execute(JdbcConstants.CREATE_ANIMAL_TABLE_QUERY);
        }

    }

    @AfterEach
    public void tearDown() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcConstants.ANIMAL_DROP_SEQ_QUERY);
            ConnectionManager.getConnection().close();
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