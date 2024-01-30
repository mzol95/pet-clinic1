package pl.zoltowskimarcin.java.app.web;

import pl.zoltowskimarcin.java.app.repository.jdbc.AnimalJdbc;
import pl.zoltowskimarcin.java.app.utils.JdbcUtilities;
import pl.zoltowskimarcin.java.app.web.model.Animal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(JdbcUtilities.JDBC_H2_DATABASE
                , JdbcUtilities.USER
                , JdbcUtilities.PASSWORD)) {
            Statement statement = connection.createStatement();

            statement.execute(JdbcUtilities.CREATE_ANIMAL_TABLE_QUERY);
            statement.execute(JdbcUtilities.CUSTOM_SEQUENCER);


            AnimalJdbc animalJdbc = new AnimalJdbc(connection);
            Animal dupa = new Animal("dupa", LocalDate.of(1, 1, 1));
            Animal dupa1 = new Animal("dupa", LocalDate.of(1, 1, 1));
            Animal dupa2 = new Animal("dupa", LocalDate.of(1, 1, 1));


            System.out.println(animalJdbc.create(dupa));
            System.out.println(animalJdbc.create(dupa1));
            System.out.println(animalJdbc.create(dupa2));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
