package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.telephony.gsm.GsmCellLocation;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.fitgoose.fitgoosedemo.MyDate;
import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Jiayi Wang on 2015-07-16.
 */
public class ExerciseDetailsCard extends Card{
    private Exercise exercise;

    public ExerciseDetailsCard(Context context, Exercise exercise) {
        super(context, R.layout.exercise_card_inner);
        this.exercise = exercise;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        TextView unit_one = (TextView) parent.findViewById(R.id.exercise_card_unit_one);
        unit_one.setText(GlobalVariables.exerciseUnit.get(exercise.unit1));

        TextView unit_two = (TextView) parent.findViewById(R.id.exercise_card_unit_two);
        TextView unit_two_name = (TextView) parent.findViewById(R.id.exercise_card_unit_two_name);
        if (! exercise.secondUnit) {
            unit_two_name.setVisibility(View.INVISIBLE);
            unit_two.setVisibility(View.INVISIBLE);
        } else {
            unit_two_name.setVisibility(View.VISIBLE);
            unit_two_name.setText("Unit 2:");
            unit_two.setVisibility(View.VISIBLE);
            unit_two.setText(GlobalVariables.exerciseUnit.get(exercise.unit2));
        }

        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_shoulder)).setChecked(exercise.shoulder);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_chest)).setChecked(exercise.chest);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_farm)).setChecked(exercise.forearm);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_uarm)).setChecked(exercise.upper_arm);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_abs)).setChecked(exercise.abs);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_quads)).setChecked(exercise.quads);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_back)).setChecked(exercise.back);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_cardio)).setChecked(exercise.cardio);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_calves)).setChecked(exercise.calves);
        ((CheckBox) parent.findViewById(R.id.checkBox_exercise_secondUnit)).setChecked(exercise.secondUnit);

    }

}

