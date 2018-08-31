package com.projects.android.bakingapp;


import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.projects.android.bakingapp.data.Ingredient;
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
    private boolean mIsPhone = true;
    private Ingredient[] mIngredients;
    private boolean mIsPlaying = false;
    private FragmentStepDetailBinding mBinding;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public void setDetail(Step detail){
        mDetail = detail;
    }

    public void setIngredients(Ingredient[] ingredients){
        mIngredients = ingredients;
    }

    public void setIsPhone(boolean isPhone){
        mIsPhone = isPhone;
    }

    private boolean validVideoUrl(String url){
        return url!=null && !url.isEmpty();
    }

    private void displayVideoPlayer(FragmentStepDetailBinding binding, String url){
        Uri uri = Uri.parse(url);
        mPlayerView = binding.playerView;
        if(videoShouldBeFullScreen()){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.height = getHeightForLandscapeMode();
            mPlayerView.setLayoutParams(params);
        }
        mPlayerView.setVisibility(View.VISIBLE);
        initializePlayer(uri);
    }

    private int getHeightForLandscapeMode() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return height < width ? height : width;
    }

    private boolean videoShouldBeFullScreen() {
        return mIsPhone && isInLandscapeMode();
    }

    private boolean isInLandscapeMode() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void displayVideoIfUrlExists() {

        if(validVideoUrl(mDetail.getVideoURL())){
            displayVideoPlayer(mBinding, mDetail.getVideoURL());
        }else if(validVideoUrl(mDetail.getThumbnailURL())){
            displayVideoPlayer(mBinding, mDetail.getThumbnailURL());
        }
    }

    private void displayDescription() {
        TextView descriptionTextView = mBinding.tvStepDetail;
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
            mExoPlayer.setPlayWhenReady(mIsPlaying);
        }
    }

    private void releasePlayer() {
        if(mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void displayIngredients() {
        mBinding.tvIngredientsList.setVisibility(View.VISIBLE);
        String ingredients = "";
        for(Ingredient i : mIngredients){
            ingredients += i.getQuantity()+" "+i.getMeasure()+" "+i.getIngredient()+"\n";
        }
        mBinding.tvIngredientsList.setText(ingredients);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState!=null){
            mDetail = savedInstanceState.getParcelable(getString(R.string.step_details));
            mPosition = savedInstanceState.getLong(getString(R.string.video_position));
            mIsPhone = savedInstanceState.getBoolean(getString(R.string.is_phone));
            mIsPlaying = savedInstanceState.getBoolean(getString(R.string.is_playing));
            Parcelable[] parcel = savedInstanceState.getParcelableArray(getString(R.string.ingredients_list));
            if(parcel!=null){
                mIngredients = new Ingredient[parcel.length];
                System.arraycopy(parcel, 0, mIngredients, 0, parcel.length);
            }
        }
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        View root = mBinding.getRoot();
        if(mDetail !=null){
            displayDescription();
        }

        if(mIngredients!=null){
            displayIngredients();
        }

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayerView==null)) {
            if(mDetail!=null){
                displayVideoIfUrlExists();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && mDetail!=null) {
            displayVideoIfUrlExists();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.step_details), mDetail);
        outState.putBoolean(getString(R.string.is_phone), mIsPhone);
        outState.putBoolean(getString(R.string.is_playing), mExoPlayer.getPlayWhenReady());
        outState.putParcelableArray(getString(R.string.ingredients_list),mIngredients);
        long position = 0;
        if(mExoPlayer!=null){
            position= mExoPlayer.getCurrentPosition();
        }
        outState.putLong(getString(R.string.video_position), position);
    }

}
