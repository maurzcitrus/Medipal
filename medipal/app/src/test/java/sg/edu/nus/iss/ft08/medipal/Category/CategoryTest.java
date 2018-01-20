package sg.edu.nus.iss.ft08.medipal.category;

import org.junit.Test;

import sg.edu.nus.iss.ft08.medipal.core.Category;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CategoryTest {

    private Category category;

    @Test
    public void isValidName_should_validate_category_name() {
        given_a_category();
        when_category_name_is_null();
        then_isValidName_is_false();

        when_category_name_is_empty();
        then_isValidName_is_false();

        when_category_name_is_more_than_50_characters();
        then_isValidName_is_false();

        when_category_name_is_not_null_not_empty_and_up_to_50_characters();
        then_isValidName_is_true();
    }

    private void given_a_category() {
        category = new Category();
    }

    private void when_category_name_is_null(){
        category.setName(null);
    }

    private void then_isValidName_is_false() {
        assertFalse(category.isValidName());
    }

    private void then_isValidName_is_true() {
        assertTrue(category.isValidName());
    }

    private void when_category_name_is_empty() {
        category.setName("");
    }

    private void when_category_name_is_more_than_50_characters() {
        String moreThan50Characters = "123456789012345678901234567890123456789012345678901";
        category.setName(moreThan50Characters);
    }

    private void when_category_name_is_not_null_not_empty_and_up_to_50_characters(){
        String upto50Characters = "12345678901234567890123456789012345678901234567890";
        category.setName(upto50Characters);
    }

    @Test
    public void isValidDescription_should_validate_category_description() {
        given_a_category();

        when_category_description_is_more_than_255_characters();
        then_isValidDescription_is_false();

        when_category_description_is_up_to_255_characters();
        then_isValidDescription_is_true();

        when_category_description_is_null_or_empty();
        then_isValidDescription_is_true();
    }

    private void when_category_description_is_null_or_empty() {
        category.setDescription(null);
    }

    private void when_category_description_is_more_than_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            b.append('a');
        }
        String moreThan255Characters = b.toString();
        category.setDescription(moreThan255Characters);
    }

    private void then_isValidDescription_is_false() {
        assertFalse(category.isValidDescription());
    }

    private void when_category_description_is_up_to_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            b.append('a');
        }
        String upto255Characters = b.toString();
        category.setDescription(upto255Characters);
    }

    private void then_isValidDescription_is_true() {
        assertTrue(category.isValidDescription());
    }

    @Test
    public void isValidCode_should_validate_category_code() {
        given_a_category();
        when_category_code_is_null();
        then_isValidCode_is_false();

        when_category_code_is_empty();
        then_isValidCode_is_false();

        when_category_code_is_more_than_4_characters();
        then_isValidCode_is_false();

        when_category_code_is_not_null_not_empty_and_up_to_4_characters();
        then_isValidCode_is_true();
    }

    private void when_category_code_is_null(){
        category.setCode(null);
    }

    private void then_isValidCode_is_false() {
        assertFalse(category.isValidCode());
    }

    private void then_isValidCode_is_true() {
        assertTrue(category.isValidCode());
    }

    private void when_category_code_is_empty() {
        category.setCode("");
    }

    private void when_category_code_is_more_than_4_characters() {
        String moreThan4Characters = "12345";
        category.setCode(moreThan4Characters);
    }

    private void when_category_code_is_not_null_not_empty_and_up_to_4_characters(){
        String upto4Characters = "1234";
        category.setCode(upto4Characters);
    }

}
