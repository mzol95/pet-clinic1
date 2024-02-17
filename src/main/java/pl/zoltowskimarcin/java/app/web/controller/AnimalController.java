package pl.zoltowskimarcin.java.app.web.controller;

import org.springframework.stereotype.Controller;
import pl.zoltowskimarcin.java.app.service.AnimalService;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

//todo restowy controller + testy w postmanie
@Controller
public class AnimalController {

    private static final Logger LOGGER = Logger.getLogger(AnimalController.class.getName());

    private final AnimalService animalService;

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


    public Animal update(Animal animal) {
        LOGGER.info("update(id: " + animal.getId() + ")");
        Animal resultAnimal = animalService.update(animal);
        LOGGER.info("update(...) succeed");
        return resultAnimal;
    }


    public boolean delete(Long id) {
        LOGGER.info("delete(id: " + id + ")");
        boolean result = animalService.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return false;
    }

}
