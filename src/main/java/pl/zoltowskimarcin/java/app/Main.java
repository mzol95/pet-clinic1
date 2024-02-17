package pl.zoltowskimarcin.java.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        //todo 08.02.24
        //główne klasy z poszczególnych warstw zrobić komponentami springowymi todo 2:
        //dla nowopowstałych komponentów springowych napisac testy jednostkowe @SpringBootTest + @Autowired
        //zrobić poprawną propagacje własnych wyjątków od repository przez service do kontrolera
        //dodac globalna klase @ControllerAdvice
        //dla wybanej encji stworzyć relacje OneToMany + testy
        //nowy filmik yt github coopilot
        //usunac runtime exceptions
    }
}
