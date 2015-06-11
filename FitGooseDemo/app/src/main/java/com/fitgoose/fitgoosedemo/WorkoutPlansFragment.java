package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.StatChunk;
import com.fitgoose.fitgoosedemo.plan_tab.ExpandableListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WorkoutPlansFragment extends Fragment{

    private Context context;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List< ArrayList<String> > listDataChild;
    private FGDataSource datasource;

    public WorkoutPlansFragment() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        datasource = FGDataSource.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        datasource = FGDataSource.getInstance(context);

        View rootView = inflater.inflate(R.layout.fragment_plans_workout, container, false);

/*        // get the listview
        expListView = (ExpandableListView) rootView.findViewById(R.id.plan_expandableListView);

        // preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new ArrayList <  ArrayList<String> >();
        // get date in yyyy-MM-dd format
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // then store the data, to pass to adapter
        for (int i=0; i<7; i++) {
            // first add the date to listDataHeader[]
            String curDate = sdf.format(c.getTime());
            listDataHeader.add(curDate);
            // then store the pid into listDataChild[]
            listDataChild.add(datasource.searchPNameByDate(curDate));
            // then go to tomorrow
            c.add(Calendar.DATE, 1);
        }


        listAdapter = new ExpandableListAdapter(context, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
*/
        datasource.close();
        return rootView;
    }

}
