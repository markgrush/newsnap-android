package com.newsnap;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mark on 1/24/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Thread[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        public ViewHolder(View v) {

            super(v);
            mLinearLayout = (LinearLayout) v;
        }
    }

    public MyRecyclerViewAdapter(Thread[] mDataset) {

        this.mDataset = mDataset;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item, viewGroup, false);

        ViewHolder vh = new MyRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        ((TextView)viewHolder.mLinearLayout.findViewById(R.id.text_view_info))
                .setText(mDataset[i].getName() + " - "
                        + mDataset[i].getEmail() + " * "
                        + mDataset[i].getCreatedAt());

        ((TextView)viewHolder.mLinearLayout.findViewById(R.id.text_view_body))
                .setText(mDataset[i].getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateList(Thread[] mDataset) {
        this.mDataset = mDataset;
        notifyDataSetChanged();
    }

}
