package pl.zoltowskimarcin.java.app.web.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Tag("plain")
class VisitTest {

    public static LocalDateTime VISIT_DATE_01_01_2000 = LocalDateTime.of(2000, 01, 01, 0, 0);

    @Test
    void visit_date_is_01_01_2000() {
        //given
        Visit visit = new Visit();

        //when
        visit.setDate(VISIT_DATE_01_01_2000);
        LocalDateTime actualVisitDate = visit.getDate();

        //then
        Assertions.assertEquals(VISIT_DATE_01_01_2000, actualVisitDate, "Visit dates doesn't match");
    }

}