package com.fitgoose.fitgoosedemo;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.plan_tab.BaseFragment;
import com.fitgoose.fitgoosedemo.camera_utilities.CameraActivity;
import com.fitgoose.fitgoosedemo.utilities.CalendarDialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import android.net.Uri;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import android.annotation.SuppressLint;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import hirondelle.date4j.DateTime;
import com.fitgoose.fitgoosedemo.data.FGDataSource;

import static com.fitgoose.fitgoosedemo.data.FGDataSource.searchProgressByDate;


public class MainActivity extends CameraActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, BaseFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FGDataSource mFGDataSource;
    private CaldroidFragment caldroidFragment = null;
    boolean caldroidFragmentExist = false;
    public static File picture_folder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFGDataSource = FGDataSource.getInstance(this);
        mFGDataSource.cacheExercise();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("DefaultDB");
        query.getInBackground("UUulziCovR", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    try {
                        File file = new File(getApplicationContext().getFilesDir(), "/default.json");

                        if (!file.exists()) {
                            ParseFile json_file = object.getParseFile("json");
                            byte[] content = json_file.getData();
                            FileOutputStream outputStream = new FileOutputStream(file);
                            outputStream.write(content);
                            outputStream.close();

                            Toast.makeText(getApplicationContext(),
                                    "Download DB from server success.", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exception) {
                        //
                    }
                }
            }
        });

        setContentView(R.layout.activity_main);

        File sdCard = Environment.getExternalStorageDirectory();
        picture_folder = new File (sdCard.getAbsolutePath() + "/FitGoose");
        if (! picture_folder.exists()) picture_folder.mkdirs();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    /**
     * Part of the NavigationDrawerCallbacks interface so the NavigationDrawerFragment can make a
     * callback to change the content fragment that's being displayed
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment contentFragment = null;

        switch(position) {
            case 0:
                contentFragment = new TodaysExercisesFragment();
                break;
            case 1:
                contentFragment = new TodaysExercisesFragment();
                break;
            case 2:
                contentFragment = new WorkoutPlansFragment();
                break;
            case 3:
                contentFragment = new ExercisesFragment();
                break;
            case 4:
                caldroidFragment = new CaldroidFragment();
                break;
            case 5:
                contentFragment = new StatisticsFragment();
                break;
            case 6:
                contentFragment = new SimpleCameraIntentFragment();
                break;
            case 7:
                contentFragment = new SettingsFragment();
                break;
            case 8:
                contentFragment = new SimplePhotoGalleryListFragment();

        }



        if(caldroidFragment != null) {

            if (! caldroidFragmentExist) {
                caldroidFragmentExist = true;
                // put in argument
                Bundle args = new Bundle();
                Calendar cal = Calendar.getInstance();
                args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
                args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
                args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
                args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
                args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
                caldroidFragment.setArguments(args);
            }
                // set color based on store data;
                setColorForDates();

                //add listener
                final CaldroidListener listener = new CaldroidListener() {
                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                    //@TODO: add listener and call function
                    @Override
                    public void onSelectDate(Date date, View view) {
                        //Toast.makeText(getApplicationContext(), formatter.format(date), Toast.LENGTH_SHORT).show();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        MyDate myDate = new MyDate();
                        myDate.setFromCalendar(calendar);
                        CalendarDialog calendarDialog = new CalendarDialog(MainActivity.this, myDate);
                        calendarDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        calendarDialog.show();
                    }
                };
                caldroidFragment.setCaldroidListener(listener);
            //}

            // commit and transfer to fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, caldroidFragment)
                    .commit();
        }




        // update the main content by replacing fragments
        if(contentFragment != null) {
            Bundle args = new Bundle();
            contentFragment.setArguments(args);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, contentFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(int actionId) {

    }




    private void addlistener(){
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final CaldroidListener listener = new CaldroidListener(){

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private int getColorOfDate(Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ArrayList<Integer> sets = FGDataSource.searchProgressByDate(formatter.format(date));
        int total_sets = sets.get(0);
        int complete_sets = sets.get(1);

        if (total_sets == 0) {
            return R.color.no_percent_done;
        } else if (complete_sets >= (total_sets * 0.7) ) {
            return R.color.seventy_percent_done;
        } else if (complete_sets >= (total_sets * 0.3) ) {
            return R.color.thirty_percent_done;
        } else {
            return R.color.zero_percent_done;
        }
    }


    private void setColorForDates() {
        Calendar cal;

        for(int i=-30; i<=30; i++) { // just colour 2 months to test
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            Date curDate = cal.getTime();

            int colour = getColorOfDate(curDate);
            caldroidFragment.setBackgroundResourceForDate(colour, curDate);
        }
    }



}