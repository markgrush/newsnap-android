package com.newsnap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsnap.R;
import com.newsnap.items.*;

/**
 * Created by Mark on 2/10/2015.
 */
public class ThreadRecyclerViewAdapter
        extends RecyclerView.Adapter<ThreadRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        public ViewHolder(View v) {

            super(v);
            mLinearLayout = (LinearLayout) v;
        }
    }

    private ThreadPost[] mDataset;

    public ThreadRecyclerViewAdapter(ThreadPost[] mDataset) {

        this.mDataset = mDataset;
    }

    @Override
    public ThreadRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_threadpost_item, viewGroup, false);

        ViewHolder vh = new ThreadRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String name = mDataset[i].getName();
        String email = mDataset[i].getEmail();
        String createdAt = mDataset[i].getCreatedAt();
        String body = mDataset[i].getBody();

        if (name.equals("")) {
            name = "Anonymous";
        }

        ((TextView) viewHolder.mLinearLayout.findViewById(R.id.text_view_info))
                .setText(name + " - " + email + " * " + createdAt);

        ((TextView)viewHolder.mLinearLayout.findViewById(R.id.text_view_body))
                .setText(body);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public void updateList(ThreadPost[] mDataset) {
        this.mDataset = mDataset;
        notifyDataSetChanged();
    }
}
