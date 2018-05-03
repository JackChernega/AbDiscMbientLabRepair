package com.ab_disc.abdisc;

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

    private SensorManager sensorManager;
    private SharedPreferences sharedPreferences;
    private Sensor sensor;
    private boolean isSensorPresent = false;
    private StepsFragment stepsFragment;
    private EditText editTextStepsGoal;
    private EditText editTextCrunchesGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        stepsFragment = new StepsFragment();

        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String dateSaved = sharedPreferences.getString(getString(R.string.saved_current_date), "ERROR");
        if (!currentDateString.equals(dateSaved)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.saved_steps_today), 0);
            editor.putString(getString(R.string.saved_current_date), currentDateString);
            editor.commit();
        }

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
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            sensorManager.unregisterListener(this);
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
