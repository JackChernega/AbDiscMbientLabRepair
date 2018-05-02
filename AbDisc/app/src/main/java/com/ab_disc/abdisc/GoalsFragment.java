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
import android.widget.EditText;

public class GoalsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goals,null);
        EditText editTextStepsGoal = (EditText) rootView.findViewById(R.id.steps_goal_edit_text);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String stepsGoalString = String.valueOf(sharedPreferences.getInt(getString(R.string.saved_steps_goal),1000));

        editTextStepsGoal.setText(stepsGoalString);
        return rootView;
    }
}
