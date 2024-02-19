package pl.zoltowskimarcin.java.app.repository.jdbc;

import org.junit.jupiter.api.*;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalNotFoundException;
import pl.zoltowskimarcin.java.app.sql.JdbcTestConstants;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

@Tag("plain")
class AnimalJdbcIntegrationTest {

    private static final String ANIMAL_NAME = "Jerry";
    private static final String UPDATE_ANIMAL_NAME = "UpdatedJerry";
    private static final LocalDate ANIMAL_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate UPDATE_ANIMAL_BIRTH_DATE = LocalDate.of(3000, 2, 2);
    private static final long ANIMAL_ID_1 = 1L;


    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(JdbcTestConstants.CUSTOM_SEQUENCER_WITH_PREVIOUS_DROP);
            statement.execute(JdbcTestConstants.CREATE_ANIMAL_TABLE_QUERY_WITH_PREVIOUS_DROP);
        }

    }

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


    @Test
    void read() throws AnimalNotFoundException {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJdbc.create(animal);
        Animal resultAnimal = animalJdbc.read(1L).orElseThrow(() -> new AnimalNotFoundException());

        String actualName = resultAnimal.getName();
        LocalDate actualDate = resultAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(ANIMAL_NAME, actualName, "Animal objects doesn't match"),
                () -> Assertions.assertEquals(ANIMAL_BIRTH_DATE, actualDate, "Animal objects doesn't match")
        );
    }


    @Test
    void update() {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);
        Animal newAnimal = new Animal(UPDATE_ANIMAL_NAME, UPDATE_ANIMAL_BIRTH_DATE);

        //when
        animal = animalJdbc.create(animal);
        newAnimal.setId(animal.getId());
        Animal updatedAnimal = animalJdbc.update(newAnimal);

        String actualName = updatedAnimal.getName();
        LocalDate actualBirthDate = updatedAnimal.getBirthDate();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(UPDATE_ANIMAL_NAME, actualName, "Animal names don't match"),
                () -> Assertions.assertEquals(UPDATE_ANIMAL_BIRTH_DATE, actualBirthDate, "Animal birthday date don't match")
        );

    }

    @Test
    void delete() {
        //given
        AnimalJdbc animalJdbc = new AnimalJdbc();
        Animal animal = new Animal(ANIMAL_NAME, ANIMAL_BIRTH_DATE);

        //when
        Animal createdAnimal = animalJdbc.create(animal);
        animalJdbc.delete(createdAnimal.getId());
        Optional<Animal> readAnimal = animalJdbc.read(ANIMAL_ID_1);

        //then
        Assertions.assertEquals(Optional.empty(), readAnimal, "Object still exists in database");
    }


}