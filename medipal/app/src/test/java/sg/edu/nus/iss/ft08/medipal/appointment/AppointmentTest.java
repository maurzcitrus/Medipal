package sg.edu.nus.iss.ft08.medipal.appointment;

import org.junit.Test;

import sg.edu.nus.iss.ft08.medipal.core.Appointment;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class AppointmentTest {

    private Appointment appointment;

    @Test
    public void isValidLocation_should_validate_appointment_location() {
        given_a_appointment();
        when_appointment_location_is_null();
        then_isValidLocation_is_false();

        when_appointment_location_is_empty();
        then_isValidLocation_is_false();

        when_appointment_location_is_more_than_50_characters();
        then_isValidLocation_is_false();

        when_appointment_location_is_not_null_not_empty_and_up_to_50_characters();
        then_isValidLocation_is_true();
    }

    private void given_a_appointment() {
        appointment = new Appointment();
    }

    private void when_appointment_location_is_null(){
        appointment.setLocation(null);
    }

    private void then_isValidLocation_is_false() {
        assertFalse(appointment.isValidLocation());
    }

    private void then_isValidLocation_is_true() {
        assertTrue(appointment.isValidLocation());
    }

    private void when_appointment_location_is_empty() {
        appointment.setLocation("");
    }

    private void when_appointment_location_is_more_than_50_characters() {
        String moreThan50Characters = "123456789012345678901234567890123456789012345678901";
        appointment.setLocation(moreThan50Characters);
    }

    private void when_appointment_location_is_not_null_not_empty_and_up_to_50_characters(){
        String upto50Characters = "12345678901234567890123456789012345678901234567890";
        appointment.setLocation(upto50Characters);
    }

    @Test
    public void isValidDescription_should_validate_appointment_description() {
        given_a_appointment();

        when_appointment_description_is_more_than_255_characters();
        then_isValidDescription_is_false();

        when_appointment_description_is_null_or_empty();
        then_isValidDescription_is_false();

        when_appointment_description_is_up_to_255_characters();
        then_isValidDescription_is_true();
    }

    private void when_appointment_description_is_null_or_empty() {
        appointment.setDescription(null);
    }

    private void when_appointment_description_is_more_than_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            b.append('a');
        }
        String moreThan255Characters = b.toString();
        appointment.setDescription(moreThan255Characters);
    }

    private void then_isValidDescription_is_false() {
        assertFalse(appointment.isValidDescription());
    }

    private void when_appointment_description_is_up_to_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            b.append('a');
        }
        String upto255Characters = b.toString();
        appointment.setDescription(upto255Characters);
    }

    private void then_isValidDescription_is_true() {
        assertTrue(appointment.isValidDescription());
    }

    @Test
    public void isReminderEnabled_should_check_reminder_flag(){
        given_a_appointment();
        when_appointment_reminder_flag_is_null();
        then_isReminderEnabled_is_false();

        when_appointment_reminder_flag_is_set_N();
        then_isReminderEnabled_is_false();

        when_appointment_reminder_flag_is_set_Y();
        then_isReminderEnabled_is_true();
    }

    private void when_appointment_reminder_flag_is_null(){
        appointment.setReminderFlag(null);
    }

    private void then_isReminderEnabled_is_false(){
        assertFalse(appointment.isReminderEnabled());
    }

    private void when_appointment_reminder_flag_is_set_N(){
        appointment.setReminderFlag("N");
    }

    private void when_appointment_reminder_flag_is_set_Y(){
        appointment.setReminderFlag("Y");
    }

    private void then_isReminderEnabled_is_true(){
        assertTrue(appointment.isReminderEnabled());
    }

}
