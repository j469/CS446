package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.StatChunk;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private List< ArrayList<String> > _listDataChild;
    private FGDataSource datasource;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 List< ArrayList<String> > listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        datasource = FGDataSource.getInstance(context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childPName = (String) getChild(groupPosition, childPosition);//plan name

        // inflate layer two child layout xml
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.plan_layer_two_child, null);
        }

/*        // first display plan name
        TextView plan_list_header = (TextView) convertView
                .findViewById(R.id.plan_p_name);
        plan_list_header.setTypeface(null, Typeface.BOLD);
        plan_list_header.setText(childPName);

        // set the adapter
        int pid = datasource.searchPidByPname(childPName);
        ArrayList<Integer> eIDs = datasource.searchEidByPid(pid);
        ArrayList<StatChunk>statChunks = new ArrayList<>();
        for (int eid: eIDs) {
            ArrayList<ExSet> tempExSets = datasource.searchExSet(pid,eid);
            int i = 0;
            for (ExSet es: tempExSets) {
                StatChunk tempStatChunk = new StatChunk( eid, i, es.quantity, es.numOfReps, es.complete);
                statChunks.add(tempStatChunk);
                i++;
            }
        }

        PlanDetailAdapter planDetailListAdapter = new PlanDetailAdapter(_context,
                                R.layout.plan_layer_three_child_with_second_unit, statChunks);
        ListView listViewItems = (ListView) convertView.findViewById(R.id.plan_list_items);
        listViewItems.setAdapter(planDetailListAdapter);
*/
        datasource.close();
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        //Log.d("ExpandableListAdapter","getGroupView "+headerTitle);

        // inflate layer two header layout xml
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.plan_layer_two_header, null);
        }

        TextView plan_list_header = (TextView) convertView
                .findViewById(R.id.plan_list_header);
        plan_list_header.setTypeface(null, Typeface.BOLD);
        plan_list_header.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
