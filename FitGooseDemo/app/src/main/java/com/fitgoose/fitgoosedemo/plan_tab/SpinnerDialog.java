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
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
import com.fitgoose.fitgoosedemo.data.Plan;

import java.util.ArrayList;

public class SpinnerDialog extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    private int localEID = -1;

    public interface DialogListener {
        public void ready(int eid, int number_of_sets);
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
        Button button = (Button) findViewById (R.id.daily_add_button);
        button.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                ExerciseDialog exerciseDialog = new ExerciseDialog(getContext(), new ExerciseDialog.ExerciseDialogListener() {
                    public void ready(int eid) {
                        localEID = eid;
                    }
                });
                exerciseDialog.setTitle("Choose an exercise");
                exerciseDialog.show();
            }
        });

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
                // eid
                if (localEID == -1) {
                    Toast.makeText(mContext, "You need to choose an exercise.",Toast.LENGTH_LONG).show();
                    return;
                }
                mReadyListener.ready(localEID,sets);
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