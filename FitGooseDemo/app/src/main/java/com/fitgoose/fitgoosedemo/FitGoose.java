package com.fitgoose.fitgoosedemo;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class FitGoose extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "J0iocNjTLKuwoohJgOdI1mKQc4ivzENjZnNbIajV", "I6xbRpfjzXcyDzCsAXXDicgpaQWRVZcmfTGBcKg8");
        // Parse push service
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}