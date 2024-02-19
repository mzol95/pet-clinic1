package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;

import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@Tag("plain")
class AnimalRepoTest {
    public static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";

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

    @Test
    void create() throws AnimalCreateFaultException {
        //given
        AnimalRepo animalRepo = new AnimalRepo();
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalRepo.create(animal);

        Long createdAnimalId = createdAnimal.getId();
        String createdAnimalName = createdAnimal.getName();
        LocalDate createdAnimalBirthDate = createdAnimal.getBirthDate();


        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, createdAnimalId, "Id doesn't match"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, createdAnimalName, "Animal names are different"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, createdAnimalBirthDate, "Animal birth dates are different")
        );

    }

}