package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.fitgoose.fitgoosedemo.plan_tab.ExerciseDetailsCardExpand;
import com.fitgoose.fitgoosedemo.plan_tab.RegimenDetailsCardExpand;
import com.fitgoose.fitgoosedemo.utilities.CustomExerciseDialog;
import com.fitgoose.fitgoosedemo.utilities.YouTubeDialog;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_icon_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_menu_option) {
            CustomExerciseDialog customExerciseDialog = new CustomExerciseDialog(context,
                    new CustomExerciseDialog.CustomExerciseDialogListener() {
                        public void ready() {
                            updateCardList(0);
                        }
                    });
            customExerciseDialog.setTitle("New Exercise:");
            customExerciseDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateCardList (int type) {
        //prepare data
        ArrayList<Card> cards = new ArrayList<>();

        if (type == 10) { // regimen
            ArrayList<Regimen> regimens = FGDataSource.searchAllRegimen();

            for (Regimen regimen: regimens) {
                //card
                ExerciseDetailsCard card = new ExerciseDetailsCard(context);

                //card expand
                RegimenDetailsCardExpand expand = new RegimenDetailsCardExpand(context,regimen);
                expand.setTitle("Details:");
                card.addCardExpand(expand);

                // card header
                CardHeader header = new CardHeader(context);
                header.setButtonExpandVisible(true);
                header.setTitle(regimen.rname);
                card.addCardHeader(header);

                card.setClickable(false);
                card.setSwipeable(false);

                // add card to the list
                cards.add(card);
            }

            // set adapter
            CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

            mRecyclerView.setAdapter(mCardArrayAdapter);

        } else { // exercise

            final ArrayList<Exercise> exercises = GlobalVariables.getExercisesByType(type);

            for (final Exercise e : exercises) {
                // card constructor
                ExerciseDetailsCard card = new ExerciseDetailsCard(context);

                // card expand
                ExerciseDetailsCardExpand expand = new ExerciseDetailsCardExpand(context,e, new ExerciseDetailsCardExpand.CardExpandListener(){

                    public void remove(int rtnEID) {
                        if (FGDataSource.deleteExercise(rtnEID) == 1) {
                            Toast.makeText(context, "Delete exercise done.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Cannot delete default exercise.", Toast.LENGTH_SHORT).show();
                        }
                        updateCardList(0);
                    }

                    public void show_youtube(String rtnEName) {
                        String videoID = e.youtubeURL;
                        // Testing Script:
//                        String videoID = null;
//                        int choice = e.getID() % 3;
//                        switch(choice) {
//                            case 0:
//                                videoID = "MDuXuUg15mk";
//                                break;
//                            case 1:
//                                videoID = "GkLqraq4m6U";
//                                break;
//                            case 2:
//                                videoID = "eh7lp9umG2I";
//                        }

                        if(videoID != null && !videoID.equalsIgnoreCase("")) {
                            YouTubeDialog videoDialog = YouTubeDialog.newInstance(rtnEName, videoID);
                            videoDialog.show(getActivity().getFragmentManager(), rtnEName);
                        }
                        else {
                            Toast.makeText(context, "No video for this exercise", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void edit(Exercise rtnExercise) {
                        if (! rtnExercise.type) {
                            Toast.makeText(context, "Cannot edit default exercise.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ContentValues values = new ContentValues();
                        values.put("secondUnit", (rtnExercise.secondUnit ? 1: 0) );
                        values.put("unit1", rtnExercise.unit1 );
                        values.put("unit2", rtnExercise.unit2 );
                        values.put("shoulder", (rtnExercise.shoulder ? 1: 0) );
                        values.put("chest", (rtnExercise.chest ? 1: 0) );
                        values.put("abs", (rtnExercise.abs ? 1: 0) );
                        values.put("upper_arm", (rtnExercise.upper_arm ? 1: 0) );
                        values.put("forearm", (rtnExercise.forearm ? 1: 0) );
                        values.put("quads", (rtnExercise.quads ? 1: 0) );
                        values.put("calves", (rtnExercise.calves ? 1: 0) );
                        values.put("back", (rtnExercise.back ? 1: 0) );
                        values.put("cardio", (rtnExercise.cardio ? 1: 0) );
                        FGDataSource.dbUpdate("exercise", values, "name", new String[]{rtnExercise.name});

                        GlobalVariables.updateExercise(rtnExercise);

                        Toast.makeText(context, "Changes on "+rtnExercise.name+" are done.", Toast.LENGTH_LONG).show();
                        updateCardList(0);
                    }
                });
                expand.setTitle("Details:");
                card.addCardExpand(expand);

                // card header
                CardHeader header = new CardHeader(context);
                header.setButtonExpandVisible(true);
                header.setTitle(e.name);
                card.addCardHeader(header);

                card.setClickable(false);
                card.setSwipeable(false);

                // add card to the list
                cards.add(card);
            }
        }

        // set adapter
        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        mRecyclerView.setAdapter(mCardArrayAdapter);
    }
}

