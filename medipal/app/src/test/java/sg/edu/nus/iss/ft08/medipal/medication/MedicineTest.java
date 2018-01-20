package sg.edu.nus.iss.ft08.medipal.medication;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.ft08.medipal.core.Consumption;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;
import sg.edu.nus.iss.ft08.medipal.core.MediPalUtils;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Reminder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class MedicineTest {

    Medicine medicine;

    @Test
    public void string_setters_should_trim_string_values() {
        given_a_medicine();
        when_string_values_are_set();
        then_setters_should_trim_these_values();
    }

    private void given_a_medicine() {
        medicine = new Medicine();
    }

    private void when_string_values_are_set() {
        String dummy = " dummy string value with white spaces at start and end  ";

        medicine.setName(dummy);
        medicine.setDescription(dummy);
    }

    private void then_setters_should_trim_these_values() {
        String whiteSpace = " ";
        assertFalse(medicine.getName().startsWith(whiteSpace));
        assertFalse(medicine.getName().endsWith(whiteSpace));
    }

    @Test
    public void isRemindEnabled_should_reflect_reminder_flag_value() {
        given_a_medicine();
        when_reminder_flag_is_set_to_Y();
        then_isRemindEnabled_should_be_true();

        when_reminder_flag_is_set_to_N();
        then_isRemindEnabled_should_be_false();

        when_reminder_flag_is_not_set();
        then_isRemindEnabled_should_be_false();
    }

    private void when_reminder_flag_is_set_to_Y() {
        medicine.setReminderFlag(MediPalConstants.FLAG_YES);
    }

    private void then_isRemindEnabled_should_be_true() {
        assertTrue(medicine.isReminderEnabled());
    }

    private void when_reminder_flag_is_set_to_N() {
        medicine.setReminderFlag(MediPalConstants.FLAG_NO);
    }

    private void when_reminder_flag_is_not_set() {
        medicine.setReminderFlag(null);
    }

    private void then_isRemindEnabled_should_be_false() {
        assertFalse(medicine.isReminderEnabled());
    }

    @Test
    public void isExpired_should_reflect_based_on_dateIssued_and_expireFactor() {
        given_a_medicine();
        when_issuedDate_plus_expiryFactor_is_earlier_than_today();
        then_medicine_should_be_expired();

        when_issuedDate_plus_expiryFactor_is_later_than_today();
        then_medicine_should_not_be_expired();
    }

    private void when_issuedDate_plus_expiryFactor_is_earlier_than_today() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date twoMonthsAgo = calendar.getTime();
        int oneMonth = 1;

        medicine.setDateIssued(twoMonthsAgo);
        medicine.setExpiryFactor(oneMonth);
    }

    private void then_medicine_should_be_expired() {
        assertTrue(medicine.isExpired());
    }

    private void when_issuedDate_plus_expiryFactor_is_later_than_today() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date twoMonthsAgo = calendar.getTime();
        int fiveMonths = 5;

        medicine.setDateIssued(twoMonthsAgo);
        medicine.setExpiryFactor(fiveMonths);
    }

    private void then_medicine_should_not_be_expired() {
        assertFalse(medicine.isExpired());
    }

    @Test
    public void isLowQuantity_should_reflect_based_on_quantity_and_threshold() {
        given_a_medicine();
        when_quantity_is_less_than_threshold();
        then_isLowQuantity_should_be_true();

        when_quantity_is_equal_to_threshold();
        then_isLowQuantity_should_be_true();

        when_quantity_is_more_than_threshold();
        then_isLowQuantity_should_be_false();

        when_threshold_is_less_than_zero();
        then_isLowQuantity_should_be_false();
    }

    private void when_threshold_is_less_than_zero() {
        medicine.setThreshold(-1);
    }

    private void when_quantity_is_equal_to_threshold() {
        medicine.setQuantity(5);
        medicine.setThreshold(5);
    }

    private void when_quantity_is_more_than_threshold() {
        medicine.setQuantity(6);
        medicine.setThreshold(5);
    }

    private void then_isLowQuantity_should_be_false() {
        assertFalse(medicine.isLowQuantity());
    }

    private void when_quantity_is_less_than_threshold() {
        medicine.setQuantity(5);
        medicine.setThreshold(10);
    }

    private void then_isLowQuantity_should_be_true() {
        assertTrue(medicine.isLowQuantity());
    }

    @Test
    public void isValidName_should_validate_medicine_name() {
        given_a_medicine();
        when_medicine_name_is_null();
        then_isValidName_is_false();

        when_medicine_name_is_empty();
        then_isValidName_is_false();

        when_medicine_name_is_more_than_50_characters();
        then_isValidName_is_false();

        when_medicine_name_is_not_null_not_empty_and_up_to_50_characters();
        then_isValidName_is_true();
    }

    private void when_medicine_name_is_null() {
        medicine.setName(null);
    }

    private void when_medicine_name_is_empty() {
        medicine.setName("");
    }

    private void when_medicine_name_is_more_than_50_characters() {
        String moreThan50Characters = "123456789012345678901234567890123456789012345678901";
        medicine.setName(moreThan50Characters);
    }

    private void then_isValidName_is_false() {
        assertFalse(medicine.isValidName());
    }

    private void when_medicine_name_is_not_null_not_empty_and_up_to_50_characters() {
        String upto50Characters = "12345678901234567890123456789012345678901234567890";
        medicine.setName(upto50Characters);
    }

    private void then_isValidName_is_true() {
        assertTrue(medicine.isValidName());
    }

    @Test
    public void isValidDescription_should_validate_medicine_description() {
        given_a_medicine();

        when_medicine_description_is_more_than_255_characters();
        then_isValidDescription_is_false();

        when_medicine_description_is_up_to_255_characters();
        then_isValidDescription_is_true();

        when_medicine_description_is_null_or_empty();
        then_isValidDescription_is_true();
    }

    private void when_medicine_description_is_null_or_empty() {
        medicine.setDescription(null);
    }

    private void when_medicine_description_is_more_than_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            b.append('a');
        }
        String moreThan255Characters = b.toString();
        medicine.setDescription(moreThan255Characters);
    }

    private void then_isValidDescription_is_false() {
        assertFalse(medicine.isValidDescription());
    }

    private void when_medicine_description_is_up_to_255_characters() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            b.append('a');
        }
        String upto255Characters = b.toString();
        medicine.setDescription(upto255Characters);
    }

    private void then_isValidDescription_is_true() {
        assertTrue(medicine.isValidDescription());
    }

    @Test
    public void medicine_should_have_same_ID_for_reminder_and_its_reminderId() {
        given_a_medicine();

        when_a_reminder_is_set();
        then_medicine_reminderId_should_be_the_same_as_reminder_id();
    }

    private void when_a_reminder_is_set() {
        Reminder dummy = new Reminder();
        dummy.setId(1234);

        medicine.setReminder(dummy);
    }

    private void then_medicine_reminderId_should_be_the_same_as_reminder_id() {
        assertEquals(medicine.getReminderId(), medicine.getReminder().getId());
    }

    @Test
    public void isValidRemindToTakeStartTime_should_validate_remind_to_take_start_time() {
        given_a_medicine();
        when_remind_to_take_is_enabled();

        when_remind_to_take_start_time_is_null();
        then_isValidRemindToTakeStartTime_is_false();

        whe_remind_to_take_start_time_is_not_null();
        then_isValidRemindToTakeStartTime_is_true();
    }

    private void when_remind_to_take_is_enabled() {
        medicine.enableReminderToTake(MediPalUtils.getToday(), 3, 1);
    }

    private void when_remind_to_take_start_time_is_null() {
        medicine.getReminder().setStartTime(null);
    }

    private void then_isValidRemindToTakeStartTime_is_false() {
        assertFalse(medicine.isValidRemindToTakeStartTime());
    }

    private void whe_remind_to_take_start_time_is_not_null() {
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        medicine.getReminder().setStartTime(now);
    }

    private void then_isValidRemindToTakeStartTime_is_true() {
        assertTrue(medicine.isValidRemindToTakeStartTime());
    }

    @Test
    public void isValidRemindToTakeFrequency_should_validate_remind_to_take_frequency() {
        given_a_medicine();
        when_remind_to_take_is_enabled();

        when_remind_to_take_frequency_is_less_than_one();
        then_isValidRemindToTakeFrequency_is_false();

        when_remind_to_take_frequency_is_more_than_zero();
        then_isValidRemindToTakeFrequency_is_true();
    }

    private void when_remind_to_take_frequency_is_more_than_zero() {
        medicine.getReminder().setFrequency(1);
    }

    private void then_isValidRemindToTakeFrequency_is_true() {
        assertTrue(medicine.isValidRemindToTakeFrequency());
    }

    private void when_remind_to_take_frequency_is_less_than_one() {
        medicine.getReminder().setFrequency(0);
    }

    private void then_isValidRemindToTakeFrequency_is_false() {
        assertFalse(medicine.isValidRemindToTakeFrequency());
    }

    @Test
    public void isValidRemindToTakeInterval_should_validate_remind_to_take_interval() {
        given_a_medicine();
        when_remind_to_take_is_enabled();

        when_remind_to_take_interval_is_less_than_one();
        then_isValidRemindToTakeInterval_is_false();

        when_remind_to_take_interval_is_more_than_zero();
        then_isValidRemindToTakeInterval_is_true();

    }

    private void when_remind_to_take_interval_is_less_than_one() {
        medicine.getReminder().setInterval(0);
    }

    private void then_isValidRemindToTakeInterval_is_false() {
        assertFalse(medicine.isValidRemindToTakeInterval());
    }

    private void when_remind_to_take_interval_is_more_than_zero() {
        medicine.getReminder().setInterval(1);
    }

    private void then_isValidRemindToTakeInterval_is_true() {
        assertTrue(medicine.isValidRemindToTakeInterval());
    }

    @Test
    public void consume_should_add_one_consumption(){
        int quantityBefore = 100;
        int dosage = 10;
        given_a_medicine_with_quantity_and_dosage(quantityBefore, dosage);
        when_the_medicined_is_consumed();
        then_one_consumption_should_be_added_in_medicine();
    }

    private void when_the_medicined_is_consumed() {
        medicine.consume();
    }

    private void then_one_consumption_should_be_added_in_medicine() {
        List<Consumption> consumptions = medicine.getConsumptions();
        assertTrue(consumptions.size() > 0);
    }

    @Test
    public void consume_should_reduce_medicine_quantity_by_its_dosage()
    {
        int quantityBefore = 100;
        int dosage = 10;
        int quantityAfter = 90;
        given_a_medicine_with_quantity_and_dosage(quantityBefore, dosage);
        when_the_medicined_is_consumed();
        then_quantity_should_reduce_by_dosage(quantityAfter);
    }

    private void given_a_medicine_with_quantity_and_dosage(int quantityBefore, int dosage) {
        medicine = new Medicine();
        medicine.setQuantity(quantityBefore);
        medicine.setDosage(dosage);
    }

    private void then_quantity_should_reduce_by_dosage(int quantityAfter) {
        assertEquals(quantityAfter, medicine.getQuantity());
    }

    @Test
    public void consume_should_reflect_hasNewConsumption_status(){
        int quantityBefore = 100;
        int dosage = 10;
        given_a_medicine_with_quantity_and_dosage(quantityBefore, dosage);
        when_the_medicined_is_consumed();
        then_hasNewConsumption_status_is_true();
    }

    private void then_hasNewConsumption_status_is_true() {
        assertTrue(medicine.hasNewConsumption());
    }
}
