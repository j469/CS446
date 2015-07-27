package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Regimen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by Jiayi Wang on 2015-07-24.
 */
public class RegimenDetailsCardExpand extends CardExpand {
    private Regimen regimen;
    private Context context;

    /*public interface CardExpandListener {
        public void remove(int rid);
        public void edit(Regimen r);
    }
    private CardExpandListener cardExpandListener;*/

    public RegimenDetailsCardExpand(Context context, Regimen regimen) {
        super(context, R.layout.regimen_card_inner);
        this.context = context;
        this.regimen = regimen;
        //this.cardExpandListener = cardExpandListener;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        parent.setBackgroundColor(mContext.getResources().getColor(R.color.card_backgroundExpand));

        ListView list = (ListView) parent.findViewById(R.id.regimen_card_listview);

        String[] strings = {"name","sets"};

        int[] ids = {R.id.regimen_card_exercise_name, R.id.regimen_card_exercise_sets};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, getData(), R.layout.regimen_card_inner_main, strings, ids);
        list.setAdapter(simpleAdapter);
    }

    private List<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = null;

        for (int i = 0; i < regimen.eIDs.size(); i++) {
            int eid = regimen.eIDs.get(i);
            String ename = GlobalVariables.searchENameByEid(eid);

            map = new HashMap<String, Object>();

            map.put("name", ename);
            map.put("sets", regimen.sets.get(i));

            list.add(map);
        }
        return list;
    }
}
