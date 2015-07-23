package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.utilities.YouTubeDialog;

import java.util.ArrayList;

/**
 * Created by selway on 15-07-20.
 */
public class ExerciseItemAdapter extends ArrayAdapter<Plan> {

    private Context mContext; //instance variable

    public ExerciseItemAdapter(Context context, ArrayList<Plan> plans) {
        super(context, R.layout.exercise_item_layout, plans);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater tempInflater = LayoutInflater.from(getContext());
        View customView = tempInflater.inflate(R.layout.exercise_item_layout, parent, false);

        final Plan todayPlan = getItem(position);
        final String exName = GlobalVariables.searchENameByEid(todayPlan.eID);
        TextView text1 = (TextView) customView.findViewById(R.id.exerciseName);
        TextView text2 = (TextView) customView.findViewById(R.id.exerciseSetNumber);
        ImageView ytbBtn = (ImageView) customView.findViewById(R.id.ytbBtn);

        ytbBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String ytbAdd = GlobalVariables.getExerciseByEid(todayPlan.eID).youtubeURL;
                Toast.makeText(getContext(), ytbAdd, Toast.LENGTH_SHORT).show();
                YouTubeDialog videoDialog = YouTubeDialog.newInstance(exName, ytbAdd);
                videoDialog.show(((Activity) mContext).getFragmentManager(), exName);
            }

        });

        text1.setText(exName);
        text2.setText(Integer.toString(todayPlan.exSets.size()) + "/" + Integer.toString(todayPlan.numOfSets));

        return customView;

    }
}
