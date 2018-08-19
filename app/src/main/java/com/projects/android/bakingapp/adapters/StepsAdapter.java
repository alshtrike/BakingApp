package com.projects.android.bakingapp.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.bakingapp.R;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>{

    private String[] mData;
    private final StepsAdapterOnClickHandler mClickHandler;

    public StepsAdapter(StepsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_item, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
        String title = mData[holder.getAdapterPosition()];
        TextView stepTitle = holder.mStepTitle;
        stepTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
    }

    public void setSteps(String[] steps){
        mData = steps;
        notifyDataSetChanged();
    }

    protected class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView mStepTitle;

        public StepsAdapterViewHolder(View view) {
            super(view);
            mStepTitle = view.findViewById(R.id.tv_steps_item);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickHandler.onClick(position);
        }
    }

    public interface StepsAdapterOnClickHandler{
        void onClick(int adapterPosition);
    }
}
