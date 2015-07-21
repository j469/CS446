package com.fitgoose.fitgoosedemo.plan_tab;

import android.content.Context;
import android.telephony.gsm.GsmCellLocation;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.fitgoose.fitgoosedemo.MyDate;
import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.Exercise;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.Plan;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Jiayi Wang on 2015-07-16.
 */
public class ExerciseDetailsCard extends Card{

    public ExerciseDetailsCard(Context context) {
        super(context, R.layout.empty_layout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
    }

}

