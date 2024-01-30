package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.exceptions.EntityNotFoundException;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.utils.JdbcUtilities;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

class AnimalJdbcIntegrationTest {

    private static final String ANIMAL_NAME = "Dog";
    private static final String UPDATE_ANIMAL_NAME = "UpdatedDog";
    private static final LocalDate ANIMAL_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate UPDATE_ANIMAL_BIRTH_DATE = LocalDate.of(3000, 2, 2);
    private static final long ANIMAL_ID = 1L;

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
    void read() {
        //given
        AnimalJdbc animalJDBC = new AnimalJdbc(connection);
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJDBC.create(animal);
        //todo orElseThrow -> wlasny wyjatek - done
        Animal resultAnimal = animalJDBC.read(ANIMAL_ID).orElseThrow(() -> new EntityNotFoundException());

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(createdAnimal.getName(), resultAnimal.getName(), "Animal objects doesn't match"),
                () -> Assertions.assertEquals(createdAnimal.getBirthDate(), resultAnimal.getBirthDate(), "Animal objects doesn't match")
        );
    }

    @Test
    void update() {
        //given
        //todo usunac id z konstruktora Animal - done
        AnimalJdbc animalJDBC = new AnimalJdbc(connection);
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);
        Animal newAnimal = new Animal(UPDATE_ANIMAL_NAME, UPDATE_ANIMAL_BIRTH_DATE);

        //when
        //todo zwracac z create obiekt z id -> generowanie sekwencja/pobieranie wygenerowanych - done
        animal = animalJDBC.create(animal);
        newAnimal.setId(animal.getId());
        Animal updatedAnimal = animalJDBC.update(newAnimal);

        //todo assertAll - done
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(UPDATE_ANIMAL_NAME, updatedAnimal.getName(), "Animal names don't match"),
                () -> Assertions.assertEquals(UPDATE_ANIMAL_BIRTH_DATE, updatedAnimal.getBirthDate(), "Animal birthday date don't match")
        );

    }

    @Test
        //todo do poprawy id - done
    void delete() {
        //given
        AnimalJdbc animalJDBC = new AnimalJdbc(connection);
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJDBC.create(animal);
        animalJDBC.delete(createdAnimal.getId());
        Optional<Animal> readAnimal = animalJDBC.read(ANIMAL_ID);

        //then
        Assertions.assertEquals(Optional.empty(), readAnimal, "Object still exists in database");
    }
}