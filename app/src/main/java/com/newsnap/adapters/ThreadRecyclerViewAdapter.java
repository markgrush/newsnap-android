package com.newsnap.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.adapterdelegates.ListDelegationAdapter;
import com.newsnap.R;
import com.newsnap.adapters.delegates.ThreadFirstPostAdapterDelegate;
import com.newsnap.adapters.delegates.ThreadPostAdapterDelegate;
import com.newsnap.items.*;

import java.util.List;

/**
 * Created by Mark on 2/10/2015.
 */
public class ThreadRecyclerViewAdapter extends
        ListDelegationAdapter<List<ThreadPost>> {

    public final int VIEW_TYPE_FIRST_POST = 0;
    public final int VIEW_TYPE_POST = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        public ViewHolder(View view) {

            super(view);
            mLinearLayout = (LinearLayout) view;
        }
    }

    public ThreadRecyclerViewAdapter(List<ThreadPost> data) {
        this.delegatesManager.addDelegate(new ThreadFirstPostAdapterDelegate(VIEW_TYPE_FIRST_POST));
        this.delegatesManager.addDelegate(new ThreadPostAdapterDelegate(VIEW_TYPE_POST));
        setItems(data);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
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
                    .map(new Func1<Context, LayoutInflater>() {
                        @Override
                        public LayoutInflater call(Context context) {
                            return (LayoutInflater) context.getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);
                        }
                    })
                    .map(new Func1<LayoutInflater, View>() {
                        @Override
                        public View call(LayoutInflater layoutInflater) {
                            return layoutInflater.inflate(R.layout.threadpost_op_title, null);
                        }
                    })
                    .map(new Func1<View, TextView>() {
                        @Override
                        public TextView call(View view) {
                            return (TextView) view.findViewById(R.id.op_title);
                        }
                    })
                    .map(new Func1<TextView, TextView>() {
                        @Override
                        public TextView call(TextView textView) {
                            textView.setText(mDataset[i].getTitle());
                            return textView;
                        }
                    })
                    .subscribe(new Action1<TextView>() {
                        @Override
                        public void call(TextView textView) {
                            ((LinearLayout) viewHolder.mLinearLayout
                                    .findViewById(R.id.threadpost_body_layout))
                                    .addView(textView.getRootView(), 0);
                        }
                    });
        }

        ((TextView)viewHolder.mLinearLayout.findViewById(R.id.text_view_body))
                .setText(body);
    }


    public void updateList(List<ThreadPost> threadPosts) {
        setItems(threadPosts);
        notifyDataSetChanged();
    }
}
