package pl.zoltowskimarcin.java.app.web.model;

import java.util.List;
import java.util.Objects;

public class Doctor {

    private String name;
    private String surname;
    private List<Visit> visitList;

    public Doctor() {
    }

    public Doctor(String name, String surname, List<Visit> visitList) {
        this.name = name;
        this.surname = surname;
        this.visitList = visitList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(name, doctor.name) && Objects.equals(surname, doctor.surname) && Objects.equals(visitList, doctor.visitList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, visitList);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", visitList=" + visitList +
                '}';
    }
}
