package com.projects.android.bakingapp.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.bakingapp.R;
import com.projects.android.bakingapp.data.Step;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>{

    private Step[] mSteps;

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.step_item, parent, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder holder, int position) {
        Step step = mSteps[holder.getAdapterPosition()];
        TextView stepTitle = holder.mStepTitle;
        String recipeName = step.getShortDescription();
        stepTitle.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.length;
    }

    public void setSteps(Step[] steps){
        mSteps = steps;
        notifyDataSetChanged();
    }
    protected class StepsAdapterViewHolder extends RecyclerView.ViewHolder{

        final TextView mStepTitle;

        public StepsAdapterViewHolder(View view) {
            super(view);
            mStepTitle = view.findViewById(R.id.tv_steps_item);

        }
    }
}
