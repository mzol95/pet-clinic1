package pl.zoltowskimarcin.java.app.web.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Tag("plain")
class AnimalTest {

    private static final String ANIMAL_POJO_NAME_JERRY = "Jerry";
    private static final LocalDate ANIMAL_BIRTHDAY_01_01_2000 = LocalDate.of(2000, 1, 1);
    private static final int ANIMAL_VISIT_LIST_SIZE = 2;

    @Test
    void animal_name_is_jerry() {
        //given
        Animal animal = new Animal();

        //when
        animal.setName(ANIMAL_POJO_NAME_JERRY);
        String nameAfterChange = animal.getName();

        //then
        Assertions.assertEquals(ANIMAL_POJO_NAME_JERRY, nameAfterChange, "Names doesn't match.");
    }

    @Test
    void animal_birth_date_is_01_01_2000() {
        //given
        Animal animal = new Animal();

        //when
        animal.setBirthDate(ANIMAL_BIRTHDAY_01_01_2000);
        LocalDate animalBirthday = animal.getBirthDate();

        //then
        Assertions.assertEquals(ANIMAL_BIRTHDAY_01_01_2000, animalBirthday, "Animal birthday dates doesn't match");

    }
}