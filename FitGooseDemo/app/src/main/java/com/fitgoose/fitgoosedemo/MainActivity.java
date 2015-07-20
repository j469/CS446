package com.fitgoose.fitgoosedemo;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.plan_tab.BaseFragment;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FGDataSource mFGDataSource;

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

                        if (! file.exists()) {
                            ParseFile json_file =  object.getParseFile("json");
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
        BaseFragment contentFragment2=null;
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
                contentFragment = new CalendarFragment();
                break;
            case 5:
                contentFragment = new StatisticsFragment();
                break;
            case 6:
                contentFragment = new CameraFragment();
                break;
            case 7:
                contentFragment = new SettingsFragment();
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
//            getMenuInflater().inflate(R.menu.main, menu);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}