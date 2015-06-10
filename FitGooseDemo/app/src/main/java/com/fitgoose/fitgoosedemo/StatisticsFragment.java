package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsFragment extends Fragment {

    Spinner exerciseSpinner;
    Spinner timeRangeSpinner;
    Spinner propertySpinner;
    ArrayAdapter<CharSequence> exerciseAdapter;
    ArrayAdapter<CharSequence> timeRangeAdapter;
    ArrayAdapter<CharSequence> adapter;
    GraphView graph;

    private String[] sampleExerciseNames = {"Push Up", "Jogging", "Bench Press"};

    public StatisticsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        // Setup the spinners for selecting graph settings
        exerciseSpinner = (Spinner) rootView.findViewById(R.id.exercise_spinner);
        exerciseAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                android.R.layout.simple_spinner_item, getExerciseNames());
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);

        timeRangeSpinner = (Spinner) rootView.findViewById(R.id.time_range_spinner);
        timeRangeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graph_time_ranges,
                android.R.layout.simple_spinner_item);
        timeRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeRangeSpinner.setAdapter(timeRangeAdapter);

        propertySpinner = (Spinner) rootView.findViewById(R.id.properties_spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.graph_properties,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(adapter);

        // Create the graph view
        graph = (GraphView) rootView.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private ArrayList<CharSequence> getExerciseNames() {
        ArrayList<CharSequence> list = new ArrayList<CharSequence>(
                Arrays.asList(sampleExerciseNames));
        list.add(0, "Exercise:");

        return list;
    }
}
