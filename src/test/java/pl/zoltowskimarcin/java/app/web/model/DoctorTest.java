package pl.zoltowskimarcin.java.app.web.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@Tag("plain")
class DoctorTest {

    public static int DOCTOR_VISIT_LIST_SIZE = 2;
    public static String DOCTOR_NAME_TOM = "Tom";
    public static String DOCTOR_SURNAME_KOWALSKI = "Kowalski";

    @Test
    void doctor_name_is_tom() {
        //given
        Doctor doctor = new Doctor();

        //when
        doctor.setName(DOCTOR_NAME_TOM);
        String actualDoctorName = doctor.getName();

        //then
        Assertions.assertEquals(DOCTOR_NAME_TOM, actualDoctorName, "Names doesn't match");
    }

    @Test
    void doctor_surname_is_kowalski() {
        //given
        Doctor doctor = new Doctor();

        //when
        doctor.setSurname(DOCTOR_SURNAME_KOWALSKI);
        String actualDoctorSurname = doctor.getSurname();

        //then
        Assertions.assertEquals(DOCTOR_SURNAME_KOWALSKI, actualDoctorSurname, "Surnames doesn't match");

    }

    @Test
    void visits_list_size_for_doctor_is_2() {

        //given
        Doctor doctor = new Doctor();
        Visit visit1 = new Visit();
        Visit visit2 = new Visit();
        List<Visit> visitList = List.of(visit1, visit2);

        //when
        doctor.setVisitList(visitList);
        int actualListSize = doctor.getVisitList().size();

        //then
        Assertions.assertEquals(DOCTOR_VISIT_LIST_SIZE, actualListSize, "List size doesn't match");


    }
}