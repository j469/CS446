package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
        Button chestButton = (Button) rootView.findViewById(R.id.chest_btn);
        chestButton.setOnClickListener(this);
        Button shoulder1Button = (Button) rootView.findViewById(R.id.shoulder1_btn);
        shoulder1Button.setOnClickListener(this);
        Button shoulder2Button = (Button) rootView.findViewById(R.id.shoulder2_btn);
        shoulder2Button.setOnClickListener(this);
        Button upperArm1Button = (Button) rootView.findViewById(R.id.upper_arm1_btn);
        upperArm1Button.setOnClickListener(this);
        Button upperArm2Button = (Button) rootView.findViewById(R.id.upper_arm2_btn);
        upperArm2Button.setOnClickListener(this);
        Button forearm1Button = (Button) rootView.findViewById(R.id.forearm1_btn);
        forearm1Button.setOnClickListener(this);
        Button forearm2Button = (Button) rootView.findViewById(R.id.forearm2_btn);
        forearm2Button.setOnClickListener(this);
        Button absButton = (Button) rootView.findViewById(R.id.abs_btn);
        absButton.setOnClickListener(this);
        Button quadsButton = (Button) rootView.findViewById(R.id.quads_btn);
        quadsButton.setOnClickListener(this);
        Button calvesButton = (Button) rootView.findViewById(R.id.calves_btn);
        calvesButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    // Button switch based on which part of muscle clicked
    @Override
    public void onClick(View view) {
        // Create new dialog to show exercises
        DialogFragment newDialog = new DialogFragmentChest();
        // Send the value of muscle area which user clicked
        Bundle args = new Bundle();
        switch (view.getId()) {
            case R.id.chest_btn:
                args.putString("muscleArea", "chest");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
            case R.id.shoulder1_btn:
            case R.id.shoulder2_btn:
                args.putString("muscleArea", "shoulder");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
            case R.id.upper_arm1_btn:
            case R.id.upper_arm2_btn:
                Toast.makeText(getActivity(), "No exercise for Upper Arm today! :D", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forearm1_btn:
            case R.id.forearm2_btn:
                Toast.makeText(getActivity(), "No exercise for Forearm today! :D", Toast.LENGTH_SHORT).show();
                break;
            case R.id.abs_btn:
                args.putString("muscleArea", "abs");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
            case R.id.quads_btn:
                args.putString("muscleArea", "quads");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
            case R.id.calves_btn:
                Toast.makeText(getActivity(), "No exercise for Calves today! :D", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
