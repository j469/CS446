package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.StatChunk;

import java.util.ArrayList;


public class PlanDetailListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<Integer> _listDataHeader;
    private ArrayList< ArrayList<StatChunk> > _listDataChild;

    public PlanDetailListAdapter(Context context, ArrayList<Integer> eIDs,
                                    ArrayList< ArrayList<StatChunk> > statChunks) {
        this._context = context;
        this._listDataHeader = eIDs;
        this._listDataChild = statChunks;
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

        final StatChunk childDetails = (StatChunk) getChild(groupPosition, childPosition);
        int eid = (Integer) getGroup(groupPosition);


        // Test
        Log.d("PlanDetailListAdapter","getChildView "+Integer.toString(eid));


        // inflate layer three chlid layout xml
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // check if this exercise has two units
            if ( GlobalVariables.searchSecondUnitByEid(eid) ) {
                convertView = infalInflater.inflate(R.layout.plan_layer_three_child_with_second_unit, null);
            } else {
                convertView = infalInflater.inflate(R.layout.plan_layer_three_child_without_second_unit, null);
            }
        }

        // set id
        TextView textView = (TextView) convertView.findViewById(R.id.plan_list_exset_setid);
        int i = childDetails.setid;
        textView.setText(i);
        // complete
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_complete);
        String s = (childDetails.complete) ? "Complete" : "Not complete";
        textView.setText(s);
        // quantity
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_quantity);
        i = childDetails.quantity;
        textView.setText(i);
        // unit
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_complete);
        s = GlobalVariables.searchUnitByEid(eid);
        textView.setText(s);
        // second unit, number of repeats
        if ( GlobalVariables.searchSecondUnitByEid(eid) ) {
            textView = (TextView) convertView.findViewById(R.id.plan_list_exset_reps);
            i = childDetails.reps;
            textView.setText(i);
        }

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
        int eid = (Integer) getGroup(groupPosition);
        String headerTitle = GlobalVariables.searchENameByEid(eid);

        Log.d("PlanDetailListAdapter", "getGroupView eid: " + Integer.toString(eid)
                + " ename: " + headerTitle +" groupP: " + Integer.toString(groupPosition));

        // inflate layer three header layout xml
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.plan_layer_three_header, null);
        }

        TextView plan_list_header = (TextView) convertView
                .findViewById(R.id.plan_exercise_ename);
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