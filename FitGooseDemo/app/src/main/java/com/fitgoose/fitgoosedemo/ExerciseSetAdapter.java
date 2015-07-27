package com.fitgoose.fitgoosedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import java.util.ArrayList;

/**
 * Created by selway on 15-07-20.
 */
public class ExerciseSetAdapter extends ArrayAdapter<ExSet> {

    public ExerciseSetAdapter(Context context, ArrayList<ExSet> exsets) {
        super(context,R.layout.exercise_set_layout, exsets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater tempInflater = LayoutInflater.from(getContext());
        View customView = tempInflater.inflate(R.layout.exercise_set_layout, parent, false);

        ExSet tempExset = getItem(position);
        TextView setNumber = (TextView) customView.findViewById(R.id.setNumber);
        TextView setUnit1 = (TextView) customView.findViewById(R.id.setUnit1);
        TextView setUnit2 = (TextView) customView.findViewById(R.id.setUnit2);

        TextView unit1 = (TextView) customView.findViewById(R.id.unit1);
        TextView unit2 = (TextView) customView.findViewById(R.id.unit2);

        unit1.setText(GlobalVariables.unit1);

        if (tempExset.quantity2 != 0) {
            setUnit2.setText(Integer.toString(tempExset.quantity2));
            unit2.setText(GlobalVariables.unit2);
        }
        else {
            setUnit2.setText("");
            unit2.setText("");
        }
        setNumber.setText(Integer.toString(position));
        setUnit1.setText(Integer.toString(tempExset.quantity1));

        return customView;

    }
}
