package pl.zoltowskimarcin.java.app.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalCreateFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalDeleteFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalReadFaultException;
import pl.zoltowskimarcin.java.app.exceptions.animal.AnimalUpdateFaultException;
import pl.zoltowskimarcin.java.app.mapper.AnimalMapper;
import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.repository.entity.AnimalEntity;
import pl.zoltowskimarcin.java.app.utils.HibernateUtility;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class AnimalRepo implements AnimalDao {

    private static final Logger LOGGER = Logger.getLogger(AnimalRepo.class.getName());



    @Override
    public Animal create(Animal animal) throws AnimalCreateFaultException {
        LOGGER.info("create(" + animal + ")");

        AnimalEntity animalToPersist = AnimalMapper.mapToEntity(animal);
        Transaction transaction = null;

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(animalToPersist);
            transaction.commit();
            animal.setId(animalToPersist.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Creating animal failed ", e);
            throw new AnimalCreateFaultException("Creating animal failed");
        }

        LOGGER.info("create(...) = " + animal);
        return animal;
    }

    @Override
    public Optional<Animal> read(Long id) throws AnimalReadFaultException {
        LOGGER.info("read(id:  " + id + ")");

        AnimalEntity readAnimal;

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            readAnimal = session.find(AnimalEntity.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Reading animal failed", e);
            throw new AnimalReadFaultException("Reading animal failed");
        }

        Animal animal = AnimalMapper.mapToModel(readAnimal);

        LOGGER.info("read(...) = " + animal);
        return Optional.ofNullable(animal);
    }

    @Override
    public Animal update(Animal animal) throws AnimalUpdateFaultException {
        LOGGER.info("update(" + animal + ")");

        Transaction transaction = null;
        AnimalEntity updatedAnimal = AnimalMapper.mapToEntity(animal);

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(updatedAnimal);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Updating animal failed", e);
            throw new AnimalUpdateFaultException("Updating animal failed");
        }

        LOGGER.info("update(...) = " + updatedAnimal);
        return animal;
    }

    @Override
    public boolean delete(Long id) throws AnimalDeleteFaultException {
        LOGGER.info("delete(id: " + id + ")");
        Transaction transaction = null;
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            AnimalEntity animalToRemove = session.get(AnimalEntity.class, id);
            if (animalToRemove != null) {
                transaction = session.beginTransaction();
                session.remove(animalToRemove);
                LOGGER.info("delete (id: " + id + " succeed");
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Deleting animal fault", e);
            throw new AnimalDeleteFaultException("Deleting animal fault");
        }

        LOGGER.info("delete (id: " + id + " not succeed");
        return false;
    }


}
