package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
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
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        try (Statement statement = connection.createStatement()) {
            statement.execute(JdbcUtilities.CUSTOM_SEQUENCER);
            statement.execute(JdbcUtilities.CREATE_ANIMAL_TABLE_QUERY);
        }
    }

    @AfterEach
    public void closeDownDatabase() throws SQLException {
        connection.close();
    }

    @Test
    void create() {
        //given
        AnimalJdbc animalJDBC = new AnimalJdbc(connection);
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJDBC.create(animal);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(animal.getName(), createdAnimal.getName(), "Animal objects names don't match"),
                () -> Assertions.assertEquals(animal.getBirthDate(), createdAnimal.getBirthDate(), "Animal objects birthday dates don't match")
        );
    }

}