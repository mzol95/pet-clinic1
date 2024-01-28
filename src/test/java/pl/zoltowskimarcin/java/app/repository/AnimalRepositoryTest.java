package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

class AnimalRepositoryTest {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE Animal (\n" +
            "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    birth_date DATE NOT NULL\n" +
            ");";
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
            statement.execute(CREATE_TABLE_QUERY);
        }
    }

    @AfterEach
    public void closeDownDatabase() throws SQLException {
        connection.close();
    }

    @Test
    void create() {
        //given
        AnimalRepository animalRepository = new AnimalRepository(connection);
        Animal animal = new Animal(ANIMAL_ID, ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalRepository.create(animal);

        //then
        Assertions.assertEquals(animal.getName(), createdAnimal.getName(), "Animal objects doesn't match");
        Assertions.assertEquals(animal.getBirthDate(), createdAnimal.getBirthDate(), "Animal objects doesn't match");
    }

    @Test
    void read() {
        //given
        AnimalRepository animalRepository = new AnimalRepository(connection);
        Animal animal = new Animal(ANIMAL_ID, ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalRepository.create(animal);
        Animal resultAnimal = animalRepository.read(ANIMAL_ID).get();

        //then
        Assertions.assertEquals(createdAnimal.getName(), resultAnimal.getName(), "Animal objects doesn't match");
        Assertions.assertEquals(createdAnimal.getBirthDate(), resultAnimal.getBirthDate(), "Animal objects doesn't match");
    }

    @Test
    void update() {
        //given
        AnimalRepository animalRepository = new AnimalRepository(connection);
        Animal animalToBeUpdate = new Animal(ANIMAL_ID, ANIMAL_NAME, ANIMAL_BIRTH_DATE);
        Animal newAnimal = new Animal(ANIMAL_ID, UPDATE_ANIMAL_NAME, UPDATE_ANIMAL_BIRTH_DATE);

        //when
        animalRepository.create(animalToBeUpdate);
        animalRepository.update(newAnimal);
        Animal updatedAnimal = animalRepository.read(ANIMAL_ID).get();


        //then
        Assertions.assertEquals(UPDATE_ANIMAL_NAME, updatedAnimal.getName(), "Animal names don't match");
        Assertions.assertEquals(UPDATE_ANIMAL_BIRTH_DATE, updatedAnimal.getBirthDate(), "Animal birthday date don't match");

    }

    @Test
    void delete() {
        //given
        AnimalRepository animalRepository = new AnimalRepository(connection);
        Animal animal = new Animal(ANIMAL_ID, ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalRepository.create(animal);
        animalRepository.delete(animal.getId());
        Optional<Animal> readAnimal = animalRepository.read(ANIMAL_ID);

        //then
        Assertions.assertEquals(Optional.empty(), readAnimal, "Object still exists in database");
    }
}