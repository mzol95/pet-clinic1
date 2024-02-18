package pl.zoltowskimarcin.java.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zoltowskimarcin.java.app.exceptions.FailedQueryExecutionException;
import pl.zoltowskimarcin.java.app.exceptions.animal.*;
import pl.zoltowskimarcin.java.app.repository.hibernate.AnimalRepo;
import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.utils.JdbcConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

class AnimalServiceIntegrationTest {

    private static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final LocalDate UPDATE_ANIMAL_BIRTH_DATE_02_02_3000 = LocalDate.of(3000, 2, 2);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";
    private static final String ANIMAL_ENTITY_NAME_UPDATED_JERRY = "UpdatedJerry";

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.CUSTOM_SEQUENCER);
            statement.execute(JdbcConstants.CREATE_ANIMAL_TABLE_QUERY);
        }

    }

    @AfterEach
    public void tearDown() throws FailedQueryExecutionException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcConstants.ANIMAL_DROP_SEQ_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FailedQueryExecutionException();
        }
    }

    @Test
    void create() throws AnimalCreateFaultException {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        AnimalService animalService = new AnimalService(animalJdbc);
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalService.create(animal);
        animal.setId(FIRST_ANIMAL_ID_1);

        Long actualId = createdAnimal.getId();
        String actualName = createdAnimal.getName();
        LocalDate actualBirtDate = createdAnimal.getBirthDate();


        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(FIRST_ANIMAL_ID_1, actualId, "Id doesn't match"),
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, actualName, "Animal names are different"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, actualBirtDate, "Animal birth dates are different")
        );
    }

    @Test
    void read() throws AnimalCreateFaultException, AnimalNotFoundException, AnimalReadFaultException {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        AnimalService animalService = new AnimalService(animalJdbc);
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalService.create(animal);
        Animal resultAnimal = animalService.read(1L).orElseThrow(() -> new AnimalNotFoundException());
        String actualName = resultAnimal.getName();
        LocalDate actualBirthDate = resultAnimal.getBirthDate();


        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(ANIMAL_ENTITY_NAME_JERRY, actualName, "Animal objects doesn't match"),
                () -> Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, actualBirthDate, "Animal objects doesn't match")
        );

    }

    @Test
    void update() throws AnimalCreateFaultException, AnimalUpdateFaultException {
        //given
        AnimalRepo animalRepo = new AnimalRepo();
        AnimalService animalService = new AnimalService(animalRepo);
        Animal animalBeforeUpdate = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal animalAfterUpdate = animalService.create(animalBeforeUpdate);
        animalAfterUpdate.setName(ANIMAL_ENTITY_NAME_UPDATED_JERRY);
        animalAfterUpdate.setBirthDate(UPDATE_ANIMAL_BIRTH_DATE_02_02_3000);

        Animal updatedAnimal = animalService.update(animalAfterUpdate);

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
        boolean isDeleted = false;
        AnimalRepo animalRepo = new AnimalRepo();
        AnimalService animalService = new AnimalService(animalRepo);
        Animal animal = new Animal(ANIMAL_ENTITY_NAME_JERRY, ANIMAL_BIRTHDAY_01_01_2000);

        //when
        Animal createdAnimal = animalService.create(animal);
        isDeleted = animalService.delete(createdAnimal.getId());

        //then
        Assertions.assertTrue(isDeleted, "Object still exists in database");
    }
}