package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;


public class CalendarFragment extends Fragment{
    CalendarView calendar;
    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = (CalendarView) rootView.findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {
                // need to add 1 to Month
                 String date = String.valueOf(year) +"-"+ String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth);
                //then toast
                Toast.makeText(getActivity().getApplicationContext(),date,Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
