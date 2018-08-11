package com.projects.android.bakingapp.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.android.bakingapp.R;
import com.projects.android.bakingapp.data.Recepie;

public class RecepieAdapter extends RecyclerView.Adapter<RecepieAdapter.RecepieAdapterViewHolder>{

    private Recepie[] mRecepies;
    private final RecepieAdapterOnClickHandler mClickHandler;

    public RecepieAdapter(RecepieAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public void setRecepies(Recepie[] recepies){
        mRecepies = recepies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecepieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recepie_item, parent, false);
        return new RecepieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecepieAdapterViewHolder holder, int position) {
        Recepie recepie = mRecepies[holder.getAdapterPosition()];
        TextView mRecepieTitle = holder.mRecepieTitle;
        String recepieName = recepie.getName();
        mRecepieTitle.setText(recepieName);
    }

    @Override
    public int getItemCount() {
        return mRecepies == null ? 0 : mRecepies.length;
    }

    protected class RecepieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView mRecepieTitle;
        public RecepieAdapterViewHolder(View view) {
            super(view);
            mRecepieTitle = view.findViewById(R.id.tv_recepie_card_text);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Recepie currentRecepie = mRecepies[getAdapterPosition()];
            mClickHandler.onClick(currentRecepie);
        }
    }

    public interface RecepieAdapterOnClickHandler{
        void onClick(Recepie movie);
    }
}
