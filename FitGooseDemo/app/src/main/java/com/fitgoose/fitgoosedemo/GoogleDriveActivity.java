package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YuFan on 7/26/15.
 */
public class GoogleDriveActivity extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final int REQUEST_CODE_RESOLUTION = 1;
    private static final String TAG = "FitGoose_Google_Drive";

    private GoogleApiClient mGoogleApiClient;
    private Activity mActivity;
    private DriveId driveFileId; // Used to pass the id to the Alert Dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_google_drive);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addScope(Drive.SCOPE_APPFOLDER)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            Log.d(TAG, "API client disconnected.");
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.google_drive, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Called up the Upload Button
    public void upload(View view) {
        Query query = new Query.Builder().addFilter(Filters.and(
                Filters.eq(SearchableField.MIME_TYPE, "application/octet-stream"),
                Filters.contains(SearchableField.TITLE, "FitGoose.db")
        )).build();
        DriveFolder folder = Drive.DriveApi.getAppFolder(mGoogleApiClient);
        if(folder == null) {
            createNewFile();
            return;
        }
        folder.queryChildren(mGoogleApiClient, query)
                .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "Problem while retrieving results", Toast.LENGTH_LONG).show();
                            // No existing database file, create a new one
                            createNewFile();
                        } else {
                            // Write over existing file
                            if (result.getMetadataBuffer().getCount() > 0) {
                                DriveId id = result.getMetadataBuffer().get(0).getDriveId();
                                writeOverFile(id);
                            } else {
                                // No existing database file, create a new one
                                createNewFile();
                            }
                        }
                        result.getMetadataBuffer().release();
                    }
                });
    }

    // Create a new file on Google Drive to store the backup
    private void createNewFile() {
        // Start by creating a new contents, and setting a callback.
        Drive.DriveApi.newDriveContents(mGoogleApiClient)
                .setResultCallback(new ResultCallback<DriveContentsResult>() {
                    @Override
                    public void onResult(DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "Failed to create new contents.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        final DriveContents driveContents = result.getDriveContents();

                        // Create the initial metadata - MIME type and title.
                        // Note that the user will be able to change the title later.
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("application/octet-stream").setTitle("FitGoose.db").build();

                        Drive.DriveApi.getAppFolder(mGoogleApiClient)
                                .createFile(mGoogleApiClient, metadataChangeSet, driveContents)
                                .setResultCallback(new ResultCallback<DriveFolder.DriveFileResult>() {
                                    @Override
                                    public void onResult(DriveFolder.DriveFileResult driveFileResult) {
                                        if(!driveFileResult.getStatus().isSuccess()) {
                                            Toast.makeText(mActivity,
                                                    "Failed to create new file",
                                                    Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        writeOverFile(driveFileResult.getDriveFile().getDriveId());
                                    }
                                });
                    }
                });
    }

    // Copy the local database file into the one on Google Drive
    private void writeOverFile(DriveId driveId) {
        DriveFile driveFile = Drive.DriveApi.getFile(mGoogleApiClient, driveId);

        driveFile.open(mGoogleApiClient, DriveFile.MODE_WRITE_ONLY, null)
                .setResultCallback(new ResultCallback<DriveContentsResult>() {
                    @Override
                    public void onResult(DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "Failed to connect to file writer", Toast.LENGTH_LONG).show();
                            return;
                        }

                        DriveContents driveContents = result.getDriveContents();

                        // Fetch database file
                        File dbFile = getDatabasePath("FitGoose.db");
                        FileInputStream fileInputStream = null;
                        try {
                            fileInputStream = new FileInputStream(dbFile);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(mActivity, dbFile.getAbsolutePath() + " not found.", Toast.LENGTH_LONG).show();
                        }

                        // convert database file to byte[]
                        byte[] dbBytes = null;
                        try {
                            dbBytes = IOUtils.toByteArray(fileInputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            Toast.makeText(mActivity, dbFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Write byte[] to outputStream
                        OutputStream outputStream = result.getDriveContents().getOutputStream();
                        try {
                            outputStream.write(dbBytes);
                        } catch (IOException e1) {
                            Toast.makeText(mActivity, "Unable to write file contents.", Toast.LENGTH_LONG).show();
                        }

                        // Commit file to Google Drive
                        driveContents.commit(mGoogleApiClient, null).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                if (status.isSuccess()) {
                                    Toast.makeText(mActivity, "Backup Success!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(mActivity, "Unable to commit changes to Google Drive", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
    }

    // Called by the Download Button
    public void download(View view) {
        Query query = new Query.Builder().addFilter(Filters.and(
                Filters.eq(SearchableField.MIME_TYPE, "application/octet-stream"),
                Filters.contains(SearchableField.TITLE, "FitGoose.db")
        )).build();
        Drive.DriveApi.getAppFolder(mGoogleApiClient).queryChildren(mGoogleApiClient, query)
                .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "No Backup Available", Toast.LENGTH_LONG).show();
                        }
                        else {
                            // Retrieve the backup file
                            if(result.getMetadataBuffer().getCount() > 0) {
                                driveFileId = result.getMetadataBuffer().get(0).getDriveId();
                                Date modDate = result.getMetadataBuffer().get(0).getModifiedDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedString = sdf.format(modDate);

                                // Double check with user whether or not to replace local db
                                // with the backup on Drive
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("Continue?")
                                        .setMessage("The backup was made on "
                                                + formattedString + "\n"
                                                + "Replace all local data with backup?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // User clicks yes, replace local db with backup
                                                readFileIntoDB(driveFileId);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            else {
                                Toast.makeText(mActivity, "No Backup Available", Toast.LENGTH_LONG).show();
                            }
                        }
                        result.getMetadataBuffer().release();
                    }
                });
    }

    // Replace the local database file with the one on Google Drive
    private void readFileIntoDB(DriveId driveId) {
        DriveFile driveFile = Drive.DriveApi.getFile(mGoogleApiClient, driveId);

        driveFile.open(mGoogleApiClient, DriveFile.MODE_READ_ONLY, null)
                .setResultCallback(new ResultCallback<DriveContentsResult>() {
                    @Override
                    public void onResult(DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "Failed to connect to file reader", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            // Get the input stream for the Drive file
                            DriveContents driveContents = result.getDriveContents();
                            InputStream inputStream = driveContents.getInputStream();

                            // Get the file for the local db file
                            File dbFile = getDatabasePath("FitGoose.db");

                            // Replace the file with the input stream
                            FileUtils.copyInputStreamToFile(inputStream, dbFile);

                            // Refresh the Exercise cache
                            FGDataSource.cacheExercise();

                            Toast.makeText(mActivity, "Backup applied successfully!", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RESOLUTION && resultCode == RESULT_OK) {
            mGoogleApiClient.connect();
        }
    }


    // From ConnectionCallbacks

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "API client connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "GoogleApiClient connection suspended");
    }

    // From OnConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "GoogleApiClient connection failed: " + connectionResult.toString());

        if (!connectionResult.hasResolution()) {
            // show the localized error dialog.
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        // The failure has a resolution. Resolve it.
        // Called typically when the app is not yet authorized, and an
        // authorization
        // dialog is displayed to the user.
        try {
            connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
            mGoogleApiClient.connect();
        } catch (IntentSender.SendIntentException e) {
            Toast.makeText(this, "Exception while starting resolution activity", Toast.LENGTH_LONG).show();
        }
    }
}
