package com.ab_disc.abdisc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StepsFragment extends Fragment {
    @Nullable
    TextView dateView;
    TextView stepsView;
    SharedPreferences sharedPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps,null);
        dateView = (TextView) rootView.findViewById(R.id.date_steps_text_view);
        stepsView = (TextView) rootView.findViewById(R.id.step_count_text_view);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String currentDate = sharedPreferences.getString(getString(R.string.saved_current_date),"Error Retrieving Date.");
        String stepsToday = String.valueOf(sharedPreferences.getInt(getString(R.string.saved_steps_today),0));

        dateView.setText(currentDate);
        stepsView.setText(stepsToday);

        return rootView;
    }

    public void updateStepsFragment() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        String currentDate = sharedPreferences.getString(getString(R.string.saved_current_date),"ERROR");
        String stepsToday = String.valueOf(sharedPreferences.getInt(getString(R.string.saved_steps_today),0));

        dateView.setText(currentDate);
        stepsView.setText(stepsToday);
    }
}
