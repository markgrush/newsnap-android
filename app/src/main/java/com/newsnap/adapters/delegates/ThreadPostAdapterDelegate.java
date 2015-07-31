package com.newsnap.adapters.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.newsnap.R;
import com.newsnap.items.ThreadPost;

import java.util.List;

/**
 * Created by umgrushe on 15-07-31.
 */
public class ThreadPostAdapterDelegate extends AbsAdapterDelegate<List<ThreadPost>> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        public ViewHolder(View view) {

            super(view);
            mLinearLayout = (LinearLayout) view;
        }
    }

    public ThreadPostAdapterDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public boolean isForViewType(List<ThreadPost> threadPosts, int i) {
        /**
         * any item that is not first is a regular post
         */
        return i > 0;
    }

    @NonNull
    @Override
    public ThreadPostAdapterDelegate.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_threadpost_item, viewGroup, false);

        ViewHolder viewHolder = new ThreadPostAdapterDelegate.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadPost> threadPosts, int i,
                                 @NonNull RecyclerView.ViewHolder viewHolder) {

    }
}
