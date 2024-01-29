package pl.zoltowskimarcin.java.app.service;

import pl.zoltowskimarcin.java.app.repository.AnimalRepository;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class AnimalService {
    private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    //todo co z loggerami
    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalRepository.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    //todo własny wyjątek
    public Animal read(Long id) {
        LOGGER.info("read(id:  " + id + ")");
        Animal recivedAnimal = animalRepository.read(id)
                .orElseThrow(() -> new NoSuchElementException("Animal doesn't exists."));
        LOGGER.info("read(...) = " + recivedAnimal);
        return recivedAnimal;
    }

    public boolean update(Animal animal) {
        boolean result = false;
        LOGGER.info("update(" + animal + ")");
        result = animalRepository.update(animal);
        LOGGER.info("update(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

    public boolean delete(Long id) {
        boolean result = false;
        LOGGER.info("delete(id: " + id + ")");
        result = animalRepository.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

}
