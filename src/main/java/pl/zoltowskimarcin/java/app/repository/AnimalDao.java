package pl.zoltowskimarcin.java.app.repository;

import pl.zoltowskimarcin.java.app.exceptions.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;

public interface AnimalDao {

    Animal create(Animal animal);

    Optional<Animal> read(Long id);

    Animal update(Animal animal);

    boolean delete(Long id);
}
