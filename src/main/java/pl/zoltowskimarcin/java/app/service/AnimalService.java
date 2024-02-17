package pl.zoltowskimarcin.java.app.service;

import org.springframework.stereotype.Service;
import pl.zoltowskimarcin.java.app.exceptions.animal.*;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

@Service
public class AnimalService {
    private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());

    private final AnimalDao animalDao;

    public AnimalService(AnimalDao animalDao) {
        this.animalDao = animalDao;
    }

    public Animal create(Animal animal) throws AnimalCreateFaultException {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalDao.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    public Animal read(Long id) throws AnimalNotFoundException, AnimalReadFaultException {
        LOGGER.info("read(id:  " + id + ")");
        Animal recivedAnimal = animalDao.read(id)
                .orElseThrow(() -> new AnimalNotFoundException("Entity does not exist in database"));
        LOGGER.info("read(...) = " + recivedAnimal);
        return recivedAnimal;
    }

    public Animal update(Animal animal) throws AnimalUpdateFaultException {
        LOGGER.info("update(" + animal + ")");
        Animal resultAnimal = animalDao.update(animal);
        LOGGER.info("update(...) = " + resultAnimal);
        return resultAnimal;
    }

    public boolean delete(Long id) throws AnimalDeleteFaultException {
        LOGGER.info("delete(id: " + id + ")");
        boolean result = animalDao.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

}
