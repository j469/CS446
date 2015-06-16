package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitgoose.fitgoosedemo.utilities.CustomExerciseDialog;

public class SettingsFragment extends Fragment {

    private Context context;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button button = (Button) rootView.findViewById(R.id.setting_add_exercise_button);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                CustomExerciseDialog dialog = new CustomExerciseDialog(context);
                dialog.setTitle("New Exercise:");
                dialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}