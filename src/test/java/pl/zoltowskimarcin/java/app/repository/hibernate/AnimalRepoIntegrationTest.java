package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zoltowskimarcin.java.app.exceptions.AnimalNotFoundException;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.utils.JdbcConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@SpringBootTest
class AnimalRepoIntegrationTest {
    public static final long FIRST_ANIMAL_ID_1 = 1L;
    private static LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final LocalDate UPDATE_ANIMAL_BIRTH_DATE_02_02_3000 = LocalDate.of(3000, 2, 2);
    private static String ANIMAL_ENTITY_NAME_JERRY = "Jerry";
    private static final String ANIMAL_ENTITY_NAME_UPDATED_JERRY = "UpdatedJerry";
    private Connection connection;



    @BeforeEach
    void setUp() throws FailedQueryExecutionException {
        connection = ConnectionManager.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.CUSTOM_SEQUENCER);
            statement.execute(JdbcConstants.CREATE_ANIMAL_TABLE_QUERY);
        } catch (SQLException e) {
            throw new FailedQueryExecutionException();
        }
    }

    @AfterEach
    void tearDown() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcConstants.ANIMAL_DROP_SEQ_QUERY);
            ConnectionManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    void read() throws AnimalNotFoundException {
        //given
        AnimalRepo animalRepo = new AnimalRepo();
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        animalRepo.create(animal);
        Animal readAnimal = animalRepo.read(FIRST_ANIMAL_ID_1)
                .orElseThrow(() -> new AnimalNotFoundException("Entity not found"));

        Long readAnimalId = readAnimal.getId();
        String readAnimalName = readAnimal.getName();
        LocalDate readAnimalBirthDate = readAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, readAnimalId, "Id are different"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, readAnimalName, "Names are different"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, readAnimalBirthDate, "Birth dates are different")
        );
    }

    @Test
    void update() {
        //given
        AnimalRepo animalRepo = new AnimalRepo();
        Animal animalBeforeUpdate = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal animalAfterUpdate = animalRepo.create(animalBeforeUpdate);
        animalAfterUpdate.setName(ANIMAL_ENTITY_NAME_UPDATED_JERRY);
        animalAfterUpdate.setBirthDate(UPDATE_ANIMAL_BIRTH_DATE_02_02_3000);

        Animal updatedAnimal = animalRepo.update(animalAfterUpdate);

        Long updatedAnimalId = updatedAnimal.getId();
        String updatedAnimalName = updatedAnimal.getName();
        LocalDate updatedAnimalBirthDate = updatedAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, updatedAnimalId, "Ids are different"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_UPDATED_JERRY, updatedAnimalName, "Names are different"),
                () -> Assertions.assertEquals(UPDATE_ANIMAL_BIRTH_DATE_02_02_3000, updatedAnimalBirthDate, "Birth dates are different")
        );

    }

    @Test
    void delete() {
        //given
        AnimalRepo animalRepo = new AnimalRepo();
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalRepo.create(animal);
        boolean isEntityDeleted = animalRepo.delete(createdAnimal.getId());

        //then
        Assertions.assertEquals(true, isEntityDeleted, "Entity is not deleted");
    }
}