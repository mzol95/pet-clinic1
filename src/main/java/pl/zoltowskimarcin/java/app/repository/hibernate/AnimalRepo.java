package pl.zoltowskimarcin.java.app.repository.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;

public class AnimalRepo implements AnimalDao {

    @PersistenceContext
    private EntityManager entityManager;

    //todo 29.01.2024
    //implementacja z wykorzystaniem Hibernate i Entity
    //Przygotować teorie o hibernate, entity, jpa, orm
    //https://www.juniorjavadeveloper.pl/2024/01/08/omg-orm-jpa-hibernate-sessionfactory-entitymanager-jparepository-crudrepository-wyjasnione/#ktory-ze-sposobow-uzycia-hibernate-byl-pierwszy-odpowiedz-chatgpt
    //https://github.com/juniorjavadeveloper-pl/hibernate-examples/tree/master/src/test/java/pl/juniorjavadeveloper/examples/hibernate/basic/configuration

    @Override
    public Animal create(Animal animal) {
        entityManager.persist(animal);
        entityManager.flush();
        return animal;
    }

    @Override
    public Optional<Animal> read(Long id) {
        return Optional.empty();
    }

    @Override
    public Animal update(Animal animal) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


}
