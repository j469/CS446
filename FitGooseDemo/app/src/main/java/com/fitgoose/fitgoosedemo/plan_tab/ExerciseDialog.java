package com.fitgoose.fitgoosedemo.plan_tab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by Jiayi Wang on 2015-07-17.
 */
public class ExerciseDialog extends Dialog {
    private Context context;
    protected Spinner mSpinner;
    protected CardRecyclerView mRecyclerView;

    public interface DialogListener {
        public void ready(int position, int number_of_sets);
        public void cancelled();
    }
    private DialogListener mReadyListener;

    public ExerciseDialog(Context context, DialogListener readyListener) {
        super(context);
        this.mReadyListener = readyListener;
        this.context = context;
    }

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercises);

        // spinner view
        mSpinner = (Spinner) findViewById(R.id.exercise_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (context, android.R.layout.simple_spinner_dropdown_item, GlobalVariables.bodyPartName);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateCardList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // CardRecyclerView view
        mRecyclerView = (CardRecyclerView) findViewById(R.id.exercise_recyclerview);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        updateCardList(0);
    }

    private void updateCardList (int type) {
        //prepare data
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Exercise> exercises = GlobalVariables.getExercisesByType(type);

        for (Exercise e: exercises) {
            // card constructor
            ExerciseDetailsCard card = new ExerciseDetailsCard(context,e);

            // card header
            CardHeader header = new CardHeader(context,R.layout.daily_card_inner_header);
            // popup menu. This method set OverFlow button to visible
            header.setPopupMenu(R.menu.exercise_card_header_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.exercise_card_header_action_save:
                            break;
                        case R.id.exercise_card_header_action_edit:
                            break;
                        case R.id.exercise_card_header_action_remove:
                            break;
                    }

                }
            });
            header.setTitle(e.name); //should use R.string
            card.addCardHeader(header);

            card.setClickable(true);
            card.setSwipeable(true);
            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Toast.makeText(context, "Display youtube video after click.", Toast.LENGTH_LONG).show();
                }
            });
            card.setOnSwipeListener(new Card.OnSwipeListener() {
                @Override
                public void onSwipe(Card card) {
                    Toast.makeText(context, "On swipe.", Toast.LENGTH_SHORT).show();
                    //FGDataSource.deleteExercise(date, -1);
                }
            });

            // add card to the list
            cards.add(card);
        }

        // set adapter
        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        mRecyclerView.setAdapter(mCardArrayAdapter);
    }
}
