package com.fitgoose.fitgoosedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

/**
 * Created by selway on 15-06-13.
 */
public class TodaysExercisesDialog extends DialogFragment{

    String[] shoulder_exercises = new String[] {"Standing Alternating Dumbbell Press", "Clean and Press", "Single Dumbbell Raise", "Single-Arm Linear Jammer"};
    String[] chest_exercises = new String[] {"Dumbell Bench Press", "Incline Dumbbell Press", "Push ups", "Barbell Bench Press - Medium Grip"};
    String[] upper_arm_exercises = new String[] {};
    String[] forearm_exercises = new String[] {};
    String[] abs_exercises = new String[] {"Bottoms Up", "Spell Caster", "Spider Crawl"};
    String[] quads_exercises = new String[] {"Barbell Full Squat", "Barbell Walking Lunge"};
    String[] calves_exercises = new String[] {};
    String muscleArea;

    public TodaysExercisesDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        muscleArea = getArguments().getString("muscleArea");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // List for exercises
        View view = inflater.inflate(R.layout.dialog_body_chart, container);
        ListView exercisesList = (ListView) view.findViewById(R.id.exercises_list);

        if (muscleArea == "shoulder") {
            getDialog().setTitle("Shoulder Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, shoulder_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }

        else if (muscleArea == "chest") {
            getDialog().setTitle("Chest Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, chest_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }
        else if (muscleArea == "upper_arm") {
            getDialog().setTitle("Upper Arm Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, upper_arm_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }
        else if (muscleArea == "forearm") {
            getDialog().setTitle("Forearm Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, forearm_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }
        else if (muscleArea == "abs") {
            getDialog().setTitle("Abs Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, abs_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }
        else if (muscleArea == "quads") {
            getDialog().setTitle("Quads Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, quads_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }
        else if (muscleArea == "calves") {
            getDialog().setTitle("Calves Area");
            ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, calves_exercises);
            exercisesList.setAdapter(exercisesAdapter);
            View emptyView = (TextView) view.findViewById(R.id.empty_view);
            exercisesList.setEmptyView(emptyView);
        }


        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) l) {
                    case 0:
                        getDialog().setContentView(R.layout.dialog_body_chart_next);

                        final EditText num_lbs = (EditText) getDialog().findViewById(R.id.num_lbs);
                        final EditText num_set = (EditText) getDialog().findViewById(R.id.num_set);

                        Button sub_lbs = (Button) getDialog().findViewById(R.id.sub_lbs);
                        sub_lbs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                                //num_lbs.setText("@/"current_lbs);
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

                        Button done_btn = (Button) getDialog().findViewById(R.id.done_btn);
                        done_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Button chestBtn = (Button) getActivity().findViewById(R.id.chest_btn);
                                chestBtn.setBackgroundResource(R.drawable.chest_done);
                                getDialog().dismiss();
                            }
                        });

                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Incline Dumbbell Press", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Push ups", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Barbell Bench Press - Medium Grip", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });


        return view;
    }
}
