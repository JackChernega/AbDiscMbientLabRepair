package com.ab_disc.abdisc;
/*
 Main activity calculates step count, crunch count, current date and sets step goal and crunch goal.
 */


import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    // sensor manager manages all the sensor from the application. In this case
    // it manages a step detector sensor.
    private SensorManager sensorManager;
    // In this case it is a step detector sensor
    private Sensor sensor;
    // this boolean variable will indicatee wheter the sensor is present on the phone.
    private boolean isSensorPresent = false;
    // shared preferences is used for persistent data. Because this application
    // does not store more information than step daily count, crunch dalily count, date, daily goals
    // it does not need a database. However, when handling multiple dates, a database should be used.
    private SharedPreferences sharedPreferences;
    private StepsFragment stepsFragment;
    private EditText editTextStepsGoal;
    private EditText editTextCrunchesGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        // intialize step counter sensor.
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isSensorPresent = true;
        } else {
            // here logic for couting steps with accelerometer could be used.
            isSensorPresent = false;
        }

        //
        String currentDateString = DateFormat.getDateInstance().format(new Date());
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String dateSaved = sharedPreferences.getString(getString(R.string.saved_current_date), "ERROR");
        if (!currentDateString.equals(dateSaved)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.saved_steps_today), 0);
            editor.putString(getString(R.string.saved_current_date), currentDateString);
            editor.commit();
        }

        stepsFragment = new StepsFragment();

        // load default fragment to be shown in framelayout
        loadFragment(stepsFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String currentDateString = DateFormat.getDateInstance().format(new Date());
        String dateSaved = sharedPreferences.getString(getString(R.string.saved_current_date), "ERROR");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!currentDateString.equals(dateSaved)) {
            editor.putInt(getString(R.string.saved_steps_today), 0);
            editor.putString(getString(R.string.saved_current_date), currentDateString);
            editor.commit();
        } else {
            int stepTodaySaved = sharedPreferences.getInt(getString(R.string.saved_steps_today), 0);
            int stepsToday = stepTodaySaved + 1;
            editor.putInt(getString(R.string.saved_steps_today), stepsToday);
            editor.commit();
        }
        stepsFragment.updateStepsFragment();
    }

    public void setStepsGoal(View view) {
        editTextStepsGoal = (EditText) findViewById(R.id.steps_goal_edit_text);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int newStepGoal = Integer.parseInt(editTextStepsGoal.getText().toString());
        editor.putInt(getString(R.string.saved_steps_goal), newStepGoal);
        editor.commit();
    }

    public void setCrunchesGoal(View view) {
        editTextStepsGoal = (EditText) findViewById(R.id.crunches_goal_edit_text);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int newCrunchesGoal = Integer.parseInt(editTextStepsGoal.getText().toString());
        editor.putInt(getString(R.string.saved_crunches_goal), newCrunchesGoal);
        editor.commit();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_steps:
                fragment = new StepsFragment();
                break;
            case R.id.navigation_goals:
                fragment = new GoalsFragment();
                break;
            case R.id.navigation_crunches:
                fragment = new CrunchesFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
