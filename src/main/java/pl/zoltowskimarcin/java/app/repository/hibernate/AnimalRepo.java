package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.repository.entity.AnimalEntity;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;
import java.util.logging.Logger;

public class AnimalRepo implements AnimalDao {

    private SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(AnimalRepo.class.getName());

    public AnimalRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //todo 29.01.2024
    //implementacja z wykorzystaniem Hibernate i Entity
    //Przygotować teorie o hibernate, entity, jpa, orm
    //https://www.juniorjavadeveloper.pl/2024/01/08/omg-orm-jpa-hibernate-sessionfactory-entitymanager-jparepository-crudrepository-wyjasnione/#ktory-ze-sposobow-uzycia-hibernate-byl-pierwszy-odpowiedz-chatgpt
    //https://github.com/juniorjavadeveloper-pl/hibernate-examples/tree/master/src/test/java/pl/juniorjavadeveloper/examples/hibernate/basic/configuration

    @Override
    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
        AnimalEntity animalToPersist = new AnimalEntity();
        animalToPersist.setName(animal.getName());
        animalToPersist.setBirthDate(animal.getBirthDate());

        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(animalToPersist);
            transaction.commit();
            animal.setId(animalToPersist.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.info("create(...) = " + animal);
        return animal;
    }

    @Override
    public Optional<Animal> read(Long id) {
        LOGGER.info("read(id:  " + id + ")");
        AnimalEntity readAnimal;
        Animal animal = new Animal();

        try (Session session = sessionFactory.openSession()) {
            readAnimal = session.find(AnimalEntity.class, id);
        }

        animal.setId(readAnimal.getId());
        animal.setName(readAnimal.getName());
        animal.setBirthDate(readAnimal.getBirthDate());
        LOGGER.info("read(...) = " + animal);
        return Optional.ofNullable(animal);
    }

    @Override
    public Animal update(Animal animal) {
        LOGGER.info("update(" + animal + ")");
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        AnimalEntity updatedAnimal = new AnimalEntity();
        updatedAnimal.setId(animal.getId());
        updatedAnimal.setName(animal.getName());
        updatedAnimal.setBirthDate(animal.getBirthDate());

        try {
            transaction = session.beginTransaction();
            session.merge(updatedAnimal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        LOGGER.info("update(...) = " + updatedAnimal);
        return animal;
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("delete(id: " + id + ")");
        try (Session session = sessionFactory.openSession()) {
            AnimalEntity animalToRemove = session.get(AnimalEntity.class, id);
            if (animalToRemove != null) {
                session.remove(animalToRemove);
                LOGGER.info("delete (id: " + id + " succeed");
                return true;
            }
        }
        LOGGER.info("delete (id: " + id + " not succeed");
        return false;
    }


}
