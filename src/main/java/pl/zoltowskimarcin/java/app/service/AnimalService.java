package pl.zoltowskimarcin.java.app.service;

import org.springframework.stereotype.Service;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalDeleteFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalReadFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalUpdateFaultException;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AnimalService {
    private static final Logger LOGGER = Logger.getLogger(AnimalService.class.getName());

    private final AnimalDao animalRepo;

    public AnimalService(AnimalDao animalRepo) {
        this.animalRepo = animalRepo;
    }

    public Animal create(Animal animal) throws AnimalCreateFaultException {
        LOGGER.info("create(" + animal + ")");
        Animal createdAnimal = animalRepo.create(animal);
        LOGGER.info("create(...) = " + createdAnimal);
        return createdAnimal;
    }

    public Optional<Animal> read(Long id) throws AnimalReadFaultException {
        LOGGER.info("read(id:  " + id + ")");
        Optional<Animal> receivedAnimal = animalRepo.read(id);
        LOGGER.info("read(...) = " + receivedAnimal);
        return receivedAnimal;
    }

    public Animal update(Animal animal) throws AnimalUpdateFaultException {
        LOGGER.info("update(" + animal + ")");
        Animal resultAnimal = animalRepo.update(animal);
        LOGGER.info("update(...) = " + resultAnimal);
        return resultAnimal;
    }

    public boolean delete(Long id) throws AnimalDeleteFaultException {
        LOGGER.info("delete(id: " + id + ")");
        boolean result = animalRepo.delete(id);
        LOGGER.info("delete(...) " + (result ? "succeed" : "not succeed"));
        return result;
    }

}
