package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.StatChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guest on 2015-06-09.
 */
public class PlanDetailAdapter extends ArrayAdapter< StatChunk > {
    private Context _context;
    private int layoutResourceId;
    private ArrayList<StatChunk> _listDataChild;

    public PlanDetailAdapter(Context mContext, int layoutResourceId, ArrayList<StatChunk> listDataChild) {
        super(mContext,layoutResourceId,listDataChild);
        this._context = mContext;
        this.layoutResourceId = layoutResourceId;
        this._listDataChild = listDataChild;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final StatChunk childDetails = _listDataChild.get(position);
        int eid = childDetails.eid;

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

        // set exercise name
        TextView textView = (TextView) convertView.findViewById(R.id.plan_list_exercise_name);
        String s = GlobalVariables.searchENameByEid(eid);
        textView.setText(String.valueOf(s));
        // set id
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_setid);
        int i = childDetails.setid;
        textView.setText(String.valueOf(i));
        // complete
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_complete);
        s = (childDetails.complete) ? "Yes" : "No";
        textView.setText(String.valueOf(s));
        // quantity
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_quantity);
        i = childDetails.quantity;
        textView.setText(String.valueOf(i));
        // unit
        textView = (TextView) convertView.findViewById(R.id.plan_list_exset_unit);
        s = GlobalVariables.searchUnitByEid(eid);
        textView.setText(String.valueOf(s));
        // second unit, number of repeats
        if ( GlobalVariables.searchSecondUnitByEid(eid) ) {
            textView = (TextView) convertView.findViewById(R.id.plan_list_exset_reps);
            i = childDetails.reps;
            textView.setText(String.valueOf(i));
        }

        return convertView;
    }
}

