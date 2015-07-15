package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class StatisticsFragment extends Fragment {

    private View rootView;
    private Spinner exerciseSpinner;
    private Spinner timeRangeSpinner;
    private Spinner propertySpinner;
    private ArrayAdapter<String> exerciseAdapter;
    private ArrayAdapter<CharSequence> timeRangeAdapter;
    private ArrayAdapter<CharSequence> propertyAdapter;
    private SpinnerListener spinnerListener;
    private GraphView graph;
    private ArrayList<Plan> plans;

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
        exerciseAdapter = new ArrayAdapter<>(getActivity(),
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
        propertyAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.graph_properties,
                android.R.layout.simple_spinner_item);
        propertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        propertySpinner.setAdapter(propertyAdapter);
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

    private ArrayList<String> getExerciseNames() {
        ArrayList<String> list = GlobalVariables.getAllEName();
        list.add(0, "-");

        return list;
    }

    private void evaluatePlot() {
        int numDataPts = 0;
        graph.removeAllSeries();

        MyDate current = MyDate.getToday();
        MyDate before = MyDate.getToday();

        int exPos = exerciseSpinner.getSelectedItemPosition();
        if(exPos == 0) return;
        int eid = GlobalVariables.storedExercises.get(exPos - 1).getID();

        switch (timeRangeSpinner.getSelectedItemPosition()) {
            case 0: // Past Week
                before.add(MyDate.DATE, -7);
                break;
            case 1: // Past Month
                before.add(MyDate.MONTH, -1);
                break;
            case 2: // Past Year
                before.add(MyDate.YEAR, -1);
                break;
            case 3: // All time
                before = FGDataSource.searchEarliestPlanDate();
                break;
        }

        plans = FGDataSource.searchPlanByExerciseAndTimeRange(eid, before, current);
        testPrintout();


//        graph.getViewport().setXAxisBoundsManual(true);
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(numDataPts - 1);
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(max);
//        graph.addSeries();

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
        if (max <= min) return null;

        Random rng = new Random();
        DataPoint[] dpts = new DataPoint[numDataPts];

        for (int i = 0; i < numDataPts; i++) {
            dpts[i] = new DataPoint(i, min + rng.nextInt(max - min));
        }

        return new LineGraphSeries<DataPoint>(dpts);
    }

    private void testPrintout() {
        for(Plan p : plans) {
            Log.d(StatisticsFragment.class.getName(),
                    "pid:" + Integer.toString(p.pID) + " " +
                    "date:" + Integer.toString(p.date.getTimeStamp()) + " " +
                    "eid:" + Integer.toString(p.eID) + " " +
                    "numOfSets:" + Integer.toString(p.numOfSets));

        }
    }
}
