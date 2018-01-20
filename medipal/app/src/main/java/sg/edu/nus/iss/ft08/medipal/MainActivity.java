package sg.edu.nus.iss.ft08.medipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.appointment.AppointmentDetailActivity;
import sg.edu.nus.iss.ft08.medipal.appointment.AppointmentFragment;
import sg.edu.nus.iss.ft08.medipal.category.CategoryListActivity;
import sg.edu.nus.iss.ft08.medipal.core.Appointment;
import sg.edu.nus.iss.ft08.medipal.core.HealthBio;
import sg.edu.nus.iss.ft08.medipal.core.Measurement;
import sg.edu.nus.iss.ft08.medipal.core.MediPalConstants;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.core.Person;
import sg.edu.nus.iss.ft08.medipal.emergency.EmergencyContactListActivity;
import sg.edu.nus.iss.ft08.medipal.healthBio.HealthBioDetailActivity;
import sg.edu.nus.iss.ft08.medipal.healthBio.HealthBioListActivity;
import sg.edu.nus.iss.ft08.medipal.help.HelpActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.BloodPressure;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementDetailActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementDetailBloodPressureActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementDetailPulseActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementDetailTemperatureActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementDetailWeightActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.MeasurementFragment;
import sg.edu.nus.iss.ft08.medipal.measurement.Pulse;
import sg.edu.nus.iss.ft08.medipal.measurement.Temperature;
import sg.edu.nus.iss.ft08.medipal.measurement.bloodpressureListActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.pulseListActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.temperatureListActivity;
import sg.edu.nus.iss.ft08.medipal.measurement.weightListActivity;
import sg.edu.nus.iss.ft08.medipal.medication.MedicationDetailActivity;
import sg.edu.nus.iss.ft08.medipal.medication.MedicationFragment;
import sg.edu.nus.iss.ft08.medipal.medication.UpdateCommand;
import sg.edu.nus.iss.ft08.medipal.personal.PersonListActivity;
import sg.edu.nus.iss.ft08.medipal.personal.PersonalDetailActivity;
import sg.edu.nus.iss.ft08.medipal.reminder.ReminderServiceManager;

public class MainActivity
        extends
        AppCompatActivity
        implements
        ViewPager.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener,
        FloatingActionButton.OnClickListener,
        MedicationFragment.OnMedicineDetailListener,
        MedicationFragment.OnConsumeListener,
        MeasurementFragment.OnListFragmentInteractionListener,
        AppointmentFragment.OnListFragmentInteractionListener {

    // To ensure that findViewById is called only one time for each attribute
    // do not access these private attribute directly
    // access them via getXyz() methods
    // benefit?
    // 1. UI element is cached in memory for the life time of Activity
    // 2. code is easier to read
    // 3. focus on implementation instead of distracting you with findViewById calls everywhere

    private MainActivityPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private TabLayout tabLayout;
    private DrawerLayout drawer;

    private NavigationView getNavigationView() {
        if (navigationView == null) {
            navigationView = (NavigationView) findViewById(R.id.nav_view);
        }

        return navigationView;
    }

    private FloatingActionButton getFloatingActionButton() {
        if (floatingActionButton == null) {
            floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        }
        return floatingActionButton;
    }

    private ViewPager getViewPager() {
        if (viewPager == null) {
            viewPager = (ViewPager) findViewById(R.id.container);
        }
        return viewPager;
    }

    private TabLayout getTabLayout() {
        if (tabLayout == null) {
            tabLayout = (TabLayout) findViewById(R.id.tabs);
        }
        return tabLayout;
    }

    private Toolbar getToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
        }
        return toolbar;
    }

    private DrawerLayout getDrawer() {
        if (drawer == null) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
        return drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasPersonal = checkPersonal();

        if (!hasPersonal) {
            gotoPersonalAsFirstTime();
        } else {
            initLayout();
            initToolbar();
            initDrawer();
            initTab();
            initFloatingButton();
            initNavigationView();
        }
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
    }

    private void initToolbar() {
        setSupportActionBar(getToolbar());
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                getDrawer(),
                getToolbar(),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        getDrawer().addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initTab() {
        pagerAdapter = new MainActivityPagerAdapter(getSupportFragmentManager());

        getViewPager().setAdapter(pagerAdapter);
        getViewPager().addOnPageChangeListener(this);
        getTabLayout().setupWithViewPager(viewPager);
    }

    private void initFloatingButton() {
        getFloatingActionButton().setOnClickListener(this);
    }

    private void initNavigationView() {
        getNavigationView().setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = getDrawer();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // insert menu items to action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        startActivity(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        startActivity(item);
        getDrawer().closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onViewMedicineDetail(Medication item) {
        // prepare bundle
        Bundle parameters = new Bundle();
        parameters.putInt(MedicationFragment.ARG_MEDICINE_ID, item.getMedicineId());

        // go to medication edit page
        Intent i = new Intent(MainActivity.this, MedicationDetailActivity.class);
        i.putExtras(parameters);

        startActivity(i);
    }

    @Override
    public void onListFragmentInteraction(Measurement item) {
        Intent i = null;
        if(item instanceof BloodPressure) {
            i = new Intent(MainActivity.this, bloodpressureListActivity.class);
        } else if (item instanceof Temperature) {
            i = new Intent(MainActivity.this, temperatureListActivity.class);
        } else if (item instanceof Pulse) {
            i = new Intent(MainActivity.this, pulseListActivity.class);
        } else {
            i = new Intent(MainActivity.this, weightListActivity.class);
        }
        startActivity(i);
    }

    @Override
    public void onListFragmentInteraction(Appointment item) {
        Bundle parameters = new Bundle();
        parameters.putInt(AppointmentFragment.ARG_APPOINTMENT_ID, item.getId());

        Intent i = new Intent(MainActivity.this, AppointmentDetailActivity.class);
        i.putExtras(parameters);

        startActivity(i);
    }

    @Override
    public void onConsumeMedicine(Medication item) {
        Medicine medicine;

        medicine = item.getMedicine();

        if (medicine.isExpired()) {
            Toast.makeText(this, medicine.getName() + " is expired.", Toast.LENGTH_SHORT).show();
        } else if (medicine.isOutOfStock()) {
            Toast.makeText(this, medicine.getName() + " is out of stock.", Toast.LENGTH_SHORT).show();
        } else {
            medicine.consume();
            UpdateCommand command = new UpdateCommand(MainActivity.this);
            command.execute(item);

            StringBuilder b = new StringBuilder();
            b.append("Consumption update successful.");

            if (medicine.isLowQuantity()) {
                b.append("Need to top-up ");
                b.append(medicine.getName());
                b.append(".");
                ReminderServiceManager.startThresholdReminder(this, medicine);
            }

            Toast.makeText(this, b.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View v) {
        // Handle floating action button clicks here.
        int position = viewPager.getCurrentItem();

        switch (position) {
            case MainActivityPagerAdapter.FOR_MEASUREMENT: {
                Intent i3 = new Intent(MainActivity.this, MeasurementDetailActivity.class);
                startActivity(i3);
                break;
            }
            case MainActivityPagerAdapter.FOR_APPOINTMENT: {
                Intent i2 = new Intent(MainActivity.this, AppointmentDetailActivity.class);
                startActivity(i2);
                break;
            }
            case MainActivityPagerAdapter.FOR_MEDICATION:
            default: {
                Intent i1 = new Intent(MainActivity.this, MedicationDetailActivity.class);
                startActivity(i1);
                break;
            }
        }

    }

    private void startActivity(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_emergency) {
            intent = new Intent(MainActivity.this, EmergencyContactListActivity.class);
        } else if (id == R.id.nav_personal_bio) {
            intent = new Intent(MainActivity.this, PersonalDetailActivity.class);
        } else if (id == R.id.nav_health_bio) {
            intent = new Intent(MainActivity.this, HealthBioListActivity.class);
        } else if (id == R.id.nav_medicine_category) {
            intent = new Intent(MainActivity.this, CategoryListActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    private boolean checkPersonal() {
        sg.edu.nus.iss.ft08.medipal.personal.FindAllQuery personalQuery;
        List<Person> personalRecords = new ArrayList<>();

        personalQuery = new sg.edu.nus.iss.ft08.medipal.personal.FindAllQuery(this);

        try {
            personalQuery.execute();
            personalRecords = personalQuery.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return !personalRecords.isEmpty();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case MainActivityPagerAdapter.FOR_MEASUREMENT: {
                getFloatingActionButton().setVisibility(View.GONE);
                break;
            }
            case MainActivityPagerAdapter.FOR_APPOINTMENT:
            case MainActivityPagerAdapter.FOR_MEDICATION:
            default: {
                getFloatingActionButton().setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void gotoPersonalAsFirstTime() {
        // prepare bundle
        Bundle parameters = new Bundle();
        parameters.putBoolean(MediPalConstants.ARG_IS_FIRST_TIME, true);

        // go to medication edit page
        Intent i = new Intent(MainActivity.this, PersonalDetailActivity.class);
        i.putExtras(parameters);

        startActivity(i);
    }
}
