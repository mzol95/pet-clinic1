package pl.zoltowskimarcin.java.app.repository;

import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalDeleteFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalReadFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalUpdateFaultException;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;

public interface AnimalDao {

    Animal create(Animal animal) throws AnimalCreateFaultException;

    Optional<Animal> read(Long id) throws AnimalReadFaultException;

    Animal update(Animal animal) throws AnimalUpdateFaultException;

    boolean delete(Long id) throws AnimalDeleteFaultException;
}
