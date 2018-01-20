package sg.edu.nus.iss.ft08.medipal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sg.edu.nus.iss.ft08.medipal.appointment.AppointmentFragment;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementFragment;
import sg.edu.nus.iss.ft08.medipal.medication.MedicationFragment;


public class MainActivityPagerAdapter extends FragmentPagerAdapter {

    public static final int FOR_MEDICATION = 0;
    public static final int FOR_MEASUREMENT = 1;
    public static final int FOR_APPOINTMENT = 2;

    private static final String TAB_TITLE_MEDICATION = "Medication";
    private static final String TAB_TITLE_MEASUREMENT = "Measurement";
    private static final String TAB_TITLE_APPOINTMENT = "Appointment";

    private static final int totalTabs = 3;

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FOR_MEASUREMENT: {
                return MeasurementFragment.newInstance(1);
            }
            case FOR_APPOINTMENT: {
                return AppointmentFragment.newInstance(1);
            }
            case FOR_MEDICATION:
            default: {
                return MedicationFragment.newInstance(1);
            }
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FOR_MEASUREMENT: {
                return TAB_TITLE_MEASUREMENT;
            }
            case FOR_APPOINTMENT: {
                return TAB_TITLE_APPOINTMENT;
            }
            case FOR_MEDICATION:
            default: {
                return TAB_TITLE_MEDICATION;
            }
        }
    }
}
