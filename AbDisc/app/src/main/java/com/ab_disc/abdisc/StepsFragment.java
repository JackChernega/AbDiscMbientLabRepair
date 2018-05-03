package com.ab_disc.abdisc;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class StepsFragment extends Fragment {
    @Nullable
    TextView dateView;
    TextView stepsView;
    TextView currentGoalView;
    TextView stepsGoalPercentageCompletedView;
    SharedPreferences sharedPreferences;
    DecoView arcView;
    SeriesItem seriesItem;
    int seriesIndex;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps,null);
        // get all the views that are going to be modified
        dateView = (TextView) rootView.findViewById(R.id.date_steps_text_view);
        stepsView = (TextView) rootView.findViewById(R.id.step_count_text_view);
        currentGoalView = (TextView) rootView.findViewById(R.id.step_goal_text_view);
        stepsGoalPercentageCompletedView = (TextView) rootView.findViewById(R.id.step_goal_percentage_completed_text_view);
        arcView = (DecoView) rootView.findViewById(R.id.steps_arc_view);

        // persistent data
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String currentDateString = sharedPreferences.getString(getString(R.string.saved_current_date),"Error Retrieving Date.");
        int stepsToday = sharedPreferences.getInt(getString(R.string.saved_steps_today),0);
        String stepsTodayString = String.valueOf(stepsToday);
        int stepsGoalInteger = sharedPreferences.getInt(getString(R.string.saved_steps_goal),1000);
        String stepsGoalString = String.valueOf(stepsGoalInteger);

        double stepsCompletedPercentage = (stepsToday* 100.0)/stepsGoalInteger ;

        dateView.setText(currentDateString);
        stepsView.setText(stepsTodayString);
        currentGoalView.setText(stepsGoalString);
        String stepsCompletedPercentageString = String.format("%.2f",stepsCompletedPercentage) + "%";
        stepsGoalPercentageCompletedView.setText(stepsCompletedPercentageString);

        // Create background track
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build());

        //Create data series track
        seriesItem = new SeriesItem.Builder(getResources().getColor(R.color.colorPrimary))
                .setRange(0, 100, 0)
                .setLineWidth(32f)
                .build();

        seriesIndex = arcView.addSeries(seriesItem);
        arcView.addEvent(new DecoEvent.Builder((int)stepsCompletedPercentage).setIndex(seriesIndex).setDelay(500).build());

        return rootView;
    }

    public void updateStepsFragment() {
        if(isVisible()){

            sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

            String currentDateString = sharedPreferences.getString(getString(R.string.saved_current_date),"Error Retrieving Date.");
            int stepsToday = sharedPreferences.getInt(getString(R.string.saved_steps_today),0);
            String stepsTodayString = String.valueOf(stepsToday);
            int stepsGoalInteger = sharedPreferences.getInt(getString(R.string.saved_steps_goal),1000);
            String stepsGoalString = String.valueOf(stepsGoalInteger);

            double stepsCompletedPercentage = (stepsToday* 100.0)/stepsGoalInteger ;

            dateView.setText(currentDateString);
            stepsView.setText(stepsTodayString);
            currentGoalView.setText(stepsGoalString);
            String stepsCompletedPercentageString = String.format("%.2f",stepsCompletedPercentage) + "%";
            stepsGoalPercentageCompletedView.setText(stepsCompletedPercentageString);
        }
    }
}
