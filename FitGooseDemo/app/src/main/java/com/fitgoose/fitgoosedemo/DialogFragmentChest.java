package com.fitgoose.fitgoosedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

/**
 * Created by selway on 15-06-13.
 */
public class DialogFragmentChest extends DialogFragment{

    public DialogFragmentChest() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Chest Area");
        // List for exercises
        View view = inflater.inflate(R.layout.dialog_body_chart, container);
        ListView chestList = (ListView) view.findViewById(R.id.chest_list);
        chestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch ((int) l) {
                    case 0:
                        getDialog().setContentView(R.layout.dialog_body_chart_next);

                        final EditText num_lbs = (EditText) getDialog().findViewById(R.id.num_lbs);
                        final EditText num_set = (EditText) getDialog().findViewById(R.id.num_set);

                        Button sub_lbs = (Button) getDialog().findViewById(R.id.sub_lbs);
                        sub_lbs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                                //num_lbs.setText("@/"current_lbs);
                                String tempNum = num_lbs.getText().toString();
                                int tempLbs = Integer.parseInt(tempNum) - 5;
                                tempNum = String.valueOf(tempLbs);
                                num_lbs.setText(tempNum);
                            }
                        });

                        Button add_lbs = (Button) getDialog().findViewById(R.id.add_lbs);
                        add_lbs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String tempNum = num_lbs.getText().toString();
                                int tempLbs = Integer.parseInt(tempNum) + 5;
                                tempNum = String.valueOf(tempLbs);
                                num_lbs.setText(tempNum);
                            }
                        });

                        Button sub_set = (Button) getDialog().findViewById(R.id.sub_set);
                        sub_set.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                                //num_lbs.setText("@/"current_lbs);
                                String tempNum = num_set.getText().toString();
                                int tempLbs = Integer.parseInt(tempNum) - 1;
                                tempNum = String.valueOf(tempLbs);
                                num_set.setText(tempNum);
                            }
                        });

                        Button add_set = (Button) getDialog().findViewById(R.id.add_set);
                        add_set.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getActivity(), "Sub lbs", Toast.LENGTH_SHORT).show();
                                //num_lbs.setText("@/"current_lbs);
                                String tempNum = num_set.getText().toString();
                                int tempLbs = Integer.parseInt(tempNum) + 1;
                                tempNum = String.valueOf(tempLbs);
                                num_set.setText(tempNum);
                            }
                        });

                        Button done_btn = (Button) getDialog().findViewById(R.id.done_btn);
                        done_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getDialog().dismiss();
                            }
                        });

                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Incline Dumbbell Press", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Push ups", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Low Cable Crossover", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "Body Weight Flyes", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });


        return view;
    }
}
