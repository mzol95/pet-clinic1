package pl.zoltowskimarcin.java.app.repository;

import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalRepository {

    private static final Logger LOGGER = Logger.getLogger(AnimalRepository.class.getName());
    private Connection dbConnection;

    public AnimalRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");

        String createQuery = "INSERT INTO animal (name, birth_date) VALUES (?, ?)";

        try (PreparedStatement createStatement = dbConnection.prepareStatement(createQuery)) {
            createStatement.setString(1, animal.getName());
            createStatement.setDate(2, Date.valueOf(animal.getBirthDate()));
            createStatement.execute();
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
        }

        LOGGER.info("create(...) = " + animal);
        return animal;
    }

    public Optional<Animal> read(Long id) {
        LOGGER.info("read (id: " + id + ")");

        Animal recivedAnimal = null;

        String selectQuery = "SELECT * FROM animal WHERE id = ?";

        try (PreparedStatement readStatement = dbConnection.prepareStatement(selectQuery)) {

            readStatement.setLong(1, id);

            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    long resultId = resultSet.getLong(1);
                    String resultName = resultSet.getString(2);
                    LocalDate resultBirthDate = resultSet.getDate(3).toLocalDate();

                    recivedAnimal = new Animal(resultId, resultName, resultBirthDate);
                    LOGGER.info("read (" + recivedAnimal + ")");
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.severe(sqlException.getMessage());
        }

        return Optional.ofNullable(recivedAnimal);
    }


    public boolean update(Animal animal) {
        LOGGER.info("update (" + animal + ")");
        boolean result = false;
        String updateQuery = "UPDATE animal SET name = ?, birth_date = ? WHERE id = ?";

        try (PreparedStatement updateStatement = dbConnection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, animal.getName());
            updateStatement.setDate(2, Date.valueOf(animal.getBirthDate()));
            updateStatement.setLong(3, animal.getId());
            result = updateStatement.executeUpdate() > 0;
            LOGGER.info("delete (id: " + animal.getId() + ") succeed");
            return result;
        } catch (SQLException sqlException) {
            LOGGER.log(Level.SEVERE, sqlException.getMessage());
        }

        LOGGER.info("delete (id: " + animal.getId() + ") not succeed");
        return result;
    }

    public boolean delete(Long id) {
        LOGGER.info("delete (id: " + id + ")");
        boolean result = false;
        String deleteQuery = "DELETE FROM animal WHERE id = ?";

        try (PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteQuery)) {
            deleteStatement.setLong(1, id);
            result = deleteStatement.executeUpdate() > 0;
            LOGGER.info("delete (id: " + id + ") succeed");
            return result;
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        }
        LOGGER.info("delete (id: " + id + ") not succeed");
        return result;
    }


}

//todo 25.01.2024
//zaimplementować dostęp do bazy danych za pomocą JDBC dla klasy Animal i metody create -> stworzyć tabele w bazie
//kroki do wykonania dla JDBC
//1. DriverManager
//2. Connection
//3. Statement/PreparedStatement
//4. ResultSet
//0. try-with-resources
