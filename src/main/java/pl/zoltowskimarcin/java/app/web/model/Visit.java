package pl.zoltowskimarcin.java.app.web.model;

import java.time.LocalDateTime;

public class Visit {

    private Long id;
    private LocalDateTime date;
    private Doctor doctor;
    private Animal animal;
    private String description;

    public Visit() {
    }

    public Visit(LocalDateTime date, Doctor doctor, Animal animal, String description) {
        this.date = date;
        this.doctor = doctor;
        this.animal = animal;
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "date=" + date +
                ", doctor=" + doctor +
                ", animal=" + animal +
                ", description='" + description + '\'' +
                '}';
    }
}
