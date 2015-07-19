package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.data.Regimen;
import com.fitgoose.fitgoosedemo.utilities.CalendarDialog;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Jiayi Wang on 2015-07-18.
 */
public class RegimenDetailCard extends CardWithList {
    private Regimen mRegimen;

    public RegimenDetailCard(Context context, Regimen regimen) {
        super(context, R.layout.daily_card_inner_main);
        this.mRegimen = regimen;
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(),R.layout.daily_card_inner_header);

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.daily_card_header_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_add:
                        /*DailyAddPlanDialog dailyAddPlanDialog = new DailyAddPlanDialog(getContext(), new DailyAddPlanDialog.DialogListener() {
                            public void cancelled() {
                                // do your code here
                            }

                            public void ready(int eid, int number_of_sets) {
                                Plan plan = new Plan(date, eid, number_of_sets);
                                FGDataSource.storePlan(plan);
                                updateItems(plan);
                                //WorkoutPlansFragment.mCardArrayAdapter.notifyDataSetChanged();
                            }
                        });
                        dailyAddPlanDialog.setTitle(strDate);
                        dailyAddPlanDialog.show();*/
                        break;
                    case R.id.action_remove:
                        //FGDataSource.deletePlan(date, -1);
                        //deleteAllDaily();
                        break;
                    case R.id.action_save:
                        //CalendarDialog calendarDialog = new CalendarDialog(getContext(),date);
                        //calendarDialog.show();
                        break;
                }

            }
        });
        header.setTitle(mRegimen.rname);
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                //FGDataSource.deletePlan(date, -1);
            }
        });
        setUseEmptyView(true);
    }


    @Override
    protected List<ListObject> initChildren() {

        List<ListObject> mObjects = new ArrayList<ListObject>();

        for (int i = 0; i< mRegimen.eIDs.size(); i++) {
            DailyObject dailyObject = new DailyObject(this);

            dailyObject.eID = mRegimen.eIDs.get(i);
            dailyObject.sets = mRegimen.sets.get(i);
            dailyObject.ename = GlobalVariables.searchENameByEid(dailyObject.eID);
            dailyObject.unitOne = GlobalVariables.searchUnitByEid(dailyObject.eID);
            dailyObject.unitTwo = GlobalVariables.searchSecondUnitByEid(dailyObject.eID);

            dailyObject.setObjectId(dailyObject.ename);
            dailyObject.setSwipeable(true);

            dailyObject.setSwipeable(true);
            dailyObject.setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    //FGDataSource.deletePlan(date, d.eID);
                }
            });
            mObjects.add(dailyObject);
        }
        return mObjects;
    }

    /*public void updateItems(final Plan d) {
        // set values
        DailyObject dailyObject = new DailyObject(this);
        dailyObject.exercise = GlobalVariables.searchENameByEid(d.eID);
        dailyObject.total = d.numOfSets;
        dailyObject.complete = 0;
        dailyObject.setObjectId(dailyObject.exercise);
        dailyObject.setSwipeable(true);
        // set swipeable
        dailyObject.setSwipeable(true);
        dailyObject.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipe(ListObject object, boolean dismissRight) {
                FGDataSource.deletePlan(date, d.eID);
            }
        });
        getLinearListAdapter().add(dailyObject);
    }*/

    public void deleteAllDaily() {
        getLinearListAdapter().clear();
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Find the views
        TextView ename  = (TextView) convertView.findViewById(R.id.regimen_card_exercise_name);
        TextView sets  = (TextView) convertView.findViewById(R.id.regimen_card_exercise_sets);
        TextView unitOne  = (TextView) convertView.findViewById(R.id.regimen_card_unit_one);
        TextView unitTwo  = (TextView) convertView.findViewById(R.id.regimen_card_unit_two);

        //Retrieve the values from the object
        DailyObject dailyObject = (DailyObject) object;

        ename.setText( dailyObject.ename);
        sets.setText( String.valueOf(dailyObject.sets) + " ");

        if (dailyObject.unitOne != -1) unitOne.setText(GlobalVariables.exerciseUnit.get(dailyObject.unitOne));

        if (dailyObject.unitTwo != -1) unitTwo.setText(GlobalVariables.exerciseUnit.get(dailyObject.unitTwo));

        return  convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.regimen_card_inner_main;
    }

    // -------------------------------------------------------------
    // Plan Object
    // -------------------------------------------------------------

    public class DailyObject extends DefaultListObject{

        public String ename;
        public int eID;
        public int sets;
        public int unitOne;
        public int unitTwo;

        public DailyObject(Card parentCard){
            super(parentCard);
            init();
        }

        private void init(){
            //OnClick Listener
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });

            //OnItemSwipeListener
            setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
