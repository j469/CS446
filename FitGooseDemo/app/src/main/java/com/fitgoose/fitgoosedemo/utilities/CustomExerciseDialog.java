package com.fitgoose.fitgoosedemo.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import java.util.ArrayList;

public class CustomExerciseDialog extends Dialog {
    private Context mContext;

    public CustomExerciseDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_custom_exercise);

        Button buttonDone = (Button) findViewById(R.id.setting_add_exercise_done);
        Button buttonCancel = (Button) findViewById(R.id.setting_add_exercise_cancel);
        buttonDone.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.custom_exercise_name)).getText().toString();
                String unit= ((EditText)findViewById(R.id.custom_exercise_unit)).getText().toString();
                Boolean checkBox_shoulder= ((CheckBox) findViewById(R.id.checkBox_shoulder)).isChecked();
                Boolean checkBox_arm= ((CheckBox) findViewById(R.id.checkBox_arm)).isChecked();
                Boolean checkBox_back= ((CheckBox) findViewById(R.id.checkBox_back)).isChecked();
                Boolean checkBox_chest= ((CheckBox) findViewById(R.id.checkBox_chest)).isChecked();
                Boolean checkBox_abs= ((CheckBox) findViewById(R.id.checkBox_abs)).isChecked();
                Boolean checkBox_legs= ((CheckBox) findViewById(R.id.checkBox_legs)).isChecked();
                Boolean checkBox_oxy= ((CheckBox) findViewById(R.id.checkBox_oxy)).isChecked();
                Boolean checkBox_cardio= ((CheckBox) findViewById(R.id.checkBox_cardio)).isChecked();
                Boolean checkBox_secondUnit= ((CheckBox) findViewById(R.id.checkBox_secondUnit)).isChecked();

                if (!name.equals("") && !unit.equals("")) {
                    Exercise e = new Exercise(1, true, name, unit, checkBox_shoulder, checkBox_arm, checkBox_back, checkBox_chest,
                            checkBox_abs, checkBox_legs, checkBox_oxy, checkBox_cardio, checkBox_secondUnit);
                    FGDataSource.storeExercise(e);
                    FGDataSource.cacheExercise();
                    CustomExerciseDialog.this.dismiss();
                } else {
                    Toast.makeText(mContext, "Needs valid name and unit.",Toast.LENGTH_SHORT).show();
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