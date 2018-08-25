package com.projects.android.bakingapp;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.projects.android.bakingapp.data.Step;
import com.projects.android.bakingapp.databinding.FragmentStepDetailBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailFragment extends Fragment {
    private Step mDetail;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long mPosition = 0;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public void setDetail(Step detail){
        mDetail = detail;
    }

    private boolean validVideoUrl(String url){
        return url!=null && !url.isEmpty();
    }

    private void displayVideoPlayer(FragmentStepDetailBinding binding, String url){
        Uri uri = Uri.parse(url);
        mPlayerView = binding.playerView;
        mPlayerView.setVisibility(View.VISIBLE);
        initializePlayer(uri);
    }

    private void displayVideoIfUrlExists(FragmentStepDetailBinding binding) {

        if(validVideoUrl(mDetail.getVideoURL())){
            displayVideoPlayer(binding, mDetail.getVideoURL());
        }else if(validVideoUrl(mDetail.getThumbnailURL())){
            displayVideoPlayer(binding, mDetail.getThumbnailURL());
        }
    }

    private void displayDescription(FragmentStepDetailBinding binding) {
        TextView descriptionTextView = binding.tvStepDetail;
        descriptionTextView.setVisibility(View.VISIBLE);
        descriptionTextView.setText(mDetail.getDescription());
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            Context context = getContext();
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(mPosition);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState!=null){
            mDetail = savedInstanceState.getParcelable(getString(R.string.step_details));
            mPosition = savedInstanceState.getLong(getString(R.string.video_position));
        }
        FragmentStepDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        View root = binding.getRoot();
        if(mDetail !=null){
            displayDescription(binding);
            displayVideoIfUrlExists(binding);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.step_details), mDetail);
        long position = 0;
        if(mExoPlayer!=null){
            position= mExoPlayer.getCurrentPosition();
        }
        outState.putLong(getString(R.string.video_position), position);
    }

}
