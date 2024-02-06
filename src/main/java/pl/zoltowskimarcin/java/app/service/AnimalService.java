package pl.zoltowskimarcin.java.app.service;

import pl.zoltowskimarcin.java.app.exceptions.EntityNotFoundException;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

public class AnimalService {
    private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());

    private final AnimalDao animalDao;

    public AnimalService(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }


    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalDao.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    //todo własny wyjątek - done
    public Animal read(Long id) {
        LOGGER.info("read(id:  " + id + ")");
        Animal recivedAnimal = animalDao.read(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity does not exist in database"));
        LOGGER.info("read(...) = " + recivedAnimal);
        return recivedAnimal;
    }

    public Animal update(Animal animal) {
        LOGGER.info("update(" + animal + ")");
        Animal resultAnimal = animalDao.update(animal);
        LOGGER.info("update(...) = " + resultAnimal);
        return resultAnimal;
    }

    public boolean delete(Long id) {
        boolean result;
        LOGGER.info("delete(id: " + id + ")");
        result = animalDao.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

}
