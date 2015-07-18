package com.fitgoose.fitgoosedemo.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.ExercisesFragment;
import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomExerciseDialog extends Dialog {
    private Context mContext;
    private List<String> spinner_dropdown_string = Arrays.asList("None", "lbs", "Repeats", "Minutes", "Meters");
    private int unit_one_int = -1;
    private int unit_two_int = -1;

    public interface CustomExerciseDialogListener {
        public void ready();
    }
    private CustomExerciseDialogListener mReadyListener;

    public CustomExerciseDialog(Context context) {
        super(context);
        mContext = context;
    }
    public CustomExerciseDialog(Context context, CustomExerciseDialogListener c) {
        super(context);
        mContext = context;
        mReadyListener = c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_custom_exercise);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, spinner_dropdown_string);

        Spinner unit_one = (Spinner) findViewById(R.id.custom_unit_one);
        unit_one.setAdapter(adapter);
        unit_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unit_one_int = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Spinner unit_two = (Spinner) findViewById(R.id.custom_unit_two);
        unit_two.setAdapter(adapter);
        unit_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unit_two_int = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        Button buttonDone = (Button) findViewById(R.id.setting_add_exercise_done);
        Button buttonCancel = (Button) findViewById(R.id.setting_add_exercise_cancel);

        buttonDone.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {

                String name = ((EditText)findViewById(R.id.custom_exercise_name)).getText().toString();
                Boolean checkBox_shoulder= ((CheckBox) findViewById(R.id.checkBox_shoulder)).isChecked();
                Boolean checkBox_chest= ((CheckBox) findViewById(R.id.checkBox_chest)).isChecked();
                Boolean checkBox_farm= ((CheckBox) findViewById(R.id.checkBox_farm)).isChecked();
                Boolean checkBox_uarm= ((CheckBox) findViewById(R.id.checkBox_uarm)).isChecked();
                Boolean checkBox_abs= ((CheckBox) findViewById(R.id.checkBox_abs)).isChecked();
                Boolean checkBox_quads= ((CheckBox) findViewById(R.id.checkBox_quads)).isChecked();
                Boolean checkBox_back= ((CheckBox) findViewById(R.id.checkBox_back)).isChecked();
                Boolean checkBox_cardio= ((CheckBox) findViewById(R.id.checkBox_cardio)).isChecked();
                Boolean checkBox_calves= ((CheckBox) findViewById(R.id.checkBox_calves)).isChecked();
                Boolean checkBox_secondUnit= ((CheckBox) findViewById(R.id.checkBox_secondUnit)).isChecked();

                if (name.equals("")) {
                    Toast.makeText(mContext, "Needs valid name.",Toast.LENGTH_SHORT).show();
                } else if (unit_one_int == -1) {
                    Toast.makeText(mContext, "Needs valid unit one.",Toast.LENGTH_SHORT).show();
                } else if (checkBox_secondUnit && unit_two_int == -1) {
                    Toast.makeText(mContext, "Needs valid unit two.",Toast.LENGTH_SHORT).show();
                } else {
                    Exercise e = new Exercise(0, true, name, checkBox_secondUnit, unit_one_int, unit_two_int,
                                    checkBox_shoulder, checkBox_chest, checkBox_abs, checkBox_uarm, checkBox_farm,
                                    checkBox_quads, checkBox_calves, checkBox_back, checkBox_cardio,"");
                    FGDataSource.storeExercise(e);
                    FGDataSource.cacheExercise();
                    mReadyListener.ready();
                    CustomExerciseDialog.this.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                CustomExerciseDialog.this.dismiss();
            }
        });
    }
}