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
            mIsPhone = savedInstanceState.getBoolean(getString(R.string.is_phone));
            Parcelable[] parcel = savedInstanceState.getParcelableArray(getString(R.string.ingredients_list));
            if(parcel!=null){
                mIngredients = new Ingredient[parcel.length];
                System.arraycopy(parcel, 0, mIngredients, 0, parcel.length);
            }
        }
        FragmentStepDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        View root = binding.getRoot();
        if(mDetail !=null){
            displayDescription(binding);
            displayVideoIfUrlExists(binding);
        }

        if(mIngredients!=null){
            displayIngredients(binding);
        }

        return root;
    }

    private void displayIngredients(FragmentStepDetailBinding binding) {
        binding.tvIngredientsList.setVisibility(View.VISIBLE);
        String ingredients = "";
        for(Ingredient i : mIngredients){
            ingredients+= i.getQuantity()+" "+i.getMeasure()+" "+i.getIngredient()+"\n";
        }
        binding.tvIngredientsList.setText(ingredients);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.step_details), mDetail);
        outState.putBoolean(getString(R.string.is_phone), mIsPhone);
        outState.putParcelableArray(getString(R.string.ingredients_list),mIngredients);
        long position = 0;
        if(mExoPlayer!=null){
            position= mExoPlayer.getCurrentPosition();
        }
        outState.putLong(getString(R.string.video_position), position);
    }

}
