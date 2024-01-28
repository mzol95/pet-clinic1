package pl.zoltowskimarcin.java.app.web.controller;

import pl.zoltowskimarcin.java.app.service.AnimalService;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

public class AnimalController {

    private static final Logger LOGGER = Logger.getLogger(AnimalController.class.getName());

    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalService.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    public Animal read(Long id) {
        LOGGER.info("read(id: " + id + ")");
        Animal readAnimal = animalService.read(id);
        LOGGER.info("read(...) = " + readAnimal);
        return null;
    }


    public boolean update(Animal animal) {
        LOGGER.info("update(id: " + animal.getId() + ")");
        boolean result = animalService.update(animal);
        LOGGER.info("update(...) " + (result ? "succeed" : "not succeed"));
        return false;
    }


    public boolean delete(Long id) {
        LOGGER.info("delete(id: " + id + ")");
        boolean result = animalService.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return false;
    }


    //todo 25.01.2024
    /*
     * stworzyć metody crud z logerami
     * dla każdej metody stworzyć testy jednostkowe oraz DID
     * */
}
