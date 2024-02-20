package pl.zoltowskimarcin.java.app.repository.hibernate;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.exceptions.animal.*;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@SpringBootTest
@Tag("springboot")
@Transactional
class AnimalRepoIntegrationSpringTest {
    private static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final LocalDate UPDATE_ANIMAL_BIRTH_DATE_02_02_3000 = LocalDate.of(3000, 2, 2);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";
    private static final String ANIMAL_ENTITY_NAME_UPDATED_JERRY = "UpdatedJerry";

    @Autowired
    private AnimalRepo animalRepo;

    @AfterAll
    static void tearDown() throws FailedQueryExecutionException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcTestConstants.ANIMAL_DROP_SEQ_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }
    }

    @BeforeEach
    void setUp() throws FailedQueryExecutionException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.CUSTOM_SEQUENCER_WITH_PREVIOUS_DROP);
            statement.execute(JdbcTestConstants.CREATE_ANIMAL_TABLE_QUERY_WITH_PREVIOUS_DROP);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }
    }

    @Test
    void read() throws AnimalNotFoundException, AnimalCreateFaultException, AnimalReadFaultException {
        //given
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        animalRepo.create(animal);
        Animal readAnimal = animalRepo.read(FIRST_ANIMAL_ID_1)
                .orElseThrow(() -> new AnimalNotFoundException("Entity not found"));

        Long actualId = readAnimal.getId();
        String actualName = readAnimal.getName();
        LocalDate actualBirthDate = readAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, actualId, "Ids are different"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, actualName, "Names are different"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, actualBirthDate, "Birth dates are different")
        );
    }

    @Test
    void update() throws AnimalCreateFaultException, AnimalUpdateFaultException {
        //given
        Animal animalBeforeUpdate = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal animalAfterUpdate = animalRepo.create(animalBeforeUpdate);
        animalAfterUpdate.setName(ANIMAL_ENTITY_NAME_UPDATED_JERRY);
        animalAfterUpdate.setBirthDate(UPDATE_ANIMAL_BIRTH_DATE_02_02_3000);

        Animal updatedAnimal = animalRepo.update(animalAfterUpdate);

        Long actualId = updatedAnimal.getId();
        String actualName = updatedAnimal.getName();
        LocalDate actualBirthDate = updatedAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, actualId, "Ids are different"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_UPDATED_JERRY, actualName, "Names are different"),
                () -> Assertions.assertEquals(UPDATE_ANIMAL_BIRTH_DATE_02_02_3000, actualBirthDate, "Birth dates are different")
        );

    }

    @Test
    void delete() throws AnimalCreateFaultException, AnimalDeleteFaultException {
        //given
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalRepo.create(animal);
        boolean isEntityDeleted = animalRepo.delete(createdAnimal.getId());

        //then
        Assertions.assertTrue(isEntityDeleted, "Entity is not deleted");
    }
}