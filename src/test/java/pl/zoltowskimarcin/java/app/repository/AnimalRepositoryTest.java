package pl.zoltowskimarcin.java.app.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.zoltowskimarcin.java.app.web.model.Animal;

class AnimalRepositoryTest {

    @Test
    void create() {
        //given
        AnimalRepository animalRepository = new AnimalRepository();
        Animal animal = new Animal();

        //when
        Animal createdAnimal = animalRepository.create(animal);

        //then
        Assertions.assertNotNull(createdAnimal, "createdAnimal is null");

    }
}