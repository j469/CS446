package com.fitgoose.fitgoosedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.data.FGDataSource;
import com.fitgoose.fitgoosedemo.data.GlobalVariables;
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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;

/**
 * Created by YuFan on 7/26/15.
 */
public class GoogleDriveActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;

    private GoogleApiClient mGoogleApiClient;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_google_drive);

        Button uploadButton = (Button) findViewById(R.id.upload_button);

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
            Toast.makeText(this, "API client disconnected.", Toast.LENGTH_LONG).show();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.google_drive, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void upload(View view) {
        Query query = new Query.Builder().addFilter(Filters.and(
                Filters.eq(SearchableField.MIME_TYPE, "application/octet-stream"),
                Filters.contains(SearchableField.TITLE, "FitGoose.db")
        )).build();
        Drive.DriveApi.query(mGoogleApiClient, query)
                .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "Problem while retrieving results", Toast.LENGTH_LONG).show();
                            // No existing database file, create a new one
                            createNewFile();
                        }
                        else {
                            // Write over existing file
                            if(result.getMetadataBuffer().getCount() > 0) {
                                DriveId id = result.getMetadataBuffer().get(0).getDriveId();
                                writeOverFile(id);
                            }
                            else {
                                // No existing database file, create a new one
                                createNewFile();
                            }
                        }
                        result.getMetadataBuffer().release();
                    }
                });
    }

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
                        OutputStream outputStream = driveContents.getOutputStream();
                        try {
                            outputStream.write(dbBytes);
                        } catch (IOException e1) {
                            Toast.makeText(mActivity, "Unable to write file contents.", Toast.LENGTH_LONG).show();
                        }

                        // Create the initial metadata - MIME type and title.
                        // Note that the user will be able to change the title later.
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("application/octet-stream").setTitle("FitGoose.db").build();

                        // Create an intent for the file chooser, and start it.
                        IntentSender intentSender = Drive.DriveApi
                                .newCreateFileActivityBuilder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(driveContents)
                                .build(mGoogleApiClient);
                        try {
                            startIntentSenderForResult(
                                    intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Toast.makeText(mActivity, "Failed to launch file chooser.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

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

    public void download(View view) {
        Query query = new Query.Builder().addFilter(Filters.and(
                Filters.eq(SearchableField.MIME_TYPE, "application/octet-stream"),
                Filters.contains(SearchableField.TITLE, "FitGoose.db")
        )).build();
        Drive.DriveApi.query(mGoogleApiClient, query)
                .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
                    @Override
                    public void onResult(DriveApi.MetadataBufferResult result) {
                        if (!result.getStatus().isSuccess()) {
                            Toast.makeText(mActivity, "No Backup Available", Toast.LENGTH_LONG).show();
                        }
                        else {
                            // Retrieve the backup file
                            if(result.getMetadataBuffer().getCount() > 0) {
                                DriveId id = result.getMetadataBuffer().get(0).getDriveId();
                                readFileIntoDB(id);
                            }
                            else {
                                Toast.makeText(mActivity, "No Backup Available", Toast.LENGTH_LONG).show();
                            }
                        }
                        result.getMetadataBuffer().release();
                    }
                });
    }

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
                            // Get the file reader for the Drive file
                            DriveContents driveContents = result.getDriveContents();
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(driveContents.getInputStream()));

                            // Get the file writer for the local db file
                            File dbFile = getDatabasePath("FitGoose.db");
                            BufferedWriter writer = new BufferedWriter(new FileWriter(dbFile.getAbsoluteFile()));

                            // Write from the Drive file to the DB file
                            int c;
                            while ((c = reader.read()) != -1) {
                                writer.write(c);
                            }
                            writer.close();
                            reader.close();

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
//        switch (requestCode) {
//            case REQUEST_CODE_CAPTURE_IMAGE:
//                // Called after a photo has been taken.
//                if (resultCode == Activity.RESULT_OK) {
//                    // Store the image data as a bitmap for writing later.
//                    mBitmapToSave = (Bitmap) data.getExtras().get("data");
//                }
//                break;
//            case REQUEST_CODE_CREATOR:
//                // Called after a file is saved to Drive.
//                if (resultCode == RESULT_OK) {
//                    Log.i(TAG, "Image successfully saved.");
//                    mBitmapToSave = null;
//                    // Just start the camera again for another photo.
//                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
//                            REQUEST_CODE_CAPTURE_IMAGE);
//                }
//                break;
//        }
    }


    // From ConnectionCallbacks

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "API client connected.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "GoogleApiClient connection suspended", Toast.LENGTH_LONG).show();
    }

    // From OnConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "GoogleApiClient connection failed: " + connectionResult.toString(), Toast.LENGTH_LONG).show();
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
