package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.os.Bundle;
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
import java.util.LinkedList;
import java.util.Random;

import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.jjoe64.graphview.DefaultLabelFormatter;
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
        graph.removeAllSeries();

        MyDate current = MyDate.getToday();
        MyDate firstDate = MyDate.getToday();

        int exPos = exerciseSpinner.getSelectedItemPosition();
        if(exPos == 0) return;
        int eid = GlobalVariables.storedExercises.get(exPos - 1).getID();

        switch (timeRangeSpinner.getSelectedItemPosition()) {
            case 0: // Past Week
                firstDate.add(MyDate.DATE, -7);
                break;
            case 1: // Past Month
                firstDate.add(MyDate.MONTH, -1);
                break;
            case 2: // Past Year
                firstDate.add(MyDate.YEAR, -1);
                break;
            case 3: // All time
                firstDate = FGDataSource.searchEarliestPlanDate();
                break;
        }

        plans = FGDataSource.searchPlanByExerciseAndTimeRange(eid, firstDate, current);

        testPrintout();

        LineGraphSeries<DataPoint> dataPoints = processData(propertySpinner.getSelectedItemPosition());
        if(dataPoints == null) return;

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(firstDate.toCalendar().getTimeInMillis());
        graph.getViewport().setMaxX(current.toCalendar().getTimeInMillis());
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        double ceiling = 4*(Math.ceil( (int) dataPoints.getHighestValueY() / 4));
        graph.getViewport().setMaxY(ceiling);

        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    Calendar cal = Calendar.getInstance();
                    long millis = (long) value;
                    cal.setTimeInMillis(millis);
                    Log.d("WTF", Long.toString(millis));
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DATE);
                    return String.format("%02d", month) + "/" + String.format("%02d", day);
                } else {
                    // show normal y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        graph.addSeries(dataPoints);
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

    private LineGraphSeries<DataPoint> processData(int pos) {
        LinkedList<Plan> validPlans = new LinkedList<>();
        for(Plan p : plans) {
            if (!p.exSets.isEmpty()) {
                validPlans.add(p);
            }
        }
        if(validPlans.size() <= 0) return null;

        DataPoint[] dpts = new DataPoint[validPlans.size()];
        int index = 0;
        for(Plan p : validPlans) {
            switch (pos) {
                case 0: // Average
                    dpts[index] = processAverage(p);
                    break;
                case 1: // Best
                    dpts[index] = processBest(p);
                    break;
                case 2: // Number of Reps
                    dpts[index] = processNumReps(p);
                    break;
                case 3: // Number of Sets
                    dpts[index] = processNumSets(p);
                    break;
            }
            index++;
        }

        return new LineGraphSeries<>(dpts);
    }

    private DataPoint processAverage(Plan p) {
        int sum = 0;
        for(ExSet set : p.exSets) {
            sum += set.quantity;
        }
        double average = (double) sum / (double) p.exSets.size();
        double time = (double) p.date.toCalendar().getTimeInMillis();

        return new DataPoint(time, average);
    }

    private DataPoint processBest(Plan p) {
        double max = p.exSets.get(0).quantity;
        for(ExSet set : p.exSets) {
            if (set.quantity > max) {
                max = set.quantity;
            }
        }
        double time = (double) p.date.toCalendar().getTimeInMillis();
        return new DataPoint(time, max);
    }

    private DataPoint processNumReps(Plan p) {
        double numReps = 0;
        for(ExSet set : p.exSets) {
            numReps += set.numOfReps;
        }
        double time = (double) p.date.toCalendar().getTimeInMillis();
        return new DataPoint(time, numReps);
    }
    private DataPoint processNumSets(Plan p) {
        double numSets = p.exSets.size();
        double time = (double) p.date.toCalendar().getTimeInMillis();
        return new DataPoint(time, numSets);
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
