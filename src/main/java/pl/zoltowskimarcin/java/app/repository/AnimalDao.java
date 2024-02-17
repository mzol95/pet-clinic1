package pl.zoltowskimarcin.java.app.repository;

import org.springframework.stereotype.Repository;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;

public interface AnimalDao {

    Animal create(Animal animal);

    Optional<Animal> read(Long id);

    Animal update(Animal animal);

    boolean delete(Long id);
}
