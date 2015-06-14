package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TodaysExercisesFragment extends Fragment implements View.OnClickListener{

    public TodaysExercisesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_today, container, false);

        // Body Part Button
        Button chestButton = (Button) rootView.findViewById(R.id.chest);
        chestButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chest:
                DialogFragment chest = new DialogFragmentChest();
                chest.show(getActivity().getFragmentManager(), "theDialog");
                break;
        }

    }
}
