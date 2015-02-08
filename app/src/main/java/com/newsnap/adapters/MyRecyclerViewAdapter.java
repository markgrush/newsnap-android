package com.newsnap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsnap.*;
import com.newsnap.items.Thread;

/**
 * Created by Mark on 1/24/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final String EXTRA_THREAD_ID = "com.newsnap.adapters.EXTRA_THREAD_ID";
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

        String id = mDataset[i].getThreadId();
        String name = mDataset[i].getName();
        String email = mDataset[i].getEmail();
        String createdAt = mDataset[i].getCreatedAt();
        String title = mDataset[i].getTitle();

        if (name.equals("")) {
            name = "Anonymous";
        }

        ((TextView) viewHolder.mLinearLayout.findViewById(R.id.text_view_info))
                .setText(name + " - " + email + " * " + createdAt);

        ((TextView)viewHolder.mLinearLayout.findViewById(R.id.text_view_body))
                .setText(title);

        ((Button)viewHolder.mLinearLayout.findViewById(R.id.view_thread_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.v(getClass().getSimpleName(), "Clicked view button of thread " + id);

                        Context context = v.getContext();
                        Intent intent = new Intent(context, ThreadActivity.class);
                        intent.putExtra(EXTRA_THREAD_ID, id);
                        context.startActivity(intent);
                    }
                });
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
