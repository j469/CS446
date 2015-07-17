package com.fitgoose.fitgoosedemo.utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fitgoose.fitgoosedemo.MyDate;
import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.plan_tab.DailyNativeCard;

import java.util.Calendar;

import it.gmariotti.cardslib.library.view.CardViewNative;


public class CalendarDialog extends Dialog {
    private DailyNativeCard mCard;

    public CalendarDialog(Context context, MyDate date) {
        super(context);
        mCard = new DailyNativeCard(context,date);
        mCard.init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_daily_card);

        //card
        CardViewNative cardView = (CardViewNative) findViewById(R.id.calendar_daily_card);
        cardView.setCard(mCard);

        //button
        Button buttonReturn = (Button) findViewById(R.id.calendar_daily_card_return);
        buttonReturn.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                CalendarDialog.this.dismiss();
            }
        });
    }
}

