package pl.zoltowskimarcin.java.app.repository;

import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.util.logging.Logger;

public class AnimalRepository {

    private static final Logger LOGGER = Logger.getLogger(AnimalRepository.class.getName());

    public Animal create(Animal animal) {
        LOGGER.info("create(" + animal + ")");
//todo 25.01.2024
//zaimplementować dostęp do bazy danych za pomocą JDBC dla klasy Animal i metody create -> stworzyć tabele w bazi
//kroki do wykonania dla JDBC
        //1. DriverManager
        //2. Connection
        //3. Statement/PreparedStatement
        //4. ResultSet
        //0. try-with-resources

        LOGGER.info("create(...) = ");
        return null;
    }

    public void read() {

    }


}
