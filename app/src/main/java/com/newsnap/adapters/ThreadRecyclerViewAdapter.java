package com.newsnap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsnap.R;
import com.newsnap.items.*;

import org.w3c.dom.Text;

import rx.Observable;
import rx.functions.Func1;

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

        // first post of every thread should contain a title.
        // here we insert the title into the first post, inflated from a separate view.
        if (i == 0) {

            Observable.just(viewHolder.mLinearLayout.getContext())
                    .map(context -> (LayoutInflater)
                            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .map(inflater -> inflater.inflate(R.layout.threadpost_op_title, null))
                    .map(view -> (TextView) view.findViewById(R.id.op_title))
                    .map(title -> {
                        title.setText(mDataset[i].getTitle());
                        return title;
                    })
                    .subscribe(title -> {
                        ((LinearLayout) viewHolder.mLinearLayout
                                .findViewById(R.id.threadpost_body_layout))
                                .addView(title.getRootView(), 0);
                    });
        }

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
