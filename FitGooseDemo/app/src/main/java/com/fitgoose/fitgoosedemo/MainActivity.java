package com.fitgoose.fitgoosedemo;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.fitgoose.fitgoosedemo.data.Daily;
import com.fitgoose.fitgoosedemo.data.ExSet;
import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.Plan;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    private FGDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first get a FGDataSource instance
        datasource = FGDataSource.getInstance(this);

/*    {//TODO: this is a fake database to start with

        datasource.deleteAll();

        // Today
        // two sets of running, one set of bench press
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(c.getTime());
        Daily daily = new Daily(0,curDate,0,2);
        datasource.storeDaily(daily);
        ExSet exSet = new ExSet(0,0,1000,0,0);
        datasource.storeExSet(exSet);
        exSet = new ExSet(0,0,500,0,0);
        datasource.storeExSet(exSet);
        daily = new Daily(0,curDate,1,1);
        datasource.storeDaily(daily);
        exSet = new ExSet(0,1,50,5,1);
        datasource.storeExSet(exSet);

        // tomorrow
        // two sets of bench press, one set of running
        c.add(Calendar.DATE, 1);
        curDate = sdf.format(c.getTime());
        daily = new Daily(0,curDate,1,1);
        datasource.storeDaily(daily);
        exSet = new ExSet(0,2,100,10,1);
        datasource.storeExSet(exSet);
        daily = new Daily(0,curDate,0,2);
        datasource.storeDaily(daily);
        exSet = new ExSet(0,3,7000,10,0);
        datasource.storeExSet(exSet);
        exSet = new ExSet(0,3,500,1,1);
        datasource.storeExSet(exSet);
    }// fake database
*/
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // By default, selects first item in the menu
//        mNavigationDrawerFragment.selectItem(0);
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
                contentFragment = new WorkoutPlansFragment();
                break;
            case 2:
                contentFragment = new CalendarFragment();
                break;
            case 3:
                contentFragment = new CameraFragment();
                break;
            case 4:
                contentFragment = new StatisticsFragment();
                break;
            case 5:
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
            getMenuInflater().inflate(R.menu.main, menu);

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
