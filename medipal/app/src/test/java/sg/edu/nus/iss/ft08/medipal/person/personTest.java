package sg.edu.nus.iss.ft08.medipal.person;

import org.junit.Test;

import sg.edu.nus.iss.ft08.medipal.core.Person;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class personTest  {
    private Person person;
    @Test
    public void isValidLocation_should_validate_person_name() {
        given_a_person();
        when_person_name_is_null();
        then_isValidName_is_false();

        when_person_name_is_not_null();
        then_isValidName_is_true();
    }

    private void given_a_person() {
        person = new Person();
    }

    private void when_person_name_is_null(){
        person.setName(null);
    }

    private void then_isValidName_is_false() {assertFalse(person.isValidName());}

    private void when_person_name_is_not_null(){
        String personName = "John";
        person.setName(personName);
    };

    private void then_isValidName_is_true() {
        assertTrue(person.isValidName());
    }

}
