package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.utilities.YouTubeDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by selway on 15-06-13.
 */
public class TodaysExercisesDialog extends DialogFragment {

    ArrayList<Plan> selected_plan = new ArrayList<>();

    String muscleArea;

    public TodaysExercisesDialog() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        muscleArea = getArguments().getString("muscleArea");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // List exercises for different part of body
        View view = inflater.inflate(R.layout.dialog_body_chart, container);
        final ListView exercisesList = (ListView) view.findViewById(R.id.exercises_list);


        if (muscleArea == "shoulder") {
            getDialog().setTitle("Shoulder Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.shoulderPlan);
            selected_plan = GlobalVariables.shoulderPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "chest") {
            getDialog().setTitle("Chest Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.chestPlan);
            selected_plan = GlobalVariables.chestPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "upper_arm") {
            getDialog().setTitle("Upper Arm Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.upperarmPlan);
            selected_plan = GlobalVariables.upperarmPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "forearm") {
            getDialog().setTitle("Forearm Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.forearmPlan);
            selected_plan = GlobalVariables.forearmPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "abs") {
            getDialog().setTitle("Abs Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.absPlan);
            selected_plan = GlobalVariables.absPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "quads") {
            getDialog().setTitle("Quads Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.quadsPlan);
            selected_plan = GlobalVariables.quadsPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        else if (muscleArea == "calves") {
            getDialog().setTitle("Calves Area");
            ArrayAdapter<Plan> exercisesAdapter = new ExerciseItemAdapter(getActivity(), GlobalVariables.calvesPlan);
            selected_plan = GlobalVariables.calvesPlan;
            exercisesList.setAdapter(exercisesAdapter);
        }
        // If no exercises for today, set empty view.
        View emptyView = (TextView) view.findViewById(R.id.empty_view);
        exercisesList.setEmptyView(emptyView);

        // When user click on a specific exercise.
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Change the view
                getDialog().setContentView(R.layout.dialog_body_chart_next);
                // Set up exercise set list
                final Plan selectedPlan = selected_plan.get((int) l);
                final ArrayList<ExSet> tempExSet = selectedPlan.exSets;
                ListView exsetList = (ListView) getDialog().findViewById(R.id.exset_list);
                //Toast.makeText(getActivity(), "Set Number " + Integer.toString(selectedPlan.exSets.size()), Toast.LENGTH_SHORT).show();
                GlobalVariables.unit1 = GlobalVariables.exerciseUnit.get(GlobalVariables.searchUnitByEid(selectedPlan.eID));

                if (GlobalVariables.getExerciseByEid(selectedPlan.eID).secondUnit) {
                    GlobalVariables.unit2 = GlobalVariables.exerciseUnit.get(GlobalVariables.searchSecondUnitByEid(selectedPlan.eID));
                }
                final ArrayAdapter<ExSet> exsetAdapter = new ExerciseSetAdapter(getActivity(), tempExSet);
                exsetList.setAdapter(exsetAdapter);

                TextView titleText = (TextView) getDialog().findViewById(R.id.exercise_title);
                titleText.setText(GlobalVariables.searchENameByEid(selectedPlan.eID));



                //exsetList.findViewById(R.id.setUnit1);

                // Set up Button
                final EditText num_lbs = (EditText) getDialog().findViewById(R.id.num_lbs);
                final EditText num_set = (EditText) getDialog().findViewById(R.id.num_set);

                Button sub_lbs = (Button) getDialog().findViewById(R.id.sub_lbs);
                sub_lbs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tempNum = num_lbs.getText().toString();
                        int tempLbs = Integer.parseInt(tempNum) - 5;
                        tempNum = String.valueOf(tempLbs);
                        num_lbs.setText(tempNum);
                    }
                });

                Button add_lbs = (Button) getDialog().findViewById(R.id.add_lbs);
                add_lbs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tempNum = num_lbs.getText().toString();
                        int tempLbs = Integer.parseInt(tempNum) + 5;
                        tempNum = String.valueOf(tempLbs);
                        num_lbs.setText(tempNum);
                    }
                });

                Button sub_set = (Button) getDialog().findViewById(R.id.sub_set);
                sub_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                        //num_lbs.setText("@/"current_lbs);
                        String tempNum = num_set.getText().toString();
                        int tempLbs = Integer.parseInt(tempNum) - 1;
                        tempNum = String.valueOf(tempLbs);
                        num_set.setText(tempNum);
                    }
                });

                Button add_set = (Button) getDialog().findViewById(R.id.add_set);
                add_set.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                        //num_lbs.setText("@/"current_lbs);
                        String tempNum = num_set.getText().toString();
                        int tempLbs = Integer.parseInt(tempNum) + 1;
                        tempNum = String.valueOf(tempLbs);
                        num_set.setText(tempNum);
                    }
                });

                Button add_btn = (Button) getDialog().findViewById(R.id.add_btn);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Integer secondUnit = 0;
                        if (GlobalVariables.getExerciseByEid(selectedPlan.eID).secondUnit) {
                            secondUnit = Integer.parseInt(num_set.getText().toString());
                        }
                        ExSet newSet = new ExSet(0, selectedPlan.pID, Integer.parseInt(num_lbs.getText().toString()), secondUnit);
                        FGDataSource.storeExSet(newSet);

                        tempExSet.add(newSet);
                        exsetAdapter.notifyDataSetChanged();


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

                            if (tempExercise.shoulder) {
                                GlobalVariables.shoulderPlan.add(todayPlan);
                                Button shoulderBtn1 = (Button) getActivity().findViewById(R.id.shoulder1_btn);
                                Button shoulderBtn2 = (Button) getActivity().findViewById(R.id.shoulder2_btn);
                                // Init part color
                                if (!hasShoulder) {
                                    shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_undo);
                                    shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_undo);
                                    hasShoulder = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (shoulderProcess) {
                                        shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_in_process);
                                        shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!shoulderProcess) {
                                        shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_done);
                                        shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_done);
                                        shoulderProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    shoulderBtn1.setBackgroundResource(R.drawable.shoulder1_in_process);
                                    shoulderBtn2.setBackgroundResource(R.drawable.shoulder2_in_process);
                                    shoulderProcess = true;
                                }
                            }

                            if (tempExercise.chest) {
                                GlobalVariables.chestPlan.add(todayPlan);
                                Button chestBtn = (Button) getActivity().findViewById(R.id.chest_btn);
                                // Init part color
                                if (!hasChest) {
                                    chestBtn.setBackgroundResource(R.drawable.chest_undo);
                                    hasChest = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (chestProcess) {
                                        chestBtn.setBackgroundResource(R.drawable.chest_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!chestProcess) {
                                        chestBtn.setBackgroundResource(R.drawable.chest_done);
                                        chestProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    chestBtn.setBackgroundResource(R.drawable.chest_in_process);
                                    chestProcess = true;
                                }
                            }

                            if (tempExercise.abs) {
                                GlobalVariables.absPlan.add(todayPlan);
                                Button absBtn = (Button) getActivity().findViewById(R.id.abs_btn);
                                // Init part color
                                if (!hasAbs) {
                                    absBtn.setBackgroundResource(R.drawable.abs_undo);
                                    hasAbs = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (absProcess) {
                                        absBtn.setBackgroundResource(R.drawable.abs_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!absProcess) {
                                        absBtn.setBackgroundResource(R.drawable.abs_done);
                                        absProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    absBtn.setBackgroundResource(R.drawable.abs_in_process);
                                    absProcess = true;
                                }
                            }

                            if (tempExercise.upper_arm) {
                                GlobalVariables.upperarmPlan.add(todayPlan);
                                Button upperArmBrn1 = (Button) getActivity().findViewById(R.id.upper_arm1_btn);
                                Button upperArmBrn2 = (Button) getActivity().findViewById(R.id.upper_arm2_btn);
                                // Init part color
                                if (!hasUpperArm) {
                                    upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_undo);
                                    upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_undo);
                                    hasUpperArm = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (upperArmProcess) {
                                        upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_in_process);
                                        upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!upperArmProcess) {
                                        upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_done);
                                        upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_done);
                                        upperArmProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    upperArmBrn1.setBackgroundResource(R.drawable.upper_arm1_in_process);
                                    upperArmBrn2.setBackgroundResource(R.drawable.upper_arm2_in_process);
                                    upperArmProcess = true;
                                }
                            }

                            if (tempExercise.forearm) {
                                GlobalVariables.forearmPlan.add(todayPlan);
                                Button forearmBtn1 = (Button) getActivity().findViewById(R.id.forearm1_btn);
                                Button forearmBtn2 = (Button) getActivity().findViewById(R.id.forearm2_btn);
                                // Init part color
                                if (!hasForearm) {
                                    forearmBtn1.setBackgroundResource(R.drawable.forearm1_undo);
                                    forearmBtn2.setBackgroundResource(R.drawable.forearm2_undo);
                                    hasForearm = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (forearmProcess) {
                                        forearmBtn1.setBackgroundResource(R.drawable.forearm1_in_process);
                                        forearmBtn2.setBackgroundResource(R.drawable.forearm2_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!forearmProcess) {
                                        forearmBtn1.setBackgroundResource(R.drawable.forearm1_done);
                                        forearmBtn2.setBackgroundResource(R.drawable.forearm2_done);
                                        forearmProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    forearmBtn1.setBackgroundResource(R.drawable.forearm1_in_process);
                                    forearmBtn2.setBackgroundResource(R.drawable.forearm2_in_process);
                                    forearmProcess = true;
                                }
                            }

                            if (tempExercise.quads) {
                                GlobalVariables.quadsPlan.add(todayPlan);
                                Button quadsBtn = (Button) getActivity().findViewById(R.id.quads_btn);
                                // Init part color
                                if (!hasQuads) {
                                    quadsBtn.setBackgroundResource(R.drawable.quads_undo);
                                    hasQuads = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (quadsProcess) {
                                        quadsBtn.setBackgroundResource(R.drawable.quads_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!quadsProcess) {
                                        quadsBtn.setBackgroundResource(R.drawable.quads_done);
                                        quadsProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    quadsBtn.setBackgroundResource(R.drawable.quads_in_process);
                                    quadsProcess = true;
                                }
                            }

                            if (tempExercise.calves) {
                                GlobalVariables.calvesPlan.add(todayPlan);
                                Button calvesBtn = (Button) getActivity().findViewById(R.id.calves_btn);
                                // Init part color
                                if (!hasCalves) {
                                    calvesBtn.setBackgroundResource(R.drawable.calves_undo);
                                    hasCalves = true;
                                }

                                if (todayPlan.exSets.size() == 0) {
                                    if (calvesProcess) {
                                        calvesBtn.setBackgroundResource(R.drawable.calves_in_process);
                                    }
                                } else if (todayPlan.numOfSets <= todayPlan.exSets.size()) {
                                    // if one of the exercise in in process, cannot make it complete
                                    if (!calvesProcess) {
                                        calvesBtn.setBackgroundResource(R.drawable.calves_done);
                                        calvesProcess = true;
                                    }
                                } else if (todayPlan.numOfSets > todayPlan.exSets.size()) {
                                    calvesBtn.setBackgroundResource(R.drawable.calves_in_process);
                                    calvesProcess = true;
                                }
                            }

                            //Toast.makeText(getActivity(), GlobalVariables.searchENameByEid(todayData.get(i).eID), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return view;
    }
}
