package com.newsnap.adapters.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.adapterdelegates.AbsAdapterDelegate;
import com.hannesdorfmann.adapterdelegates.AdapterDelegate;
import com.newsnap.R;
import com.newsnap.items.ThreadPost;

import java.util.List;

/**
 * Created by umgrushe on 15-07-31.
 */
public class ThreadFirstPostAdapterDelegate extends AbsAdapterDelegate<List<ThreadPost>> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout mLinearLayout;

        public ViewHolder(View view) {

            super(view);
            mLinearLayout = (LinearLayout) view;
        }
    }

    public ThreadFirstPostAdapterDelegate(int viewType) {
        super(viewType);
    }

    @Override
    public boolean isForViewType(@NonNull List<ThreadPost> threadPosts, int i) {
        /**
         * since the first post (and consequently the first item in the list) is the one with
         * a title, then the current threadPost is a VIEW_TYPE_FIRST_POST type
         */
        return i == 0;
    }

    @NonNull
    @Override
    public ThreadFirstPostAdapterDelegate.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.threadpost_first, viewGroup, false);

        ViewHolder viewHolder = new ThreadFirstPostAdapterDelegate.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull List<ThreadPost> threadPosts, int i,
                                 @NonNull RecyclerView.ViewHolder viewHolder) {
        String name = threadPosts.get(i).getName();
        String email = threadPosts.get(i).getEmail();
        String createdAt = threadPosts.get(i).getCreatedAt();
        String title = threadPosts.get(i).getTitle();
        String body = threadPosts.get(i).getBody();

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
}
