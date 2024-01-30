package pl.zoltowskimarcin.java.app.web.model;

import java.util.Set;

public class Clinic {

    private Long id;
    private Set<Doctor> doctorSet;

    public Clinic() {
    }

    public Clinic(Set<Doctor> doctorSet) {
        this.doctorSet = doctorSet;
    }

    public Set<Doctor> getDoctorSet() {
        return doctorSet;
    }

    public void setDoctorSet(Set<Doctor> doctorSet) {
        this.doctorSet = doctorSet;
    }

    @Override
    public String toString() {
        return "Clinic{" + "doctorSet=" + doctorSet + '}';
    }
}

