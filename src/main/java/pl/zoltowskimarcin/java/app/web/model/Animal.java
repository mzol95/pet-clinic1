package pl.zoltowskimarcin.java.app.web.model;

import java.time.LocalDate;
import java.util.List;

public class Animal {

    private String name;
    private LocalDate birthDate;
    private List<Visit> visitList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", visitList=" + visitList +
                '}';
    }
}
