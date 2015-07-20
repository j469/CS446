package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.utilities.CustomExerciseDialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;

public class SettingsFragment extends Fragment {

    private Context context;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button add_exercise_button = (Button) rootView.findViewById(R.id.setting_add_exercise_button);
        add_exercise_button.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                CustomExerciseDialog dialog = new CustomExerciseDialog(context);
                dialog.setTitle("New Exercise:");
                dialog.show();
            }
        });

        Button update_databse_button = (Button) rootView.findViewById(R.id.setting_update_databse_button);
        update_databse_button.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                ParseObject object = ParseObject.createWithoutData("DefaultDB", "UUulziCovR");
                object.fetchInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            try {
                                File file = new File(context.getFilesDir(), "/default.json");

                                if (file.exists())  file.delete();

                                ParseFile json_file =  object.getParseFile("json");
                                byte[] content = json_file.getData();
                                FileOutputStream outputStream = new FileOutputStream(file);
                                outputStream.write(content);
                                outputStream.close();

                                Toast.makeText(context, "Update DB from server success.", Toast.LENGTH_LONG).show();

                            } catch (Exception exception) {
                                //
                            }
                        } else {
                            Toast.makeText(context, "Update DB from server failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                FGDataSource.cacheExercise();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}