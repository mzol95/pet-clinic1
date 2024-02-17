package pl.zoltowskimarcin.java.app.service;

import org.springframework.stereotype.Service;
import pl.zoltowskimarcin.java.app.exceptions.AnimalNotFoundException;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

@Service
public class AnimalService {
    private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());


    private final AnimalDao animalJdbc;

    public AnimalService(AnimalDao animalJdbc) {
        this.animalJdbc = animalJdbc;
    }


    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalJdbc.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    public Animal read(Long id) {
        LOGGER.info("read(id:  " + id + ")");
        Animal recivedAnimal = animalJdbc.read(id)
                .orElseThrow(() -> new AnimalNotFoundException("Entity does not exist in database"));
        LOGGER.info("read(...) = " + recivedAnimal);
        return recivedAnimal;
    }

    public Animal update(Animal animal) {
        LOGGER.info("update(" + animal + ")");
        Animal resultAnimal = animalJdbc.update(animal);
        LOGGER.info("update(...) = " + resultAnimal);
        return resultAnimal;
    }

    public boolean delete(Long id) {
        boolean result;
        LOGGER.info("delete(id: " + id + ")");
        result = animalJdbc.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

}
