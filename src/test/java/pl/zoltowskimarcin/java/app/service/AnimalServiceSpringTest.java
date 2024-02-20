package pl.zoltowskimarcin.java.app.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.repository.jdbc.ConnectionManager;
import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@SpringBootTest
@Tag("springboot")
class AnimalServiceSpringTest {

    private static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";

    @Autowired
    private AnimalService animalService;

    @AfterAll
    public static void tearDown() {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.ANIMAL_DROP_TABLE_QUERY);
            statement.execute(JdbcTestConstants.ANIMAL_DROP_SEQ_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.CUSTOM_SEQUENCER_WITH_PREVIOUS_DROP);
            statement.execute(JdbcTestConstants.CREATE_ANIMAL_TABLE_QUERY_WITH_PREVIOUS_DROP);
        }

    }

    @Test
    void create() throws AnimalCreateFaultException {
        //given
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

}