package sg.edu.nus.iss.ft08.medipal.emergencyContact;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import sg.edu.nus.iss.ft08.medipal.core.EmergencyContact;

public class EmergencyContactTest {

    private EmergencyContact emergencyContact;

    @Test
    public void isValidName_should_validate_emergencyContact_name() {
        given_a_emergencyContact();
        when_emergencyContact_name_is_null();
        then_isValidName_is_false();

        when_emergencyContact_name_is_empty();
        then_isValidName_is_false();

        when_emergencyContact_name_is_more_than_20_characters();
        then_isValidName_is_false();

        when_emergencyContact_name_is_not_null_not_empty_and_up_to_20_characters();
        then_isValidName_is_true();
    }

    private void given_a_emergencyContact() {
        emergencyContact = new EmergencyContact();
    }

    private void when_emergencyContact_name_is_null(){
        emergencyContact.setName(null);
    }

    private void then_isValidName_is_false() {
        assertFalse(emergencyContact.isValidName());
    }

    private void then_isValidName_is_true() {
        assertTrue(emergencyContact.isValidName());
    }

    private void when_emergencyContact_name_is_empty() {
        emergencyContact.setName("");
    }

    private void when_emergencyContact_name_is_more_than_20_characters() {
        String moreThan20Characters = "123456789012345678901";
        emergencyContact.setName(moreThan20Characters);
    }

    private void when_emergencyContact_name_is_not_null_not_empty_and_up_to_20_characters(){
        String upto20Characters = "12345678901234567890";
        emergencyContact.setName(upto20Characters);
    }

    @Test
    public void isValidDescription_should_validate_emergencyContact_description() {
        given_a_emergencyContact();

        when_emergencyContact_description_is_more_than_255_characters();
        then_isValidDescription_is_false();

        when_emergencyContact_description_is_up_to_255_characters();
        then_isValidDescription_is_true();

        when_emergencyContact_description_is_null_or_empty();
        then_isValidDescription_is_true();
    }

    private void when_emergencyContact_description_is_null_or_empty() {
        emergencyContact.setDescription(null);
    }

    private void when_emergencyContact_description_is_more_than_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            b.append('a');
        }
        String moreThan255Characters = b.toString();
        emergencyContact.setDescription(moreThan255Characters);
    }

    private void then_isValidDescription_is_false() {
        assertFalse(emergencyContact.isValidDescription());
    }

    private void when_emergencyContact_description_is_up_to_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            b.append('a');
        }
        String upto255Characters = b.toString();
        emergencyContact.setDescription(upto255Characters);
    }

    private void then_isValidDescription_is_true() {
        assertTrue(emergencyContact.isValidDescription());
    }

    @Test
    public void isValidContact_should_validate_emergencyContact_contact() {
        given_a_emergencyContact();
        when_emergencyContact_contact_is_null();
        then_isValidContact_is_false();

        when_emergencyContact_contact_is_empty();
        then_isValidContact_is_false();

        when_emergencyContact_contact_is_more_than_8_characters();
        then_isValidContact_is_false();

        when_emergencyContact_contact_is_less_than_8_characters();
        then_isValidContact_is_false();

        when_emergencyContact_contact_is_not_null_not_empty_and_exact_8_characters();
        then_isValidContact_is_true();
    }

    private void when_emergencyContact_contact_is_null(){
        emergencyContact.setContactNo(null);
    }

    private void then_isValidContact_is_false() {
        assertFalse(emergencyContact.isValidContact());
    }

    private void then_isValidContact_is_true() {
        assertTrue(emergencyContact.isValidContact());
    }

    private void when_emergencyContact_contact_is_empty() {
        emergencyContact.setContactNo("");
    }

    private void when_emergencyContact_contact_is_more_than_8_characters() {
        String moreThan8Characters = "123456789";
        emergencyContact.setContactNo(moreThan8Characters);
    }

    private void when_emergencyContact_contact_is_less_than_8_characters() {
        String lessThan8Characters = "1234567";
        emergencyContact.setContactNo(lessThan8Characters);
    }

    private void when_emergencyContact_contact_is_not_null_not_empty_and_exact_8_characters(){
        String exact8Characters = "12345678";
        emergencyContact.setContactNo(exact8Characters);
    }
}
