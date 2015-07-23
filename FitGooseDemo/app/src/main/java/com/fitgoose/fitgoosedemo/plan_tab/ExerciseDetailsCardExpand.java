package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.utilities.YouTubeDialog;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Jiayi Wang on 2015-07-20.
 */
public class ExerciseDetailsCardExpand extends CardExpand {
    private Exercise exercise;
    Context context;
    int unit_one_int;
    int unit_two_int;

    public interface CardExpandListener {
        public void remove(int eid);
        public void edit(Exercise e);
        public void show_youtube(String ename);
    }
    private CardExpandListener cardExpandListener;

    public ExerciseDetailsCardExpand(Context context, Exercise exercise,CardExpandListener cardExpandListener) {
        super(context, R.layout.exercise_card_inner);
        this.context = context;
        this.exercise = exercise;
        this.cardExpandListener = cardExpandListener;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        parent.setBackgroundColor(mContext.getResources().getColor(R.color.card_backgroundExpand));

        //Retrieve elements
        final Spinner spinner_unit_one= (Spinner) parent.findViewById(R.id.exercise_card_unit_one);
        final Spinner spinner_unit_two= (Spinner) parent.findViewById(R.id.exercise_card_unit_two);
        final CheckBox checkBox_exercise_shoulder = (CheckBox) parent.findViewById(R.id.checkBox_exercise_shoulder);
        final CheckBox checkBox_exercise_chest = (CheckBox) parent.findViewById(R.id.checkBox_exercise_chest);
        final CheckBox checkBox_exercise_farm = (CheckBox) parent.findViewById(R.id.checkBox_exercise_farm);
        final CheckBox checkBox_exercise_uarm = (CheckBox) parent.findViewById(R.id.checkBox_exercise_uarm);
        final CheckBox checkBox_exercise_abs = (CheckBox) parent.findViewById(R.id.checkBox_exercise_abs);
        final CheckBox checkBox_exercise_quads = (CheckBox) parent.findViewById(R.id.checkBox_exercise_quads);
        final CheckBox checkBox_exercise_back = (CheckBox) parent.findViewById(R.id.checkBox_exercise_back);
        final CheckBox checkBox_exercise_cardio = (CheckBox) parent.findViewById(R.id.checkBox_exercise_cardio);
        final CheckBox checkBox_exercise_calves = (CheckBox) parent.findViewById(R.id.checkBox_exercise_calves);
        final CheckBox checkBox_exercise_secondUnit = (CheckBox) parent.findViewById(R.id.checkBox_exercise_secondUnit);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, GlobalVariables.exerciseUnit);


        spinner_unit_one.setAdapter(adapter);
        spinner_unit_one.setSelection(exercise.unit1);
        spinner_unit_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unit_one_int = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinner_unit_two.setAdapter(adapter);
        spinner_unit_two.setSelection(exercise.unit2);
        spinner_unit_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unit_two_int = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        checkBox_exercise_shoulder.setChecked(exercise.shoulder);
        checkBox_exercise_chest.setChecked(exercise.chest);
        checkBox_exercise_farm.setChecked(exercise.forearm);
        checkBox_exercise_uarm.setChecked(exercise.upper_arm);
        checkBox_exercise_abs.setChecked(exercise.abs);
        checkBox_exercise_quads.setChecked(exercise.quads);
        checkBox_exercise_back.setChecked(exercise.back);
        checkBox_exercise_cardio.setChecked(exercise.cardio);
        checkBox_exercise_calves.setChecked(exercise.calves);
        checkBox_exercise_secondUnit.setChecked(exercise.secondUnit);

        Button button_video = (Button) parent.findViewById(R.id.button_video);
        Button button_edit = (Button) parent.findViewById(R.id.button_edit);
        Button button_remove = (Button) parent.findViewById(R.id.button_remove);

        button_video.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                cardExpandListener.show_youtube(exercise.name);
            }
        });

        button_remove.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                cardExpandListener.remove(exercise.eID);
            }
        });

        button_edit.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                exercise.unit1 = unit_one_int;
                exercise.unit2 = unit_two_int;
                exercise.shoulder = checkBox_exercise_shoulder.isChecked();
                exercise.chest =checkBox_exercise_chest.isChecked();
                exercise.forearm =checkBox_exercise_farm.isChecked();
                exercise.upper_arm =checkBox_exercise_uarm.isChecked();
                exercise.abs =checkBox_exercise_abs.isChecked();
                exercise.quads =checkBox_exercise_quads.isChecked();
                exercise.back =checkBox_exercise_back.isChecked();
                exercise.cardio =checkBox_exercise_cardio.isChecked();
                exercise.calves =checkBox_exercise_calves.isChecked();
                exercise.secondUnit =checkBox_exercise_secondUnit.isChecked();

                cardExpandListener.edit(exercise);
            }
        });

    }
}
