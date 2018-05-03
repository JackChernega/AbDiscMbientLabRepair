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

public class CrunchesFragment extends Fragment {
    @Nullable
    TextView dateView;
    TextView crunchesView;
    TextView currentGoalView;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crunches, null);
        dateView = (TextView) rootView.findViewById(R.id.date_steps_text_view);
        crunchesView = (TextView) rootView.findViewById(R.id.step_count_text_view);
        currentGoalView = (TextView) rootView.findViewById(R.id.step_goal_text_view);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String crunchesGoalString = String.valueOf(sharedPreferences.getInt(getString(R.string.saved_crunches_goal), 100));
        currentGoalView.setText(crunchesGoalString);
        return rootView;
    }
}
