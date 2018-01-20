package sg.edu.nus.iss.ft08.medipal.healthBio;

import org.junit.Test;

import sg.edu.nus.iss.ft08.medipal.core.HealthBio;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class HealthBioTest {

    private HealthBio healthBio;

    @Test
    public void isValidCondition_should_validate_healthBio_condition() {
        given_a_healthBio();
        when_healthBio_condition_is_null();
        then_isValidCondition_is_false();

        when_healthBio_condition_is_empty();
        then_isValidCondition_is_false();

        when_healthBio_condition_is_more_than_50_characters();
        then_isValidCondition_is_false();

        when_healthBio_condition_is_not_null_not_empty_and_up_to_50_characters();
        then_isValidCondition_is_true();
    }

    private void given_a_healthBio() {
        healthBio = new HealthBio();
    }

    private void when_healthBio_condition_is_null(){
        healthBio.setCondition(null);
    }

    private void then_isValidCondition_is_false() {
        assertFalse(healthBio.isValidCondition());
    }

    private void then_isValidCondition_is_true() {
        assertTrue(healthBio.isValidCondition());
    }

    private void when_healthBio_condition_is_empty() {
        healthBio.setCondition("");
    }

    private void when_healthBio_condition_is_more_than_50_characters() {
        String moreThan50Characters = "123456789012345678901234567890123456789012345678901";
        healthBio.setCondition(moreThan50Characters);
    }

    private void when_healthBio_condition_is_not_null_not_empty_and_up_to_50_characters(){
        String upto50Characters = "12345678901234567890123456789012345678901234567890";
        healthBio.setCondition(upto50Characters);
    }

}
