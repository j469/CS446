package com.fitgoose.fitgoosedemo.utilities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fitgoose.fitgoosedemo.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;

/**
 * Created by YuFan on 7/18/15.
 */
public class YouTubeDialog extends DialogFragment implements OnInitializedListener{
    private String videoID;
    private String title;
    private static final String API_KEY = "AIzaSyAYPtJyvEFBlH6XT8b3pDp4nGrR73S8Ivw";
    private static YouTubePlayer player;

    private static View rootView;
    private boolean firstInit = false;

    public static YouTubeDialog newInstance(String title, String videoID) {
        YouTubeDialog newDialog = new YouTubeDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("videoID", videoID);

        newDialog.setArguments(args);
        newDialog.firstInit = true;

        return  newDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        videoID = getArguments().getString("videoID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent != null) {
                parent.removeView(rootView);
            }
        }
        try {
            rootView = inflater.inflate(R.layout.dialog_youtube, container);
        } catch (InflateException e) {
            e.printStackTrace();
        }

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView titleView = (TextView) rootView.findViewById(R.id.title_view);
        titleView.setText(title);

        // firstInit is only set to true when onCreate is called during the dialog's first creation
        // firstInit will be false for onCreateView call caused by by rotating and returning from full screen
        if(firstInit && player != null) {
            // When the dialog is first created, it needs to release the previous player so
            // the YouTubePlayerFragment can initialize
            player.release();
        }

        YouTubePlayerFragment youtubeFragment =(YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        youtubeFragment.initialize(API_KEY, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getDialog().getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(lp);
    }


    /* OnInitializedListener methods */

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        player = youTubePlayer;
        if (!wasRestored) {
            player.loadVideo(videoID);
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
    }
}
