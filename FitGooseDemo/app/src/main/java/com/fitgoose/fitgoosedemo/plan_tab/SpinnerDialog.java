package com.fitgoose.fitgoosedemo.plan_tab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.R;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;

import java.util.ArrayList;

public class SpinnerDialog extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    private Spinner mSpinner;

    public interface DialogListener {
        public void ready(int position, int number_of_sets);
        public void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = GlobalVariables.getAllEName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_add_exercise);
        mSpinner = (Spinner) findViewById (R.id.daily_add_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);

        Button buttonOK = (Button) findViewById(R.id.daily_add_dialogOK);
        Button buttonCancel = (Button) findViewById(R.id.daily_add_dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                // sets
                EditText edit = (EditText)findViewById(R.id.daily_add_set_number);
                int sets;
                try {
                    sets = Integer.parseInt(edit.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(mContext, "Needs the number of sets.",Toast.LENGTH_SHORT).show();
                    return;
                }
                // exercise
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(n,sets);
                SpinnerDialog.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                SpinnerDialog.this.dismiss();
            }
        });
    }
}