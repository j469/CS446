package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Regimen;
import com.fitgoose.fitgoosedemo.plan_tab.ExerciseDetailsCard;
import com.fitgoose.fitgoosedemo.plan_tab.RegimenDetailCard;
import com.fitgoose.fitgoosedemo.utilities.CustomExerciseDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by Jiayi Wang on 2015-07-16.
 */
public class ExercisesFragment extends Fragment {

    private Context context;
    protected Spinner mSpinner;
    protected CardRecyclerView mRecyclerView;

    public ExercisesFragment() {}


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercises, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        // spinner view
        mSpinner = (Spinner) getActivity().findViewById(R.id.exercise_spinner);
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
        mRecyclerView = (CardRecyclerView) getActivity().findViewById(R.id.exercise_recyclerview);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        updateCardList(0);
    }

    public void updateCardList (int type) {
        //prepare data
        ArrayList<Card> cards = new ArrayList<>();

        if (type == 10) { // regimen
            ArrayList<Regimen> regimens = FGDataSource.searchAllRegimen();

            for (Regimen regimen: regimens) {
                RegimenDetailCard card = new RegimenDetailCard(context,regimen);
                card.init();
                cards.add(card);
            }

        } else { // exercise

            ArrayList<Exercise> exercises = GlobalVariables.getExercisesByType(type);

            for (Exercise e : exercises) {
                // card constructor
                ExerciseDetailsCard card = new ExerciseDetailsCard(context, e);

                // card header
                CardHeader header = new CardHeader(context, R.layout.daily_card_inner_header);
                // popup menu. This method set OverFlow button to visible
                header.setPopupMenu(R.menu.exercise_card_header_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                    @Override
                    public void onMenuItemClick(BaseCard card, MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.exercise_card_header_action_add: {
                                CustomExerciseDialog customExerciseDialog = new CustomExerciseDialog(context, new CustomExerciseDialog.CustomExerciseDialogListener() {
                                    public void ready() {
                                        updateCardList(0);
                                    }
                                });
                                customExerciseDialog.setTitle("New Exercise:");
                                customExerciseDialog.show();
                                break;
                            }
                            case R.id.exercise_card_header_action_remove: {
                                Toast.makeText(context, "TODO:deleteExercise()", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case R.id.exercise_card_header_action_edit: {
                                Toast.makeText(context, "TODO:edit exercise", Toast.LENGTH_SHORT).show();
                                break;
                            }
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
                        Toast.makeText(context, "TODO:Display youtube video after click.", Toast.LENGTH_LONG).show();
                    }
                });
                card.setOnSwipeListener(new Card.OnSwipeListener() {
                    @Override
                    public void onSwipe(Card card) {
                        Toast.makeText(context, "TODO:deleteExercise()", Toast.LENGTH_SHORT).show();
                        //FGDataSource.deleteExercise(date, -1);
                    }
                });

                // add card to the list
                cards.add(card);
            }
        }

        // set adapter
        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        mRecyclerView.setAdapter(mCardArrayAdapter);
    }
}

