package pl.zoltowskimarcin.java.app.repository.jdbc;

import pl.zoltowskimarcin.java.app.repository.AnimalDao;
import pl.zoltowskimarcin.java.app.utils.JdbcUtilities;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalJdbc implements AnimalDao {

    private static final Logger LOGGER = Logger.getLogger(AnimalJdbc.class.getName());

    private Connection dbConnection;

    //todo 29.01.2024
    //1. Stworzyć oddzielną klasę zarządzającą połączeniami z bazą danych -> singleton zwracający połączenie - done
    //2. Opcjonalnie zarządzanie pulą połączeń
    //3. Podpiąć liquibase z tabelami bazy
    //4. W klasie zarządzającej połączeniem z bazą danych użyć nowej klasy zarządzającej danymi do połączenia się z bazą danych - java Properties

    public AnimalJdbc(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }


    //todo jak pobrać wygenerwoany id 29.01.24 - done
    //todo generowanie identyfikatorów zrealizować za pomocą sekwencji - done
    //todo wydzielic query do osobnej klasy - done

    @Override
    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");

        try (PreparedStatement createStatement = dbConnection.prepareStatement(JdbcUtilities.ANIMAL_INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, animal.getName());
            createStatement.setDate(2, Date.valueOf(animal.getBirthDate()));
            createStatement.execute();

            try (ResultSet rs = createStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    animal.setId(rs.getLong(1));
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
        }

        LOGGER.info("create(...) = " + animal);
        return animal;
    }

    // todo return w innym miejscu 29.01.24 - done
    public Optional<Animal> read(Long id) {
        LOGGER.info("read (id: " + id + ")");

        try (PreparedStatement readStatement = dbConnection.prepareStatement(JdbcUtilities.ANIMAL_SELECT_QUERY)) {
            readStatement.setLong(1, id);

            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    long resultId = resultSet.getLong(1);
                    String resultName = resultSet.getString(2);
                    LocalDate resultBirthDate = resultSet.getDate(3).toLocalDate();

                    Animal recivedAnimal = new Animal(resultName, resultBirthDate);
                    recivedAnimal.setId(resultId);

                    LOGGER.info("read (" + recivedAnimal + ")");
                    return Optional.ofNullable(recivedAnimal);
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.severe(sqlException.getMessage());
        }

        return Optional.empty();
    }

    //todo zmienic na animal 29.01.24 - done
    public Animal update(Animal animal) {
        LOGGER.info("update (" + animal + ")");

        try (PreparedStatement updateStatement = dbConnection.prepareStatement(JdbcUtilities.ANIMAL_UPDATE_QUERY)) {
            updateStatement.setString(1, animal.getName());
            updateStatement.setDate(2, Date.valueOf(animal.getBirthDate()));
            updateStatement.setLong(3, animal.getId());
            updateStatement.executeUpdate();
            LOGGER.info("update (id: " + animal.getId() + ") succeed");
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
        }
        return animal;
    }

    public boolean delete(Long id) {
        LOGGER.info("delete (id: " + id + ")");

        try (PreparedStatement deleteStatement = dbConnection.prepareStatement(JdbcUtilities.ANIMAL_DELETE_QUERY)) {
            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();
            LOGGER.info("delete (id: " + id + ") succeed");
            return true;
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
        LOGGER.info("delete (id: " + id + ") not succeed");
        return false;
    }


}

//todo 25.01.2024 - done
//zaimplementować dostęp do bazy danych za pomocą JDBC dla klasy Animal i metody create -> stworzyć tabele w bazie
//kroki do wykonania dla JDBC
//1. DriverManager
//2. Connection
//3. Statement/PreparedStatement
//4. ResultSet
//0. try-with-resources
