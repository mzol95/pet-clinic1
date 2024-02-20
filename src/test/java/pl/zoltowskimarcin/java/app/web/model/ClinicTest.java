package pl.zoltowskimarcin.java.app.web.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Tag("plain")
class ClinicTest {

    public static int CLINIC_DOCTORS_NUMBER = 2;

    @Test
    void setDoctorSet() {
        //given
        Clinic clinic = new Clinic();
        Doctor doctor1 = new Doctor("DoctorName1", "DoctorSurname1", null);
        Doctor doctor2 = new Doctor("DoctorName2", "DoctorSurname2", null);
        //when
        Set<Doctor> doctorSet = Set.of(doctor1, doctor2);
        clinic.setDoctorSet(doctorSet);
        int actualDoctorSetSize = clinic.getDoctorSet().size();

        //then
        Assertions.assertEquals(CLINIC_DOCTORS_NUMBER, actualDoctorSetSize, "Size of doctor set is wrong.");


    }
}