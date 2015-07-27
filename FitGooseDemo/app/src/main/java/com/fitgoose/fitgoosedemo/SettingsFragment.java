package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileOutputStream;

    public class SettingsFragment extends Fragment {

    private Activity activity;
    private ListView settingsList;

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

        settingsList = (ListView) rootView.findViewById(R.id.settings_list);
        String[] settingsOptions= new String[]{
                "Google Drive Backup",
                "Update default database"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, android.R.id.text1, settingsOptions);
        settingsList.setAdapter(adapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent driveIntent = new Intent(activity, GoogleDriveActivity.class);
                        activity.startActivity(driveIntent);
                        break;
                    case 1:
                        ParseObject object = ParseObject.createWithoutData("DefaultDB", "UUulziCovR");
                        object.fetchInBackground(new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    try {
                                        File file = new File(activity.getFilesDir(), "/default.json");

                                        if (file.exists()) file.delete();

                                        ParseFile json_file = object.getParseFile("json");
                                        byte[] content = json_file.getData();
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        outputStream.write(content);
                                        outputStream.close();

                                        Toast.makeText(activity, "Update DB from server success.", Toast.LENGTH_LONG).show();

                                    } catch (Exception exception) {
                                        //
                                    }
                                } else {
                                    Toast.makeText(activity, "Update DB from server failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        FGDataSource.cacheExercise();
                        break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}