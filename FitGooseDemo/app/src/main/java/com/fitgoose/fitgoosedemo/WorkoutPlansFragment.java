package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.plan_tab.DailyNativeCard;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class WorkoutPlansFragment extends Fragment{

    private Context context;

    public WorkoutPlansFragment() {
    }


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

        View rootView = inflater.inflate(R.layout.fragment_plans_workout, container, false);
        //prepare data
        ArrayList<String> dates = FGDataSource.searchAllDates();
        ArrayList<Card> cards = new ArrayList<>();

        // fake
        dates.add( String.valueOf("2016-01-03"));
        dates.add( String.valueOf("2016-01-04"));


        for (String date: dates) {
            DailyNativeCard card = new DailyNativeCard(context,date);
            card.init();
            cards.add(card);
        }
        // set adapter
        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(context, cards);

        // Set the CardRecyclerView view
        CardRecyclerView mRecyclerView = (CardRecyclerView) rootView.findViewById(R.id.plan_recyclerview);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        return rootView;
    }

}
