package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.WorkoutPlansFragment;
import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.Daily;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.plan_tab.SpinnerDialog;

import org.w3c.dom.Text;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;


public class DailyNativeCard extends CardWithList{
    private String date;

    public DailyNativeCard(Context context, String date) {
        super(context, R.layout.daily_card_inner_main);
        this.date = date;
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(),R.layout.daily_card_inner_header);

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.daily, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_add:
                        SpinnerDialog mSpinnerDialog = new SpinnerDialog(getContext(), new SpinnerDialog.DialogListener() {
                            public void cancelled() {
                                // do your code here
                            }

                            public void ready(int position, int number_of_sets) {
                                int eid = GlobalVariables.storedExercises[position].getID();
                                Daily daily = new Daily(date, eid, number_of_sets);
                                FGDataSource.storeDaily(daily);
                                updateItems(daily);
                                //WorkoutPlansFragment.mCardArrayAdapter.notifyDataSetChanged();
                            }
                        });
                        mSpinnerDialog.setTitle(date);
                        mSpinnerDialog.show();
                        break;
                    case R.id.action_remove:
                        FGDataSource.deleteDaily(date, -1);
                        deleteAllDaily();
                        break;
                }

            }
        });
        header.setTitle(date); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(true);
        setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onSwipe(Card card) {
                FGDataSource.deleteDaily(date, -1);
            }
        });
        setUseEmptyView(true);
    }


    @Override
    protected List<ListObject> initChildren() {
        //Search Daily arraylist by date
        ArrayList<Daily> dailies = FGDataSource.searchDailyByDate(date);
        //Init the object list
        List<ListObject> mObjects = new ArrayList<ListObject>();
        //Transfer Daily to DailyObject
        int complete;
        for (final Daily d: dailies) {
            // set values
            DailyObject dailyObject = new DailyObject(this);
            complete = 0;
            dailyObject.exercise = GlobalVariables.searchENameByEid(d.eID);
            dailyObject.total = d.numOfSets;
            for (ExSet e: d.exSets) {
                if (e.complete) complete++;
            }
            dailyObject.complete = complete;
            dailyObject.setObjectId(dailyObject.exercise);
            dailyObject.setSwipeable(true);

            // set swipeable
            dailyObject.setSwipeable(true);
            dailyObject.setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    FGDataSource.deleteDaily(date,d.eID);
                }
            });
            mObjects.add(dailyObject);
        }
        return mObjects;
    }

    public void updateItems(final Daily d) {
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
                FGDataSource.deleteDaily(date, d.eID);
            }
        });
        getLinearListAdapter().add(dailyObject);
    }

    public void deleteAllDaily() {
        getLinearListAdapter().clear();
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Find the views
        TextView exercise  = (TextView) convertView.findViewById(R.id.daily_card_exercise_name);
        TextView complete  = (TextView) convertView.findViewById(R.id.daily_card_exercise_sets_complete);
        TextView total  = (TextView) convertView.findViewById(R.id.daily_card_exercise_sets_total);

        //Retrieve the values from the object
        DailyObject dailyObject = (DailyObject) object;
        exercise.setText( String.valueOf(dailyObject.exercise));
        complete.setText( String.valueOf(dailyObject.complete));
        total.setText( String.valueOf(dailyObject.total));

        return  convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.daily_card_inner_main;
    }

    // -------------------------------------------------------------
    // Daily Object
    // -------------------------------------------------------------

    public class DailyObject extends DefaultListObject{

        public String exercise;
        public int complete;
        public int total;

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
