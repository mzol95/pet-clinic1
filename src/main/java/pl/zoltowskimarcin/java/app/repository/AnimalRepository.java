package pl.zoltowskimarcin.java.app.repository;

import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

public class AnimalRepository {

    private static final Logger LOGGER = Logger.getLogger(AnimalRepository.class.getName());

    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");

        LOGGER.info("create(...) = ");
        return null;
    }

    public void read() {

    }


}
