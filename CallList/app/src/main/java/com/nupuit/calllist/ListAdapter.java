package com.nupuit.calllist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelClass> modelClass;

    public ListAdapter(Context context, ArrayList<ModelClass> modelClass) {
        this.context = context;
        this.modelClass = modelClass;
    }

    public void setData(ArrayList<ModelClass> modelClass){
        this.modelClass = modelClass;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final ModelClass model = modelClass.get(position);


        holder.name.setText(model.getName());

        holder.number.setText(model.getNumber());


    }

    @Override
    public int getItemCount() {
        return modelClass.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder {



        @Bind(R.id.row_name)
        TextView name;

        @Bind(R.id.row_number)
        TextView number;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }

}
