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

        setNumber.setText("0");
        setUnit1.setText(Integer.toString(tempExset.quantity1));
        setUnit2.setText(Integer.toString(tempExset.quantity2));

        return customView;

    }
}
