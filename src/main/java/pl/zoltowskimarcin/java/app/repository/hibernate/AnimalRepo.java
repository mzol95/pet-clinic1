package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.zoltowskimarcin.java.app.mapper.ModelMapperManager;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.repository.entity.AnimalEntity;
import pl.zoltowskimarcin.java.app.utils.HibernateUtility;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class AnimalRepo implements AnimalDao {

    private static final Logger LOGGER = Logger.getLogger(AnimalRepo.class.getName());

    @Override
    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");

        //todo AnimalMapper
        AnimalEntity animalToPersist = ModelMapperManager.getModelMapper().map(animal, AnimalEntity.class);

        Session session = HibernateUtility.getSessionFactory().openSession();
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
            throw new RuntimeException();
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

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            readAnimal = session.find(AnimalEntity.class, id);
        }
        Animal animal = ModelMapperManager.getModelMapper().map(readAnimal, Animal.class);

        LOGGER.info("read(...) = " + animal);
        return Optional.ofNullable(animal);
    }

    @Override
    public Animal update(Animal animal) {
        LOGGER.info("update(" + animal + ")");
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = null;

        AnimalEntity updatedAnimal = ModelMapperManager.getModelMapper().map(animal, AnimalEntity.class);

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
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
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
