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

import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;

import java.util.ArrayList;

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

        // Check the process of each part
        createBodyProcess(rootView);

        // Connect Body Part Button with listener
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
        DialogFragment newDialog = new TodaysExercisesDialog();
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
                args.putString("muscleArea", "upper_arm");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
            case R.id.forearm1_btn:
            case R.id.forearm2_btn:
                args.putString("muscleArea", "forearm");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
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
                args.putString("muscleArea", "calves");
                newDialog.setArguments(args);
                newDialog.show(getActivity().getFragmentManager(), "theDialog");
                break;
        }

    }

    public static void createBodyProcess (View rootView) {

        MyDate today = MyDate.getToday();
        ArrayList<Plan> todayData = FGDataSource.searchPlanByDate(today);

        boolean hasShoulder = false;
        boolean hasChest = false;
        boolean hasAbs = false;
        boolean hasUpperArm = false;
        boolean hasForearm = false;
        boolean hasQuads = false;
        boolean hasCalves = false;
        boolean hasBack = false;

        boolean shoulderProcess = false;
        boolean chestProcess = false;
        boolean absProcess = false;
        boolean upperArmProcess = false;
        boolean forearmProcess = false;
        boolean quadsProcess = false;
        boolean calvesProcess = false;
        boolean backProcess = false;

        GlobalVariables.shoulderPlan.clear();
        GlobalVariables.chestPlan.clear();
        GlobalVariables.absPlan.clear();
        GlobalVariables.upperarmPlan.clear();
        GlobalVariables.forearmPlan.clear();
        GlobalVariables.quadsPlan.clear();
        GlobalVariables.calvesPlan.clear();
        GlobalVariables.backPlan.clear();
        for (int i = 0; i < todayData.size(); i++) {
            Plan todayPlan = todayData.get(i);
            Exercise tempExercise = GlobalVariables.getExerciseByEid(todayPlan.eID);

            if(tempExercise == null) continue;

            if (tempExercise.shoulder) {
                GlobalVariables.shoulderPlan.add(todayPlan);
                Button shoulderBtn1 = (Button) rootView.findViewById(R.id.shoulder1_btn);
                Button shoulderBtn2 = (Button) rootView.findViewById(R.id.shoulder2_btn);
                // Init part color
                if (!hasShoulder) {
                    shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_undo);
                    shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_undo);
                    hasShoulder = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (shoulderProcess){
                        shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_in_process);
                        shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!shoulderProcess) {
                        shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_done);
                        shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_done);
                        shoulderProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_in_process);
                    shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_in_process);
                    shoulderProcess = true;
                }
            }

            if (tempExercise.chest) {
                GlobalVariables.chestPlan.add(todayPlan);
                Button chestBtn = (Button) rootView.findViewById(R.id.chest_btn);
                // Init part color
                if (!hasChest) {
                    chestBtn.setBackgroundResource(R.drawable.chest_undo);
                    hasChest = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (chestProcess) {
                        chestBtn.setBackgroundResource(R.drawable.chest_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!chestProcess) {
                        chestBtn.setBackgroundResource(R.drawable.chest_done);
                        chestProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    chestBtn.setBackgroundResource(R.drawable.chest_in_process);
                    chestProcess = true;
                }
            }

            if (tempExercise.abs) {
                GlobalVariables.absPlan.add(todayPlan);
                Button absBtn = (Button) rootView.findViewById(R.id.abs_btn);
                // Init part color
                if (!hasAbs) {
                    absBtn.setBackgroundResource(R.drawable.abs_undo);
                    hasAbs = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (absProcess) {
                        absBtn.setBackgroundResource(R.drawable.abs_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!absProcess) {
                        absBtn.setBackgroundResource(R.drawable.abs_done);
                        absProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    absBtn.setBackgroundResource(R.drawable.abs_in_process);
                    absProcess = true;
                }
            }

            if (tempExercise.upper_arm) {
                GlobalVariables.upperarmPlan.add(todayPlan);
                Button upperArmBrn1 = (Button) rootView.findViewById(R.id.upper_arm1_btn);
                Button upperArmBrn2 = (Button) rootView.findViewById(R.id.upper_arm2_btn);
                // Init part color
                if (!hasUpperArm) {
                    upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_undo);
                    upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_undo);
                    hasUpperArm = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (upperArmProcess){
                        upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_in_process);
                        upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!upperArmProcess) {
                        upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_done);
                        upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_done);
                        upperArmProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_in_process);
                    upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_in_process);
                    upperArmProcess = true;
                }
            }

            if (tempExercise.forearm) {
                GlobalVariables.forearmPlan.add(todayPlan);
                Button forearmBtn1 = (Button) rootView.findViewById(R.id.forearm1_btn);
                Button forearmBtn2 = (Button) rootView.findViewById(R.id.forearm2_btn);
                // Init part color
                if (!hasForearm) {
                    forearmBtn1.setBackgroundResource(R.drawable.forearm1_undo);
                    forearmBtn2.setBackgroundResource(R.drawable.forearm2_undo);
                    hasForearm = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (forearmProcess){
                        forearmBtn1.setBackgroundResource(R.drawable.forearm1_in_process);
                        forearmBtn2.setBackgroundResource(R.drawable.forearm2_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!forearmProcess) {
                        forearmBtn1.setBackgroundResource(R.drawable.forearm1_done);
                        forearmBtn2.setBackgroundResource(R.drawable.forearm2_done);
                        forearmProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    forearmBtn1.setBackgroundResource(R.drawable.forearm1_in_process);
                    forearmBtn2.setBackgroundResource(R.drawable.forearm2_in_process);
                    forearmProcess = true;
                }
            }

            if (tempExercise.quads) {
                GlobalVariables.quadsPlan.add(todayPlan);
                Button quadsBtn = (Button) rootView.findViewById(R.id.quads_btn);
                // Init part color
                if (!hasQuads) {
                    quadsBtn.setBackgroundResource(R.drawable.quads_undo);
                    hasQuads = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (quadsProcess) {
                        quadsBtn.setBackgroundResource(R.drawable.quads_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!quadsProcess) {
                        quadsBtn.setBackgroundResource(R.drawable.quads_done);
                        quadsProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    quadsBtn.setBackgroundResource(R.drawable.quads_in_process);
                    quadsProcess = true;
                }
            }

            if (tempExercise.calves) {
                GlobalVariables.calvesPlan.add(todayPlan);
                Button calvesBtn = (Button) rootView.findViewById(R.id.calves_btn);
                // Init part color
                if (!hasCalves) {
                    calvesBtn.setBackgroundResource(R.drawable.calves_undo);
                    hasCalves = true;
                }

                if (todayPlan.exSets.size() == 0){
                    if (calvesProcess) {
                        calvesBtn.setBackgroundResource(R.drawable.calves_in_process);
                    }
                }
                else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                    // if one of the exercise in in process, cannot make it complete
                    if (!calvesProcess) {
                        calvesBtn.setBackgroundResource(R.drawable.calves_done);
                        calvesProcess = true;
                    }
                }
                else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                    calvesBtn.setBackgroundResource(R.drawable.calves_in_process);
                    calvesProcess = true;
                }
            }

            //Toast.makeText(getActivity(), GlobalVariables.searchENameByEid(todayData.get(i).eID), Toast.LENGTH_SHORT).show();
        }
    }
}
