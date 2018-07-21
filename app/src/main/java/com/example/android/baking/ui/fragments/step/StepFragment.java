package com.example.android.baking.ui.fragments.step;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.model.BakingStep;
import com.example.android.baking.utilities.NetworkUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.content.Context.NOTIFICATION_SERVICE;

public class StepFragment extends Fragment
        implements ExoPlayer.EventListener {

    private static final String TAG = StepFragment.class.getSimpleName();

    private boolean mTwoPane;

    private BakingStep mBakingStep;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private NotificationManager mNotificationManager;

    private StepFragmentSaveInstanceListener mListener;

    private long mPlayerLastPosition = -1;
    private boolean mPlayWhenReadyState;
    private Uri mUri;

    public void setTwoPane(boolean aTwoPane) {
        mTwoPane = aTwoPane;
    }

    public void setPlayerLastPosition(long aPlayerLastPosition) {
        mPlayerLastPosition = aPlayerLastPosition;
    }

    public void setPlayWhenReadyState(boolean aPlayWhenReadyState) {
        mPlayWhenReadyState = aPlayWhenReadyState;
    }

    public interface StepFragmentSaveInstanceListener {
        void onStepFragmentSaveInstance(long aPlayerPosition, boolean aPlayWhenReadyState);
    }


    public void setBakingStep(BakingStep aBakingStep) {
        mBakingStep = aBakingStep;
    }

    public StepFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_baking_step, container, false);
        mPlayerView = rootView.findViewById(R.id.baking_step_epv);

        if (mBakingStep != null) {

            mUri = Uri.parse(mBakingStep.getVideoURL());

            TextView bakingStepTextView = rootView.findViewById(R.id.baking_step_tv);
            bakingStepTextView.setText(mBakingStep.getDescription());


            // Setting the Step text view to GONE and making Exo player view full screen

            if (!mTwoPane) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mPlayerView.setSystemUiVisibility(SimpleExoPlayerView.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | SimpleExoPlayerView.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | SimpleExoPlayerView.SYSTEM_UI_FLAG_FULLSCREEN);
                    bakingStepTextView.setVisibility(View.GONE);
                } else {
                    bakingStepTextView.setVisibility(View.VISIBLE);
                }
            }

            // initializing player is done in onStart/onResume

        }
        return rootView;

    }

    private void initializeMediaSessionAndPlayer() {
        if (NetworkUtils.isOnline(getContext())) {
            initializeMediaSession();
            initializePlayer(mUri);
        } else {
            NetworkUtils.showNoNetworkToast(getContext());
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher));

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            if (mPlayerLastPosition > -1) {
                mExoPlayer.seekTo(mPlayerLastPosition);
            }

            mExoPlayer.setPlayWhenReady(mPlayWhenReadyState);
        }
    }


    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    private void showNotification(PlaybackStateCompat state) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());

        int icon;
        String play_pause;
        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = getString(R.string.play);
        }


        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(getContext(),
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new android.support.v4.app.NotificationCompat
                .Action(R.drawable.exo_controls_previous, getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (getContext(), PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (getContext(), 0, new Intent(getContext(), StepFragment.class), 0);

        builder.setContentTitle(getString(R.string.guess))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_music_note)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(0, 1));


        mNotificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        mMediaSession.setActive(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (StepFragmentSaveInstanceListener) context;
        } catch (ClassCastException aE) {
            throw new ClassCastException(context.toString()
                    + " must implement StepFragmentSaveInstanceListener");
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeMediaSessionAndPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializeMediaSessionAndPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mListener.onStepFragmentSaveInstance(mExoPlayer.getCurrentPosition(), mExoPlayer.getPlayWhenReady());
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    // ExoPlayer Event Listeners - BEGIN

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
        showNotification(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    // ExoPlayer Event Listeners - END


    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }
}
