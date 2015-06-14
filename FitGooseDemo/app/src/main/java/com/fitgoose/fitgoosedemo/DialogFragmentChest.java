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
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by selway on 15-06-13.
 */
public class DialogFragmentChest extends DialogFragment {

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
