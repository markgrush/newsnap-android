package com.newsnap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.newsnap.R;
import com.newsnap.adapters.ThreadListRecyclerViewAdapter;
import com.newsnap.adapters.ThreadRecyclerViewAdapter;
import com.newsnap.endpoint.NewsnapEndpoint;
import com.newsnap.items.ThreadPost;
import com.newsnap.services.NewsnapService;
import com.newsnap.services.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ThreadActivity extends AppCompatActivity {

    public static final String EXTRA_THREAD_ID = "com.newsnap.activities.ThreadActivity.EXTRA_THREAD_ID";

    private Endpoint endpoint = null;
    private NewsnapService newsnapService = null;

    @Bind(R.id.thread_recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private ThreadRecyclerViewAdapter threadRecyclerViewAdapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private List<ThreadPost> dataForRecyclerView = null;
    private String threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListData();
            }
        });

        getDataFromIntent();
        createEndpoint();
        createNewsnapService();
        createList();
        updateListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // if we come back from submitting a reply, then we want to update the list
        // to see the new reply right away, and not have to manually refresh it
        if (dataForRecyclerView != null) {
            updateListData();
        }
    }

    public void getDataFromIntent() {
        Intent intent = getIntent();
        threadId = intent.getStringExtra(ThreadListRecyclerViewAdapter.EXTRA_THREAD_ID);
    }

    public void createEndpoint() {
        endpoint = new NewsnapEndpoint();
    }

    public void createNewsnapService() {
        newsnapService = ServiceGenerator.createService(
                NewsnapService.class, endpoint);
    }

    public void createList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void updateListData() {
        if (newsnapService != null) {
            newsnapService.getThread(threadId, new Callback<List<ThreadPost>>() {
                @Override
                public void success(List<ThreadPost> threads, Response response) {

                    dataForRecyclerView = new ArrayList<ThreadPost>();

                    if (threadRecyclerViewAdapter == null) {
                        // init recycler view and provide it with the newly acquired data
                        threadRecyclerViewAdapter = new ThreadRecyclerViewAdapter(dataForRecyclerView);
                        recyclerView.setAdapter(threadRecyclerViewAdapter);
                    } else {
                        threadRecyclerViewAdapter.updateList(dataForRecyclerView);
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(getClass().getSimpleName(), "update failed " + error.getMessage());
                }
            });
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.refresh_thread_button)
    public void onClickedRefresh(View view) {
        updateListData();
    }

    @OnClick(R.id.new_reply_button)
    public void onClickedNewReply(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, NewReplyActivity.class);
        intent.putExtra(EXTRA_THREAD_ID, threadId);
        context.startActivity(intent);

    }
}
