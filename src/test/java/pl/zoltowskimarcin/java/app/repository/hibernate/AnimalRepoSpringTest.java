package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.time.LocalDate;

@SpringBootTest
@Tag("springboot")
class AnimalRepoSpringTest {
    public static final long FIRST_ANIMAL_ID_1 = 1L;
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final String ANIMAL_ENTITY_NAME_JERRY = "Jerry";

    @Autowired
    private AnimalRepo animalRepo;

    @Test
    void create() throws AnimalCreateFaultException {
        //given
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

