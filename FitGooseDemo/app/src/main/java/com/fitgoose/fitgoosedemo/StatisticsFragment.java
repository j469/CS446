package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsFragment extends Fragment {

    private View rootView;
    private Spinner exerciseSpinner;
    private Spinner timeRangeSpinner;
    private Spinner propertySpinner;
    private ArrayAdapter<CharSequence> exerciseAdapter;
    private ArrayAdapter<CharSequence> timeRangeAdapter;
    private ArrayAdapter<CharSequence> adapter;
    private SpinnerListener spinnerListener;
    private GraphView graph;

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
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        //Initialize the spinner listener
        spinnerListener = new SpinnerListener();

        // Setup the spinners for selecting graph settings
        exerciseSpinner = (Spinner) rootView.findViewById(R.id.exercise_spinner);
        exerciseAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                android.R.layout.simple_spinner_item, getExerciseNames());
        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(exerciseAdapter);
        exerciseSpinner.setOnItemSelectedListener(spinnerListener);

        timeRangeSpinner = (Spinner) rootView.findViewById(R.id.time_range_spinner);
        timeRangeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graph_time_ranges,
                android.R.layout.simple_spinner_item);
        timeRangeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeRangeSpinner.setAdapter(timeRangeAdapter);
        timeRangeSpinner.setOnItemSelectedListener(spinnerListener);

        propertySpinner = (Spinner) rootView.findViewById(R.id.properties_spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.graph_properties,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(adapter);
        propertySpinner.setOnItemSelectedListener(spinnerListener);

        // Create the graph view
        graph = (GraphView) rootView.findViewById(R.id.graph);
        evaluatePlot();

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

    private void evaluatePlot() {
        int numDataPts = 0;
        int min = 0;
        int max = 0;
        graph.removeAllSeries();

        switch (exerciseSpinner.getSelectedItemPosition()) {
            case 1:
                min = 4;
                max = 7;
                break;
            case 2:
                min = 20;
                max = 60;
                break;
            case 3:
                min = 0;
                max = 20;
                break;
        }
        switch (timeRangeSpinner.getSelectedItemPosition()) {
            case 1:
                numDataPts = 7;
                break;
            case 2:
                numDataPts = 30;
                break;
            case 3:
                numDataPts = 12;
                break;
            case 4:
                numDataPts = 50;
                break;
        }
        if(numDataPts != 0) {
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(numDataPts - 1);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(max);
            graph.addSeries(getRandomSeries(numDataPts, min, max));
        }
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            evaluatePlot();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // Test method for generating a random series
    private LineGraphSeries<DataPoint> getRandomSeries(int numDataPts, int min, int max) {
        Random rng = new Random();
        DataPoint[] dpts = new DataPoint[numDataPts];

        for(int i = 0; i < numDataPts; i++) {
            dpts[i] = new DataPoint(i, min + rng.nextInt(max - min));
        }

        return new LineGraphSeries<DataPoint>(dpts);
    }
}
